package com.example.santmani;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;


    //Normal Code
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Santmani");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.action_bar_bg));


        String url = "https://santmani.in/";
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                setTitle("Loading...");
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                progressBar.setProgress(100);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                /*return super.shouldOverrideUrlLoading(view, request);*/

                final Uri uri = request.getUrl();
                if (uri.toString().startsWith("mailto:")) {

                    //Handle mail Urls
                    startActivity(new Intent(Intent.ACTION_SENDTO, uri));
                } else if (uri.toString().startsWith("tel:")) {

                    //Handle telephony Urls
                    startActivity(new Intent(Intent.ACTION_DIAL, uri));
                } else if (uri.toString().startsWith("https://www.facebook.com/")) {

                    Intent facebook = new Intent(Intent.ACTION_VIEW, uri);
                    facebook.setPackage("com.facebook.android");
                    try {
                        startActivity(facebook);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }
                } else if (uri.toString().startsWith("https://twitter.com")) {

                    Intent twitter = new Intent(Intent.ACTION_VIEW, uri);
                    twitter.setPackage("com.twitter.android");
                    try {
                        startActivity(twitter);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }
                } else if (uri.toString().startsWith("https://drive.google.com/")) {

                    Intent drive = new Intent(Intent.ACTION_VIEW, uri);
                    drive.setPackage("com.drive.google.android");
                    try {
                        startActivity(drive);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }
                } else if (uri.toString().startsWith("https://api.whatsapp.com/")) {

                    Intent whatsapp = new Intent(Intent.ACTION_VIEW, uri);
                    whatsapp.setPackage("com.whatsapp.android");
                    try {
                        startActivity(whatsapp);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }
                } else if (uri.toString().startsWith("https://www.google.com/maps/")) {

                    Intent maps = new Intent(Intent.ACTION_VIEW, uri);
                    maps.setPackage("com.maps.android");
                    try {
                        startActivity(maps);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }

                } else if (uri.toString().startsWith("https://play.google.com/")) {

                    Intent playConsole = new Intent(Intent.ACTION_VIEW, uri);
                    playConsole.setPackage("com.android.application");
                    try {
                        startActivity(playConsole);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }

                } else if (uri.toString().startsWith("https://www.instagram.com/")) {

                    Intent playConsole = new Intent(Intent.ACTION_VIEW, uri);
                    playConsole.setPackage("com.instagram.android");
                    try {
                        startActivity(playConsole);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }

                } else if (uri.toString().startsWith("https://www.linkedin.com/")) {

                    Intent playConsole = new Intent(Intent.ACTION_VIEW, uri);
                    playConsole.setPackage("com.linkedin.android");
                    try {
                        startActivity(playConsole);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }

                } else if (uri.toString().startsWith("https://github.com/")) {

                    Intent playConsole = new Intent(Intent.ACTION_VIEW, uri);
                    playConsole.setPackage("com.github.android");
                    try {
                        startActivity(playConsole);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.valueOf(uri))));
                    }

                } else {
                    //Handle Web Urls
                    view.loadUrl(uri.toString());
                }
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Objects.requireNonNull(getSupportActionBar()).setTitle(title);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            //super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Forward:
                goForward();
                break;

            case R.id.Reload:
                webView.reload();
                break;

            case R.id.Help:
                Intent intent = new Intent(getApplicationContext(), Activity_About.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void goForward() {

        if (webView.canGoForward()) {
            webView.goForward();
        } else {
            Toast.makeText(this, "Cant go forward!", Toast.LENGTH_LONG).show();
        }
    }
}