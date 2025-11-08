package com.tving.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tving.core.common.model.NetworkError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

abstract class BaseViewModel<State : BaseContract.UiState, Event : BaseContract.Event, SideEffect : BaseContract.SideEffect> : ViewModel() {

    // UI 상태
    protected abstract val _state: MutableStateFlow<State>
    val state: StateFlow<State> get() = _state.asStateFlow()
    private val currentUiState: State
        get() = state.value

    // 사이드 이펙트 (네비게이션 등)
    private val _sideEffect: Channel<SideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    // 이벤트 처리
    abstract suspend fun handleEvent(event: Event)

    // 사이드 이펙트 설정
    protected fun postSideEffect(effect: SideEffect) {
        viewModelScope.launch {
            _sideEffect.send(effect)
        }
    }

    protected fun handleError(throwable: Throwable) {
        val message = when (throwable) {
            is com.tving.core.common.exception.NetworkException -> {
                when (throwable.error) {
                    is NetworkError.RateLimitExceeded -> {
                        "API 호출 한도를 초과했습니다. 잠시 후 다시 시도해주세요."
                    }
                    is NetworkError.NetworkTimeout -> {
                        "네트워크 연결이 지연되고 있습니다. 다시 시도해주세요."
                    }
                    is NetworkError.NetworkUnavailable -> {
                        "인터넷 연결을 확인해주세요."
                    }
                    is NetworkError.Unauthorized -> {
                        "인증에 실패했습니다."
                    }
                    is NetworkError.Forbidden -> {
                        "접근 권한이 없습니다."
                    }
                    is NetworkError.NotFound -> {
                        "요청하신 리소스를 찾을 수 없습니다."
                    }
                    is NetworkError.BadRequest -> {
                        "잘못된 요청입니다."
                    }
                    is NetworkError.ServerError -> {
                        val error = throwable.error
                        "서버 오류가 발생했습니다. (${error.code})"
                    }
                    is NetworkError.Unknown -> {
                        throwable.error.message
                    }
                }
            }
            else -> {
                throwable.message ?: "알 수 없는 오류가 발생했습니다."
            }
        }
        
        @Suppress("UNCHECKED_CAST")
        postSideEffect(BaseContract.CommonSideEffect.ShowToast(message) as SideEffect)
    }

    /**
     * action이 발생하면 event 전달
     */
    fun intent(event: Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    /**
     * action이 발생하면 event 전달 - Throttle 처리
     */
    fun intentThrottle(event: Event) {
        handleEventWithThrottle {
            handleEvent(event)
        }
    }

    /**
     * reduce : 상태 값 Update
     */
    protected fun reduce(reduce: State.() -> State) {
        val state = currentUiState.reduce()
        _state.value = state
    }

    // Throttle 설정을 위한 변수
    private val lastEventExecutionTime = ConcurrentHashMap<String, Long>()

    private fun executeWithThrottle(
        eventId: String,
        throttleTime: Long = 800L,
        action: suspend () -> Unit
    ) {
        val currentTime = System.currentTimeMillis()
        val lastExecutionTime = lastEventExecutionTime[eventId] ?: 0L

        if (currentTime - lastExecutionTime >= throttleTime) {
            lastEventExecutionTime[eventId] = currentTime

            viewModelScope.launch {
                action()

                delay(throttleTime)
                if (lastEventExecutionTime[eventId] == currentTime) {
                    lastEventExecutionTime.remove(eventId)
                }
            }
        }
    }

    private fun handleEventWithThrottle(
        throttleTime: Long = 1000L,
        action: suspend () -> Unit
    ) {
        val eventId = "action_${System.currentTimeMillis()}"
        executeWithThrottle(eventId, throttleTime, action)
    }
}