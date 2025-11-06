package com.tving.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    protected fun handleEventWithThrottle(
        throttleTime: Long = 800L,
        action: suspend () -> Unit
    ) {
        // 액션 함수의 해시코드로 고유 ID 생성
        val eventId = "action_${action.hashCode()}"
        executeWithThrottle(eventId, throttleTime, action)
    }
}