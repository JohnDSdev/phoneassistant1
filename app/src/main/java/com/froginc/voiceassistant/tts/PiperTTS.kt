package com.froginc.voiceassistant.tts

object PiperTTS {

    fun init() {
        // Load native library for PiperTTS
        System.loadLibrary("piper_tts")
    }

    // Native method: plays the given text using Piper TTS.
    external fun nativeSpeak(text: String)

    fun speak(text: String) {
        nativeSpeak(text)
    }
}