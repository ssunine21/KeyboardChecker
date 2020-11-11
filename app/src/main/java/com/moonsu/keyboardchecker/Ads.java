package com.moonsu.keyboardchecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

class Ads extends RewardedAdCallback {

    private RewardedAd rewardedAd;
    private InterstitialAd interstitialAd;

    private final Context context;
    private String rewardedId;

    private long REWARDED_PRESS_TIME = 0;
    private final long REWARDED_DELAY_TIME = 60000;

    protected Ads(Context context){
        this.context = context;

        // 배너 광고
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
    }

    public void createRewardAds(String AdsId){
        rewardedId = AdsId;
        rewardedAd = new RewardedAd(context, rewardedId);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {}

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError loadAdError) {}
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    public void createInterstitialAds(String AdsId){
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(AdsId);

        loadInterstitialAds();

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadInterstitialAds();
            }
        });
    }

    private void loadInterstitialAds(){
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    // load랑 show를 분기해서 loading될 시간을 주자
    public void showRewardedAds(final Activity activity, final Intent intent) {
        if (System.currentTimeMillis() > REWARDED_PRESS_TIME + REWARDED_DELAY_TIME) {
            if (rewardedAd.isLoaded()) {
                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    boolean ADS_RESULT = false;

                    @Override
                    public void onRewardedAdOpened() {
                    }

                    @Override
                    public void onRewardedAdClosed() {
                        if (ADS_RESULT) {
                            REWARDED_PRESS_TIME = System.currentTimeMillis();
                            activity.startActivity(intent);

                            ADS_RESULT = false;
                        }
                    }

                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        ADS_RESULT = true;
                    }

                    @Override
                    public void onRewardedAdFailedToShow(AdError adError) {
                    }
                };
                rewardedAd.show(activity, adCallback);
                createAndLoadRewardedAd(rewardedId);
            } else {
                showError();
            }
            return;
        }
        activity.startActivity(intent);
    }

    public void createAndLoadRewardedAd(String AdsId) {
        RewardedAd rewardedAd = new RewardedAd(context, AdsId);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        this.rewardedAd = rewardedAd;
    }

    public void createBannerAds(AdView view){
        AdRequest adRequest = new AdRequest.Builder().build();
        view.loadAd(adRequest);
    }

    public void showInterstitialAds(){
        try {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            } else {
                showError();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRewardedAdOpened() {
    }

    @Override
    public void onRewardedAdClosed() {
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
    }

    @Override
    public void onRewardedAdFailedToShow(AdError adError) {
    }

    private void showError(){
        Toast.makeText(context.getApplicationContext(), "광고가 준비되지 않았습니다.", Toast.LENGTH_LONG).show();
    }

}
