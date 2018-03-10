//Phil Heroux
//CS 478 Project 2
//Song Playlist using menus

package com.example.phil.project2_phillip_heroux;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;


/**
 * Created by Phil on 2/28/2018.
 */

public class VideoPlayActivity extends AppCompatActivity {
    private WebView mywebview;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize setup
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayactivity);
        //get intent and create a webview
        intent = getIntent();
        mywebview = (WebView) findViewById(R.id.webview);

        //initialize webview, set link in webview to intent string extra
        mywebview.setWebViewClient(new WebViewClient());
        WebSettings websettings = mywebview.getSettings();

        mywebview.getSettings().setJavaScriptEnabled( true );
        String link = intent.getExtras().getString("link");
        mywebview.loadUrl(link);

    }
}
