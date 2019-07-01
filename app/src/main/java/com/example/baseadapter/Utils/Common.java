package com.example.baseadapter.Utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.baseadapter.CallBack.NormalDialogCallBack;
import com.example.baseadapter.R;

public  class Common {

   public final static String PREF_NAME = "UserSeq";
   public final static String CONSUMER_SEQ = "consumerSeq";
   public final static String STORE_SEQ = "storeSeq";
   public final static String HAVE_CREDICARD = "haveCrediCard";//getBoolean
   public final static String DRIVER_TOKEN = "driverToken";
   public final static String NICK_NAME = "nickName";
   public final static String USER_QRCODE = "userQRCode";
   public final static String QR_CODE_STORE_ID = "QRCodeStoreID";
   public final static String QR_CODE_USER_TO_USER_ID = "QRCodeUserToUserID";
   public final static String TEN_MIN_TIME = "originalTime";
   public final static String IS_SEND_PHONE_MESSAGE = "isSendPhoneMessage";
   public final static String RESPONSE_QR_CODE_STORE_ID = "";
   public final static String IS_LOGIN = "isLogin";//getBoolean
   public final static String IS_DEBUG = "isDebug";//getBoolean
   public final static String APV_TYPE = "apvType";//getBoolean 檢查是否經過實名驗證(詳細註冊)
   public final static String IS_FIRST_USE = "isFirstUse";//getBoolean

   private NormalDialogCallBack normalDialogCallBack;
   private Context context;

   public Common(){

   }
   public Common(Context context){

   }
   public Common(Context context, NormalDialogCallBack normalDialogCallBack){
      this.normalDialogCallBack = normalDialogCallBack;
      this.context = context;
   }

   /**
    * @param title  標題
    * @param message  內文
    * @param buttonNum 按鈕數量 最多2個
    * @param leftButton  左邊按鈕
    * @param rightButton 右邊按鈕
    */
   public void noemalDialog(String title, String message,int buttonNum, String leftButton, String rightButton) {
      final Dialog dialog = new Dialog(context, R.style.NormalDialogStyle);
      View view = View.inflate(context, R.layout.dialog_normal, null);
      Button cancel = view.findViewById(R.id.btnCancel);
      Button confirm =  view.findViewById(R.id.btnConfirm);
      cancel.setText(leftButton);
      confirm.setText(rightButton);
      TextView tvTitle = view.findViewById(R.id.tvTitle);
      TextView tvMessage = view.findViewById(R.id.tvMessage);
      tvTitle.setText(title);
      tvMessage.setText(message);
//      if (!title.equals("") && !message.equals("")){
//         tvTitle.setText(title);
//         tvMessage.setText(message);
//      }else if (title.equals("")){
//         tvTitle.setVisibility(View.GONE);
//         tvMessage.setText(message);
//      }else if (message.equals("")){
//         tvMessage.setVisibility(View.GONE);
//         tvTitle.setText(title);
//      }

      dialog.setContentView(view);
      //設定點擊對話框外及返回鍵不會消失
      dialog.setCancelable(false);
      //設置對話框大小
      view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
      Window dialogWindow = dialog.getWindow();
      WindowManager.LayoutParams lp = dialogWindow.getAttributes();
      lp.width = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.75f);
      lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
      lp.gravity = Gravity.CENTER;
      dialogWindow.setAttributes(lp);
      if (buttonNum == 2){
         cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               normalDialogCallBack.onNormalDialogCancelClick();
               dialog.dismiss();
            }
         });
         confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               normalDialogCallBack.onNormalDialogConfirmClick();
               dialog.dismiss();
            }
         });
      }else if (buttonNum == 1){
         cancel.setVisibility(View.GONE);
         confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               normalDialogCallBack.onNormalDialogConfirmClick();
               dialog.dismiss();
            }
         });
      }
      if (!((Activity)context).isFinishing()){
         dialog.show();
      }
   }

   public  String getVersionName() throws Exception
   {
      // 获取packagemanager的实例
      PackageManager packageManager = context.getPackageManager();
      // getPackageName()是你当前类的包名，0代表是获取版本信息
      PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
      String version = packInfo.versionName;
      return version;
   }

   public String getNccCode(String nccName){
      switch (nccName){
//         5812 餐飲
//         5977 美妝
//         5699 服飾
//         7011 住宿
//         5399 生活購物
//         5732 3C家電
//         5499 食品
//         7999 休閒娛樂
//         4789 汽機車
         case "餐飲":
            return "5812";
         case "美妝":
            return "5977";
         case "服飾":
            return "5699";
         case "住宿":
            return "7011";
         case "生活購物":
            return "5399";
         case "3C家電":
            return "5732";
         case "食品":
            return "5499";
         case "休閒娛樂":
            return "7999";
         case "汽機車":
            return "4789";
            default:
               return "";

      }

   }


}
