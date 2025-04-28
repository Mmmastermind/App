package com.example.myapplicationyoga.presentation.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplicationyoga.R
import com.example.myapplicationyoga.data.models.yogaclasses
import com.example.myapplicationyoga.presentation.ui.theme.Beige1

@Composable
fun YogaCard(yoga: yogaclasses, getUrl: (String)->String, onClick:()->Unit){
//    var imageUrl by remember { mutableStateOf("") }
    Card(

        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            val imgState = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data((yoga.image))
                    .size(Size.ORIGINAL).build()
            ).state
            if (imgState is AsyncImagePainter.State.Error) {
                CircularProgressIndicator()

            }
            if (imgState is AsyncImagePainter.State.Success) {
                Image(

                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .clip(RoundedCornerShape(15.dp)),
                    painter = imgState.painter,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
                //
            Column {
                Spacer(modifier = Modifier.size(10.dp))
                Row {
                    Text(
                    text = yoga.name,
                    fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Justify
                )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = yoga.price.toString() + " P.",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }

                Text(
                    text = yoga.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}