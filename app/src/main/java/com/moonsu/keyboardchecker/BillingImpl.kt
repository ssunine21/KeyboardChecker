package com.moonsu.keyboardchecker

import android.app.Activity
import android.widget.Toast
import com.android.billingclient.api.*

class BillingImpl(val activity: MainActivity) : PurchasesUpdatedListener {
    private val TAG = "BillingImpl"
    private var isPremium = false;
    private var billingClient = BillingClient.newBuilder(activity)
            .setListener(this)
            .enablePendingPurchases()
            .build()

    init {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                purchaseCheck()
            }

            override fun onBillingServiceDisconnected() {

            }
        })
    }

    override fun onPurchasesUpdated(p0: BillingResult, p1: MutableList<Purchase>?) {
        TODO("Not yet implemented")
    }

    private fun purchaseCheck() {
        try {
            if (billingClient.queryPurchases(Definition.skuType).responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in billingClient.queryPurchases(Definition.skuType).purchasesList.orEmpty()) {
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        isPremium = true
                    } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                        handlePurchase(purchase)
                    } else {
                        activity.setAds()
                    }
                }
            }
        } catch (e: Exception) {

        }
    }

    private fun handlePurchase(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)

                billingClient.acknowledgePurchase(acknowledgePurchaseParams.build()) {
                    Toast.makeText(activity, "결제 되었습니다.", Toast.LENGTH_SHORT).show()
                    isPremium = true
                }
            } else {
                isPremium = true
            }
        }
    }
}