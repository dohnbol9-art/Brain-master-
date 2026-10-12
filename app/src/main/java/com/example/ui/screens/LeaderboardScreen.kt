package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class LeaderboardUser(val rank: Int, val name: String, val level: Int, val xp: Int)

@Composable
fun LeaderboardScreen(onBackClicked: () -> Unit) {
  val sampleUsers = listOf(
    LeaderboardUser(1, "BrainMaster99", 42, 6500),
    LeaderboardUser(2, "CognitiveAce", 38, 5800),
    LeaderboardUser(3, "LogicQueen", 35, 5200),
    LeaderboardUser(4, "You (Player)", 24, 3450),
    LeaderboardUser(5, "PuzzleGeek", 21, 2900),
    LeaderboardUser(6, "SynapseHero", 19, 2500)
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
          text = "Global Leaderboard",
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
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        itemsIndexed(sampleUsers) { index, user ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (user.name.contains("You")) CardBackgroundElevated else CardBackground
            ),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "#${user.rank}",
                color = if (user.rank <= 3) VibrantOrange else TextSecondary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.width(36.dp)
              )
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = user.name,
                  color = TextPrimary,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                )
                Text(
                  text = "Level ${user.level}",
                  color = TextSecondary,
                  fontSize = 12.sp
                )
              }
              Text(
                text = "${user.xp} XP",
                color = VibrantCyan,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
              )
            }
          }
        }
      }
    }
  }
}
