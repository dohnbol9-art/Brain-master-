package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.BannerAdView
import com.example.model.PlayerProfile
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  profile: PlayerProfile,
  onPlayClicked: () -> Unit,
  onChaptersClicked: () -> Unit,
  onLeaderboardClicked: () -> Unit,
  onAchievementsClicked: () -> Unit,
  onSettingsClicked: () -> Unit,
  onDailyChallengeClicked: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(DeepNavy, DarkPurple)
        )
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .navigationBarsPadding()
        .statusBarsPadding()
    ) {
      // Top Bar / Profile Header
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = VibrantCyan.copy(alpha = 0.2f),
            modifier = Modifier.size(48.dp)
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(Icons.Default.Person, contentDescription = "Profile", tint = VibrantCyan)
            }
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Brain Master",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
            )
            Text(
              text = "Level ${profile.level} • XP: ${profile.xp}/${profile.nextLevelXp}",
              color = TextSecondary,
              fontSize = 13.sp
            )
          }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          IconButton(onClick = onSettingsClicked) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = TextPrimary)
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyColumn(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Logo & Title Banner
        item {
          Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = "BRAIN BATTLE",
                color = VibrantCyan,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                letterSpacing = 2.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Mind Challenge & Cognitive Arena",
                color = TextSecondary,
                fontSize = 14.sp
              )
              Spacer(modifier = Modifier.height(20.dp))

              Button(
                onClick = onPlayClicked,
                colors = ButtonDefaults.buttonColors(containerColor = VibrantCyan),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(52.dp)
              ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = DeepNavy)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "CONTINUE PLAYING",
                  color = DeepNavy,
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp
                )
              }
            }
          }
        }

        // Daily Streak & Challenge
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            // Daily Streak Card
            Card(
              shape = RoundedCornerShape(20.dp),
              colors = CardDefaults.cardColors(containerColor = CardBackground),
              modifier = Modifier.weight(1f)
            ) {
              Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = VibrantOrange, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${profile.streak} Days", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Daily Streak", color = TextSecondary, fontSize = 12.sp)
              }
            }

            // Hints Card
            Card(
              shape = RoundedCornerShape(20.dp),
              colors = CardDefaults.cardColors(containerColor = CardBackground),
              modifier = Modifier.weight(1f)
            ) {
              Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
              ) {
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = VibrantCyan, modifier = Modifier.size(32.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${profile.hints} Available", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Hints", color = TextSecondary, fontSize = 12.sp)
              }
            }
          }
        }

        // Daily Challenge Banner
        item {
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackgroundElevated),
            modifier = Modifier
              .fillMaxWidth()
              .clickable(onClick = onDailyChallengeClicked)
          ) {
            Row(
              modifier = Modifier.padding(20.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Star, contentDescription = null, tint = VibrantOrange, modifier = Modifier.size(40.dp))
              Spacer(modifier = Modifier.width(16.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(text = "Daily Challenge", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = "Solve today's special puzzle for bonus XP", color = TextSecondary, fontSize = 13.sp)
              }
              Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextSecondary)
            }
          }
        }

        // Menu Grid
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Button(
              onClick = onChaptersClicked,
              colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .weight(1f)
                .height(80.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.GridOn, contentDescription = null, tint = VibrantCyan)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Chapters & Modes", color = TextPrimary, fontSize = 12.sp)
              }
            }

            Button(
              onClick = onLeaderboardClicked,
              colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .weight(1f)
                .height(80.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Leaderboard, contentDescription = null, tint = VibrantViolet)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Leaderboard", color = TextPrimary, fontSize = 12.sp)
              }
            }
          }
        }

        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            Button(
              onClick = onAchievementsClicked,
              colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .weight(1f)
                .height(80.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = VibrantOrange)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Achievements", color = TextPrimary, fontSize = 12.sp)
              }
            }

            Button(
              onClick = onSettingsClicked,
              colors = ButtonDefaults.buttonColors(containerColor = CardBackground),
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .weight(1f)
                .height(80.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = TextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Settings", color = TextPrimary, fontSize = 12.sp)
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // AdMob Banner at bottom
      BannerAdView()
    }
  }
}
