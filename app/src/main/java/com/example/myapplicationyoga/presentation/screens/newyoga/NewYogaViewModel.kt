package com.example.myapplicationyoga.presentation.screens.newyoga

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationyoga.data.models.Profile
import com.example.myapplicationyoga.data.models.category
import com.example.myapplicationyoga.data.models.yogaclasses
import com.example.myapplicationyoga.domain.Constant
import com.example.myapplicationyoga.domain.Constant.supabase
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.domain.states.SignUpState
import com.example.myapplicationyoga.domain.states.YogaState
import com.example.myapplicationyoga.domain.utils.ValidEmail
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class NewYogaViewModel: ViewModel() {
    private val _state = mutableStateOf(YogaState())
    val state: YogaState get() = _state.value

    private val _categories = MutableLiveData<List<category>>()
    val categories: LiveData<List<category>> get() = _categories

    private val _uiState = mutableStateOf(YogaState())
    val UiState: YogaState get() = _uiState.value


    private val _resultState = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStates: StateFlow<ResultStates> = _resultState.asStateFlow()

    fun updateState(newState: YogaState) {
        _uiState.value = newState
        _resultState.value = ResultStates.Initialized
    }
    init {
        loadCategories()
    }
    private fun loadCategories() {
        viewModelScope.launch {
            try {
                _categories.value =
                    supabase.postgrest.from("category").select().decodeList<category>()
                Log.d("loadCategories", "Success")
                Log.d("loadCategories", _categories.toString())

            } catch (_ex: AuthRestException) {
                Log.d("loadCategories", _ex.message.toString())
                Log.d("loadCategories", _ex.errorCode.toString())
            }
        }
    }
    fun SignUp() {
        _resultState.value = ResultStates.Loading

                viewModelScope.launch {
                    try {

                        Log.d("SignUp", "Success")
                        val id = UUID.randomUUID().toString()
                        val yoga = yogaclasses(null, _uiState.value.name,_uiState.value.description,_uiState.value.category,
                            _uiState.value.price, id
                        )
                        supabase.from("yogaclasses").insert(yoga)
                        _resultState.value = ResultStates.Success("Success SignUp")
                    } catch (_ex: AuthRestException) {
                        Log.d("SignUp", _ex.message.toString())
                        _resultState.value = ResultStates.Error("Ошибка авторизации")
                    }
                }
            }


        }

