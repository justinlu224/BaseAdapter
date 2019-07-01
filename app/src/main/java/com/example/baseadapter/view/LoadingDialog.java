package com.example.baseadapter.view;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.example.baseadapter.R;
import com.example.baseadapter.Utils.ScreenSizeUtils;

public class LoadingDialog {
    Dialog mLoadingDialog;
    TextView tvLoaging;

    public LoadingDialog(Context context, RequestManager requestManager) {

    View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_view, null);

    ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.loadingDailog);
    ImageView gifView = view.findViewById(R.id.ivLoagingCat);
    tvLoaging = view.findViewById(R.id.tvLoaging);

        requestManager.load(R.drawable.cat_fly).into(gifView);

    mLoadingDialog = new Dialog(context, R.style.NormalDialogStyle);

        mLoadingDialog.setCancelable(false);

        mLoadingDialog.setContentView(layout, new ConstraintLayout.LayoutParams(
    ConstraintLayout.LayoutParams.MATCH_PARENT,
    ConstraintLayout.LayoutParams.MATCH_PARENT));
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
    Window dialogWindow = mLoadingDialog.getWindow();
    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
    lp.width = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.5f);
    lp.height = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.5f);
    lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
}

    public void show(){
        mLoadingDialog.show();
    }

    public void close(){
        if (mLoadingDialog!=null) {
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }

    public void setTitle(String title){
        this.tvLoaging.setText(title);
    }

}
