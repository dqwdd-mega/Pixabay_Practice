package com.pixabay.core.common.base

interface BaseContract {
    /**
     * UI 상태를 나타내는 인터페이스
     */
    interface UiState

    /**
     * 단방향 이벤트를 나타내는 인터페이스 (네비게이션, 토스트 메시지 등)
     */
    interface SideEffect

    /**
     * 사용자 액션을 나타내는 인터페이스
     */
    interface Event

    /**
     * 공통 SideEffect들
     */
    sealed interface CommonSideEffect : SideEffect {
        data class ShowToast(val message: String) : CommonSideEffect
    }
}