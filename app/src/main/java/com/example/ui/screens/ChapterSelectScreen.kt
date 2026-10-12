package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CHAPTERS
import com.example.model.Chapter
import com.example.ui.theme.*

@Composable
fun ChapterSelectScreen(
  onBackClicked: () -> Unit,
  onChapterSelected: (Int) -> Unit
) {
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
          text = "Select Chapter",
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
        items(CHAPTERS) { chapter ->
          ChapterCard(chapter = chapter, onClick = { onChapterSelected(chapter.id) })
        }
      }
    }
  }
}

@Composable
fun ChapterCard(chapter: Chapter, onClick: () -> Unit) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Chapter ${chapter.id}",
          color = VibrantCyan,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = chapter.title,
          color = TextPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = chapter.subtitle,
          color = TextSecondary,
          fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Levels ${chapter.startLevel} - ${chapter.endLevel}",
          color = VibrantOrange,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
      Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = null,
        tint = TextSecondary
      )
    }
  }
}
