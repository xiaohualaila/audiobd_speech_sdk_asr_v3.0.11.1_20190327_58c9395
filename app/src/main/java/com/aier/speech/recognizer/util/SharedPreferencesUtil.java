package com.aier.speech.recognizer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

public class SharedPreferencesUtil {

	/**
	 * 清除共享数据
	 * 
	 * @param context
	 * @param fileName
	 */

	public static void deleteShared(Context context, String fileName) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 存String�?
	 * 
	 * @param context
	 * @param value
	 */
	public static void putString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * @param context
	 * @return
	 */
	public static String getString(Context context, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, value);

	}

	/**
	 * 存boolean�?
	 * 
	 * @param context
	 * @param value
	 */
	public static void putBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 取boolean�?
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, value);

	}

	/**
	 * 存int�?
	 * 
	 * @param context
	 * @param value
	 */
	public static void putInt(Context context, String key, Integer value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 取int�?
	 * 
	 * @param context
	 * @param value
	 * @return
	 */
	public static Integer getInt(Context context, String key, Integer value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"username", Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, value);

	}

	/**
	 * 存信�?
	 * 
	 * @param context
	 * @param fileName
	 * @param map
	 * @return
	 */
	public boolean saveSharedPreference(Context context, String fileName,
                                        Map<String, Object> map) {
		if(map==null)
			return false;
		boolean falg = false;
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
        
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object object = entry.getValue();
			if (object instanceof Boolean) {
				Boolean new_name = (Boolean) object;
				editor.putBoolean(key, new_name);
			} else if (object instanceof Integer) {
				Integer new_name = (Integer) object;
				editor.putInt(key, new_name);
			} else if (object instanceof Float) {
				Float new_name = (Float) object;
				editor.putFloat(key, new_name);
			} else if (object instanceof Long) {
				Long new_name = (Long) object;
				editor.putLong(key, new_name);
			} else if (object instanceof String) {
				String new_name = (String) object;
				editor.putString(key, new_name);
			}
		}
		falg = editor.commit();
		return falg;
	}

	/**
	 * 取信�?
	 * 
	 * @param context
	 * @param fileName
	 * @return map
	 */
	public Map<String, ?> readSharedPreference(Context context, String fileName) {
		SharedPreferences preferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		Map<String, ?> map = preferences.getAll();
		return map;
	}

	/**
	 * 存int�?
	 * 
	 * @param context
	 * @param fileName
	 *            ---key
	 * @param value
	 */
	public static void putInt(Context context, String fileName, String key,
                              Integer value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 取int�?
	 * 
	 * @param context
	 * @param fileName
	 *            ---key
	 * @param value
	 * @return
	 */
	public static Integer getInt(Context context, String fileName, String key,
                                 Integer value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, value);

	}
	/**
	 * @param value
	 */
	public static void putString(Context context, String fileName, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString(key,value);
		editor.commit();
	}

	/**
	 * 去String�?
	 * 
	 * @param context
	 *            ---key
	 * @param value
	 * @return
	 */
	public static String getString(Context context, String fileName, String key, String value) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				fileName, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, value);

	}

}
