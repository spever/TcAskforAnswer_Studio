package com.tuanche.askforanswer.source.dialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import com.tuanche.askforanswer.R;
public class MyLoadingDialog extends Dialog {

    public MyLoadingDialog(Context context) {
        super(context, R.style.LoadingDialogStyle);
        init();
    }

    private void init() {
        setContentView(R.layout.webview_loading_content);
    }
}