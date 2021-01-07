package com.moonsu.keyboardchecker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Ads ads;
    private BillingImpl billingImpl;

    public final int PAGE_KEYBOARD = 0;
    public final int PAGE_MOUSE = 1;
    public final int PAGE_GAMEPAD = 2;
    private boolean isPremium = false;

    private int adsCount = 2;

    private SharedPreferences sharedPreferences;

    private long BACKKEY_PRESS_TIME = 0;
    private final long BACKKEY_DELAY_TIME = 2000;
    private final String TAG = "MainActivity";

    private final int button_mouseId = R.id.button_mouse;
    private final int button_gamepadId = R.id.button_gamepad;
    private final int button_keyboardId = R.id.button_keyboard;
    private final int button_noAdsId = R.id.noAds;

    private ImageButton button_mouse, button_gamepad, button_keyboard, button_noAds;

    private Toast toast;
    private Intent intent;
    private int Device_Width = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display;
        WindowManager wm;

        DisplayMetrics dm = new DisplayMetrics();
        wm = getWindowManager();
        display = wm.getDefaultDisplay();
        display.getMetrics(dm);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isPremium = sharedPreferences.getBoolean(Definition.PREMIUM, false);
        DeviceSizeCheck();

        setContentView(R.layout.activity_main_tab);

        keySetting();

        billingImpl = new BillingImpl(this);

        if (isPremium) {
            setPremium();
        } else {
            appBilling = new InAppBilling(this);
            appBilling.setPackage();
        }
    }

    public void setPremium(){
        String resName = "@drawable/" + Definition.PREMIUM;
        int resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());
        button_noAds.setImageResource(resID);
        button_noAds.setOnClickListener(null);
        sharedPreferences.edit().putBoolean(Definition.PREMIUM, true).apply();
        isPremium = sharedPreferences.getBoolean(Definition.PREMIUM, false);
    }

    public void setAds(){
        ads = new Ads(this);
        // 리워드 광고
        ads.createRewardAds(getString(R.string.rewardedTestAds));
        // 전면 광고
        ads.createInterstitialAds(getString(R.string.interstitialAds));
        // 배너 광고
        ads.createBannerAds((AdView) findViewById(R.id.adView));
    }

    private void DeviceSizeCheck() {
        // TODO Auto-generated method stub
        int Device_Height = 0;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Device_Width = getContentSize(dm);
        //Device_Height = display.getHeight();
    }

    private int getContentSize(DisplayMetrics dm) {
        int result;

        //ds.getMetrics(dm);
        double x = Math.pow(dm.heightPixels / dm.xdpi, 2);
        double screenInches = Math.sqrt(x);

        if (screenInches >= 3.553) {
            result = 4;
        } else if (screenInches >= 2.79) {
            result = 3;
        } else if (screenInches >= 2.36) {
            result = 2;
        } else {
            result = 1;
        }

        return result;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "");
        switch (v.getId()) {
            case button_keyboardId:
                goIntent(PAGE_KEYBOARD);
                break;

            case button_gamepadId:
                goIntent(PAGE_GAMEPAD);
                break;

            case button_mouseId:
                goIntent(PAGE_MOUSE);
                break;

            case button_noAdsId:
                appBilling.getBuy();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void goIntent(int pageNum) {

        switch (pageNum) {
            case PAGE_KEYBOARD:
                intent = new Intent(this, KeyboardActivity.class);
                break;

            case PAGE_MOUSE:
                intent = new Intent(this, MouseActivity.class);
                break;

            case PAGE_GAMEPAD:
                intent = new Intent(this, ControllerActivity.class);
                break;

            default:
                break;
        }

        intent.putExtra(Definition.IS_PREMIUM, isPremium);
        startActivityForResult(intent, Definition.INTERSTITIALADS_CODE);
        //ads.rewardedAdsShow(this, intent);
    }

    private void keySetting() {
        button_gamepad = findViewById(R.id.button_gamepad);
        button_keyboard = findViewById(R.id.button_keyboard);
        button_mouse = findViewById(R.id.button_mouse);
        button_noAds = findViewById(R.id.noAds);

        button_gamepad.setOnClickListener(this);
        button_mouse.setOnClickListener(this);
        button_keyboard.setOnClickListener(this);
        button_noAds.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        //현제 시간보다 백키누른시간이 2초가 작다면 알림창 띄움
        if (System.currentTimeMillis() > BACKKEY_PRESS_TIME + BACKKEY_DELAY_TIME) {
            toast = Toast.makeText(this, R.string.plzBackKey, Toast.LENGTH_SHORT);
            toast.show();

            BACKKEY_PRESS_TIME = System.currentTimeMillis();
        } else if (System.currentTimeMillis() <= BACKKEY_PRESS_TIME + BACKKEY_DELAY_TIME)
            clearFinish();
    }

    private void clearFinish() {
        moveTaskToBack(true);
        toast.cancel();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(appBilling != null) {
            appBilling.onDestroy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Definition.INTERSTITIALADS_CODE) {
            if(!isPremium) {
                if (--adsCount <= 0) {
                    try {
                        adsCount = 3;
                        ads.showInterstitialAds();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        } else if (requestCode == Definition.INAPP_BUY_CODE) {
            if (resultCode == RESULT_OK) {
                sharedPreferences.edit().putBoolean(Definition.PREMIUM, true).apply();
                Toast.makeText(this, "결제에 성공했습니다. 앱을 재실행 해주세요.", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
