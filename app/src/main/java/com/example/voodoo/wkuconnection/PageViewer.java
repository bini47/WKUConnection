package com.example.voodoo.wkuconnection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PageViewer extends AppCompatActivity {
    WebView webView;
    String url;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView= (WebView) findViewById(R.id.viewer);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        Intent i= getIntent();
          url = i.getStringExtra("urlLink");
          title= i.getStringExtra("title");

        PageViewer.this.setTitle(title);

            webView.loadUrl(url);

            if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){

            webView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int OldscrollX, int OldscrollY) {
                    if(scrollY> OldscrollY && scrollY >0)
                        fab.hide();
                    if (scrollY<OldscrollY)
                        fab.show();
                }
            });

            }
            //share url

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent((Intent.ACTION_SEND));
                sharingIntent.setType("text/plain");
                String shareSubject=title;
                String sharebody=url;

                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
                sharingIntent.putExtra(Intent.EXTRA_TEXT,sharebody);

                startActivity(Intent.createChooser(sharingIntent, "share via"));



            }
        });
    }

}
