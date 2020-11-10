package com.moonsu.keyboardchecker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MouseActivity extends AppCompatActivity {
    private final String TAG = "MouseActivity";
    private AdView mAdView;
    private boolean IS_BUTTON_TERTIARY = false;

    private RelativeLayout mouse_container;
    private ImageView mouse_left, mouse_right, mouse_front, mouse_back, mouse_wheel;
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
    protected void onPause() {
        if(IS_BUTTON_TERTIARY){
            IS_BUTTON_TERTIARY = false;
            Toast.makeText(this, "휠 버튼은 홈 키로 작동합니다.", Toast.LENGTH_LONG).show();
        }
        super.onPause();
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.e(TAG, "" + event.getAction());
        Log.e(TAG, "" + event);

        if(event.getToolType(0) == MotionEvent.TOOL_TYPE_MOUSE){
            switch (event.getAction()){
                case MotionEvent.ACTION_HOVER_MOVE:
                    if(event.getButtonState() == MotionEvent.BUTTON_TERTIARY) {
                        mouseKeyDown(mouse_wheel, "wheel");
                        IS_BUTTON_TERTIARY = true;
                    }
                    else if (event.getButtonState() == MotionEvent.BUTTON_SECONDARY){
                        mouseKeyDown(mouse_right, "right");
                    }
                    break;

                case MotionEvent.ACTION_HOVER_ENTER:
                    mouseKeyUp(mouse_wheel, "wheel");
                    mouseKeyUp(mouse_right, "right");
                    break;

                case MotionEvent.ACTION_SCROLL:
                    onMouseScroll();
                    break;
            }
        }

        switch (event.getAction()){
            case MotionEvent.ACTION_SCROLL:

                break;
        }
        return true;
    }

    private void onMouseScroll(){
        int resID;
        String resName = "@drawable/mouse_scroll";
        resID = getResources().getIdentifier(resName, "drawable", this.getPackageName());

        mouse_wheel.setImageResource(resID);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mouseKeyUp(mouse_wheel, "wheel");
            }
        }, 500);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "keydown"+keyCode);
        Log.e(TAG, "keydown"+event.getAction());
        return false;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e(TAG, "keyup"+keyCode);
        Log.e(TAG, "keyup"+event.getAction());
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

//UserInteraction()
    void keySetting() {
        mouse_container = findViewById(R.id.mouse_container);
        mouse_left  = findViewById(R.id.mouse_left);
        mouse_right = findViewById(R.id.mouse_right);
        mouse_back  = findViewById(R.id.mouse_line);
        mouse_wheel = findViewById(R.id.mouse_wheel);

        backButton = findViewById(R.id.mouseBackButton);
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