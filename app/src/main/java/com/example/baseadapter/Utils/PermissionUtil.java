package com.example.baseadapter.Utils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

    public static final String[] CAMERA_PERSMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 是否所有權限都同意
     *
     * @param permissions 申請權限清單
     * @return 是否所有權限都同意
     */
    public static boolean isAllPermissionGranted(Context context, String[] permissions) {
        return getDeniedPermissions(context, permissions).length == 0;
    }

    /**
     * 獲取被拒絕的權限
     *
     * @param permissions 申請權限清單
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions) {
        if (null != permissions && permissions.length > 0) {
            List<String> deniedPermissionList = new ArrayList<>();

            boolean[] permissionsStatus = checkPermissionsStatus(context, permissions);
            for (int i = 0; i < permissions.length; i++) {
                if (!permissionsStatus[i]) {
                    deniedPermissionList.add(permissions[i]);
                }
            }

            String[] deniedPermissions = new String[deniedPermissionList.size()];
            return deniedPermissionList.toArray(deniedPermissions);
        } else {
            throw new IllegalArgumentException("Can't empty");
        }
    }

    /**
     * 檢查所有權限是否同意
     *
     * @param permissions 申請權限清單
     * @return 權限狀態
     */
    public static boolean[] checkPermissionsStatus(Context context, String[] permissions) {
        if (null != permissions && permissions.length > 0) {
            boolean[] permissionsStatus = new boolean[permissions.length];

            for (int i = 0; i < permissions.length; i++) {
                boolean permissionStatus = checkPermissionStatus(context, permissions[i]);
                permissionsStatus[i] = permissionStatus;
            }
            return permissionsStatus;
        } else {
            throw new IllegalArgumentException("Can't empty");
        }
    }

    /**
     * 檢查權限是否同意
     *
     * @param permission 申請的權限
     */
    public static boolean checkPermissionStatus(Context context, String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, permission);
    }

    public static void requestNecessaryPermissions(AppCompatActivity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }



    private String[] galleryPermissions = {
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    private String[] cameraPermissions = {
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    public String[] getGalleryPermissions() {
        return galleryPermissions;
    }

    public String[] getCameraPermissions() {
        return cameraPermissions;
    }

    public boolean verifyPermissions(Context context, String[] grantResults) {
        for (String result : grantResults) {
            if (ActivityCompat.checkSelfPermission(context, result) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public boolean checkMarshMellowPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    public static void showPermissionDialog(Context mContext, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("沒有權限");
        builder.setMessage(msg);
        builder.setPositiveButton("確認", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
            intent.setData(uri);
            (mContext).startActivity(intent);
        });

        builder.setNegativeButton("取消", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        builder.show();
    }
}
