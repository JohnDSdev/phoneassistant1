package com.froginc.voiceassistant.stt

object WhisperSTT {

    fun init() {
        // Load native library for WhisperSTT
        System.loadLibrary("whisper_stt")
    }

    // Native method: processes audio input and returns a transcript.
    external fun nativeRecognize(): String

    // Simulate asynchronous recognition (in real use, audio would be captured).
    fun recognize(callback: (String) -> Unit) {
        Thread {
            // Call the native function (stub implementation).
            val transcript = nativeRecognize()
            callback(transcript)
        }.start()
    }
}