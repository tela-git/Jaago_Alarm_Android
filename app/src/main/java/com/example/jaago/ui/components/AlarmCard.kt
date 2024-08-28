package com.example.jaago.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.jaago.R
import com.example.jaago.domain.model.Alarm
import com.example.jaago.domain.model.Days

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
) {
   val alarm = Alarm(
       time = "12:00",
       label = remember { mutableStateOf("") },
       Song = "Bhaja Govindam by M.S Subbalakshmi",
       doVibrate = true,
       repetition = Days()
   )

    var isExpanded by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(

    )
    var showTimePicker by remember{ mutableStateOf(false)}
    var isScheduled by remember { mutableStateOf(false) }

    Card(
        onClick = {
            isExpanded = !isExpanded
        },
        modifier = Modifier
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
                    OutlinedTextField(
                        value = alarm.label.value,
                        onValueChange = { alarm.label.value = it },
                        singleLine = true,
                        keyboardActions = KeyboardActions(onDone =  { isExpanded  = false}),
                    )
                }
                if(!isExpanded) {
                    Text(
                        text = alarm.label.value,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Icon(
                    painter = if(isExpanded) painterResource(R.drawable.expand_less) else painterResource(R.drawable.expand_more),
                    contentDescription = stringResource(R.string.expand_more)
                )
            }
            if(!isExpanded) {
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
                        checked = isScheduled,
                        onCheckedChange = { isScheduled = it }
                    )
                }
            }
            if(isExpanded){
                TimePicker(
                    state = timePickerState
                )
            }
            if(isExpanded) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    OutlinedButton(
                        onClick = {
                            isExpanded = false
                            isScheduled = false
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
                            isScheduled = true
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


@Preview
@Composable
fun AlarmCardPreview() {
    AlarmCard(

    )
}