#include "com_knight_jone_mySuperDemo_ndk_NDKTools.h"

JNIEXPORT jstring JNICALL Java_com_knight_jone_mySuperDemo_ndk_NDKTools_getStringFromNDK
    (JNIEnv *env, jobject obj){
         return (*env)->NewStringUTF(env,"Hellow World，NDK的第一行代码");
    }