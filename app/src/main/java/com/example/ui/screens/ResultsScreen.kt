package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun ResultsScreen(
  levelId: Int,
  xpEarned: Int,
  onNextLevel: () -> Unit,
  onHome: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(DeepNavy),
    contentAlignment = Alignment.Center
  ) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = CardBackground),
      modifier = Modifier
        .fillMaxWidth(0.85f)
        .wrapContentHeight()
    ) {
      Column(
        modifier = Modifier.padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(
          imageVector = Icons.Default.EmojiEvents,
          contentDescription = null,
          tint = VibrantOrange,
          modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "Level $levelId Completed!",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
          repeat(3) {
            Icon(Icons.Default.Star, contentDescription = null, tint = VibrantOrange, modifier = Modifier.size(32.dp))
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = "+$xpEarned XP Earned",
          color = VibrantCyan,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(28.dp))

        Button(
          onClick = onNextLevel,
          colors = ButtonDefaults.buttonColors(containerColor = VibrantCyan),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
        ) {
          Text(
            text = "NEXT LEVEL",
            color = DeepNavy,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
          )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onHome) {
          Text(text = "Back to Home", color = TextSecondary, fontSize = 14.sp)
        }
      }
    }
  }
}
