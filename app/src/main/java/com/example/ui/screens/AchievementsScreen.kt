package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class Achievement(val title: String, val description: String, val isUnlocked: Boolean)

@Composable
fun AchievementsScreen(onBackClicked: () -> Unit) {
  val achievements = listOf(
    Achievement("First Steps", "Complete your first brain puzzle", true),
    Achievement("Streak Master", "Maintain a 3-day login streak", true),
    Achievement("Number Whiz", "Complete 10 number sequence puzzles", true),
    Achievement("Memory Pro", "Successfully finish 5 memory card challenges", false),
    Achievement("Logic Wizard", "Solve 20 logic grid puzzles", false),
    Achievement("Century Club", "Reach Level 50 in Brain Battle", false)
  )

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
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBackClicked) {
          Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Achievements",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(achievements) { item ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (item.isUnlocked) VibrantOrange.copy(alpha = 0.2f) else CardBackgroundElevated,
                modifier = Modifier.size(48.dp)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = if (item.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (item.isUnlocked) VibrantOrange else TextSecondary
                  )
                }
              }
              Spacer(modifier = Modifier.width(16.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = item.title,
                  color = TextPrimary,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = item.description,
                  color = TextSecondary,
                  fontSize = 13.sp
                )
              }
            }
          }
        }
      }
    }
  }
}
