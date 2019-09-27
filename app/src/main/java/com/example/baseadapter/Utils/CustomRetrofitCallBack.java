package com.example.baseadapter.Utils;

import com.example.baseadapter.model.ErrorBody;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class CustomRetrofitCallBack<T> implements Callback<T> {
    private static final String TAG = CustomRetrofitCallBack.class.getCanonicalName();
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && response.errorBody() != null){
         ErrorBody errorBody = errorBody(response.errorBody());
            if (errorBody.getStatus()>=500 && errorBody.getStatus() <600){
                onError(errorBody.getStatus(),"錯誤500",null);
            }else if (errorBody.getStatus()>=400 && errorBody.getStatus() <500){
                onError(errorBody.getStatus(),"錯誤400",null);
            }
        }else{
            onSuccess(response.body());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onError(0,"連線異常",t);
    }

    public abstract void onSuccess(T data);

    public abstract void onError(int status,String msg, Throwable t);

    private ErrorBody errorBody(ResponseBody responseBody){
        ErrorBody errorBody = null;
        try {
            errorBody = ConvertUtil.fromJson(responseBody.string(),ErrorBody.class);
        } catch (IOException e) {
            LogUtlis.d(TAG,113,"IOException: "+e.getMessage());
        }
        LogUtlis.d(TAG,115,".isSuccessful:: "+errorBody.getMessage());
        return errorBody;
    }
}
