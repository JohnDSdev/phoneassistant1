#include <jni.h>
#include <android/log.h>
#include <string>

#define LOG_TAG "PiperTTS"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT void JNICALL
Java_com_froginc_voiceassistant_tts_PiperTTS_nativeSpeak(JNIEnv *env, jobject /* this */, jstring text) {
    const char *nativeText = env->GetStringUTFChars(text, 0);
    LOGI("Piper TTS speaking: %s", nativeText);
    // TODO: Integrate actual Piper TTS native library code here.
    env->ReleaseStringUTFChars(text, nativeText);
}