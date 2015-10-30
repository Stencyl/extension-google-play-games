package com.stencyl.GoogleServices;

import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.util.Log;

import com.google.android.gms.ads.*;

import dalvik.system.DexClassLoader;

public class AdMob extends Extension
{
	private static LinearLayout layout;
    private static AdRequest adReq;
    private static HaxeObject callback;
    
    private static boolean initialized = false;

	static AdView adView;
    static InterstitialAd interstitial;
    
    /// --- Banners --- ///

	static public void init(final HaxeObject cb, final String adUnitID, final String adUnitID1, final int position)
	{
        callback = cb;
        
		mainActivity.runOnUiThread(new Runnable()
		{
        	public void run()
			{
                AdRequest.Builder builder = new AdRequest.Builder();
                builder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
                adReq = builder.build();
                //banner
                if(adUnitID!=""){
                    layout = new LinearLayout(mainActivity);
                    setBannerPosition(position);
                    
                    adView = new AdView(mainActivity);
                    adView.setAdUnitId(adUnitID);
                    adView.setAdSize(AdSize.SMART_BANNER);
                    adView.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            showBanner();
                            callback.call("onAdmobLoaded", new Object[] {});
                        }
                        public void onAdFailedToLoad(int errorcode) {
                            callback.call("onAdmobFailed", new Object[] {});
                        }
                        public void onAdClosed(){
                            callback.call("onAdmobClosed", new Object[] {});
                        }
                        public void onAdOpened(){
                            callback.call("onAdmobOpened", new Object[] {});
                        }
                    });
                    
                    mainActivity.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
                    layout.addView(adView);
                    layout.bringToFront();
                    
                    adView.loadAd(adReq);
            
                }
                //Interstitial
                if(adUnitID1!=""){
                    interstitial = new InterstitialAd(mainActivity);
                    interstitial.setAdUnitId(adUnitID1);
                    interstitial.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            callback.call("onAdmobLoaded", new Object[] {});
                        }
                        public void onAdFailedToLoad(int errorcode) {
                            callback.call("onAdmobFailed", new Object[] {});
                        }
                        public void onAdClosed(){
                            callback.call("onAdmobClosed", new Object[] {});
                        }
                        public void onAdOpened(){
                            callback.call("onAdmobOpened", new Object[] {});
                        }
                    });
                    
                    interstitial.loadAd(adReq);
                }
                
            }
        });
    }


	static public void showBanner()
	{
        mainActivity.runOnUiThread(new Runnable()
        {
        	public void run() 
			{
				
     				adView.setVisibility(AdView.VISIBLE);
                    
                    Animation animation1 = new AlphaAnimation(0.0f, 1.0f);
                    animation1.setDuration(1000);
                    layout.startAnimation(animation1);
            }
        });
    }

	static public void hideBanner()
	{
        mainActivity.runOnUiThread(new Runnable()
        {
        	public void run() 
        	{
                    Animation animation1 = new AlphaAnimation(1.0f, 0.0f);
                    animation1.setDuration(1000);
                    layout.startAnimation(animation1);
                    
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adView.setVisibility(AdView.GONE);
                        }
                    }, 1000);
            }
        });
	}
    
    static public void setBannerPosition(final int position)
    {
        mainActivity.runOnUiThread(new Runnable()
                                   {
        	public void run()
        	{
                if(position == 0) //Bottom-Center
                {
                    layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
                }
                else //Top-Center
                {
                    layout.setGravity(Gravity.CENTER_HORIZONTAL);
                }
            }
        });
    }
    
    /// --- Other(Banners) --- ///
    
    @Override
    public void onPause() {
        mainActivity.runOnUiThread(new Runnable()
        {
        	public void run()
        	{
                if (adView != null) {
                    adView.pause();
                }
            }
        });
        
        super.onPause();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
        mainActivity.runOnUiThread(new Runnable()
        {
        	public void run()
        	{
                if (adView != null) {
                    adView.resume();
                }
            }
        });
    }
    
    @Override
    public void onDestroy() {
        mainActivity.runOnUiThread(new Runnable()
        {
        	public void run()
        	{
                if (adView != null) {
                    adView.destroy();
                }
            }
        });
        
        super.onDestroy();
    }
    
    /// --- Interstitials --- ///
    
    static public void loadInterstitial()
    {
        mainActivity.runOnUiThread(new Runnable()
                                   {
            public void run()
            {
                interstitial.loadAd(adReq);
            }
        });
    }
    
    static public void showInterstitial()
    {
        mainActivity.runOnUiThread(new Runnable()
                                   {
            public void run()
            {
               if(interstitial.isLoaded())
               {
                   interstitial.show();
               }
            }
        });
    }
    
}