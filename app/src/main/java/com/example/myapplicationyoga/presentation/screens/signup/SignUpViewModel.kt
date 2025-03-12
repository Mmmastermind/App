package com.example.myapplicationyoga.presentation.screens.signup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationyoga.data.models.Profile
import com.example.myapplicationyoga.domain.Constant
import com.example.myapplicationyoga.domain.Constant.supabase
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.domain.states.SignInState
import com.example.myapplicationyoga.domain.states.SignUpState
import com.example.myapplicationyoga.domain.utils.ValidEmail
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel: ViewModel() {
    private val _uiState = mutableStateOf(SignUpState())
    val UiState: SignUpState get() = _uiState.value


    private val _resultState = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStates: StateFlow<ResultStates> = _resultState.asStateFlow()

    fun updateState(newState: SignUpState) {
        _uiState.value = newState
        _uiState.value.errorEmail = _uiState.value.email.ValidEmail()
        _resultState.value = ResultStates.Initialized
    }


    fun SignUp() {
        _resultState.value = ResultStates.Loading
        if (_uiState.value.errorEmail) {
            if(_uiState.value.password == _uiState.value.confirmpassword) {
                viewModelScope.launch {
                    try {
                        Constant.supabase.auth.signUpWith(Email) {
                            email = _uiState.value.email
                            password = _uiState.value.password
                        }


                        Log.d("SignUp", "Success")
                        val CurrentUser = Constant.supabase.auth.currentUserOrNull()
                        val user = Profile(null,_uiState.value.birthday, _uiState.value.username, _uiState.value.surname,
                            CurrentUser?.id
                        )
                        supabase.from("profile").insert(user)
                        _resultState.value = ResultStates.Success("Success SignUp")
                    } catch (_ex: AuthRestException) {
                        Log.d("SignUp", _ex.message.toString())
                        _resultState.value = ResultStates.Error("Ошибка авторизации")
                    }
                }
            }

            else{_resultState.value = ResultStates.Error("Пароли не совпадают")}
        } else {
            _resultState.value = ResultStates.Error("Неверный формат почты")
        }
    }
}


