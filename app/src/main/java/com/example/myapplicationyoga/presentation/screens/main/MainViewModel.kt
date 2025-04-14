package com.example.myapplicationyoga.presentation.screens.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationyoga.data.models.category
import com.example.myapplicationyoga.data.models.yogaclasses
import com.example.myapplicationyoga.domain.Constant.supabase
import com.example.myapplicationyoga.domain.states.ResultStates
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel:ViewModel() {

    private val _resultState = MutableStateFlow<ResultStates>(ResultStates.Loading)
    val resultStates: StateFlow<ResultStates> = _resultState.asStateFlow()

    private val _yoga = MutableLiveData<List<yogaclasses>>()
    val yoga: LiveData<List<yogaclasses>> get() = _yoga

    private val _categories = MutableLiveData<List<category>>()
    val categories: LiveData<List<category>> get() = _categories

    private var allYoga: List<yogaclasses> = listOf()

    init{
        Log.d("MainStart", "Success")
        loadYoga()
        loadCategories()
    }

    private fun loadYoga(){
        _resultState.value = ResultStates.Loading
        viewModelScope.launch {
            try {
                allYoga = supabase.postgrest.from("yogaclasses").select().decodeList<yogaclasses>()
                _yoga.value = allYoga
                Log.d("MainYoga", "Success")
            _resultState.value = ResultStates.Success("Success")
            }
            catch (_ex:AuthRestException){
                Log.d("MainYoga", _ex.message.toString())
                _resultState.value = ResultStates.Error(_ex.error)
            }
        }
    }

    suspend fun getImage(yogaName: String):String{
        return withContext(Dispatchers.IO){
            try {
                val url = supabase.storage.from("yogaclasses").publicUrl("${yogaName}.jpeg")
                url
            } catch (ex:AuthRestException){
                Log.e("Error", "Failed to get URL")
                ""
            }
        }
    }


    private fun loadCategories(){

        viewModelScope.launch {
            try {
                val allCategories = supabase.postgrest.from("category").select().decodeList<category>()
                _categories.value = allCategories
                Log.d("MainCategory", "Success")
                Log.d("CategoriesDebug", "Categories loaded: ${_categories.value!!.size}")
                _resultState.value = ResultStates.Success("Success")
            }
            catch (_ex:AuthRestException){
                Log.d("MainCategory", _ex.message.toString())
                _resultState.value = ResultStates.Error(_ex.error)
            }
        }
    }

    fun filterList(query: String?, categoryId: Int?){
        val filteredYoga = if (query.isNullOrEmpty() && (categoryId == -1 || categoryId == null)) {
            allYoga
        } else {
            allYoga.filter { yoga ->
                val matchesName = query.isNullOrEmpty() || yoga.name.contains(
                    query,
                    ignoreCase = true
                ) || yoga.description.contains(query, ignoreCase = true)
                val matchesCategory = categoryId == -1 || yoga.categoryId == categoryId
                matchesName && matchesCategory
            }
        }
        _yoga.value = filteredYoga
    }
}