package com.doing.notification;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

public class ResultActivity extends Activity {

    private static final String TAG = "ResultActivity";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Log.d(TAG, "onCreate: " + TAG);

        WebView mWebView = (WebView) findViewById(R.id.webview);
        WebViewClient client = new MyWebViewClient();
        mWebView.setWebViewClient(client);
        WebSettings settings = mWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWebView.setDownloadListener(new DownloadListener() {
            /**
             * @param url 该参数即无法解析的链接
             * @param contentDisposition 下载文件的各种描述，如名称
             * @param contentLength 下载文件大小
             */
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //比如下载链接是WebView不支持访问的功能，我们可以拿到这个连接作进一步下载处理
            }
        });

        CookieManager manager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            manager.removeAllCookies(null);
            manager.flush();//强制同步
        } else {
            manager.removeAllCookie();
            CookieSyncManager syncManager = CookieSyncManager.createInstance(this);
            syncManager.sync();//同步，把内存中的Cookie同步存储到硬盘中
            syncManager.startSync();//同步机制，每隔一段时间同步一次
        }
        String cookie = manager.getCookie("url");//获取Url中的Cookie值
        manager.setCookie("url", cookie);//设置Url中的Cookie值

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && BuildConfig.DEBUG) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: " + TAG);
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            Uri url = request.getUrl();
            if (url.getPath().contains("png")) {
                return new WebResourceResponse(null, null, new ByteArrayInputStream(new byte[0]));
            }
            return super.shouldInterceptRequest(view, request);

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            view.loadData("Html错误页面内容", "text/html", null);
        }

        /**
         * 页面开始加载时
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        /**
         * 页面加载完成时
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        /**
         * 页面可见时
         */
        @Override
        public void onPageCommitVisible(WebView view, String url) {
            super.onPageCommitVisible(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        /**
         * 拦截JS中的alert()函数
         */
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            result.confirm();//必须调用，否则拦截后JS中alert()不会结束
            return true;
        }

        /**
         *拦截JS中的prompt()函数
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            result.confirm("result");//必须调用，该返回结果作为JS中confirm函数的返回结果
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }
}
