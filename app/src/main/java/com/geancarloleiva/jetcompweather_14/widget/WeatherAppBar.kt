package com.geancarloleiva.jetcompweather_14.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.geancarloleiva.jetcompweather_14.R
import com.geancarloleiva.jetcompweather_14.navigation.WeatherScreens

@Composable
fun WeatherAppBar(
    title: String,
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(
            showDialog = showDialog,
            navController = navController
        )
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colors.onSecondary,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        },
        actions = {
            if (isMainScreen) {
                //Search icon
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.app_bar_search)
                    )
                }
                //More menu icon
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = stringResource(id = R.string.app_bar_menu)
                    )
                }
            }
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = title,
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        //When is needed, this should be implemented in the method that called it. ID 27676
                        onButtonClicked.invoke()
                    })
            }
        },
        elevation = elevation,
        backgroundColor = Color.White
    )

    //Different way to build the AppBar
    /*TopAppBar(backgroundColor = Color.White) {
        Text(
            text = title,
            color = MaterialTheme.colors.onSecondary,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
    }*/
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {
    val menuItemAbout = stringResource(id = R.string.app_bar_menu_item_about)
    val menuItemFavorite = stringResource(id = R.string.app_bar_menu_item_favorite)
    val menuItemSettings = stringResource(id = R.string.app_bar_menu_item_settings)
    var expanded by remember {
        mutableStateOf(true)
    }
    val items = listOf(menuItemAbout, menuItemFavorite, menuItemSettings)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (text) {
                            menuItemAbout -> Icons.Default.Info
                            menuItemFavorite -> Icons.Default.FavoriteBorder
                            menuItemSettings -> Icons.Default.Settings
                            else -> Icons.Default.ArrowBack
                        },
                        contentDescription = stringResource(id = R.string.app_bar_menu),
                        tint = Color.LightGray
                    )
                    Text(
                        text = text,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when (text) {
                                    menuItemAbout -> WeatherScreens.AboutScreen.name
                                    menuItemFavorite -> WeatherScreens.FavoriteScreen.name
                                    menuItemSettings -> WeatherScreens.SettingsScreen.name
                                    else -> WeatherScreens.MainScreen.name
                                }
                            )
                        },
                        fontWeight = FontWeight.W300,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }
        }
    }
}
