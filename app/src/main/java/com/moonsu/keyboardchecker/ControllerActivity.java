package com.moonsu.keyboardchecker;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class ControllerActivity extends AppCompatActivity {
    private final String TAG = "ControllerActivity";
    private int Device_Width = 0;

    private ImageView RB, RT, LB, LT, LS, RS, menu, start, A, B, X, Y, up, down, right, left;
    private ImageButton backButton;
    private AdView mAdView;

    boolean isDpad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm;
        Display display;
        WindowManager wm;

        dm = new DisplayMetrics();
        wm = getWindowManager();
        display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        DeviceSizeCheck();

        setContentView(R.layout.activity_controller);

        keySetting();

        //광고
        if (!getIntent().getBooleanExtra(Definition.IS_PREMIUM, false)) {
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {

                }
            });
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "keyDown" + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_L1:
                keyDown(LB, "lb");
                return true;
            case KeyEvent.KEYCODE_BUTTON_R1:
                keyDown(RB, "rb");
                return true;
            case KeyEvent.KEYCODE_BUTTON_L2:
                keyDown(LT, "lt");
                return true;
            case KeyEvent.KEYCODE_BUTTON_R2:
                keyDown(RT, "rt");
                return true;
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
                keyDown(LS, "ls");
                return true;
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
                keyDown(RS, "rs");
                return true;
            case KeyEvent.KEYCODE_BUTTON_SELECT:
                keyDown(menu, "menu");
                return true;
            case KeyEvent.KEYCODE_BUTTON_START:
                keyDown(start, "start");
                return true;
            case KeyEvent.KEYCODE_BUTTON_X:
                keyDown(X, "x");
                return true;
            case KeyEvent.KEYCODE_BUTTON_A:
                keyDown(A, "a");
                return true;
            case KeyEvent.KEYCODE_BUTTON_Y:
                keyDown(Y, "y");
                return true;
            case KeyEvent.KEYCODE_BUTTON_B:
                keyDown(B, "b");
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isDpad)
                    keyDown(up, "up");
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isDpad)
                    keyDown(down, "down");
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (isDpad)
                    keyDown(left, "left");
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (isDpad)
                    keyDown(right, "right");
                return true;
            case KeyEvent.KEYCODE_BACK:
                finish();
                return true;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_L1:
                keyUp(LB, "lb");
                return true;
            case KeyEvent.KEYCODE_BUTTON_R1:
                keyUp(RB, "rb");
                return true;
            case KeyEvent.KEYCODE_BUTTON_L2:
                keyUp(LT, "lt");
                return true;
            case KeyEvent.KEYCODE_BUTTON_R2:
                keyUp(RT, "rt");
                return true;
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
                keyUp(LS, "ls");
                return true;
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
                keyUp(RS, "rs");
                return true;
            case KeyEvent.KEYCODE_BUTTON_SELECT:
                keyUp(menu, "menu");
                return true;
            case KeyEvent.KEYCODE_BUTTON_START:
                keyUp(start, "start");
                return true;
            case KeyEvent.KEYCODE_BUTTON_X:
                keyUp(X, "x");
                return true;
            case KeyEvent.KEYCODE_BUTTON_A:
                keyUp(A, "a");
                return true;
            case KeyEvent.KEYCODE_BUTTON_Y:
                keyUp(Y, "y");
                return true;
            case KeyEvent.KEYCODE_BUTTON_B:
                keyUp(B, "b");
                return true;
            case KeyEvent.KEYCODE_DPAD_UP:
                keyUp(up, "up");
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                keyUp(down, "down");
                return true;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                keyUp(left, "left");
                return true;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                keyUp(right, "right");
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        float axis_ltrigger = event.getAxisValue(MotionEvent.AXIS_LTRIGGER);
        float axis_rtrigger = event.getAxisValue(MotionEvent.AXIS_RTRIGGER);
        float axis_rtriggera = event.getAxisValue(MotionEvent.AXIS_THROTTLE);
        float axis_LX = event.getAxisValue(MotionEvent.AXIS_X);
        float axis_LY = event.getAxisValue(MotionEvent.AXIS_Y);
        float axis_RX = event.getAxisValue(MotionEvent.AXIS_Z);
        float axis_RY = event.getAxisValue(MotionEvent.AXIS_RZ);

        if (Math.abs(axis_LX) > 1 || Math.abs(axis_LY) > 1)
            isDpad = true;
        else
            isDpad = false;


        Log.e(TAG, "" + event);
        Log.e(TAG, "" + axis_ltrigger);
        Log.e(TAG, "" + axis_rtrigger);

        Log.e(TAG, "x : " + axis_LX + " / y : " + axis_LY);
        Log.e(TAG, "z : " + axis_RX + " / rz : " + axis_RY);


        if (axis_ltrigger > 0) {
            keyDown(LT, "lt");
        } else if (axis_ltrigger == 0) {
            keyUp(LT, "lt");
        }

        if (axis_rtrigger > 0) {
            keyDown(RT, "rt");
        } else if (axis_rtrigger == 0) {
            keyUp(RT, "rt");
        }

        return false;
    }

    private void keyDown(ImageView view, String midName) {
        int resID;
        String resName = "@drawable/controller_" + midName + "_keydown";
        resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());

        view.setImageResource(resID);
    }

    private void keyUp(ImageView view, String midName) {
        int resID;
        String resName = "@drawable/controller_" + midName;
        resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());

        view.setImageResource(resID);
    }

    void keySetting() {
        RB = findViewById(R.id.controller_RB);
        RT = findViewById(R.id.controller_RT);
        LB = findViewById(R.id.controller_LB);
        LT = findViewById(R.id.controller_LT);
        LS = findViewById(R.id.controller_LS);
        RS = findViewById(R.id.controller_RS);
        menu = findViewById(R.id.controller_menuL);
        start = findViewById(R.id.controller_menuR);
        A = findViewById(R.id.controller_A);
        B = findViewById(R.id.controller_B);
        X = findViewById(R.id.controller_X);
        Y = findViewById(R.id.controller_Y);
        up = findViewById(R.id.controller_up);
        down = findViewById(R.id.controller_down);
        right = findViewById(R.id.controller_right);
        left = findViewById(R.id.controller_left);

        mAdView = findViewById(R.id.adView);

        backButton = findViewById(R.id.controllerBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_OK);
        super.onDestroy();
    }
}