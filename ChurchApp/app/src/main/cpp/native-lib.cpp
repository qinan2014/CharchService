#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_service_church_churchapp_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++ hello";
    return env->NewStringUTF(hello.c_str());
}
