package com.example.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data.GameRepository
import com.example.model.PlayerProfile
import com.example.ui.screens.*
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
  object Home : Screen("home")
  object Chapters : Screen("chapters")
  object LevelSelect : Screen("levels/{chapterId}") {
    fun createRoute(chapterId: Int) = "levels/$chapterId"
  }
  object Game : Screen("game/{levelId}") {
    fun createRoute(levelId: Int) = "game/$levelId"
  }
  object Leaderboard : Screen("leaderboard")
  object Achievements : Screen("achievements")
  object Settings : Screen("settings")
  object PrivacyPolicy : Screen("privacy_policy")
}

@Composable
fun NavGraph() {
  val context = LocalContext.current
  val repository = remember { GameRepository(context) }
  val profile by repository.playerProfileFlow.collectAsState(initial = PlayerProfile())
  val navController = rememberNavController()
  val scope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
    repository.checkDailyLogin()
  }

  NavHost(navController = navController, startDestination = Screen.Home.route) {
    composable(Screen.Home.route) {
      HomeScreen(
        profile = profile,
        onPlayClicked = {
          val nextLevel = (profile.completedLevels.maxOrNull() ?: 0) + 1
          navController.navigate(Screen.Game.createRoute(nextLevel))
        },
        onChaptersClicked = { navController.navigate(Screen.Chapters.route) },
        onLeaderboardClicked = { navController.navigate(Screen.Leaderboard.route) },
        onAchievementsClicked = { navController.navigate(Screen.Achievements.route) },
        onSettingsClicked = { navController.navigate(Screen.Settings.route) },
        onDailyChallengeClicked = {
          val dailyLevel = ((profile.streak * 7) % 1000) + 1
          navController.navigate(Screen.Game.createRoute(dailyLevel))
        }
      )
    }

    composable(Screen.Chapters.route) {
      ChapterSelectScreen(
        onBackClicked = { navController.popBackStack() },
        onChapterSelected = { chapterId ->
          navController.navigate(Screen.LevelSelect.createRoute(chapterId))
        }
      )
    }

    composable(
      route = Screen.LevelSelect.route,
      arguments = listOf(navArgument("chapterId") { type = NavType.IntType })
    ) { backStackEntry ->
      val chapterId = backStackEntry.arguments?.getInt("chapterId") ?: 1
      LevelSelectScreen(
        chapterId = chapterId,
        completedLevels = profile.completedLevels,
        onBackClicked = { navController.popBackStack() },
        onLevelSelected = { levelId ->
          navController.navigate(Screen.Game.createRoute(levelId))
        }
      )
    }

    composable(
      route = Screen.Game.route,
      arguments = listOf(navArgument("levelId") { type = NavType.IntType })
    ) { backStackEntry ->
      val levelId = backStackEntry.arguments?.getInt("levelId") ?: 1
      GameHostScreen(
        levelId = levelId,
        soundEnabled = profile.soundEnabled,
        hapticsEnabled = profile.hapticsEnabled,
        hintsCount = profile.hints,
        onLevelCompleted = { completedId, xp ->
          scope.launch {
            repository.completeLevel(completedId, xp)
          }
          navController.popBackStack()
        },
        onUseHint = {
          scope.launch {
            repository.addHint(1)
          }
        },
        onBackClicked = { navController.popBackStack() }
      )
    }

    composable(Screen.Leaderboard.route) {
      LeaderboardScreen(onBackClicked = { navController.popBackStack() })
    }

    composable(Screen.Achievements.route) {
      AchievementsScreen(onBackClicked = { navController.popBackStack() })
    }

    composable(Screen.Settings.route) {
      SettingsScreen(
        soundEnabled = profile.soundEnabled,
        hapticsEnabled = profile.hapticsEnabled,
        onSettingsChanged = { sound, haptics ->
          scope.launch {
            repository.updateSettings(sound, haptics)
          }
        },
        onPrivacyPolicyClicked = { navController.navigate(Screen.PrivacyPolicy.route) },
        onBackClicked = { navController.popBackStack() }
      )
    }

    composable(Screen.PrivacyPolicy.route) {
      PrivacyPolicyScreen(onBackClicked = { navController.popBackStack() })
    }
  }
}
