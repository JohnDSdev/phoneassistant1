package com.froginc.voiceassistant.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    modelName: String,
    systemPrompt: String,
    wakeWord: String,
    onModelNameChange: (String) -> Unit,
    onSystemPromptChange: (String) -> Unit,
    onWakeWordChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Settings", style = androidx.compose.material.MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = modelName,
            onValueChange = onModelNameChange,
            label = { Text("Ollama Model Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = systemPrompt,
            onValueChange = onSystemPromptChange,
            label = { Text("System Prompt") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = wakeWord,
            onValueChange = onWakeWordChange,
            label = { Text("Wake Word") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Settings")
        }
    }
}