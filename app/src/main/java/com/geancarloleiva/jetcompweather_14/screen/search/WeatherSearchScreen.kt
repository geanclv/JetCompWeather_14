package com.geancarloleiva.jetcompweather_14.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geancarloleiva.jetcompweather_14.R
import com.geancarloleiva.jetcompweather_14.navigation.WeatherScreens
import com.geancarloleiva.jetcompweather_14.widget.WeatherAppBar
import kotlin.math.log

@Composable
fun WeatherSearchScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.app_bar_search),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .align(Alignment.CenterHorizontally)
                ){ selectedCity ->
                    navController.navigate("${WeatherScreens.MainScreen.name}/$selectedCity")
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {}
) {
    val searchQueryState = rememberSaveable() {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) {
        searchQueryState.value.trim().isNotEmpty()
    }

    Column() {
        CommonTextField(
            valueState = searchQueryState,
            placeHolder = "city",
            onAction = KeyboardActions {
                if (!valid) {
                    return@KeyboardActions
                }
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeHolder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = {
            Text(text = placeHolder, color = MaterialTheme.colors.onSecondary)
        },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Blue,
            cursorColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colors.onSecondary,
            textColor = MaterialTheme.colors.onSecondary
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp
            )
    )
}
