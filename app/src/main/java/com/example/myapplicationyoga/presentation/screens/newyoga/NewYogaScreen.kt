package com.example.myapplicationyoga.presentation.screens.newyoga

import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.screens.components.Button
import com.example.myapplicationyoga.presentation.screens.components.TextFieldDropDown
import com.example.myapplicationyoga.presentation.screens.components.TextFieldEmail
import com.example.myapplicationyoga.presentation.screens.components.TextFieldPassword
import com.example.myapplicationyoga.presentation.screens.components.TextFieldStandart
import com.example.myapplicationyoga.presentation.screens.signin.SignInViewModel
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1
import java.util.Date

@Composable
fun NewYogaScreen(navController: NavController, newYogaViewModel: NewYogaViewModel = viewModel()) {

    val UiState = newYogaViewModel.UiState

    val ResultState =newYogaViewModel.resultStates.collectAsState()

    val yogaState = newYogaViewModel.state


    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige1)

    ) {
        Column(
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 50.dp).padding(top = 30.dp)
        ) {
            Text(
                "Создайте занятие:",
                color = Brown1,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

                    .padding(horizontal = 20.dp)

            )

            Spacer(Modifier.height(40.dp))
            Text(
                "Название занятия",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldStandart(
                value = UiState.name,
                onvaluechange = { it -> newYogaViewModel.updateState(UiState.copy(name = it)) }
            )
            Spacer(Modifier.height(6.dp))


            var expanded by remember { mutableStateOf(false) }
            val selectedCategory =
                newYogaViewModel.categories.value?.find { it.id == yogaState.category }
            Text(
                "Категория занятия",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
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
                    newYogaViewModel.categories.value!!.forEach { category ->
                        // Создаем элемент выпадающего меню для каждой категории
                        DropdownMenuItem(
                            text = { Text(category.categoryname) }, // Отображаем имя категории
                            onClick = {
                                // При нажатии на элемент устанавливаем выбранную категорию
                                yogaState.category = category.id
                                expanded = false // Закрываем меню
                                UiState.category = category.id
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Text(
                "Описание занятия",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldStandart(
                value = UiState.description,
                onvaluechange = { it -> newYogaViewModel.updateState(UiState.copy(description = it)) }
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Цена занятия",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldStandart(
                value = UiState.price,
                onvaluechange = { it -> newYogaViewModel.updateState(UiState.copy(price = it)) }
            )
            Spacer(Modifier.height(30.dp))
            when (ResultState.value) {
                is ResultStates.Error -> {
                    Button(
                        "Создать"
                    ) {
                        newYogaViewModel.SignUp().toString()
                    }
                    Text((ResultState as ResultStates.Error).message)
                }

                is ResultStates.Initialized -> {
                    Button("Создать") {
                        newYogaViewModel.SignUp().toString()
                    }
                }

                is ResultStates.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is ResultStates.Success -> {
                    LaunchedEffect(Unit) { navController.navigate(NavigationRoutes.MAIN)
                    {
                        popUpTo(NavigationRoutes.MAIN){
                            inclusive = true;
                        }
                    } }

                }
            }


        }
    }

}