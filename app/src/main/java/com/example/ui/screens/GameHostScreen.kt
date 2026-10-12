package com.example.ui.screens

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdManager
import com.example.audio.SoundManager
import com.example.games.PuzzleGenerator
import com.example.model.getGameModeForLevel
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun GameHostScreen(
  levelId: Int,
  soundEnabled: Boolean,
  hapticsEnabled: Boolean,
  hintsCount: Int,
  onLevelCompleted: (Int, Int) -> Unit,
  onUseHint: () -> Unit,
  onBackClicked: () -> Unit
) {
  val context = LocalContext.current
  val activity = context as? Activity
  val gameMode = getGameModeForLevel(levelId)
  val puzzle = remember(levelId) { PuzzleGenerator.generatePuzzle(levelId, gameMode) }

  var selectedOption by remember { mutableStateOf<String?>(null) }
  var isCorrect by remember { mutableStateOf<Boolean?>(null) }
  var timeLeft by remember { mutableStateOf(60) }
  var showHintDialog by remember { mutableStateOf(false) }
  var showResults by remember { mutableStateOf(false) }

  // Timer countdown
  LaunchedEffect(levelId) {
    while (timeLeft > 0 && isCorrect != true) {
      delay(1000L)
      timeLeft -= 1
    }
  }

  if (showResults) {
    ResultsScreen(
      levelId = levelId,
      xpEarned = 50,
      onNextLevel = {
        if (activity != null) {
          AdManager.showInterstitial(activity) {
            onLevelCompleted(levelId, 50)
          }
        } else {
          onLevelCompleted(levelId, 50)
        }
      },
      onHome = onBackClicked
    )
    return
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavy)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .navigationBarsPadding()
        .statusBarsPadding()
    ) {
      // Top Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBackClicked) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Text(
          text = "Level $levelId: ${gameMode.title}",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
        IconButton(onClick = { showHintDialog = true }) {
          Icon(Icons.Default.Lightbulb, contentDescription = "Hint", tint = VibrantOrange)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Timer and progress bar
      LinearProgressIndicator(
        progress = { timeLeft / 60f },
        modifier = Modifier.fillMaxWidth().height(8.dp),
        color = VibrantCyan,
        trackColor = CardBackground,
      )
      Spacer(modifier = Modifier.height(24.dp))

      // Puzzle Card
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = puzzle.question,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 24.dp)
          )

          if (puzzle.options.isNotEmpty()) {
            puzzle.options.forEach { option ->
              val isSelected = selectedOption == option
              val buttonColor = when {
                isSelected && isCorrect == true -> SuccessGreen
                isSelected && isCorrect == false -> ErrorRed
                else -> CardBackgroundElevated
              }

              Button(
                onClick = {
                  if (isCorrect == null) {
                    selectedOption = option
                    val correct = option == puzzle.correctAnswer
                    isCorrect = correct
                    if (correct) {
                      SoundManager.vibrateSuccess(context, hapticsEnabled)
                      showResults = true
                    } else {
                      SoundManager.vibrateError(context, hapticsEnabled)
                    }
                  }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp)
                  .height(52.dp)
              ) {
                Text(
                  text = option,
                  color = TextPrimary,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                )
              }
            }
          } else {
            // Interactive puzzle action for non-MCQ
            Button(
              onClick = {
                SoundManager.vibrateSuccess(context, hapticsEnabled)
                showResults = true
              },
              colors = ButtonDefaults.buttonColors(containerColor = VibrantCyan),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
            ) {
              Text(
                text = "Complete Puzzle Challenge",
                color = DeepNavy,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
              )
            }
          }
        }
      }
    }
  }

  if (showHintDialog) {
    AlertDialog(
      onDismissRequest = { showHintDialog = false },
      title = { Text("Level Hint") },
      text = { Text(puzzle.hint) },
      confirmButton = {
        TextButton(onClick = { showHintDialog = false }) {
          Text("Got it", color = VibrantCyan)
        }
      },
      dismissButton = {
        TextButton(onClick = {
          if (activity != null) {
            AdManager.showRewarded(
              activity,
              onRewardEarned = {
                onUseHint()
                showHintDialog = false
              },
              onAdClosed = {}
            )
          } else {
            showHintDialog = false
          }
        }) {
          Text("Watch Ad for Free Hint", color = VibrantOrange)
        }
      },
      containerColor = CardBackground,
      titleContentColor = TextPrimary,
      textContentColor = TextSecondary
    )
  }
}
