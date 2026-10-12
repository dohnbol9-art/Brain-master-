package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
  soundEnabled: Boolean,
  hapticsEnabled: Boolean,
  onSettingsChanged: (Boolean, Boolean) -> Unit,
  onPrivacyPolicyClicked: () -> Unit,
  onBackClicked: () -> Unit
) {
  var sound by remember { mutableStateOf(soundEnabled) }
  var haptics by remember { mutableStateOf(hapticsEnabled) }

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
          text = "Settings",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Sound Effects", color = TextPrimary, fontSize = 16.sp)
            Switch(
              checked = sound,
              onCheckedChange = {
                sound = it
                onSettingsChanged(sound, haptics)
              },
              colors = SwitchDefaults.colors(checkedThumbColor = VibrantCyan)
            )
          }

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "Haptic Vibration", color = TextPrimary, fontSize = 16.sp)
            Switch(
              checked = haptics,
              onCheckedChange = {
                haptics = it
                onSettingsChanged(sound, haptics)
              },
              colors = SwitchDefaults.colors(checkedThumbColor = VibrantCyan)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onPrivacyPolicyClicked)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "Privacy Policy", color = TextPrimary, fontSize = 16.sp)
          Icon(Icons.Default.ArrowBack, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(16.dp))
        }
      }
    }
  }
}
