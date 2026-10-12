package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.PlayerProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "brain_battle_prefs")

class GameRepository(private val context: Context) {

  private object PreferencesKeys {
    val LEVEL = intPreferencesKey("player_level")
    val XP = intPreferencesKey("player_xp")
    val NEXT_XP = intPreferencesKey("player_next_xp")
    val HINTS = intPreferencesKey("player_hints")
    val STREAK = intPreferencesKey("player_streak")
    val LAST_LOGIN = stringPreferencesKey("last_login_date")
    val SOUND = booleanPreferencesKey("sound_enabled")
    val HAPTICS = booleanPreferencesKey("haptics_enabled")
    val COMPLETED_LEVELS = stringPreferencesKey("completed_levels")
  }

  val playerProfileFlow: Flow<PlayerProfile> = context.dataStore.data.map { preferences ->
    val level = preferences[PreferencesKeys.LEVEL] ?: 1
    val xp = preferences[PreferencesKeys.XP] ?: 0
    val nextXp = preferences[PreferencesKeys.NEXT_XP] ?: 100
    val hints = preferences[PreferencesKeys.HINTS] ?: 5
    val streak = preferences[PreferencesKeys.STREAK] ?: 1
    val lastLogin = preferences[PreferencesKeys.LAST_LOGIN] ?: ""
    val sound = preferences[PreferencesKeys.SOUND] ?: true
    val haptics = preferences[PreferencesKeys.HAPTICS] ?: true
    val completedStr = preferences[PreferencesKeys.COMPLETED_LEVELS] ?: ""
    val completedLevels = if (completedStr.isBlank()) emptySet() else completedStr.split(",").mapNotNull { it.toIntOrNull() }.toSet()

    PlayerProfile(
      level = level,
      xp = xp,
      nextLevelXp = nextXp,
      hints = hints,
      streak = streak,
      lastLoginDate = lastLogin,
      soundEnabled = sound,
      hapticsEnabled = haptics,
      completedLevels = completedLevels
    )
  }

  suspend fun completeLevel(levelId: Int, xpEarned: Int) {
    context.dataStore.edit { preferences ->
      val currentXp = preferences[PreferencesKeys.XP] ?: 0
      val currentLevel = preferences[PreferencesKeys.LEVEL] ?: intPreferencesKey("player_level").let { 1 }
      val nextXpTarget = preferences[PreferencesKeys.NEXT_XP] ?: 100
      val currentHints = preferences[PreferencesKeys.HINTS] ?: 5

      val newXp = currentXp + xpEarned
      var newLevel = currentLevel
      var newNextXp = nextXpTarget

      if (newXp >= newNextXp) {
        newLevel += 1
        newNextXp += 150
      }

      preferences[PreferencesKeys.XP] = newXp
      preferences[PreferencesKeys.LEVEL] = newLevel
      preferences[PreferencesKeys.NEXT_XP] = newNextXp
      preferences[PreferencesKeys.HINTS] = currentHints + 1 // Bonus hint on level completion

      val completedStr = preferences[PreferencesKeys.COMPLETED_LEVELS] ?: ""
      val completedSet = if (completedStr.isBlank()) mutableSetOf() else completedStr.split(",").mapNotNull { it.toIntOrNull() }.toMutableSet()
      completedSet.add(levelId)
      preferences[PreferencesKeys.COMPLETED_LEVELS] = completedSet.joinToString(",")
    }
  }

  suspend fun useHint(): Boolean {
    var success = false
    context.dataStore.edit { preferences ->
      val currentHints = preferences[PreferencesKeys.HINTS] ?: 5
      if (currentHints > 0) {
        preferences[PreferencesKeys.HINTS] = currentHints - 1
        success = true
      }
    }
    return success
  }

  suspend fun addHint(count: Int) {
    context.dataStore.edit { preferences ->
      val currentHints = preferences[PreferencesKeys.HINTS] ?: 5
      preferences[PreferencesKeys.HINTS] = currentHints + count
    }
  }

  suspend fun updateSettings(sound: Boolean, haptics: Boolean) {
    context.dataStore.edit { preferences ->
      preferences[PreferencesKeys.SOUND] = sound
      preferences[PreferencesKeys.HAPTICS] = haptics
    }
  }

  suspend fun checkDailyLogin() {
    val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    val today = dateFormat.format(Date())
    context.dataStore.edit { preferences ->
      val lastLogin = preferences[PreferencesKeys.LAST_LOGIN] ?: ""
      val currentStreak = preferences[PreferencesKeys.STREAK] ?: 1
      if (lastLogin != today) {
        // Check if yesterday
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DATE, -1)
        val yesterday = dateFormat.format(cal.time)
        val newStreak = if (lastLogin == yesterday) currentStreak + 1 else 1
        preferences[PreferencesKeys.STREAK] = newStreak
        preferences[PreferencesKeys.LAST_LOGIN] = today
        // Bonus hints for daily streak
        val currentHints = preferences[PreferencesKeys.HINTS] ?: 5
        preferences[PreferencesKeys.HINTS] = currentHints + 2
      }
    }
  }
}
