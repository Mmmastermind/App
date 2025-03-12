package com.example.myapplicationyoga.presentation.screens.signin

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationyoga.domain.Constant
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.domain.states.SignInState
import com.example.myapplicationyoga.domain.utils.ValidEmail
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel: ViewModel() {
    private val _uiState = mutableStateOf(SignInState())
    val UiState: SignInState get() = _uiState.value


    private val _resultState = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStates: StateFlow<ResultStates> = _resultState.asStateFlow()

    fun updateState(newState: SignInState) {
        _uiState.value = newState
        _uiState.value.errorEmail = _uiState.value.email.ValidEmail()
        _resultState.value = ResultStates.Initialized
    }

    fun SignIn() {
        _resultState.value = ResultStates.Loading
        if (_uiState.value.errorEmail) {
            viewModelScope.launch {
                try {
                    Constant.supabase.auth.signInWith(Email){
                        email = _uiState.value.email
                        password = _uiState.value.password
                    }
                    Log.d("SignIn", "Success")
                    _resultState.value = ResultStates.Success("Success SignIn")
                } catch (_ex:AuthRestException){
                    Log.d("SignIn", _ex.message.toString())
                    _resultState.value = ResultStates.Error("Ошибка регистрации")
                }
            }
        } else {
_resultState.value = ResultStates.Error("Неверный формат почты")
        }
        }
    }


