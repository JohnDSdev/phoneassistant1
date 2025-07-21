````markdown
# Voice LLM Assistant Android

This Android app is a voice-based assistant that integrates a local LLM (via an Ollama server) with on-device speech-to-text using Whisper and text-to-speech using Piper (both integrated via JNI). It also includes:
- A modern Compose UI with a conversation view and settings panel.
- A settings screen to configure the Ollama model name, system prompt, and wake word.
- Retrofit-based API calls to send LLM responses over your LAN to a Pi Pico W for tool calls.

## Repository Structure
