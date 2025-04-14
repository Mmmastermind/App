package com.example.myapplicationyoga.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1

@Composable
fun CategoryItem(category: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.DarkGray else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() })
            .background(Brown1, shape = RoundedCornerShape(15.dp))
            .padding(10.dp)
            .width(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = category, color = Beige1, textAlign = TextAlign.Center)
    }
}