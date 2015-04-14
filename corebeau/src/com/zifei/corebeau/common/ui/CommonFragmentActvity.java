package com.zifei.corebeau.common.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

/**
 * Created by im14s_000 on 2015/3/25.
 */
public abstract class CommonFragmentActvity extends FragmentActivity implements WaitableDialog{

    private Dialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    /**
     * show wait dialog (Cancelable)
     */
    public void showWaitDialog(String message, boolean isCancelable, DialogInterface.OnCancelListener listener) {
        if (isFinishing()) {
            return;
        }
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
            waitDialog = null;
        }

//        waitDialog = new Dialog(this, R.style.dialogProgress);
        waitDialog = new Dialog(this);
        waitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        waitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        waitDialog.setContentView(R.layout.dialog_progress);
//        ((TextView) waitDialog.findViewById(R.id.message)).setText(message);
        waitDialog.setCanceledOnTouchOutside(false);
        waitDialog.setCancelable(isCancelable);
        waitDialog.setOnCancelListener(listener);
        waitDialog.show();
    }

    @Override
    public void showWaitDialog(String message) {

    }

    @Override
    public void dismissWaitDialog() {
        if (waitDialog != null && waitDialog.isShowing() && !this.isFinishing()) {
            waitDialog.dismiss();
        }
        waitDialog = null;
    }
}
