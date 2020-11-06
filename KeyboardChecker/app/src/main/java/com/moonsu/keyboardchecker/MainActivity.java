package com.moonsu.keyboardchecker;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Ads ads;
    private InAppBilling appBilling;

    public final int PAGE_KEYBOARD = 0;
    public final int PAGE_MOUSE = 1;
    public final int PAGE_GAMEPAD = 2;

    private long BACKKEY_PRESS_TIME = 0;
    private final long BACKKEY_DELAY_TIME = 2000;

    public static final int button_mouse = R.id.button_mouse;
    public static final int button_gamepad = R.id.button_gamepad;
    public static final int button_keyboard = R.id.button_keyboard;
    public static final int button_noAds = R.id.noAds;

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

        DeviceSizeCheck();

        if (Device_Width >= 4) {
            if (dm.heightPixels < 1232) {
                setContentView(R.layout.activity_main_tab);
            } else {
                setContentView(R.layout.activity_main_tab);
            }
        } else {
            setContentView(R.layout.activity_main_tab);
        }

        keySetting();

        ads = new Ads(this);
        // 리워드 광고
        ads.createRewardAds(getString(R.string.rewardedTestAds));
        // 전면 광고
        ads.createInterstitialAds(getString(R.string.interstitialAds));
        // 배너 광고
        ads.createBannerAds((AdView) findViewById(R.id.adView));

        appBilling = new InAppBilling(this);
        appBilling.setPackage();
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
        switch (v.getId()) {
            case button_keyboard:
                goIntent(PAGE_KEYBOARD);
                break;

            case button_gamepad:
                goIntent(PAGE_GAMEPAD);
                break;

            case button_mouse:
                goIntent(PAGE_MOUSE);
                break;

            case button_noAds:
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
                startActivity(intent);
                return;

            case PAGE_MOUSE:
                intent = new Intent(this, MouseActivity.class);
                break;

            case PAGE_GAMEPAD:
                intent = new Intent(this, ControllerActivity.class);
                break;

            default:
                break;
        }

        startActivity(intent);
        //ads.rewardedAdsShow(this, intent);
    }

    private void keySetting() {
        ImageButton button_gamepad = findViewById(R.id.button_gamepad);
        ImageButton button_keyboard = findViewById(R.id.button_keyboard);
        ImageButton button_mouse = findViewById(R.id.button_mouse);

        button_gamepad.setOnClickListener(this);
        button_mouse.setOnClickListener(this);
        button_keyboard.setOnClickListener(this);
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
        appBilling.onDestroy();
    }
}
