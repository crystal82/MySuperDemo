LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE    := ndkTest-jni
LOCAL_SRC_FILES := ndkTest.c
include $(BUILD_SHARED_LIBRARY)