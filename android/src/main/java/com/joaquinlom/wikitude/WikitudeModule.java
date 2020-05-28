package com.joaquinlom.wikitude;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.wikitude.architect.ArchitectView;
import com.wikitude.common.permission.PermissionManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class WikitudeModule extends ReactContextBaseJavaModule {

    private WikitudeView wikitudeView;
    private WikitudeViewManager wikiManager;


  public WikitudeModule(ReactApplicationContext reactContext,WikitudeViewManager wikitude) {
      super(reactContext);
      if(wikitude != null){
        //As first run it should be null
        wikiManager = wikitude;
        wikiManager.setActivity(getCurrentActivity());
      }else{
          Log.d("WikitudeManager", "WikitudeViewManager is null");
      }
  }

  @Override
  public String getName() {
    return "WikitudeModule";
  }

  @ReactMethod
  public void setUrl(String url) {
      //getWikitudeView().setUrl(url);
      if(wikiManager != null){
        wikiManager.setUrl(url);
          //Log.d("WikitudeModule", wikitudeView.toString());
      }
  }
  @ReactMethod
  public void injectLocation(ReadableMap location){
    if(wikiManager != null){
      Double latitude = location.hasKey("latitude")?location.getDouble("latitude"): 0.0;
      Double longitude = location.hasKey("longitude")?location.getDouble("longitude"): 0.0;
      wikiManager.setLocation(latitude,longitude);
    }
  }

  @ReactMethod
  public void callJavascript(String js){
    if(wikiManager != null){
      wikiManager.callJavascript(js);
    }
  }

  @ReactMethod
  public void resumeAR(){
     // getWikitudeView().onResume();
      wikiManager.resumeAR();
  }

  @ReactMethod
  public void stopAR(){
        // getWikitudeView().onResume();
      wikiManager.stopAR();
  }

    public WikitudeView getWikitudeView() {
      if(wikitudeView == null){
          wikitudeView = wikiManager.getWikitudeView();
      }
      return wikitudeView;
    }

    @ReactMethod
  public void openNewWindow(String architectWorldURL, boolean hasGeolocation, boolean hasImageRecognition, boolean hasInstantTracking, String wikitudeSDKKey)
  {

  }

}