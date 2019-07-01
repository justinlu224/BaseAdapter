package com.example.baseadapter.CallBack;

import android.location.Location;

public interface OnDownLoadCallback {
    void onComplete(Location location);
    void onFail();
}
