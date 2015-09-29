LOCAL_PATH := $(call my-dir)


# Prebuilt libssl
include $(CLEAR_VARS)
LOCAL_MODULE := ssl
LOCAL_SRC_FILES := precompiled/$(TARGET_ARCH_ABI)/libssl.so
include $(PREBUILT_SHARED_LIBRARY)

# Prebuilt libcrypto
include $(CLEAR_VARS)
LOCAL_MODULE := crypto
LOCAL_SRC_FILES := precompiled/$(TARGET_ARCH_ABI)/libcrypto.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
c_includes := $(LOCAL_PATH)
cf_includes := includes
cf_includes := $(addprefix -Ijni/,$(cf_includes))
export_c_includes := $(c_includes)
LOCAL_MODULE := TLSManager
LOCAL_SRC_FILES := TLSManager.cpp
LOCAL_LDLIBS := -llog 
LOCAL_SHARED_LIBRARIES= ssl crypto liblog
LOCAL_CFLAGS += $(cf_includes)
LOCAL_EXPORT_C_INCLUDES := $(export_c_includes)
include $(BUILD_SHARED_LIBRARY)


