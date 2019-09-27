LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under,src)
LOCAL_REQUIRED_MODULES := android.hardware.copro
LOCAL_STATIC_JAVA_LIBRARIES := android.hardware.copro-V1.0-java
LOCAL_PACKAGE_NAME := CoproService
LOCAL_PROGUARD_ENABLED := disabled
LOCAL_CERTIFICATE := platform
LOCAL_PRIVILEGED_MODULE := true
LOCAL_PRIVATE_PLATFORM_APIS := true
include $(BUILD_PACKAGE)
