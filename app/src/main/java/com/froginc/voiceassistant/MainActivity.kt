package com.froginc.voiceassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.froginc.voiceassistant.network.OllamaService
import com.froginc.voiceassistant.network.ToolCallService
import com.froginc.voiceassistant.stt.WhisperSTT
import com.froginc.voiceassistant.tts.PiperTTS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Holds a list of conversation messages
    private val conversation = mutableStateListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize native components
        WhisperSTT.init()
        PiperTTS.init()

        setContent {
            VoiceAssistantApp()
        }
    }

    @Composable
    fun VoiceAssistantApp() {
        var isListening by remember { mutableStateOf(false) }
        var showSettings by remember { mutableStateOf(false) }
        var modelName by remember { mutableStateOf("default-model") }
        var systemPrompt by remember { mutableStateOf("Default system prompt") }
        var wakeWord by remember { mutableStateOf("Hey Assistant") }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Voice LLM Assistant") },
                    actions = {
                        IconButton(onClick = { showSettings = true }) {
                            Icon(Icons.Filled.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (showSettings) {
                SettingsScreen(
                    modelName = modelName,
                    systemPrompt = systemPrompt,
                    wakeWord = wakeWord,
                    onModelNameChange = { modelName = it },
                    onSystemPromptChange = { systemPrompt = it },
                    onWakeWordChange = { wakeWord = it },
                    onDismiss = { showSettings = false }
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Conversation Log
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(conversation) { message ->
                            Text(text = message, style = MaterialTheme.typography.body1)
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    // Controls
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                // simulate wake word detection (if wakeWord matches, then listen)
                                conversation.add("Wake word '$wakeWord' detected. Starting STT…")
                                isListening = true
                                // Call the native Whisper STT asynchronously; update conversation when done.
                                WhisperSTT.recognize { transcript ->
                                    conversation.add("You: $transcript")
                                    sendToLLM(transcript, modelName, systemPrompt)
                                }
                            },
                            enabled = !isListening
                        ) {
                            Text("Start Listening")
                        }
                        Button(
                            onClick = {
                                conversation.add("Conversation ended.")
                            }
                        ) {
                            Text("End Conversation")
                        }
                    }
                }
            }
        }
    }

    private fun sendToLLM(prompt: String, modelName: String, systemPrompt: String) {
        // Add a message indicating processing
        runOnUiThread {
            conversation.add("LLM processing…")
        }
        // Run a coroutine to simulate sending the prompt to the local Ollama server and processing its streaming response.
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = OllamaService.create()
                val response = service.requestLLM(mapOf("prompt" to prompt, "model" to modelName, "system" to systemPrompt))
                // In a real-world case, you'd capture streamed responses.
                val responseText = response["text"] as? String ?: "No response received."
                // Send the response over API to your Pico W
                val toolService = ToolCallService.create()
                toolService.callTool(mapOf("command" to responseText))
                // Update UI and read aloud the response using Piper TTS.
                runOnUiThread {
                    conversation.add("LLM: $responseText")
                    PiperTTS.speak(responseText)
                }
            } catch (ex: Exception) {
                runOnUiThread {
                    conversation.add("Error contacting LLM: ${ex.message}")
                }
            }
        }
    }
}