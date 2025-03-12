package com.example.myapplicationyoga.presentation.screens.components


import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1


@Composable
fun Button(label: String, onClick:()-> Unit,){
androidx.compose.material3.Button(onClick = {
    onClick()
} ,
    shape = RoundedCornerShape(15.dp),
    colors = ButtonDefaults.buttonColors(
        contentColor = Beige1,
        containerColor = Brown1
    ),

    modifier = Modifier
        .height(50.dp)
        .defaultMinSize(minWidth = 360.dp)
        .wrapContentWidth()

)

    {
        Text(
            label,
            fontSize = 22.sp,
            color = Color.White,
            fontWeight = FontWeight.W600
        )
}
}