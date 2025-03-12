package com.example.myapplicationyoga.presentation.screens.signup

import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.screens.components.Button
import com.example.myapplicationyoga.presentation.screens.components.TextFieldEmail
import com.example.myapplicationyoga.presentation.screens.components.TextFieldPassword
import com.example.myapplicationyoga.presentation.screens.components.TextFieldStandart
import com.example.myapplicationyoga.presentation.screens.signin.SignInScreen
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1
import java.util.Date
import androidx.compose.material3.IconButton as IconButton1

@Composable
fun SignUpScreen(navController: NavController, signUpViewModel: SignUpViewModel = viewModel()){
val UiState = signUpViewModel.UiState

    val ResultState = signUpViewModel.resultStates.collectAsState()
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("выберите дату") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige1)

    ) {
        Column(
            modifier = Modifier.padding(horizontal = 35.dp, vertical = 50.dp).padding(top = 50.dp)
        ) {
            Text(
                "Создайте аккаунт:",
                color = Brown1,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)

                    .padding(horizontal = 20.dp)

            )

            Spacer(Modifier.height(20.dp))
            Text(
                "Email",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldEmail(
               UiState.email, UiState.errorEmail,
                onvaluechange = { it -> signUpViewModel.updateState(UiState.copy(email = it)) })
            Spacer(Modifier.height(6.dp))
            Text(
                "Пароль",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldPassword(
                value = UiState.password,
                onvaluechange = { it -> signUpViewModel.updateState(UiState.copy(password = it)) }
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Повторите пароль",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldPassword(
                value = UiState.confirmpassword,
                onvaluechange = { it -> signUpViewModel.updateState(UiState.copy(confirmpassword = it)) }
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Имя пользователя",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldStandart(
                value = UiState.username,
                onvaluechange = { it -> signUpViewModel.updateState(UiState.copy(username = it)) }
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Фамилия пользователя",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            TextFieldStandart(
                value = UiState.surname,
                onvaluechange = { it -> signUpViewModel.updateState(UiState.copy(surname = it)) }
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Дата рождения",
                color = Brown1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            )
            val mDatePickerDialog = android.app.DatePickerDialog(
                mContext,
                { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                    mDate.value = "$mYear-${mMonth + 1}-$mDayOfMonth"
                    signUpViewModel.updateState(UiState.copy(birthday = "$mYear-${mMonth + 1}-$mDayOfMonth"))
                }, mYear, mMonth, mDay
            )

            TextField(
                value = UiState.birthday,
                onValueChange = {
                    Unit->(UiState.birthday)
                },
                trailingIcon = {
                    val IconImage = Icons.Filled.CalendarToday

                    IconButton(onClick = {
                        mDatePickerDialog.show()
                    }){ Icon(imageVector = IconImage, contentDescription = "description") }
                },
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                textStyle = MaterialTheme.typography.bodyLarge,

                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor =  Color.LightGray,
                    focusedContainerColor =  Color.LightGray,
                    errorPlaceholderColor = Color.Red,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

                ),
                shape = RoundedCornerShape(20.dp),
                keyboardOptions =   KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            )
            Spacer(Modifier.height(5.dp))
            when (ResultState.value) {
                is ResultStates.Error -> {
                    Button(
                        "Создать"
                    ) {
                        signUpViewModel.SignUp().toString()
                    }
                    Text((ResultState as ResultStates.Error).message)
                }

                is ResultStates.Initialized -> {
                    Button("Создать") {
                        signUpViewModel.SignUp().toString()
                    }
                }

                is ResultStates.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is ResultStates.Success -> {
                    navController.navigate(NavigationRoutes.MAIN)
                    {

                    }
                }
            }


        }
    }
}

