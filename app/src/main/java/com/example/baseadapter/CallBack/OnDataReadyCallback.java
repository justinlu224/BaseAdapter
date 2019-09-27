package com.example.baseadapter.CallBack;

import com.example.baseadapter.model.BaseData;

public interface OnDataReadyCallback {
    void onDataReady(BaseData<?> datas);

    void onFailure(Throwable t, int status, String msg);
}
