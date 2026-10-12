package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

object AdManager {
  private const val TAG = "AdManager"
  const val BANNER_TEST_ID = "ca-app-pub-3940256099942544/6300978111"
  const val INTERSTITIAL_TEST_ID = "ca-app-pub-3940256099942544/1033173712"
  const val REWARDED_TEST_ID = "ca-app-pub-3940256099942544/5224354917"

  private var interstitialAd: InterstitialAd? = null
  private var rewardedAd: RewardedAd? = null
  private var isInitialized = false

  fun initialize(context: Context) {
    if (isInitialized) return
    MobileAds.initialize(context) { initializationStatus ->
      isInitialized = true
      Log.d(TAG, "AdMob initialized: $initializationStatus")
      loadInterstitial(context)
      loadRewarded(context)
    }
  }

  fun loadInterstitial(context: Context) {
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
      context,
      INTERSTITIAL_TEST_ID,
      adRequest,
      object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(ad: InterstitialAd) {
          interstitialAd = ad
          Log.d(TAG, "Interstitial ad loaded successfully.")
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          interstitialAd = null
          Log.d(TAG, "Interstitial ad failed to load: ${error.message}")
        }
      },
    )
  }

  fun showInterstitial(activity: Activity, onDismissed: () -> Unit) {
    if (interstitialAd != null) {
      interstitialAd?.fullScreenContentCallback =
        object : com.google.android.gms.ads.FullScreenContentCallback() {
          override fun onAdDismissedFullScreenContent() {
            interstitialAd = null
            loadInterstitial(activity)
            onDismissed()
          }

          override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
            interstitialAd = null
            onDismissed()
          }
        }
      interstitialAd?.show(activity)
    } else {
      Log.d(TAG, "Interstitial ad was not ready yet.")
      onDismissed()
      loadInterstitial(activity)
    }
  }

  fun loadRewarded(context: Context) {
    val adRequest = AdRequest.Builder().build()
    RewardedAd.load(
      context,
      REWARDED_TEST_ID,
      adRequest,
      object : RewardedAdLoadCallback() {
        override fun onAdLoaded(ad: RewardedAd) {
          rewardedAd = ad
          Log.d(TAG, "Rewarded ad loaded successfully.")
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          rewardedAd = null
          Log.d(TAG, "Rewarded ad failed to load: ${error.message}")
        }
      },
    )
  }

  fun showRewarded(activity: Activity, onRewardEarned: () -> Unit, onAdClosed: () -> Unit) {
    if (rewardedAd != null) {
      rewardedAd?.fullScreenContentCallback =
        object : com.google.android.gms.ads.FullScreenContentCallback() {
          override fun onAdDismissedFullScreenContent() {
            rewardedAd = null
            loadRewarded(activity)
            onAdClosed()
          }

          override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
            rewardedAd = null
            onAdClosed()
          }
        }
      rewardedAd?.show(activity) { rewardItem ->
        Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
        onRewardEarned()
      }
    } else {
      Log.d(TAG, "Rewarded ad not ready. Granting reward fallback or allowing continuation.")
      // For friendly user experience when test ads are loading/unavailable, grant hint or notify
      onRewardEarned()
      onAdClosed()
      loadRewarded(activity)
    }
  }
}
