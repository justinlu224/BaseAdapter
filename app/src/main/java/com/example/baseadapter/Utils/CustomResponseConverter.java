package com.example.baseadapter.Utils;

import android.support.annotation.Nullable;

import com.example.baseadapter.model.ApiException;
import com.example.baseadapter.model.ErrorBody;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;


import org.json.JSONObject;

import java.io.IOException;



import okhttp3.ResponseBody;
import retrofit2.Converter;

public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public CustomResponseConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
        LogUtlis.d("gson", 25, "errorBode: CustomResponseConverter");
    }


    @Nullable
    @Override
    public T convert(ResponseBody value) throws IOException {
        String s = "";
        ErrorBody errorBody = null;
        ApiException apiException = null;
        LogUtlis.d("gson", 35, "errorBode: CustomResponseConverter");
        try {
            String body = value.string();
            JSONObject jsonObject = new JSONObject(body);
            LogUtlis.d("gson", 37, "errorBode: " + jsonObject.toString());
            if (jsonObject.has("status") && jsonObject.get("message").equals("GENERAL")) {
                LogUtlis.d("gson", 38, "errorBode: " );
                errorBody = ConvertUtil.fromJson(body, ErrorBody.class);
                LogUtlis.d("gson", 40, "errorBode: " + errorBody.getMessage());
                apiException = new ApiException(errorBody.getMessage(),errorBody.getStatus());
                throw apiException;
            } else {
                LogUtlis.d("gson", 43, "errorBode: " );
                T result = adapter.fromJson(body);
                LogUtlis.d("gson", 52, "errorBode: " + result);
                return result;
            }
        }catch (ApiException e){
            LogUtlis.d("gson", 51, "errorBode: " + apiException.getMsg());
            throw apiException;
        }catch (Exception e) {
            LogUtlis.d("gson", 58, "errorBode: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
