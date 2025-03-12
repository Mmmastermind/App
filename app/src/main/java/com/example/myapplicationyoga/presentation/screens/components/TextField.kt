package com.example.myapplicationyoga.presentation.screens.components

import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationyoga.R
import com.example.myapplicationyoga.presentation.ui.theme.Brown1
import com.yandex.mapkit.logo.VerticalAlignment
import java.lang.Error
import java.util.Date
import androidx.compose.material3.Text as Text1

@Composable
fun TextFieldEmail(value: String, error: Boolean, onvaluechange: (String)-> Unit){

TextField(
    modifier = Modifier.fillMaxWidth().padding(10.dp),
    value = value,
    textStyle = MaterialTheme.typography.bodyLarge,
    onValueChange = {
        onvaluechange(it)
    },
    isError = !error,
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
    placeholder = { Text1("test@mail.ru", color = Color.Gray) },
    keyboardOptions =   KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
    shape = RoundedCornerShape(20.dp)
)

}

@Composable
fun TextFieldPassword(value: String, onvaluechange: (String)-> Unit){
     val passwordVisible = remember {
        mutableStateOf(false)
   }
    TextField(
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),

        trailingIcon = {

                val IconImage = if(passwordVisible.value){
                      Icons.Filled.Visibility
                } else{
                      Icons.Filled.VisibilityOff
                }
                var description = if(passwordVisible.value){
                    "Hide password"
                } else{
                    "Show password"
            }

            IconButton(onClick = {
                passwordVisible.value = !passwordVisible.value
                }){
                Icon(imageVector = IconImage, contentDescription = description)
            }
        }  ,
        modifier = Modifier.fillMaxWidth().padding(10.dp),
        value = value,
        textStyle = MaterialTheme.typography.bodyLarge,
        onValueChange = {
            onvaluechange(it)
        },
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
        placeholder = { Text1(text = "123456", color = Color.Gray) },
        keyboardOptions =   KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        shape = RoundedCornerShape(20.dp),


                 )
}

@Composable
fun TextFieldStandart (value: String, onvaluechange: (String)-> Unit){
   TextField(
       modifier = Modifier.fillMaxWidth().padding(10.dp),
    value = value,
    textStyle = MaterialTheme.typography.bodyLarge,
    onValueChange = {
        onvaluechange(it)},
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

}



@Composable
fun DividerText(){
    Row(

        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(modifier = Modifier.fillMaxWidth().weight(1f),
            color = Brown1,
            thickness =  1.dp)
Text(
    "или",
    fontSize = 16.sp, color = Brown1, modifier = Modifier.padding(8.dp)
)
        HorizontalDivider(modifier = Modifier.fillMaxWidth().weight(1f),
            color = Brown1,
            thickness =  1.dp)
    }
}