package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun PrivacyPolicyScreen(onBackClicked: () -> Unit) {
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
          text = "Privacy Policy",
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Column(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = "Brain Battle Privacy Policy",
          color = VibrantCyan,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )
        Text(
          text = "Last updated: October 2026",
          color = TextSecondary,
          fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Brain Battle respects your privacy and is committed to protecting your personal data. This application stores game progress, level scores, and user preferences locally on your device using encrypted local storage.",
          color = TextPrimary,
          fontSize = 14.sp
        )
        Text(
          text = "Advertising: We use Google AdMob to display test advertisements during development and standard ads upon official release. AdMob may collect non-personal device identifiers to serve contextual advertisements in accordance with Google Play policies.",
          color = TextPrimary,
          fontSize = 14.sp
        )
        Text(
          text = "Data Collection: We do not collect or transmit personal user information, location data, or contacts. All puzzle sessions and achievements remain local to your device.",
          color = TextPrimary,
          fontSize = 14.sp
        )
      }
    }
  }
}
