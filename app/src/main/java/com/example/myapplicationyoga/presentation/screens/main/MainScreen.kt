package com.example.myapplicationyoga.presentation.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.myapplicationyoga.domain.states.ResultStates
import com.example.myapplicationyoga.presentation.navigation.NavigationRoutes
import com.example.myapplicationyoga.presentation.screens.components.CategoryItem
import com.example.myapplicationyoga.presentation.screens.components.TextFieldSearch
import com.example.myapplicationyoga.presentation.screens.components.YogaCard
import com.example.myapplicationyoga.presentation.ui.theme.Beige1
import com.example.myapplicationyoga.presentation.ui.theme.Brown1
import kotlinx.coroutines.runBlocking

@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel = viewModel()){

    val textSearch = remember { mutableStateOf("") }

    val categories = mainViewModel.categories.observeAsState(emptyList())


    //состояние хранящая модель
    val yoga = mainViewModel.yoga.observeAsState(emptyList())

    val selectedCategory = remember { mutableIntStateOf(-1) }

    val resultState by mainViewModel.resultStates.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beige1)

    ) {
        Column(modifier = Modifier
            .padding(horizontal = 40.dp, vertical = 50.dp,)
            .padding(top = 20.dp)) {
            Text(
                "Добрый день!",
                color = Brown1,
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 50.dp)

            )
            Spacer(modifier = Modifier.size(10.dp))
            TextFieldSearch(
                value = textSearch.value,
                onvaluechange = { newText ->
                    textSearch.value = newText
                    mainViewModel.filterList(
                        newText,
                        selectedCategory.intValue
                    )
                }
            )

            when (resultState) {
                is ResultStates.Error ->
                    Text(text = (resultState as ResultStates.Error).message)

                ResultStates.Initialized -> {

                }
                ResultStates.Loading -> {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ResultStates.Success -> {
                    Log.d("CategoriesDebug", "Categories in Success: ${categories.value.size}")
                    LazyRow (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ){
                        items(categories.value.indices.toList()) { index ->
                            CategoryItem(
                                category = categories.value[index].categoryname,
                                isSelected = selectedCategory.intValue == categories.value[index].id,
                                onClick = {
                                    if (selectedCategory.intValue == categories.value[index].id) {

                                        selectedCategory.intValue = -1
                                    } else {

                                        selectedCategory.intValue = categories.value[index].id
                                    }
                                    mainViewModel.filterList(
                                        textSearch.value,
                                        selectedCategory.intValue
                                    )
                                }
                            )
                        }
                    }
                    Log.i("yoga value",yoga.value.toString())


                    LazyColumn {
                        items(yoga.value) { it ->
                            YogaCard(yoga = it ) {
                                runBlocking {
                                    mainViewModel.getImage(it)
                                }
                            }
                        }
                    }
                    }
                }
            }
        }

        }
