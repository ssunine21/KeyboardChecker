package com.moonsu.keyboardchecker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MouseActivity extends AppCompatActivity {
    private final String TAG = "MouseActivity";
    private AdView mAdView;

    private RelativeLayout mouse_container;
    private ImageView mouse_left, mouse_right, mouse_front, mouse_back;
    private ImageButton backButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mouse);
        keySetting();

        mouse_container.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){

                Log.e(TAG, "onTouch" + event.getAction());
                Log.e(TAG, "onTouch" + event.getButtonState());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        switch (event.getButtonState()) {
                            case MotionEvent.BUTTON_PRIMARY:
                                mouseKeyDown(mouse_left, "left");
                                break;

                            case MotionEvent.BUTTON_SECONDARY:
                                mouseKeyDown(mouse_right, "right");
                                break;

                            case MotionEvent.BUTTON_FORWARD:
                                mouseKeyDown(mouse_front, "front");
                                break;

                            case MotionEvent.BUTTON_BACK:
                                mouseKeyDown(mouse_back, "back");
                                break;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        switch (event.getButtonState()) {
                            case 0:
                                mouseKeyUp(mouse_left, "left");
                                break;

                            case MotionEvent.BUTTON_SECONDARY:
                                mouseKeyUp(mouse_right, "right");
                                break;

                            case MotionEvent.BUTTON_FORWARD:
                                mouseKeyUp(mouse_front, "front");
                                break;

                            case MotionEvent.BUTTON_BACK:
                                mouseKeyUp(mouse_back, "back");
                                break;


                        }
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        finish();
                        break;
                }
                return true;
            }
        });

        //광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "keydown"+keyCode);
        Log.e(TAG, "keydown"+event.getAction());

        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                mouseKeyDown(mouse_right, "right");
                return true;
        }
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e(TAG, "keyup"+keyCode);
        Log.e(TAG, "keyup"+event.getAction());

        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                mouseKeyUp(mouse_right, "right");
                return true;
        }
        return false;
    }

    private void mouseKeyDown(ImageView view, String midName){
        int resID;
        String resName = "@drawable/mouse_" + midName + "_keydown";
        resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());

        view.setImageResource(resID);
    }

    private void mouseKeyUp(ImageView view, String midName){
        int resID;
        String resName = "@drawable/mouse_" + midName + "_keyup";
        resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());

        view.setImageResource(resID);
    }

    @Override
    protected void onUserLeaveHint() {
        //super.onUserLeaveHint();
        Log.e(TAG, "onUserLeaveHint");
    }


//UserInteraction()
    void keySetting() {
        mouse_container = findViewById(R.id.mouse_container);
        mouse_left  = findViewById(R.id.mouse_left);
        mouse_right = findViewById(R.id.mouse_right);
        mouse_back  = findViewById(R.id.mouse_line);

        backButton = findViewById(R.id.mouseBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}