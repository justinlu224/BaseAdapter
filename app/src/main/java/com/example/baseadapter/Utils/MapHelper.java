package com.example.baseadapter.Utils;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;

import com.example.baseadapter.CallBack.OnDownLoadCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 地址轉經緯度
 */

public class MapHelper extends MyLocation {

    private final String TAG = MapHelper.class.getCanonicalName();
    private Context context;
    private String addressString;
    private LatLng postion;
    private Location userLocation;
    private OnDownLoadCallback onDownLoadCallback;

    public MapHelper(){
    }
    public MapHelper(Context context, OnDownLoadCallback onDownLoadCallback){
        this.context = context;
        this.onDownLoadCallback = onDownLoadCallback;
    }
    public MapHelper(Context context){
        this.context = context;
    }
    public LatLng getLan(String addressString){
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressLocation;
            try {
                addressLocation = geoCoder.getFromLocationName(addressString, 1);
                double latitude = addressLocation.get(0).getLatitude();
                double longitude = addressLocation.get(0).getLongitude();
                LogUtlis.d(TAG,33,"latitude = "+latitude+", longitude = "+longitude);
                postion = new LatLng(latitude,longitude);

            } catch (IOException e) {
                e.printStackTrace();
            }
        return postion;
    }

    public String transformLanToStr(double lat, double lon){
//        lstAddress.get(0).getCountryName();  //台灣省
//        lstAddress.get(0).getAdminArea();  //台北市
//        lstAddress.get(0).getLocality();  //中正區
//        lstAddress.get(0).getThoroughfare();  //信陽街(包含路巷弄)
//        lstAddress.get(0).getFeatureName();  //會得到33(號)
//        lstAddress.get(0).getPostalCode();  //會得到100(郵遞區號)
        LogUtlis.d(TAG,52,"transformLanToStr: ");
        String returnAddress = "";
        Geocoder gc = new Geocoder(context, Locale.TRADITIONAL_CHINESE);
        List<Address> lstAddress = null;
        try {
            lstAddress = gc.getFromLocation(lat, lon, 1);
           returnAddress =lstAddress.get(0).getLocality();
           return returnAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnAddress;
    }


    public void getMyLocation() {
        MyLocation myLocation = new MyLocation();
      boolean lo =  myLocation.getLocation(context, locationResult);
      LogUtlis.d(TAG,76,"myLocation:"+lo);
    }

    LocationResult locationResult = new LocationResult() {
        @Override
        public void gotLocation(Location location) {
            if(location != null){
                LogUtlis.d(TAG, 75, "locationResult: " + location.getLatitude() + "," + location.getLongitude());
                userLocation = location;
                onDownLoadCallback.onComplete(location);
            }
        }
    };


    public String transformCity(double lat, double lon){
        LogUtlis.d(TAG,52,"transformLanToStr: ");
        String returnAddress = "";
        Geocoder gc = new Geocoder(context, Locale.TRADITIONAL_CHINESE);
        List<Address> lstAddress = null;
        try {
            lstAddress = gc.getFromLocation(lat, lon, 1);
            returnAddress = lstAddress.get(0).getAdminArea();

            return returnAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnAddress;
    }

    public List<Address> transformAddress(double lat, double lon){
//        lstAddress.get(0).getCountryName();  //台灣省
//        lstAddress.get(0).getAdminArea();  //台北市
//        lstAddress.get(0).getLocality();  //中正區
//        lstAddress.get(0).getThoroughfare();  //信陽街(包含路巷弄)
//        lstAddress.get(0).getFeatureName();  //會得到33(號)
//        lstAddress.get(0).getPostalCode();  //會得到100(郵遞區號)
        LogUtlis.d(TAG,52,"transformLanToStr: ");
        Geocoder gc = new Geocoder(context, Locale.TRADITIONAL_CHINESE);
        List<Address> lstAddress = null;
        try {
            lstAddress = gc.getFromLocation(lat, lon, 1);
            return lstAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstAddress;
    }

    public float getDistance(double startLat, double startLon, double endLat, double endLon){
        float[] results = new float[3];//创建返回数组
        //调用Location静态方法
        Location.distanceBetween(startLat, startLon, endLat,  endLon, results);
        float result = results[0];
//        Toast.makeText(context, String.valueOf(result), Toast.LENGTH_SHORT).show();
        return result;
    }

    public void goGoogleNavigation(double stalat, double stalon,double endLat, double endLon) {
        double startLatitude = stalat;
        double startLongitude = stalon;

        double endLatitude = endLat;
        double endLongitude = endLon;

        String saddr = "saddr=" + startLatitude + "," + startLongitude;
        String daddr = "daddr=" + endLatitude + "," + endLongitude;
        String uriString = "http://maps.google.com/maps?" + saddr + "&" + daddr;

        Uri uri = Uri.parse(uriString);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivities(new Intent[]{intent});
        }catch (RuntimeException e) {
            LogUtlis.d(TAG, 158, "RuntimeException = " + e);
        }
    }

}

