package com.FinalProject.Betelhem.EtNews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Login extends Fragment {
    View rootView;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_about, container, false);
        getActivity().setTitle("Login");

        webView= (WebView) rootView.findViewById(R.id.viewer);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        String url="https://et-news.000webhostapp.com/wp-login.php";


        webView.loadUrl(url);

        return rootView;
    }
}