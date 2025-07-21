#include <jni.h>
#include <android/log.h>
#include <string>

#define LOG_TAG "WhisperSTT"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_froginc_voiceassistant_stt_WhisperSTT_nativeRecognize(JNIEnv *env, jobject /* this */) {
    // TODO: Integrate actual Whisper STT native library code here.
    std::string result = "Simulated STT transcript.";
    LOGI("Whisper STT recognized: %s", result.c_str());
    return env->NewStringUTF(result.c_str());
}