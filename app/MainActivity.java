package com.hesabman.app;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.ValueCallback;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
public class MainActivity extends Activity {
  private WebView webView;
  private ValueCallback<Uri[]> fileCallback;
  private static final int FILE_REQUEST=42;
  @Override public void onCreate(Bundle state){ super.onCreate(state);
    getWindow().setStatusBarColor(0xff080b12); getWindow().setNavigationBarColor(0xff080b12);
    webView=new WebView(this); webView.setBackgroundColor(0xff080b12); setContentView(webView);
    WebSettings s=webView.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
    s.setAllowFileAccess(true); s.setAllowContentAccess(true);
    webView.setWebViewClient(new WebViewClient());
    webView.setWebChromeClient(new WebChromeClient(){
      @Override public boolean onShowFileChooser(WebView view,ValueCallback<Uri[]> callback,FileChooserParams params){
        if(fileCallback!=null)fileCallback.onReceiveValue(null); fileCallback=callback;
        try { startActivityForResult(params.createIntent(),FILE_REQUEST); return true; }
        catch(Exception ex){ fileCallback=null; return false; }
      }
    });
    webView.loadUrl("file:///android_asset/index.html");
  }
  @Override protected void onActivityResult(int request,int result,Intent data){super.onActivityResult(request,result,data);
    if(request==FILE_REQUEST && fileCallback!=null){ fileCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result,data)); fileCallback=null; }
  }
  @Override public void onBackPressed(){ if(webView!=null && webView.canGoBack())webView.goBack(); else super.onBackPressed(); }
}
