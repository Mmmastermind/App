package com.example.myapplicationyoga.domain.states

sealed class ResultStates {
    data object Loading : ResultStates()
    data object Initialized : ResultStates()
    data class Success(val message: String) : ResultStates()
    data class Error(val message: String) : ResultStates()
}