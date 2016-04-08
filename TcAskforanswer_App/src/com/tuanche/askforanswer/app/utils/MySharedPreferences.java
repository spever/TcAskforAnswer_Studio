package com.tuanche.askforanswer.app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class MySharedPreferences {
	
	public static SharedPreferences mySharePeference;
	private static final String  TUANCHE_PERFERENCES = "tuanche_master";
	
	public  static final String CITY = "city";
	public  static final String TELEPHONE = "telephone";
	public  static final String GROUPBUY_NUM = "groupbuynum";
	public  static final String MEMBER = "member";
	
	public static final String USER_ID = "user_id";
	public static final String USER_PHONE = "user_phone";
	
	
	public static void  save(Context context,String key,String value){
		if(mySharePeference==null){
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES, Context.MODE_PRIVATE);
		}
		Editor editor = mySharePeference.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getValueByKey(Context context,String key) {
		if (mySharePeference == null) {
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		String value = mySharePeference.getString(key, null);
		return value;

	}
	
	public static void remove(Context context,String key){
		if(mySharePeference==null){
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		Editor editor = mySharePeference.edit();
		editor.remove(key);
		editor.commit();
	}
	//清除数据
	public static void clear(Context context){
		if(mySharePeference==null){
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		Editor editor = mySharePeference.edit();
		editor.clear();
		editor.commit();
	}
	
	
	
	@SuppressWarnings("unused")
	public static <T>  String serialize(T t) throws IOException {  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(  
                byteArrayOutputStream);  
        objectOutputStream.writeObject(t);  
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");  
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");  
        objectOutputStream.close();  
        byteArrayOutputStream.close();  
        Log.d("serial", "serialize str =" + serStr);  
        return serStr;  
    }  
  
    /** 
     * 反序列化对象 
     * @param <T>
     *  
     * @param str 
     * @return 
     * @throws IOException 
     * @throws ClassNotFoundException 
     */  
    @SuppressWarnings("unused")
	public static <T> T deSerialization(String str) throws IOException,  
            ClassNotFoundException {  
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");  
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(  
                redStr.getBytes("ISO-8859-1"));  
        ObjectInputStream objectInputStream = new ObjectInputStream(  
                byteArrayInputStream);  
        @SuppressWarnings("unchecked")
		T t = (T) objectInputStream.readObject();  
        objectInputStream.close();  
        byteArrayInputStream.close();  
        return t;  
    }

	/**
	 * @param context
	 * @param key
	 *            ��
	 * @param defValue
	 *            ȱʡֵ
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
									 boolean defValue) {
		if (mySharePeference == null) {
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		return mySharePeference.getBoolean(key, defValue);
	}
	public static void putBoolean(Context context, String key, boolean value) {
		if (mySharePeference == null) {
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		mySharePeference.edit().putBoolean(key, value).commit();
	}
	public static Integer getInt(Context context, String key,
									 Integer defValue) {
		if (mySharePeference == null) {
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		return mySharePeference.getInt(key,defValue);
	}
	public static void putInt(Context context, String key, Integer value) {
		if (mySharePeference == null) {
			mySharePeference = context.getSharedPreferences(TUANCHE_PERFERENCES,
					Context.MODE_PRIVATE);
		}
		mySharePeference.edit().putInt (key, value).commit();
	}
}
