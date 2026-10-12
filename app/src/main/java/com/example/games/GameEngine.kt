package com.example.games

import com.example.model.GameModeType

data class PuzzleData(
  val question: String,
  val options: List<String> = emptyList(),
  val correctAnswer: String,
  val hint: String
)

object PuzzleGenerator {
  fun generatePuzzle(level: Int, mode: GameModeType): PuzzleData {
    val seed = level * 31
    return when (mode) {
      GameModeType.NUMBER_SEQUENCE -> {
        val start = (level % 10) + 2
        val step = (level % 5) + 3
        val seq = listOf(start, start + step, start + step * 2, start + step * 3)
        val next = start + step * 4
        PuzzleData(
          question = "Find the next number: ${seq.joinToString(", ")}, ?",
          options = listOf((next - 2).toString(), next.toString(), (next + 3).toString(), (next + 1).toString()).shuffled(),
          correctAnswer = next.toString(),
          hint = "Add $step to each previous number."
        )
      }
      GameModeType.MISSING_NUMBER -> {
        val base = level % 7 + 2
        val missing = base * 4
        PuzzleData(
          question = "Complete the grid relation: [$base] -> [${base * 2}] -> [?]",
          options = listOf((missing - 2).toString(), missing.toString(), (missing + 4).toString(), (base * 3).toString()).shuffled(),
          correctAnswer = missing.toString(),
          hint = "Multiply the previous number by 2."
        )
      }
      GameModeType.MATH_SPRINT -> {
        val a = (level % 12) + 5
        val b = (level % 8) + 3
        val ans = a * b
        PuzzleData(
          question = "Solve quickly: $a × $b = ?",
          options = listOf((ans - 4).toString(), ans.toString(), (ans + 5).toString(), (ans + 2).toString()).shuffled(),
          correctAnswer = ans.toString(),
          hint = "Basic multiplication."
        )
      }
      GameModeType.MEMORY_CARDS -> {
        PuzzleData(
          question = "Memorize and match all card pairs!",
          options = listOf("Match 4 Pairs"),
          correctAnswer = "Completed",
          hint = "Tap cards to flip and find matching symbols."
        )
      }
      GameModeType.PATTERN_MEMORY -> {
        PuzzleData(
          question = "Repeat the illuminated tile sequence!",
          options = listOf("Start Sequence"),
          correctAnswer = "Completed",
          hint = "Watch the flashing tiles and tap them in order."
        )
      }
      GameModeType.COLOR_MATCH -> {
        val colors = listOf("RED", "BLUE", "GREEN", "YELLOW")
        val target = colors[level % colors.size]
        PuzzleData(
          question = "Select the color matching the word: $target",
          options = colors.shuffled(),
          correctAnswer = target,
          hint = "Match the text color with the option."
        )
      }
      GameModeType.ODD_ONE_OUT -> {
        val list = if (level % 2 == 0) listOf("2", "4", "6", "9", "8") else listOf("15", "25", "35", "42", "55")
        val odd = if (level % 2 == 0) "9" else "42"
        PuzzleData(
          question = "Find the odd one out among numbers:",
          options = list.shuffled(),
          correctAnswer = odd,
          hint = "Look for even/odd or multiple properties."
        )
      }
      GameModeType.VISUAL_ROTATION -> {
        PuzzleData(
          question = "Which shape is rotated 90 degrees clockwise?",
          options = listOf("Shape A", "Shape B", "Shape C", "Shape D"),
          correctAnswer = "Shape B",
          hint = "Visualize the top pointing to the right."
        )
      }
      GameModeType.SHAPE_SEQUENCE -> {
        PuzzleData(
          question = "Complete the shape sequence: Circle, Square, Triangle, Circle, Square, ?",
          options = listOf("Circle", "Triangle", "Square", "Star"),
          correctAnswer = "Triangle",
          hint = "The repeating pattern has 3 shapes."
        )
      }
      GameModeType.LOGIC_GRID -> {
        PuzzleData(
          question = "Anna is taller than Bob. Bob is taller than Chris. Who is tallest?",
          options = listOf("Bob", "Chris", "Anna", "None"),
          correctAnswer = "Anna",
          hint = "Trace the inequality chain."
        )
      }
      GameModeType.RIVER_CROSSING -> {
        PuzzleData(
          question = "Cross the river with Wolf, Goat, and Cabbage without leaving Wolf alone with Goat.",
          options = listOf("Take Goat first", "Take Wolf first", "Take Cabbage first"),
          correctAnswer = "Take Goat first",
          hint = "The goat must be taken across first."
        )
      }
      GameModeType.TOWER_OF_HANOI -> {
        PuzzleData(
          question = "Move 3 disks from Peg A to Peg C following Hanoi rules.",
          options = listOf("Start Hanoi Puzzle"),
          correctAnswer = "Completed",
          hint = "Never place a larger disk on a smaller disk."
        )
      }
      GameModeType.SLIDING_PUZZLE -> {
        PuzzleData(
          question = "Slide numbered tiles into ascending order (1 to 8).",
          options = listOf("Start Sliding Puzzle"),
          correctAnswer = "Completed",
          hint = "Move tiles into the empty adjacent space."
        )
      }
      GameModeType.WORD_SCRAMBLE -> {
        val words = listOf("BRAIN", "LOGIC", "PUZZLE", "SMART", "SOLVE", "MIND")
        val word = words[level % words.size]
        val scrambled = word.toList().shuffled().joinToString("")
        PuzzleData(
          question = "Unscramble the letters: $scrambled",
          options = words.shuffled(),
          correctAnswer = word,
          hint = "Think of a cognitive ability related word."
        )
      }
      GameModeType.ANAGRAM_CHALLENGE -> {
        PuzzleData(
          question = "Find anagram for 'SILENT':",
          options = listOf("LISTEN", "TENSEL", "NESTLI", "ENTILS"),
          correctAnswer = "LISTEN",
          hint = "Rearrange the exact same 6 letters."
        )
      }
      GameModeType.FIND_DIFFERENCE -> {
        PuzzleData(
          question = "Find the subtle difference between the two grids.",
          options = listOf("Grid A Cell (2,2)", "Grid B Cell (1,3)", "Grid A Cell (3,1)", "None"),
          correctAnswer = "Grid A Cell (2,2)",
          hint = "Inspect row 2 column 2 carefully."
        )
      }
      GameModeType.PATH_FINDER -> {
        PuzzleData(
          question = "Find the valid maze path from Start (S) to Goal (G).",
          options = listOf("Right, Down, Right, Down", "Down, Down, Right, Right", "Right, Right, Up, Right", "Up, Right, Down, Down"),
          correctAnswer = "Right, Down, Right, Down",
          hint = "Avoid the wall obstacles."
        )
      }
      GameModeType.BLOCK_PLACEMENT -> {
        PuzzleData(
          question = "Fit the L-block into the 3x3 grid without overlap.",
          options = listOf("Top-Left Corner", "Bottom-Right", "Center", "Top-Right"),
          correctAnswer = "Top-Left Corner",
          hint = "Rotate the L-block to fit the empty corner."
        )
      }
      GameModeType.REACTION_TEST -> {
        PuzzleData(
          question = "Tap the green target as fast as possible when it appears!",
          options = listOf("Start Reaction Test"),
          correctAnswer = "Completed",
          hint = "Stay alert and tap immediately upon color change."
        )
      }
      GameModeType.BRAIN_TEASERS -> {
        PuzzleData(
          question = "What has keys, but no locks; space, but no room; and you can enter, but not go outside?",
          options = listOf("A Piano", "A Keyboard", "A House", "A Map"),
          correctAnswer = "A Keyboard",
          hint = "Think about typing devices."
        )
      }
    }
  }
}
