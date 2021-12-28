package com.moonsu.keyboardchecker;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

class Ads {

    private RewardedAd mRewardedAd;
    private InterstitialAd mInterstitialAd;

    private final Activity context;

    private long REWARDED_PRESS_TIME = 0;
    private final long REWARDED_DELAY_TIME = 60000;

    protected Ads(Activity context){
        this.context = context;

        // 배너 광고
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
    }

    public void createRewardAds(String AdsId){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, AdsId,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }

    public void createInterstitialAds(String AdsId){
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, AdsId, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                         mInterstitialAd = interstitialAd;
                    }
                });
    }

    // load랑 show를 분기해서 loading될 시간을 주자
    public void showRewardedAds(final Activity activity, final Intent intent) {
        if (System.currentTimeMillis() > REWARDED_PRESS_TIME + REWARDED_DELAY_TIME) {
            if (mRewardedAd != null) {
                mRewardedAd.show(context, new OnUserEarnedRewardListener() {

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        REWARDED_PRESS_TIME = System.currentTimeMillis();
                        activity.startActivity(intent);
                    }
                });
            } else {
                showError();
            }
            return;
        }
        activity.startActivity(intent);
    }

    public void createBannerAds(AdView view){
        AdRequest adRequest = new AdRequest.Builder().build();
        view.loadAd(adRequest);
    }

    public void showInterstitialAds(){
        try {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(context);
            } else {
                showError();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showError(){
        Toast.makeText(context.getApplicationContext(), "광고가 준비되지 않았습니다.", Toast.LENGTH_LONG).show();
    }

}
