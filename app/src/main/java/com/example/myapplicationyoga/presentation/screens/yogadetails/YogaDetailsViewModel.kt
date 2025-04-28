package com.example.myapplicationyoga.presentation.screens.yogadetails


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationyoga.data.models.category
import com.example.myapplicationyoga.data.models.yogaclasses
import com.example.myapplicationyoga.domain.Constant.supabase
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.domain.states.YogaState
import io.github.jan.supabase.gotrue.exception.AuthRestException
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class YogaDetailsViewMode(id: String) : ViewModel() {
    var idYoga = id

    private val _resultStateUpdate = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStateUpdate: StateFlow<ResultStates> = _resultStateUpdate.asStateFlow()

    private val _resultStateDelete = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStateDelete: StateFlow<ResultStates> = _resultStateDelete.asStateFlow()

    private val _resultStateUpload = MutableStateFlow<ResultStates>(ResultStates.Initialized)
    val resultStateUpload: StateFlow<ResultStates> = _resultStateUpload.asStateFlow()

    lateinit var yoga: yogaclasses

    private val _categories = MutableLiveData<List<category>>()
    val categories: LiveData<List<category>> get() = _categories

    private val _selectedImageUrl = MutableStateFlow<Uri?>(null)
    val selectedImageUrl: StateFlow<Uri?> get() = _selectedImageUrl


    fun updateState(newState: YogaState) {
        _state.value = newState
    }

    private val _state = mutableStateOf(YogaState())
    val state: YogaState get() = _state.value

    init {
        loadCategories()
        getYoga()
    }

    fun getYoga() {
        _resultStateUpload.value = ResultStates.Loading
        viewModelScope.launch {
            try {
                yoga = supabase.postgrest.from("yogaclasses").select() {
                    filter {
                        eq("id", idYoga)
                    }
                }.decodeSingle<yogaclasses>()

                _state.value = YogaState(
                    id = yoga.id,
                    name = yoga.name,
                    price = yoga.price,
                    category = yoga.categoryId,
                    description = yoga.description,
                    image = yoga.image

                )
                _resultStateUpload.value = ResultStates.Success("Success")
            } catch (e: AuthRestException) {
                Log.e("getYoga", "Error loading data", e)
                _resultStateUpload.value = ResultStates.Error(e.message.toString())
            }
        }
    }

    fun updateBook() {
        _resultStateUpdate.value = ResultStates.Loading
        viewModelScope.launch {
            try {
                supabase.postgrest.from("yogaclasses").update(
                    {
                        set("name", _state.value.name)
                        set("categoryId", _state.value.category)
                        set("description", _state.value.description)
                        set("price", _state.value.price)
                    }
                ) {
                    filter {
                        eq("id", idYoga)
                    }
                }
                _resultStateUpdate.value = ResultStates.Success("Success")
            } catch (e: AuthRestException) {
                Log.e("updateBook", "Error update data", e)
                _resultStateUpdate.value = ResultStates.Error(e.message.toString())
            }
        }
    }

    fun deleteBook()
    {
        _resultStateDelete.value = ResultStates.Loading
        viewModelScope.launch {
            try {
                supabase.postgrest.from("yogaclasses").delete(
                ) {
                    filter {
                        eq("id", idYoga)
                    }
                }
                _resultStateDelete.value = ResultStates.Success("Delete")
            } catch (e: AuthRestException) {
                Log.e("deleteBook", "Error delete data", e)
                _resultStateDelete.value = ResultStates.Error(e.message.toString())
            }
        }
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

    fun selectImage(context: Context) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        Log.d("URI_LOG", "Starting image picker...")
        context.startActivity(Intent.createChooser(intent, "Select Image"))
    }

    fun uploadImageToSupabase(url: Uri?, context: Context) {
        url?.let {
            _resultStateUpload.value = ResultStates.Loading
            viewModelScope.launch {
                try {
                    val inputStream = context.contentResolver.openInputStream(url)
                    val bytes = inputStream?.readBytes() ?: byteArrayOf()

                    // Генерация уникального имени файла
                    val fileName = "${idYoga}.jpg"

                    supabase.storage
                        .from("yogaclasses") // Ваш бакет в Supabase Storage
                        .update(
                            path = fileName,
                            data = bytes,
                            upsert = true
                        )

                    // Обновляем ссылку на изображение в базе данных
                    _state.value = _state.value.copy(image = fileName)
                    updateImageInDatabase(fileName)

                    _resultStateUpload.value = ResultStates.Success("Image uploaded successfully")
                } catch (e: Exception) {
                    _resultStateUpload.value = ResultStates.Error(e.message.toString())
                }
            }
        }
    }

    private suspend fun updateImageInDatabase(fileName: String) {
        supabase.postgrest.from("yogaclasses").update(
            { set("image", fileName) }
        ) { filter { eq("id", idYoga) } }
    }




}