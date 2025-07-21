cmake_minimum_required(VERSION 3.10)

project(native-lib)

# Build native library for Piper TTS
add_library(piper_tts SHARED
            src/main/cpp/piper_tts.cpp)

# Build native library for Whisper STT
add_library(whisper_stt SHARED
            src/main/cpp/whisper_stt.cpp)

find_library(log-lib log)

target_link_libraries(piper_tts ${log-lib})
target_link_libraries(whisper_stt ${log-lib})