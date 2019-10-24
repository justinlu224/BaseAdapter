package com.example.baseadapter.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 封裝sharePreferences
 * 在appliaction init 可以全域使用
 */
public class SPUtils {

    private static SPUtils spUtils = new SPUtils();
    private static SharedPreferences sharedPreferences = null;
    private SPUtils(){

    }

    public static void init(Context context,boolean isStore){
        if (isStore){
            sharedPreferences = context.getSharedPreferences("file name",Context.MODE_PRIVATE);
        }else {
            sharedPreferences = context.getSharedPreferences("file name",Context.MODE_PRIVATE);
        }

    }

    public static SPUtils getInstance(){
        if (sharedPreferences == null){
            throw new IllegalStateException("SharedPreferrnces not initialize");
        }
        return spUtils;
    }

    public static <E>void put(String key, E value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String){
            String strValue = (String)value;
            editor.putString(key,strValue).apply();
        }else if (value instanceof Long){
            Long lValue = (Long)value;
            editor.putLong(key,lValue).apply();
        }else if (value instanceof Float){
            Float fValue = (Float)value;
            editor.putFloat(key,fValue).apply();
        }else if (value instanceof Integer){
            int iValue = (Integer)value;
            editor.putInt(key,iValue).apply();
        }else if (value instanceof Boolean){
            boolean bValue = (Boolean)value;
            editor.putBoolean(key,bValue).apply();
        }
    }

   public static String getString(String key, String defultValue){
        return sharedPreferences.getString(key,defultValue);
   }
    public static int getInt(String key,int defultValue){
        return sharedPreferences.getInt(key,defultValue);
    }
    public static Long getLong(String key, long defultValue){
        return sharedPreferences.getLong(key,defultValue);
    }
    public static float getFloat(String key, float defultValue){
        return sharedPreferences.getFloat(key,defultValue);
    }
    public static boolean getBoolean(String key, boolean defultValue){
        return sharedPreferences.getBoolean(key,defultValue);
    }

    public static void remove(String key){
        sharedPreferences.edit().remove(key).apply();
    }

    public static void clear(){
        sharedPreferences.edit().clear().apply();
    }

}
