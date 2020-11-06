package com.moonsu.keyboardchecker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

class InAppBilling {
    private final String TAG = "InAppBilling";

    private final Activity activity;
    private final int API_VERSION = 3;
    private final String PRIMIUM = "primium";
    private final String DEVELOPERPAY_LOAD = "primiumCode";


    private IInAppBillingService mService;
    private final ServiceConnection mServiceConn;

    public InAppBilling(Activity activity) {
        this.activity = activity;

        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name,
                                           IBinder service) {
                mService = IInAppBillingService.Stub.asInterface(service);
            }
        };
    }

    public void setPackage() {
        try {
            Intent serviceIntent =
                    new Intent("com.android.vending.billing.InAppBillingService.BIND");
            serviceIntent.setPackage("com.android.vending");
            activity.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        } catch (Exception e){
            showError();
        }
    }

    public void getBuy(){
        try {
            Bundle buyIntentBundle = mService.getBuyIntent(API_VERSION, activity.getPackageName(),
                    PRIMIUM, "inapp", DEVELOPERPAY_LOAD);
            PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

            if(pendingIntent != null){
                activity.startIntentSenderForResult(pendingIntent.getIntentSender(), Definition.INAPP_BUY_CODE,
                        new Intent(), 0, 0, 0);
            }
        } catch (RemoteException e){
            Log.e(TAG, "getBuy()::RemoteException");
            showError();
        } catch (IntentSender.SendIntentException e){
            Log.e(TAG, "getBuy()::SendIntentException");
            showError();
        }
    }

    public void onDestroy(){
        if(mService != null) {
            activity.unbindService(mServiceConn);
        }
    }

    private void showError(){
        Toast.makeText(activity.getApplicationContext(), "알수없는 오류로 결제에 실패했습니다.", Toast.LENGTH_LONG).show();
    }
}
