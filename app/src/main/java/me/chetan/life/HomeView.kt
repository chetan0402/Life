package me.chetan.life

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomeView(
    modifier: Modifier,
    onAddSmallTask: () -> Unit,
    onAddMediumTask: () -> Unit,
    onAddLongTask: () -> Unit,
    onAddVeryLongTask: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomEnd),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SmallFloatingActionButton(onClick = { onAddSmallTask() }) {
                        Text("Very small task (1-2mins)",modifier=Modifier.padding(16.dp))
                    }
                    SmallFloatingActionButton(onClick = { onAddMediumTask() }) {
                        Text("Medium task (1-2hrs)",modifier=Modifier.padding(16.dp))
                    }
                    SmallFloatingActionButton(onClick = { onAddLongTask() }) {
                        Text("Long task (~1day)",modifier=Modifier.padding(16.dp))
                    }
                    SmallFloatingActionButton(onClick = { onAddVeryLongTask() }) {
                        Text("Very long task (~1month)",modifier=Modifier.padding(16.dp))
                    }
                }
            }
            FloatingActionButton(
                onClick = { expanded = !expanded },
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (expanded) "Close Menu" else "Open Menu"
                )
            }
        }
    }
}
