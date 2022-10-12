package com.geancarloleiva.jetcompweather_14.screen.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geancarloleiva.jetcompweather_14.R
import com.geancarloleiva.jetcompweather_14.model.Favorite
import com.geancarloleiva.jetcompweather_14.navigation.WeatherScreens
import com.geancarloleiva.jetcompweather_14.viewmodel.favorite.FavoriteViewModel
import com.geancarloleiva.jetcompweather_14.widget.WeatherAppBar

@Composable
fun WeatherFavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = stringResource(id = R.string.app_bar_menu_item_favorite),
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            elevation = 6.dp
        ) {
            navController.popBackStack()
        }
    },
        backgroundColor = Color.White) {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val lstFavorite = favoriteViewModel.favoriteLst.collectAsState().value
                LazyColumn {
                    items(items = lstFavorite) { fav ->
                        CityRow(
                            favorite = fav,
                            navController = navController,
                            viewModel = favoriteViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(
    favorite: Favorite,
    navController: NavController,
    viewModel: FavoriteViewModel
) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate("${WeatherScreens.MainScreen.name}/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xffb2dfdb)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = favorite.city,
                modifier = Modifier.padding(4.dp)
            )

            Surface(
                modifier = Modifier.padding(1.dp),
                shape = CircleShape,
                color = Color(0xffd1e3e1)
            ) {
                Text(
                    text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.app_bar_delete),
                tint = Color.Red.copy(alpha = 0.6f),
                modifier = Modifier.clickable {
                    viewModel.deleteFavorite(favorite)
                }
            )
        }
    }
}
