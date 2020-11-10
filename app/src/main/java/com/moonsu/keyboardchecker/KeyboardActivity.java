package com.moonsu.keyboardchecker;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class KeyboardActivity extends AppCompatActivity {
    private final String TAG = "KeyboardActivity";
    private int Device_Width = 0;
    private DisplayMetrics dm;

    private TextView esc, f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12;
    private TextView grave, k1, k2, k3, k4, k5, k6, k7, k8, k9, k0, minus, equals;
    private TextView tab, q, w, e, r, t, y, u, i, o, p, left_bracket, right_bracket, backslash, backspace;
    private TextView leftctrl, leftalt, spacebar, rightctrl;
    private TextView capslock, a, s, d, f, g, h, j, k, l, semicolon, apostrophe, enter;
    private TextView leftshift, z, x, c, v, b, n, m, comma, dot, slash, rightshift;
    private TextView delete, insert, home, pageup, end, pagedown;
    private ImageView window, arrowup, arrowright, arrowdown, arrowleft, rightalt;
    private ImageButton backButton;

    private LinearLayout container;

    private Animation keydown_animation;
    private Animation keyup_animation;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display;
        WindowManager wm;

        dm = new DisplayMetrics();
        wm = getWindowManager();
        display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        DeviceSizeCheck();

        if (Device_Width >= 4) {
            if (dm.heightPixels < 1232) {
                setContentView(R.layout.activity_keyboard);
            } else {
                setContentView(R.layout.activity_keyboard_tab);
            }
        } else {
            setContentView(R.layout.activity_keyboard);
        }


        keydown_animation = AnimationUtils.loadAnimation(this, R.anim.keydown);
        keyup_animation = AnimationUtils.loadAnimation(this, R.anim.keyup);
        keySetting();

        //광고
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        setOnKey();
    }

    private void setOnKey(){

        backButton.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ESCAPE:
                            keyDown(esc);
                            return true;
                        case KeyEvent.KEYCODE_F1:
                            keyDown(f1);
                            return true;
                        case KeyEvent.KEYCODE_F2:
                            keyDown(f2);
                            return true;
                        case KeyEvent.KEYCODE_F3:
                            keyDown(f3);
                            return true;
                        case KeyEvent.KEYCODE_F4:
                            keyDown(f4);
                            return true;
                        case KeyEvent.KEYCODE_F5:
                            keyDown(f5);
                            return true;
                        case KeyEvent.KEYCODE_F6:
                            keyDown(f6);
                            return true;
                        case KeyEvent.KEYCODE_F7:
                            keyDown(f7);
                            return true;
                        case KeyEvent.KEYCODE_F8:
                            keyDown(f8);
                            return true;
                        case KeyEvent.KEYCODE_F9:
                            keyDown(f9);
                            return true;
                        case KeyEvent.KEYCODE_F10:
                            keyDown(f10);
                            return true;
                        case KeyEvent.KEYCODE_F11:
                            keyDown(f11);
                            return true;
                        case KeyEvent.KEYCODE_F12:
                            keyDown(f12);
                            return true;

                        case KeyEvent.KEYCODE_GRAVE:
                            keyDown(grave);
                            return true;
                        case KeyEvent.KEYCODE_1:
                            keyDown(k1);
                            return true;
                        case KeyEvent.KEYCODE_2:
                            keyDown(k2);
                            return true;
                        case KeyEvent.KEYCODE_3:
                            keyDown(k3);
                            return true;
                        case KeyEvent.KEYCODE_4:
                            keyDown(k4);
                            return true;
                        case KeyEvent.KEYCODE_5:
                            keyDown(k5);
                            return true;
                        case KeyEvent.KEYCODE_6:
                            keyDown(k6);
                            return true;
                        case KeyEvent.KEYCODE_7:
                            keyDown(k7);
                            return true;
                        case KeyEvent.KEYCODE_8:
                            keyDown(k8);
                            return true;
                        case KeyEvent.KEYCODE_9:
                            keyDown(k9);
                            return true;
                        case KeyEvent.KEYCODE_0:
                            keyDown(k0);
                            return true;
                        case KeyEvent.KEYCODE_MINUS:
                            keyDown(minus);
                            return true;
                        case KeyEvent.KEYCODE_EQUALS:
                            keyDown(equals);
                            return true;
                        case KeyEvent.KEYCODE_DEL:
                            keyDown(backspace);
                            return true;

                        case KeyEvent.KEYCODE_TAB:
                            keyDown(tab);
                            return true;
                        case KeyEvent.KEYCODE_Q:
                            keyDown(q);
                            return true;
                        case KeyEvent.KEYCODE_W:
                            keyDown(w);
                            return true;
                        case KeyEvent.KEYCODE_E:
                            keyDown(e);
                            return true;
                        case KeyEvent.KEYCODE_R:
                            keyDown(r);
                            return true;
                        case KeyEvent.KEYCODE_T:
                            keyDown(t);
                            return true;
                        case KeyEvent.KEYCODE_Y:
                            keyDown(y);
                            return true;
                        case KeyEvent.KEYCODE_U:
                            keyDown(u);
                            return true;
                        case KeyEvent.KEYCODE_I:
                            keyDown(i);
                            return true;
                        case KeyEvent.KEYCODE_O:
                            keyDown(o);
                            return true;
                        case KeyEvent.KEYCODE_P:
                            keyDown(p);
                            return true;
                        case KeyEvent.KEYCODE_LEFT_BRACKET:
                            keyDown(left_bracket);
                            return true;
                        case KeyEvent.KEYCODE_RIGHT_BRACKET:
                            keyDown(right_bracket);
                            return true;
                        case KeyEvent.KEYCODE_BACKSLASH:
                            keyDown(backslash);
                            return true;

                        case KeyEvent.KEYCODE_CAPS_LOCK:
                            keyDown(capslock);
                            return true;
                        case KeyEvent.KEYCODE_A:
                            keyDown(a);
                            return true;
                        case KeyEvent.KEYCODE_S:
                            keyDown(s);
                            return true;
                        case KeyEvent.KEYCODE_D:
                            keyDown(d);
                            return true;
                        case KeyEvent.KEYCODE_F:
                            keyDown(f);
                            return true;
                        case KeyEvent.KEYCODE_G:
                            keyDown(g);
                            return true;
                        case KeyEvent.KEYCODE_H:
                            keyDown(h);
                            return true;
                        case KeyEvent.KEYCODE_J:
                            keyDown(j);
                            return true;
                        case KeyEvent.KEYCODE_K:
                            keyDown(k);
                            return true;
                        case KeyEvent.KEYCODE_L:
                            keyDown(l);
                            return true;
                        case KeyEvent.KEYCODE_SEMICOLON:
                            keyDown(semicolon);
                            return true;
                        case KeyEvent.KEYCODE_APOSTROPHE:
                            keyDown(apostrophe);
                            return true;
                        case KeyEvent.KEYCODE_ENTER:
                            keyDown(enter);
                            return true;

                        case KeyEvent.KEYCODE_SHIFT_LEFT:
                            keyDown(leftshift);
                            return true;
                        case KeyEvent.KEYCODE_Z:
                            keyDown(z);
                            return true;
                        case KeyEvent.KEYCODE_X:
                            keyDown(x);
                            return true;
                        case KeyEvent.KEYCODE_C:
                            keyDown(c);
                            return true;
                        case KeyEvent.KEYCODE_V:
                            keyDown(v);
                            return true;
                        case KeyEvent.KEYCODE_B:
                            keyDown(b);
                            return true;
                        case KeyEvent.KEYCODE_N:
                            keyDown(n);
                            return true;
                        case KeyEvent.KEYCODE_M:
                            keyDown(m);
                            return true;
                        case KeyEvent.KEYCODE_COMMA:
                            keyDown(comma);
                            return true;
                        case KeyEvent.KEYCODE_PERIOD:
                            keyDown(dot);
                            return true;
                        case KeyEvent.KEYCODE_SLASH:
                            keyDown(slash);
                            return true;
                        case KeyEvent.KEYCODE_SHIFT_RIGHT:
                            keyDown(rightshift);
                            return true;

                        case KeyEvent.KEYCODE_CTRL_LEFT:
                            keyDown(leftctrl);
                            return true;
                        case KeyEvent.KEYCODE_ALT_LEFT:
                            keyDown(leftalt);
                            return true;
                        case KeyEvent.KEYCODE_SPACE:
                            keyDown(spacebar);
                            return true;
                        case KeyEvent.KEYCODE_CTRL_RIGHT:
                            keyDown(rightctrl);
                            return true;
                        case KeyEvent.KEYCODE_ALT_RIGHT:
                            keyDown(rightalt);
                            return true;
                        case KeyEvent.KEYCODE_WINDOW:
                            keyDown(window);
                            return true;

                        case KeyEvent.KEYCODE_FORWARD_DEL:
                            keyDown(delete);
                            return true;
                        case KeyEvent.KEYCODE_MOVE_HOME:
                            keyDown(home);
                            return true;
                        case KeyEvent.KEYCODE_PAGE_UP:
                            keyDown(pageup);
                            return true;
                        case KeyEvent.KEYCODE_INSERT:
                            keyDown(insert);
                            return true;
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                            keyDown(pagedown);
                            return true;
                        case KeyEvent.KEYCODE_MOVE_END:
                            keyDown(end);
                            return true;

                        case KeyEvent.KEYCODE_DPAD_UP:
                            keyDown(arrowup);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            keyDown(arrowright);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            keyDown(arrowdown);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            keyDown(arrowleft);
                            return true;

                        case KeyEvent.KEYCODE_BACK:
                            finish();
                            return true;

                        default:
                            break;
                    }
                } else if (event.getAction() == KeyEvent.ACTION_UP){
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ESCAPE:
                            keyUp(esc);
                            return true;
                        case KeyEvent.KEYCODE_F1:
                            keyUp(f1);
                            return true;
                        case KeyEvent.KEYCODE_F2:
                            keyUp(f2);
                            return true;
                        case KeyEvent.KEYCODE_F3:
                            keyUp(f3);
                            return true;
                        case KeyEvent.KEYCODE_F4:
                            keyUp(f4);
                            return true;
                        case KeyEvent.KEYCODE_F5:
                            keyUp(f5);
                            return true;
                        case KeyEvent.KEYCODE_F6:
                            keyUp(f6);
                            return true;
                        case KeyEvent.KEYCODE_F7:
                            keyUp(f7);
                            return true;
                        case KeyEvent.KEYCODE_F8:
                            keyUp(f8);
                            return true;
                        case KeyEvent.KEYCODE_F9:
                            keyUp(f9);
                            return true;
                        case KeyEvent.KEYCODE_F10:
                            keyUp(f10);
                            return true;
                        case KeyEvent.KEYCODE_F11:
                            keyUp(f11);
                            return true;
                        case KeyEvent.KEYCODE_F12:
                            keyUp(f12);
                            return true;

                        case KeyEvent.KEYCODE_GRAVE:
                            keyUp(grave);
                            return true;
                        case KeyEvent.KEYCODE_1:
                            keyUp(k1);
                            return true;
                        case KeyEvent.KEYCODE_2:
                            keyUp(k2);
                            return true;
                        case KeyEvent.KEYCODE_3:
                            keyUp(k3);
                            return true;
                        case KeyEvent.KEYCODE_4:
                            keyUp(k4);
                            return true;
                        case KeyEvent.KEYCODE_5:
                            keyUp(k5);
                            return true;
                        case KeyEvent.KEYCODE_6:
                            keyUp(k6);
                            return true;
                        case KeyEvent.KEYCODE_7:
                            keyUp(k7);
                            return true;
                        case KeyEvent.KEYCODE_8:
                            keyUp(k8);
                            return true;
                        case KeyEvent.KEYCODE_9:
                            keyUp(k9);
                            return true;
                        case KeyEvent.KEYCODE_0:
                            keyUp(k0);
                            return true;
                        case KeyEvent.KEYCODE_MINUS:
                            keyUp(minus);
                            return true;
                        case KeyEvent.KEYCODE_EQUALS:
                            keyUp(equals);
                            return true;
                        case KeyEvent.KEYCODE_DEL:
                            keyUp(backspace);
                            return true;

                        case KeyEvent.KEYCODE_TAB:
                            keyUp(tab);
                            return true;
                        case KeyEvent.KEYCODE_Q:
                            keyUp(q);
                            return true;
                        case KeyEvent.KEYCODE_W:
                            keyUp(w);
                            return true;
                        case KeyEvent.KEYCODE_E:
                            keyUp(e);
                            return true;
                        case KeyEvent.KEYCODE_R:
                            keyUp(r);
                            return true;
                        case KeyEvent.KEYCODE_T:
                            keyUp(t);
                            return true;
                        case KeyEvent.KEYCODE_Y:
                            keyUp(y);
                            return true;
                        case KeyEvent.KEYCODE_U:
                            keyUp(u);
                            return true;
                        case KeyEvent.KEYCODE_I:
                            keyUp(i);
                            return true;
                        case KeyEvent.KEYCODE_O:
                            keyUp(o);
                            return true;
                        case KeyEvent.KEYCODE_P:
                            keyUp(p);
                            return true;
                        case KeyEvent.KEYCODE_LEFT_BRACKET:
                            keyUp(left_bracket);
                            return true;
                        case KeyEvent.KEYCODE_RIGHT_BRACKET:
                            keyUp(right_bracket);
                            return true;
                        case KeyEvent.KEYCODE_BACKSLASH:
                            keyUp(backslash);
                            return true;

                        case KeyEvent.KEYCODE_CAPS_LOCK:
                            keyUp(capslock);
                            return true;
                        case KeyEvent.KEYCODE_A:
                            keyUp(a);
                            return true;
                        case KeyEvent.KEYCODE_S:
                            keyUp(s);
                            return true;
                        case KeyEvent.KEYCODE_D:
                            keyUp(d);
                            return true;
                        case KeyEvent.KEYCODE_F:
                            keyUp(f);
                            return true;
                        case KeyEvent.KEYCODE_G:
                            keyUp(g);
                            return true;
                        case KeyEvent.KEYCODE_H:
                            keyUp(h);
                            return true;
                        case KeyEvent.KEYCODE_J:
                            keyUp(j);
                            return true;
                        case KeyEvent.KEYCODE_K:
                            keyUp(k);
                            return true;
                        case KeyEvent.KEYCODE_L:
                            keyUp(l);
                            return true;
                        case KeyEvent.KEYCODE_SEMICOLON:
                            keyUp(semicolon);
                            return true;
                        case KeyEvent.KEYCODE_APOSTROPHE:
                            keyUp(apostrophe);
                            return true;
                        case KeyEvent.KEYCODE_ENTER:
                            keyUp(enter);
                            return true;

                        case KeyEvent.KEYCODE_SHIFT_LEFT:
                            keyUp(leftshift);
                            return true;
                        case KeyEvent.KEYCODE_Z:
                            keyUp(z);
                            return true;
                        case KeyEvent.KEYCODE_X:
                            keyUp(x);
                            return true;
                        case KeyEvent.KEYCODE_C:
                            keyUp(c);
                            return true;
                        case KeyEvent.KEYCODE_V:
                            keyUp(v);
                            return true;
                        case KeyEvent.KEYCODE_B:
                            keyUp(b);
                            return true;
                        case KeyEvent.KEYCODE_N:
                            keyUp(n);
                            return true;
                        case KeyEvent.KEYCODE_M:
                            keyUp(m);
                            return true;
                        case KeyEvent.KEYCODE_COMMA:
                            keyUp(comma);
                            return true;
                        case KeyEvent.KEYCODE_PERIOD:
                            keyUp(dot);
                            return true;
                        case KeyEvent.KEYCODE_SLASH:
                            keyUp(slash);
                            return true;
                        case KeyEvent.KEYCODE_SHIFT_RIGHT:
                            keyUp(rightshift);
                            return true;


                        case KeyEvent.KEYCODE_CTRL_LEFT:
                            keyUp(leftctrl);
                            return true;
                        case KeyEvent.KEYCODE_ALT_LEFT:
                            keyUp(leftalt);
                            return true;
                        case KeyEvent.KEYCODE_SPACE:
                            keyUp(spacebar);
                            return true;
                        case KeyEvent.KEYCODE_CTRL_RIGHT:
                            keyUp(rightctrl);
                            return true;
                        case KeyEvent.KEYCODE_ALT_RIGHT:
                            keyUp(rightalt);
                            return true;
                        case KeyEvent.KEYCODE_WINDOW:
                            keyUp(window);
                            return true;

                        case KeyEvent.KEYCODE_FORWARD_DEL:
                            keyUp(delete);
                            return true;
                        case KeyEvent.KEYCODE_MOVE_HOME:
                            keyUp(home);
                            return true;
                        case KeyEvent.KEYCODE_PAGE_UP:
                            keyUp(pageup);
                            return true;
                        case KeyEvent.KEYCODE_INSERT:
                            keyUp(insert);
                            return true;
                        case KeyEvent.KEYCODE_PAGE_DOWN:
                            keyUp(pagedown);
                            return true;
                        case KeyEvent.KEYCODE_MOVE_END:
                            keyUp(end);
                            return true;

                        case KeyEvent.KEYCODE_DPAD_UP:
                            keyUp(arrowup);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            keyUp(arrowright);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            keyUp(arrowdown);
                            return true;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            keyUp(arrowleft);
                            return true;

                        default:
                            break;
                    }
                    return false;
                }
                return false;
            }
        });
    }



    private void DeviceSizeCheck() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Device_Width = getContentSize(dm);
    }

    private int getContentSize(DisplayMetrics dm) {
        int result;
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.e(TAG, "" + getCurrentFocus());
//        //Log.e(TAG, "" + KeyEvent.keyCodeToString(keyCode));
//
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//
//        Log.e(TAG, "" + KeyEvent.keyCodeToString(keyCode));
//        Log.e(TAG, "" + event);
//
//
//    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return true;
    }

    private void keyDown(View view) {
        if (view.getAnimation() == keydown_animation) return;

        Log.i("keydown", "keydown");
        view.startAnimation(keydown_animation);
        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffc952")));
    }

    private void keyUp(View view) {
        view.startAnimation(keyup_animation);
        view.setAnimation(null);
        Log.i("keyup", "keyup");
        view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#79bd9a")));
    }

    void keySetting() {
        esc = findViewById(R.id.esc);
        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        f4 = findViewById(R.id.f4);
        f5 = findViewById(R.id.f5);
        f6 = findViewById(R.id.f6);
        f7 = findViewById(R.id.f7);
        f8 = findViewById(R.id.f8);
        f9 = findViewById(R.id.f9);
        f10 = findViewById(R.id.f10);
        f11 = findViewById(R.id.f11);
        f12 = findViewById(R.id.f12);

        grave = findViewById(R.id.grave);
        k1 = findViewById(R.id.key1);
        k2 = findViewById(R.id.key2);
        k3 = findViewById(R.id.key3);
        k4 = findViewById(R.id.key4);
        k5 = findViewById(R.id.key5);
        k6 = findViewById(R.id.key6);
        k7 = findViewById(R.id.key7);
        k8 = findViewById(R.id.key8);
        k9 = findViewById(R.id.key9);
        k0 = findViewById(R.id.key0);
        minus = findViewById(R.id.minus);
        equals = findViewById(R.id.equals);
        backspace = findViewById(R.id.backspace);

        tab = findViewById(R.id.tab);
        q = findViewById(R.id.q);
        w = findViewById(R.id.w);
        e = findViewById(R.id.e);
        r = findViewById(R.id.r);
        t = findViewById(R.id.t);
        y = findViewById(R.id.y);
        u = findViewById(R.id.u);
        i = findViewById(R.id.i);
        o = findViewById(R.id.o);
        p = findViewById(R.id.p);
        left_bracket = findViewById(R.id.left_bracket);
        right_bracket = findViewById(R.id.right_bracket);
        backslash = findViewById(R.id.backslash);

        capslock = findViewById(R.id.caps);
        a = findViewById(R.id.a);
        s = findViewById(R.id.s);
        d = findViewById(R.id.d);
        f = findViewById(R.id.f);
        g = findViewById(R.id.g);
        h = findViewById(R.id.h);
        j = findViewById(R.id.j);
        k = findViewById(R.id.k);
        l = findViewById(R.id.l);
        semicolon = findViewById(R.id.semicolon);
        apostrophe = findViewById(R.id.apostrophe);
        enter = findViewById(R.id.enter);

        leftshift = findViewById(R.id.leftshift);
        z = findViewById(R.id.z);
        x = findViewById(R.id.x);
        c = findViewById(R.id.c);
        v = findViewById(R.id.v);
        b = findViewById(R.id.b);
        n = findViewById(R.id.n);
        m = findViewById(R.id.m);
        comma = findViewById(R.id.comma);
        dot = findViewById(R.id.dot);
        slash = findViewById(R.id.slash);
        rightshift = findViewById(R.id.rightshift);

        delete = findViewById(R.id.delete);
        home = findViewById(R.id.home);
        pagedown = findViewById(R.id.pagedown);
        pageup = findViewById(R.id.pageup);
        insert = findViewById(R.id.insert);
        end = findViewById(R.id.end);

        leftctrl = findViewById(R.id.leftctrl);
        leftalt = findViewById(R.id.leftalt);
        spacebar = findViewById(R.id.spacebar);
        rightctrl = findViewById(R.id.rightctrl);
        rightalt = findViewById(R.id.rightalt);

        arrowdown = findViewById(R.id.arrowdown);
        arrowleft = findViewById(R.id.arrowleft);
        arrowright = findViewById(R.id.arrowright);
        arrowup = findViewById(R.id.arrowup);

        container = findViewById(R.id.container);

        backButton = findViewById(R.id.keyboardBackButton);
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
