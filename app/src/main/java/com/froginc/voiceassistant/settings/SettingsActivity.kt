package com.froginc.voiceassistant.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val modelName = remember { mutableStateOf("default-model") }
            val systemPrompt = remember { mutableStateOf("Default system prompt") }
            val wakeWord = remember { mutableStateOf("Hey Assistant") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text("Settings", style = androidx.compose.material.MaterialTheme.typography.h5)
                OutlinedTextField(
                    value = modelName.value,
                    onValueChange = { modelName.value = it },
                    label = { Text("Ollama Model Name") },
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = systemPrompt.value,
                    onValueChange = { systemPrompt.value = it },
                    label = { Text("System Prompt") },
                    modifier = Modifier.padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = wakeWord.value,
                    onValueChange = { wakeWord.value = it },
                    label = { Text("Wake Word") },
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}