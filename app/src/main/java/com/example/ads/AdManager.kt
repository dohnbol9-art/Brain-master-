package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
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
  private var isInterstitialLoading = false
  private var isRewardedLoading = false

  fun initialize(context: Context) {
    if (isInitialized) return
    val appContext = context.applicationContext

    // Configure test devices to ensure standard test ad delivery
    val requestConfiguration = RequestConfiguration.Builder()
      .setTestDeviceIds(listOf(AdRequest.DEVICE_ID_EMULATOR))
      .build()
    MobileAds.setRequestConfiguration(requestConfiguration)

    MobileAds.initialize(appContext) { initializationStatus ->
      isInitialized = true
      Log.d(TAG, "AdMob initialized successfully: $initializationStatus")
      loadInterstitial(appContext)
      loadRewarded(appContext)
    }
  }

  fun loadInterstitial(context: Context) {
    if (isInterstitialLoading || interstitialAd != null) return
    isInterstitialLoading = true

    val appContext = context.applicationContext
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
      appContext,
      INTERSTITIAL_TEST_ID,
      adRequest,
      object : InterstitialAdLoadCallback() {
        override fun onAdLoaded(ad: InterstitialAd) {
          interstitialAd = ad
          isInterstitialLoading = false
          Log.d(TAG, "Interstitial ad loaded successfully.")
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          interstitialAd = null
          isInterstitialLoading = false
          Log.w(TAG, "Interstitial ad failed to load: code=${error.code}, message=${error.message}")
        }
      },
    )
  }

  fun showInterstitial(activity: Activity, onDismissed: () -> Unit) {
    val ad = interstitialAd
    if (ad != null) {
      ad.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
          interstitialAd = null
          loadInterstitial(activity.applicationContext)
          onDismissed()
        }

        override fun onAdFailedToShowFullScreenContent(error: AdError) {
          Log.w(TAG, "Interstitial ad failed to show: ${error.message}")
          interstitialAd = null
          loadInterstitial(activity.applicationContext)
          onDismissed()
        }
      }
      ad.show(activity)
    } else {
      Log.d(TAG, "Interstitial ad not ready; proceeding without blocking.")
      loadInterstitial(activity.applicationContext)
      onDismissed()
    }
  }

  fun loadRewarded(context: Context) {
    if (isRewardedLoading || rewardedAd != null) return
    isRewardedLoading = true

    val appContext = context.applicationContext
    val adRequest = AdRequest.Builder().build()
    RewardedAd.load(
      appContext,
      REWARDED_TEST_ID,
      adRequest,
      object : RewardedAdLoadCallback() {
        override fun onAdLoaded(ad: RewardedAd) {
          rewardedAd = ad
          isRewardedLoading = false
          Log.d(TAG, "Rewarded ad loaded successfully.")
        }

        override fun onAdFailedToLoad(error: LoadAdError) {
          rewardedAd = null
          isRewardedLoading = false
          Log.w(TAG, "Rewarded ad failed to load: code=${error.code}, message=${error.message}")
        }
      },
    )
  }

  fun isRewardedAdReady(): Boolean = rewardedAd != null

  fun showRewarded(
    activity: Activity,
    onRewardEarned: () -> Unit,
    onAdClosed: () -> Unit,
    onError: (String) -> Unit
  ) {
    val ad = rewardedAd
    if (ad != null) {
      ad.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
          rewardedAd = null
          loadRewarded(activity.applicationContext)
          onAdClosed()
        }

        override fun onAdFailedToShowFullScreenContent(error: AdError) {
          Log.w(TAG, "Rewarded ad failed to show: ${error.message}")
          rewardedAd = null
          loadRewarded(activity.applicationContext)
          onError("Ad failed to play. Please try again.")
        }
      }
      ad.show(activity) { rewardItem ->
        Log.d(TAG, "User earned reward: ${rewardItem.amount} ${rewardItem.type}")
        onRewardEarned()
      }
    } else {
      Log.d(TAG, "Rewarded ad not loaded yet.")
      loadRewarded(activity.applicationContext)
      onError("Ad is still loading. Please try again in a few moments.")
    }
  }
}
