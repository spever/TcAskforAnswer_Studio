/*
 * com_lashou_groupurchasing_utils_Validator.cpp
 *
 *  Created on: 2014-3-17
 *      Author: Administrator
 */
#include <jni.h>
#include "tuanche_validators.h"
//                 jukl;'eRT231*(145753YUJK.#%RC^&*(
const char *key = "jukl;'eRT231*(145753YUJK.#%RC^&*(";
const char *secretkey = "tuanche1234abcd1234";
const char *code = "UTF-8";
const int BASE64_DEFALUT = 0;
/**
 * 实现类似stringbuffer的append的方法
 */
JNIEXPORT jstring append(JNIEnv *env,jobject obj,jstring s){
	jclass scls = env->FindClass("java/lang/StringBuffer");
	jmethodID sAppid = env->GetMethodID(scls,"append","(Ljava/lang/String;)Ljava/lang/StringBuffer;");
	jmethodID tostring = env->GetMethodID(scls,"toString","()Ljava/lang/String;");
	env->CallObjectMethod(obj,sAppid,s);
	jstring re = (jstring)env->CallObjectMethod(obj,tostring);
	return re;
}

/**
 * 生成key
 */
JNIEXPORT jbyteArray createKey(JNIEnv *env,jbyteArray bytes,jclass md5Cls,jclass stringCls){
	int ctr=0,k=0;
	jmethodID stringInitID = env->GetMethodID(stringCls,"<init>","(Ljava/lang/String;)V");
	jmethodID getMd5ID = env->GetStaticMethodID(md5Cls,"getMD5","(Ljava/lang/String;)Ljava/lang/String;");
	jmethodID stringGetbytesID = env->GetMethodID(stringCls,"getBytes","(Ljava/lang/String;)[B");
	jstring encrypt_key = env->NewStringUTF(key);
	jstring ency_key = (jstring)(env->CallStaticObjectMethod(md5Cls,getMd5ID,encrypt_key));
	jobject stringKeyObj = env->NewObject(stringCls,stringInitID,ency_key);
	jstring codeStr = env->NewStringUTF(code);
	jbyteArray keyBytes = (jbyteArray)(env->CallObjectMethod(stringKeyObj,stringGetbytesID,codeStr));
	jbyte *mkey = env->GetByteArrayElements(keyBytes,0);
	int keyLen = env->GetArrayLength(keyBytes);
	int len = env->GetArrayLength(bytes);
	jbyteArray byteArray = env->NewByteArray(len);
	jbyte *mbytes = env->GetByteArrayElements(bytes,0);
	jbyte *reByte = env->GetByteArrayElements(byteArray,0);
	int i;
	for(i = 0;i < len;i++){
		if(ctr == keyLen){
			ctr = 0;
		}
		reByte[k++] = (jbyte)(mbytes[i] ^ mkey[ctr]);
		ctr++;
	}
	env->SetByteArrayRegion(byteArray,0,len,reByte);
	env->ReleaseByteArrayElements(keyBytes,mkey,0);
	env->ReleaseByteArrayElements(bytes,mbytes,0);
	env->ReleaseByteArrayElements(byteArray,reByte,0);
	return byteArray;
}

/**
 * 根据规则生成请求参数 sign 值
 */
JNIEXPORT jstring JNICALL Java_com_tuanche_askforanswer_app_utils_Validator_signNativeMethod
  (JNIEnv *env, jclass cls, jstring sign, jstring time, jstring clientId){
	jclass stringCls = env->FindClass("java/lang/String");
	jclass integerCls = env->FindClass("java/lang/Integer");
	jclass strBufferls = env->FindClass("java/lang/StringBuffer");
	jclass md5Cls = env->FindClass("com/tuanche/askforanswer/app/utils/MD5Utils");
	jmethodID initStrId = env->GetMethodID(stringCls,"<init>","(Ljava/lang/String;)V");
	jmethodID substringID = env->GetMethodID(stringCls,"substring","(II)Ljava/lang/String;");
	jmethodID parseIntId = env->GetStaticMethodID(integerCls,"parseInt","(Ljava/lang/String;)I");
	jmethodID toLowerCaseId = env->GetMethodID(stringCls,"toLowerCase","()Ljava/lang/String;");
	jmethodID bufferInitId = env->GetMethodID(strBufferls,"<init>","()V");
	jmethodID getMd5Id = env->GetStaticMethodID(md5Cls,"getMD5","(Ljava/lang/String;)Ljava/lang/String;");
	jobject timeObj = env->NewObject(stringCls,initStrId,time);
	jobject signObj = env->NewObject(stringCls,initStrId,sign);
	jobject strBufferObj = env->NewObject(strBufferls,bufferInitId);
	int timeLength = env->GetStringLength(time);
	int a = env->CallStaticIntMethod(integerCls,parseIntId,(env->CallObjectMethod(timeObj,substringID,timeLength-1,timeLength)));
	int b = env->CallStaticIntMethod(integerCls,parseIntId,(env->CallObjectMethod(timeObj,substringID,timeLength-2,timeLength-1)));
	int c = env->CallStaticIntMethod(integerCls,parseIntId,(env->CallObjectMethod(timeObj,substringID,timeLength-3,timeLength-2)));
	int d = env->CallStaticIntMethod(integerCls,parseIntId,(env->CallObjectMethod(timeObj,substringID,timeLength-4,timeLength-3)));
	int e = (a+b+c+d) % 10;
	jstring t = (jstring)(env->CallObjectMethod(timeObj,substringID,e,e+1));
	jstring re;
	sign = (jstring)(env->CallObjectMethod(signObj,toLowerCaseId));
	jstring spilt = env->NewStringUTF("|");
	re = append(env,strBufferObj,sign);
	re = append(env,strBufferObj,spilt);
	re = append(env,strBufferObj,t);
	re = append(env,strBufferObj,spilt);
	re = append(env,strBufferObj,clientId);
	re = append(env,strBufferObj,spilt);
	re = append(env,strBufferObj,time);
	re = (jstring)(env->CallStaticObjectMethod(md5Cls,getMd5Id,re));
	return re;
}


/**
 * 安全码的签名
 */
JNIEXPORT jstring JNICALL Java_com_tuanche_askforanswer_app_utils_Validator_getSafeKey
  (JNIEnv *env, jclass cls, jstring randomNum){
	jclass strBufferls = env->FindClass("java/lang/StringBuffer");
	jmethodID bufferInitId = env->GetMethodID(strBufferls,"<init>","()V");
	jobject strBufferObj = env->NewObject(strBufferls,bufferInitId);
	jclass md5Cls = env->FindClass("com/tuanche/askforanswer/app/utils/MD5Utils");
	jmethodID getMd5Id = env->GetStaticMethodID(md5Cls,"getMD5","(Ljava/lang/String;)Ljava/lang/String;");
	jstring re;
	re = append(env,strBufferObj,randomNum);
	jstring encrypt_key = env->NewStringUTF(secretkey);
	re = append(env,strBufferObj,encrypt_key);
	re = (jstring)(env->CallStaticObjectMethod(md5Cls,getMd5Id,re));
	return re;

}

/**
 * 安全码的签名
 */
JNIEXPORT jstring JNICALL Java_com_tuanche_askforanswer_app_utils_Validator_getSafeSign
  (JNIEnv *env, jclass cls, jstring txt){
	jclass md5Cls = env->FindClass("com/tuanche/askforanswer/app/utils/MD5Utils");
	jclass randomCls = env->FindClass("java/util/Random");
	jclass stringCls = env->FindClass("java/lang/String");
	jclass base64Cls = env->FindClass("android/util/Base64");
	jmethodID ranId = env->GetMethodID(randomCls,"<init>","()V");
	jmethodID ranNextIntID = env->GetMethodID(randomCls,"nextInt","(I)I");
	jmethodID getMd5ID = env->GetStaticMethodID(md5Cls,"getMD5","(Ljava/lang/String;)Ljava/lang/String;");
	jmethodID stringValueOfId = env->GetStaticMethodID(stringCls,"valueOf","(I)Ljava/lang/String;");
	jmethodID stringInitID = env->GetMethodID(stringCls,"<init>","(Ljava/lang/String;)V");
	jmethodID stringGetbytesID = env->GetMethodID(stringCls,"getBytes","(Ljava/lang/String;)[B");
	jmethodID nonestringGetbytesID = env->GetMethodID(stringCls,"getBytes","()[B");
	jmethodID strbyteInitId = env->GetMethodID(stringCls,"<init>","([BLjava/lang/String;)V");
	jmethodID base64EncodeId = env->GetStaticMethodID(base64Cls,"encode","([BI)[B");
	jobject random = env->NewObject(randomCls,ranId);
	int ran = env->CallIntMethod(random,ranNextIntID,32000);
	jstring ranStr = (jstring)env->CallStaticObjectMethod(stringCls,stringValueOfId,ran);
	jstring enencrypt_key = (jstring)(env->CallStaticObjectMethod(md5Cls,getMd5ID,ranStr));
	jobject stringKeyObj = env->NewObject(stringCls,stringInitID,enencrypt_key);
	jobject stringJstObj = env->NewObject(stringCls,stringInitID,txt);
	int ctr = 0;
	jstring jcode = env->NewStringUTF(code);
	jbyteArray keybytes = (jbyteArray)env->CallObjectMethod(stringKeyObj,stringGetbytesID,jcode);
	jbyte *keybyte = env->GetByteArrayElements(keybytes,0);
	jbyteArray jstbytes = (jbyteArray)env->CallObjectMethod(stringJstObj,stringGetbytesID,jcode);
	jbyte *jstbyte = env->GetByteArrayElements(jstbytes,0);
	jbyteArray ajstBytes = (jbyteArray)env->CallObjectMethod(stringJstObj,nonestringGetbytesID);
	int ajstSize = env->GetArrayLength(ajstBytes) * 2;
	int keySize = env->GetArrayLength(keybytes);
	int jstSize = env->GetArrayLength(jstbytes);
	jbyteArray bytes = env->NewByteArray(ajstSize);
	jbyte mbytes[ajstSize];
	int j = 0,i;
	for(i=0;i < jstSize;i++){
		if(ctr == keySize){
			ctr = 0;
		}
		mbytes[j] = keybyte[ctr];
		j++;
		jbyte temp = (jbyte)(jstbyte[i] ^ keybyte[ctr]);
		mbytes[j] = temp;
		j++;
		ctr++;
	}
	env->SetByteArrayRegion(bytes,0,ajstSize,mbytes);
	jbyteArray key1 = createKey(env,bytes,md5Cls,stringCls);
	jbyteArray finalKey =(jbyteArray)(env->CallStaticObjectMethod(base64Cls,base64EncodeId,key1,BASE64_DEFALUT));
	env->ReleaseByteArrayElements(keybytes,keybyte,0);
	env->ReleaseByteArrayElements(jstbytes,jstbyte,0);
	return (jstring)env->NewObject(stringCls,strbyteInitId,finalKey,jcode);
}

/**
 * 支付密码加密方式的 解密方法
 */
JNIEXPORT jstring JNICALL Java_com_tuanche_askforanswer_app_utils_Validator_decodeSafeSign
  (JNIEnv *env, jclass cls, jstring txt){
	jclass strCls = env->FindClass("java/lang/String");
	jclass base64Cls = env->FindClass("android/util/Base64");
	jclass md5Cls = env->FindClass("com/tuanche/askforanswer/app/utils/MD5Utils");
	jmethodID strInitId = env->GetMethodID(strCls,"<init>","(Ljava/lang/String;)V");
	jmethodID strByteInitId = env->GetMethodID(strCls,"<init>","([B)V");
	jmethodID base64DecodeId = env->GetStaticMethodID(base64Cls,"decode","([BI)[B");
	jmethodID nonestringGetbytesID = env->GetMethodID(strCls,"getBytes","()[B");
	jmethodID strByteInitByCodeId = env->GetMethodID(strCls,"<init>","([BLjava/lang/String;)V");


	jstring result;

	jobject txtObj = env->NewObject(strCls,strInitId,txt);

	jbyteArray txtBytes = (jbyteArray)(env->CallObjectMethod(txtObj,nonestringGetbytesID));
	jbyteArray txtDecodeBytes = (jbyteArray)(env->CallStaticObjectMethod(base64Cls,base64DecodeId,txtBytes,BASE64_DEFALUT));
	jbyteArray keyBytes = createKey(env,txtDecodeBytes,md5Cls,strCls);
	jstring str1 = (jstring)(env->NewObject(strCls,strByteInitId,keyBytes));
	jobject newTxtObj = env->NewObject(strCls,strInitId,str1);
	jbyteArray newTxtBytes = (jbyteArray)(env->CallObjectMethod(newTxtObj,nonestringGetbytesID));
	int size = env->GetArrayLength(newTxtBytes);
	jbyte *mTxtbyte = env->GetByteArrayElements(newTxtBytes,0);
	jbyteArray bytes = env->NewByteArray(size);
	jbyte *mBytes = env->GetByteArrayElements(bytes,0);
	int j = 0,i;
	for(i=0;i < size;i++){
		jbyte md5 = mTxtbyte[i];
		i++;
		if(i != size){
			mBytes[j++] = (jbyte)(mTxtbyte[i] ^ md5);
		}else{
			mBytes[j++] = md5;
		}
	}
	env->SetByteArrayRegion(bytes,0,size,mBytes);
	jstring jcode = env->NewStringUTF(code);
	result = (jstring)env->NewObject(strCls,strByteInitByCodeId,bytes,jcode);
	env->ReleaseByteArrayElements(newTxtBytes,mTxtbyte,0);
	env->ReleaseByteArrayElements(bytes,mBytes,0);
	return result;
}

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved){
	return JNI_VERSION_1_4;
}


