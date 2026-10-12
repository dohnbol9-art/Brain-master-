package com.example.audio

import android.content.Context
import android.os.Vibrator
import android.os.VibratorManager

object SoundManager {
  fun vibrateClick(context: Context, enabled: Boolean) {
    if (!enabled) return
    try {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        val vibratorManager =
          context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        val vibrator = vibratorManager?.defaultVibrator
        vibrator?.vibrate(
          android.os.VibrationEffect.createOneShot(
            50,
            android.os.VibrationEffect.DEFAULT_AMPLITUDE,
          )
        )
      } else {
        @Suppress("DEPRECATION")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        @Suppress("DEPRECATION")
        vibrator?.vibrate(50)
      }
    } catch (e: Exception) {
      // Ignore if vibration not permitted or unavailable
    }
  }

  fun vibrateSuccess(context: Context, enabled: Boolean) {
    if (!enabled) return
    try {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        val vibratorManager =
          context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        val vibrator = vibratorManager?.defaultVibrator
        vibrator?.vibrate(
          android.os.VibrationEffect.createWaveform(longArrayOf(0, 70, 50, 70), -1)
        )
      } else {
        @Suppress("DEPRECATION")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        @Suppress("DEPRECATION")
        vibrator?.vibrate(longArrayOf(0, 70, 50, 70), -1)
      }
    } catch (e: Exception) {}
  }

  fun vibrateError(context: Context, enabled: Boolean) {
    if (!enabled) return
    try {
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        val vibratorManager =
          context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
        val vibrator = vibratorManager?.defaultVibrator
        vibrator?.vibrate(
          android.os.VibrationEffect.createWaveform(longArrayOf(0, 100, 100, 100), -1)
        )
      } else {
        @Suppress("DEPRECATION")
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        @Suppress("DEPRECATION")
        vibrator?.vibrate(longArrayOf(0, 100, 100, 100), -1)
      }
    } catch (e: Exception) {}
  }
}
