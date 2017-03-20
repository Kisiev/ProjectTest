package com.example.a1.projecttest;


import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.io.InputStream;

@EActivity(R.layout.activity_map_yandex)
public class YandexMapActivity extends Activity {

    @ViewById(R.id.webView)
    WebView webView;

    @AfterViews
    void main(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        try {
            InputStream is = getAssets().open("assets/index.html");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String htmlText = new String(buffer);
            webView.loadDataWithBaseURL(
                    "http://ru.yandex.api.yandexmapswebviewexample.ymapapp",
                    htmlText,
                    "text/html",
                    "UTF-8",
                    null
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
