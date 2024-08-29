package com.example.jaago.ui.screens.alarm

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jaago.R
import com.example.jaago.data.Alarm
import com.example.jaago.ui.components.AlarmCard

@Composable
fun AlarmsViewScreen(
    modifier: Modifier = Modifier
) {
    val alarmViewModel: AlarmViewModel = hiltViewModel()
    val alarmsListUiState: AlarmsListUiState by alarmViewModel.alarmsListUiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    alarmViewModel.setNewAlarm(
                        Alarm(
                            isScheduled = true,
                            doVibrate = true,
                            label = "Tution time",
                            song = "Default",
                            timeInMillis = 121223
                        )
                    )

                },
                containerColor = Color(0xFF00897B),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.add_alarm)
                )
            }
        },
        modifier = modifier
    ) {innerPadding->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            when {
                alarmsListUiState.isError -> item {
                    Text(
                        text = "Error Fectching Alarms"
                    )
                }
                alarmsListUiState.isError.not() -> items(
                    items = alarmsListUiState.alarms
                ) { alarm->
                    AlarmCard(
                        alarm = alarm
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AlarmScreen() {
    AlarmScreen()
}