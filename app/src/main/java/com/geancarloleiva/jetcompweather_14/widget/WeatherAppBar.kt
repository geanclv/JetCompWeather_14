package com.geancarloleiva.jetcompweather_14.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
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
                    onButtonClicked.invoke()
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