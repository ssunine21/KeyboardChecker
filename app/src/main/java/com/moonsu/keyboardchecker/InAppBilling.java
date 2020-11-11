package com.moonsu.keyboardchecker;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import java.util.ArrayList;

class InAppBilling {
    private final String TAG = "InAppBilling";

    private final Activity activity;
    private final int API_VERSION = 3;
    private final String DEVELOPERPAY_LOAD = "premiumCode";


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
                getPurchases();
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
                    Definition.PREMIUM, "inapp", DEVELOPERPAY_LOAD);
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

    public Boolean getPurchases() {
        final Bundle ownedItems;
        try {
            ownedItems = mService.getPurchases(API_VERSION, activity.getPackageName(), "inapp", null);

            int response = ownedItems.getInt("RESPONSE_CODE");
            if (response == 0) {
                ArrayList<String> ownedSkus =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> purchaseDataList =
                        ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> signatureList =
                        ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                String continuationToken =
                        ownedItems.getString("INAPP_CONTINUATION_TOKEN");

                for (int i = 0; i < purchaseDataList.size(); ++i) {
                    String purchaseData = purchaseDataList.get(i);
                    String signature = signatureList.get(i);
                    String sku = ownedSkus.get(i);

                    if(sku.equals(Definition.PREMIUM)){
                        try {
                            return true;
                        } catch (Exception e){
                            e.printStackTrace();
                            return false;
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            showError();
        }
        return false;
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
