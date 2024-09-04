package com.example.jaago.ui.components

import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.jaago.R
import com.example.jaago.data.Alarm
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarm: Alarm,
    updateAlarmLabel: (newLabel: String) -> Unit,
    changeAlarmScheduleState: (newState: Boolean) -> Unit,
    onDeleteAlarmClicked: (alarm: Alarm) -> Unit,
    updateAlarmTime: (newTime: Long) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isLabelDialogVisible by remember { mutableStateOf(false) }
    var tempAlarmLabel by remember { mutableStateOf(alarm.label) }
    var isTimePickerVisible by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()
    val time by remember { mutableLongStateOf(timeInMillis(timePickerState.hour, timePickerState.minute)) }

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
                if(isExpanded) {
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
                        LabelEntryDialog(
                            changeIsExpanded =  { isExpanded = it },
                            isLabelDialogVisible = { isLabelDialogVisible = it },
                            onTempAlarmLabelValueChange = { tempAlarmLabel = it },
                            tempAlarmLabel = tempAlarmLabel,
                            updateAlarmLabel = { updateAlarmLabel(it) }
                        )
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = timeInMillisToHumanReadableFormat(alarm.timeInMillis),
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.clickable {
                        isExpanded = true
                        isTimePickerVisible = true
                    }
                    )
                if(!isExpanded) {
                    Switch(
                        checked = alarm.isScheduled,
                        onCheckedChange = { isOn -> changeAlarmScheduleState(isOn) }
                    )
                }
            }
            if(isExpanded) {
                AnimatedVisibility(isTimePickerVisible) {
                    TimePickerDialogBox(
                        onConfirm = {
                            updateAlarmTime(1231123L)
                            isTimePickerVisible = false
                                    },
                        onDismiss = { isTimePickerVisible = false },
                        timePickerState = timePickerState
                    )
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogBox(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    timePickerState: TimePickerState
) {
    val calendar = Calendar.getInstance()

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large))
        ){
            TimePicker(
                state = timePickerState
            )
           Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               modifier = Modifier
                   .padding(horizontal =  dimensionResource(R.dimen.large))
                   .fillMaxWidth()
           ) {
               OutlinedButton(
                   onClick = onDismiss
               ) {
                   Text(
                       text = stringResource(R.string.dismiss)
                   )
               }
               Button(
                   onClick =  onConfirm
               ) {
                   Text(
                       text = stringResource(R.string.confirm)
                   )
               }
            }
        }
    }
}

@Composable
fun LabelEntryDialog(
    isLabelDialogVisible: (Boolean) -> Unit,
    tempAlarmLabel: String,
    onTempAlarmLabelValueChange: (String) -> Unit,
    updateAlarmLabel: (String) -> Unit,
    changeIsExpanded: (Boolean) -> Unit
) {
    Dialog(
        onDismissRequest = { isLabelDialogVisible(false) },
    ) {
        ElevatedCard(
            modifier = Modifier
        ){
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.verylarge))
            ){
                OutlinedTextField(
                    value = tempAlarmLabel,
                    onValueChange = { onTempAlarmLabelValueChange(it) },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            updateAlarmLabel(tempAlarmLabel)
                            changeIsExpanded(false)
                        }
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.enter_any_message),
                            fontSize = 12.sp
                        )
                    }
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.large))
                        .fillMaxWidth(),
                ) {
                    OutlinedButton(
                        onClick = {
                            isLabelDialogVisible(false)
                        }
                    ) {
                        Text(
                            text = "Dismiss"
                        )
                    }
                    Button(
                        onClick = {
                            updateAlarmLabel(tempAlarmLabel)
                            isLabelDialogVisible(false)
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

fun timeInMillis(hour: Int, minute: Int): Long {
    val calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("IST"))

    // Set seconds and milliseconds to zero as we don't use them in setting an alarm
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    // Set hour and minute
    calendar.set(Calendar.MINUTE, minute)
    calendar.set(Calendar.HOUR_OF_DAY, hour)

    return calendar.timeInMillis
}

fun timeInMillisToHumanReadableFormat(timeInMillis: Long) : String {
    val timeFormat = SimpleDateFormat("HH:mm")
    val date = Date(timeInMillis) // Convert timeInMillis to Date
    return timeFormat.format(date)
}
