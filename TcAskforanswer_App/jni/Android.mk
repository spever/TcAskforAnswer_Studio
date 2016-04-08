LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
TARGET_ARCH = arm
APP_ABI := armeabi armeabi-v7a x86
LOCAL_MODULE    := tuanche_validators
LOCAL_SRC_FILES := tuanche_validators.cpp

include $(BUILD_SHARED_LIBRARY)



