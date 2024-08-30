package com.example.jaago.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.jaago.R
import com.example.jaago.data.Alarm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    updateAlarmLabel: (newLabel: String) -> Unit,
    changeAlarmScheduleState: (newState: Boolean) -> Unit,
    onDeleteAlarmClicked: (alarm: Alarm) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    var isLabelDialogVisible by remember { mutableStateOf(false) }
    var tempAlarmLabel by remember { mutableStateOf(alarm.label)}

    Card(
        onClick = {
            isExpanded = !isExpanded
        },
        modifier = Modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = if(isExpanded) 16.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                if(isExpanded){
                    OutlinedButton(
                        onClick = { isLabelDialogVisible = true }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small))
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.label),
                                contentDescription = "label",
                            )
                            Text(
                                text = "Label: ",
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            Text(
                                text = alarm.label
                            )
                        }
                    }
                    AnimatedVisibility(isLabelDialogVisible){
                        Dialog(
                            onDismissRequest = { isLabelDialogVisible = false },
                        ) {
                            ElevatedCard(
                                modifier = Modifier
                            ){
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.verylarge))
                                ){
                                    OutlinedTextField(
                                        value = tempAlarmLabel,
                                        onValueChange = { tempAlarmLabel = it },
                                        singleLine = true,
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                updateAlarmLabel(tempAlarmLabel)
                                                isExpanded = false
                                            }
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = dimensionResource(R.dimen.large))
                                            .fillMaxWidth(),
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                isLabelDialogVisible = false
                                            }
                                        ) {
                                            Text(
                                                text = "Dismiss"
                                            )
                                        }
                                        Button(
                                            onClick = {
                                                updateAlarmLabel(tempAlarmLabel)
                                                isLabelDialogVisible = false
                                            }
                                        ) {
                                            Text(
                                                text = "Ok"
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                if(!isExpanded) {
                    Text(
                        text = alarm.label,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Icon(
                    painter = if(isExpanded) painterResource(R.drawable.expand_less) else painterResource(R.drawable.expand_more),
                    contentDescription = if(!isExpanded) stringResource(R.string.expand) else stringResource(R.string.collapse)
                )
            }
            AnimatedVisibility(!isExpanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = String.format(
                            "%02d:%02d",
                            timePickerState.hour,
                            timePickerState.minute
                        ),
                        style = MaterialTheme.typography.displayLarge,
                    )
                    Switch(
                        checked = alarm.isScheduled,
                        onCheckedChange = { isOn-> changeAlarmScheduleState(isOn) }
                    )
                }
            }
            AnimatedVisibility(isExpanded){
                TimePicker(
                    state = timePickerState
                )
            }
            AnimatedVisibility(isExpanded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedButton(
                        onClick = {
                            onDeleteAlarmClicked(alarm)
                            isExpanded = false
                        },
                    ) {
                        Text(
                            text = "Delete",
                            color = Color.Red
                        )
                    }

                    Button(
                        onClick = {
                            isExpanded = false
                            changeAlarmScheduleState(true)
                        }
                    ) {
                        Text(
                            text = "Done"
                        )
                    }
                }
            }

        }
    }
}