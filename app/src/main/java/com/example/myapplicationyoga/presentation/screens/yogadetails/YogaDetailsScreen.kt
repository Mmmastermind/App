package com.example.myapplicationyoga.presentation.screens.yogadetails

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.screens.components.Button
import com.example.myapplicationyoga.presentation.screens.components.TextFieldDropDown
import com.example.myapplicationyoga.presentation.screens.components.TextFieldEdit
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun YogaDetailsScreen(
    navController: NavController,
    id: String,
    viewModel: YogaDetailsViewMode = viewModel { YogaDetailsViewMode(id) },
) {
    val yogaState = viewModel.state


    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() // или OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadImageToSupabase(uri, context)
        }
        Log.d("URI_LOG", "Selected URI: $uri")
    }

    val resultStateUpdate by viewModel.resultStateUpdate.collectAsState()
    val resultStateDelete by viewModel.resultStateDelete.collectAsState()
    val resultStateUpload by viewModel.resultStateUpload.collectAsState()


    when (resultStateUpload) {
        is ResultStates.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Beige1)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = (resultStateUpload as ResultStates.Error).message)
            }
        }

        ResultStates.Initialized -> {}
        ResultStates.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ResultStates.Success -> {
            LazyColumn {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Beige1)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(
                            "Редактирование занятия",
                            color = Brown1,
                            fontSize = 33.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            lineHeight = 40.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(horizontal = 50.dp)

                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        val imgState = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data((yogaState.image))
                                .size(Size.ORIGINAL).build()
                        ).state
                        if (imgState is AsyncImagePainter.State.Error) {
                            CircularProgressIndicator()

                        }
                        if (imgState is AsyncImagePainter.State.Success) {
                            Image(

                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .clickable {
                                        imagePickerLauncher.launch("image/*")
                                    }
                                    .clip(RoundedCornerShape(15.dp)),
                                painter = imgState.painter,
                                contentDescription = "",
                                contentScale = ContentScale.FillWidth
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        Text(
                            "Наименование занятия:",
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextFieldEdit(
                            value = yogaState.name,
                            onValueChanged = { viewModel.updateState(yogaState.copy(name = it)) },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        var expanded by remember { mutableStateOf(false) }
                        val selectedCategory =
                            viewModel.categories.value?.find { it.id == yogaState.category }
                        Text(
                            "Категория занятия:",
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Box {
                            // Создаем текстовое поле с выпадающим списком, где отображается имя выбранной категории
                            TextFieldDropDown(
                                selectedCategory?.categoryname ?: "Выберите категорию"
                            ) {
                                // Устанавливаем состояние expanded (развернуто/свернуто) в зависимости от переданного параметра
                                expanded = it
                            }

                            // Создаем выпадающее меню
                            DropdownMenu(
                                expanded = expanded, // Указываем, развернуто ли меню
                                onDismissRequest = {
                                    expanded = false
                                } // Закрываем меню при нажатии вне его
                            ) {
                                // Перебираем список категорий из viewModel
                                viewModel.categories.value!!.forEach { category ->
                                    // Создаем элемент выпадающего меню для каждой категории
                                    DropdownMenuItem(
                                        text = { Text(category.categoryname) }, // Отображаем имя категории
                                        onClick = {
                                            // При нажатии на элемент устанавливаем выбранную категорию
                                            yogaState.category = category.id
                                            expanded = false // Закрываем меню
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Описание занятия:",
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextFieldEdit(
                            value = yogaState.description,
                            onValueChanged = { viewModel.updateState(yogaState.copy(description = it)) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Цена занятия:",
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        TextFieldEdit(
                            value = yogaState.price,
                            onValueChanged = { viewModel.updateState(yogaState.copy(price = it)) }
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        when (resultStateUpdate) {
                            is ResultStates.Error -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultStates.Error).message,
                                    Toast.LENGTH_SHORT
                                )
                                Button(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }

                            ResultStates.Initialized -> {
                                Button(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }

                            ResultStates.Loading -> {
                                CircularProgressIndicator()
                            }

                            is ResultStates.Success -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultStates.Success).message,
                                    Toast.LENGTH_SHORT
                                )
                                Button(
                                    label = "Сохранить изменения",
                                    onClick = {
                                        viewModel.updateBook()
                                    }
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Button(
                            label = "Вернуться назад",
                            onClick = {
                                navController.navigate(NavigationRoutes.MAIN)
                                {
                                    popUpTo(NavigationRoutes.YOGADETAILS) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                        Spacer(Modifier.height(10.dp))
                        when (resultStateDelete) {
                            is ResultStates.Error -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultStates.Error).message,
                                    Toast.LENGTH_SHORT
                                )
                                Button(
                                    label = "Удалить занятие",
                                    onClick = {
                                        viewModel.deleteBook()
                                    }
                                )
                            }

                            ResultStates.Initialized -> {
                                Button(
                                    label = "Удалить занятие",
                                    onClick = {
                                        viewModel.deleteBook()
                                    }
                                )
                            }

                            ResultStates.Loading -> {
                                CircularProgressIndicator()
                            }

                            is ResultStates.Success -> {
                                Toast.makeText(
                                    context,
                                    (resultStateUpload as ResultStates.Success).message,
                                    Toast.LENGTH_SHORT
                                )
                                navController.navigate(NavigationRoutes.MAIN)
                                {
                                    popUpTo(NavigationRoutes.YOGADETAILS) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

