package com.example.baseadapter.Utils;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

/**
 * json 解析類
 */
public class ConvertUtil {

    private ConvertUtil(){}

    private static final String TAG = ConvertUtil.class.getCanonicalName();

    public static Object fromJson(String json, Object obj) {
        Gson gson = new Gson();
        return gson.fromJson(json, obj.getClass());
    }

    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static  <T> T fromJson(String json,  Type typeOfT){
        Gson gson = new Gson();
        return gson.fromJson(json,typeOfT);
    }

    public static JSONObject getResponseStringObject(String strResponse) {
        JSONObject responseString = null;
        try {
            JSONObject jsonObject = new JSONObject(strResponse);
            responseString = jsonObject.getJSONObject("responseString");

        } catch (JSONException e) {
            LogUtlis.e(TAG,44, "JSONException: "+e.getMessage());
        }

        return responseString;
    }



}

