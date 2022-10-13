package com.geancarloleiva.jetcompweather_14.screen.settings

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.geancarloleiva.jetcompweather_14.R
import com.geancarloleiva.jetcompweather_14.model.MetricUnit
import com.geancarloleiva.jetcompweather_14.utils.Constants
import com.geancarloleiva.jetcompweather_14.viewmodel.settings.SettingsViewModel
import com.geancarloleiva.jetcompweather_14.widget.WeatherAppBar

@Composable
fun WeatherSettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    var metricUnit = listOf(Constants.UNIT_IMPERIAL, Constants.UNIT_METRIC)

    val choiceFromDb = settingsViewModel.settingsLst.collectAsState().value
    val defaultChoice = if (choiceFromDb.isNullOrEmpty()) metricUnit[0] else choiceFromDb[0].unit
    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    Scaffold(
        topBar = {
            WeatherAppBar(
                title = stringResource(id = R.string.app_bar_menu_item_settings),
                navController = navController,
                isMainScreen = false,
                elevation = 6.dp,
                icon = Icons.Default.ArrowBack
            ) {
                navController.popBackStack()
            }
        },
        backgroundColor = Color.White
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.app_settings_title),
                    modifier = Modifier.padding(bottom = 15.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it

                        if (unitToggleState) {
                            choiceState = Constants.UNIT_METRIC
                            Log.e("GCLV", "en 1.1 $choiceState ", )
                        } else {
                            choiceState = Constants.UNIT_IMPERIAL
                            Log.e("GCLV", "en 1.2 $choiceState ", )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(color = Color.Blue.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = if (unitToggleState) {
                            Log.e("GCLV", "en 2.1 $choiceState ", )
                            Constants.UNIT_METRIC
                        } else {
                            Log.e("GCLV", "en 2.2 $choiceState ", )
                            Constants.UNIT_IMPERIAL
                        }
                    )
                }

                Button(
                    onClick = {
                        settingsViewModel.deleteAllMetricUnits()
                        Log.e("GCLV", "Actual metric $choiceState")
                        settingsViewModel.createMetricUnit(MetricUnit(choiceState))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xffefbe42)
                    )
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}