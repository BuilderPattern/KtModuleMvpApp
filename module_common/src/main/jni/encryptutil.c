#include <jni.h>

JNIEXPORT jlong JNICALL
Java_com_dopool_common_util_EncryptUtil_generateSecret(JNIEnv *env,jlong timeStamp) {
    int n = (int) (timeStamp % 64);
    return ((timeStamp << n) & (0x7fffffffffffffffL)) | (timeStamp >> (64 - n));
}