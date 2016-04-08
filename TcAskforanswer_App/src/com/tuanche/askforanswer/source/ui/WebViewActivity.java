package com.tuanche.askforanswer.source.ui;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tuanche.askforanswer.R;
import com.tuanche.askforanswer.app.utils.Utils;
import com.tuanche.askforanswer.source.dialog.MyLoadingDialog;

/**
 * 积分兑换规则的web页面
 * Created by Administrator on 2015/9/16 0016.
 */
public class WebViewActivity extends Activity {

    private WebView webView;
    private MyLoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        String webUrl=getIntent().getExtras().getString("webUrl");

        webView = (WebView) findViewById(R.id.wv_webview);
       // findViewById(R.id.)
        Utils.settingsWebView(this, webView);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(webUrl);
        dialog = new MyLoadingDialog(this);
        dialog.show();
    }
    class  MyWebChromeClient extends WebChromeClient
    {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress==100){
                dialog.dismiss();
            }
        }
    }
    class  MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           if(url!=null){
               view.loadUrl(url);
           }
            return true;
        }
    }
}
