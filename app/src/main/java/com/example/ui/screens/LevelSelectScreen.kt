package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CHAPTERS
import com.example.ui.theme.*

@Composable
fun LevelSelectScreen(
  chapterId: Int,
  completedLevels: Set<Int>,
  onBackClicked: () -> Unit,
  onLevelSelected: (Int) -> Unit
) {
  val chapter = CHAPTERS.find { it.id == chapterId } ?: CHAPTERS[0]

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
        Column {
          Text(
            text = chapter.title,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
          )
          Text(
            text = "Levels ${chapter.startLevel} to ${chapter.endLevel}",
            color = TextSecondary,
            fontSize = 12.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        val levels = (chapter.startLevel..chapter.endLevel).toList()
        items(levels.size) { index ->
          val levelId = levels[index]
          val isCompleted = completedLevels.contains(levelId)
          val isUnlocked = levelId <= (completedLevels.maxOrNull() ?: 0) + 1

          LevelCell(
            levelId = levelId,
            isCompleted = isCompleted,
            isUnlocked = isUnlocked,
            onClick = { if (isUnlocked) onLevelSelected(levelId) }
          )
        }
      }
    }
  }
}

@Composable
fun LevelCell(levelId: Int, isCompleted: Boolean, isUnlocked: Boolean, onClick: () -> Unit) {
  val backgroundColor = when {
    isCompleted -> VibrantCyan.copy(alpha = 0.3f)
    isUnlocked -> CardBackground
    else -> CardBackground.copy(alpha = 0.4f)
  }

  val textColor = if (isUnlocked) TextPrimary else TextSecondary.copy(alpha = 0.5f)

  Box(
    modifier = Modifier
      .aspectRatio(1f)
      .clip(RoundedCornerShape(16.dp))
      .background(backgroundColor)
      .clickable(enabled = isUnlocked, onClick = onClick),
    contentAlignment = Alignment.Center
  ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Text(
        text = levelId.toString(),
        color = textColor,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
      )
      if (isCompleted) {
        Spacer(modifier = Modifier.height(2.dp))
        Icon(
          Icons.Default.Check,
          contentDescription = "Completed",
          tint = VibrantCyan,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}
