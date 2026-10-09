package me.chetan.life

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSmallTask(modifier: Modifier = Modifier) {
    var taskName by remember { mutableStateOf("") }
    var isRelative by remember { mutableStateOf(true) }

    var interval by remember { mutableStateOf("1") }
    var selectedUnit by remember { mutableStateOf(TimeUnit.DAYS) }
    var expandedUnitMenu by remember { mutableStateOf(false) }

    val selectedDays = remember { mutableStateListOf<Int>() } // 0 = Mon, 6 = Sun
    val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
    )
    var pickingTime by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Add New Task", style = MaterialTheme.typography.headlineSmall)

        // Task Name
        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Task Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Toggle Mode
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (isRelative) "Relative Interval" else "Specific Days")
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isRelative,
                onCheckedChange = { isRelative = it }
            )
        }

        if (isRelative) {
            // Relative UI
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Every")
                OutlinedTextField(
                    value = interval,
                    onValueChange = { interval = it.filter { char -> char.isDigit() } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(80.dp)
                )
                Box {
                    OutlinedButton(onClick = { expandedUnitMenu = true }) {
                        Text(selectedUnit.name.lowercase().capitalize())
                    }
                    DropdownMenu(
                        expanded = expandedUnitMenu,
                        onDismissRequest = { expandedUnitMenu = false }
                    ) {
                        TimeUnit.entries.forEach { unit ->
                            DropdownMenuItem(
                                text = { Text(unit.name.lowercase().capitalize()) },
                                onClick = {
                                    selectedUnit = unit
                                    expandedUnitMenu = false
                                }
                            )
                        }
                    }
                }
            }
        } else {
            // Specific Days UI
            Column {
                Text("Select Days", style = MaterialTheme.typography.labelLarge)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    dayLabels.forEachIndexed { index, label ->
                        val isSelected = selectedDays.contains(index)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (isSelected) selectedDays.remove(index)
                                else selectedDays.add(index)
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }
        }

        // Time Selection Button
        OutlinedButton(onClick = { pickingTime = true }) {
            Text(text = "Set Time: ${timePickerState.hour}:${timePickerState.minute.toString().padStart(2, '0')}")
        }

        if (pickingTime) {
            Dialog(
                onDismissRequest = { pickingTime = false }
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .height(IntrinsicSize.Min)
                        .background(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            text = "Select Time",
                            style = MaterialTheme.typography.labelMedium
                        )
                        TimePicker(state = timePickerState)
                        TextButton(onClick = { pickingTime = false }) {
                            Text("OK")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                println("Saving Task")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = taskName.isNotBlank()
        ) {
            Text("Save Task")
        }
    }
}

enum class TimeUnit {
    HOURS, DAYS, WEEKS
}

private fun String.capitalize(): String = this.replaceFirstChar { it.uppercase() }

@Preview(showBackground = true)
@Composable
fun AddSmallTaskPreview() {
    AddSmallTask(Modifier.fillMaxSize())
}