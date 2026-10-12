package com.example.model

data class PlayerProfile(
  val level: Int = 1,
  val xp: Int = 0,
  val nextLevelXp: Int = 100,
  val hints: Int = 5,
  val streak: Int = 1,
  val lastLoginDate: String = "",
  val soundEnabled: Boolean = true,
  val hapticsEnabled: Boolean = true,
  val completedLevels: Set<Int> = emptySet(),
  val levelStars: Map<Int, Int> = emptyMap(), // levelId -> stars (1-3)
  val bestScores: Map<Int, Int> = emptyMap() // levelId -> score
)

data class Chapter(
  val id: Int,
  val title: String,
  val subtitle: String,
  val startLevel: Int,
  val endLevel: Int,
  val iconName: String,
  val isUnlocked: Boolean = true
)

enum class GameModeType(val id: Int, val title: String, val description: String) {
  NUMBER_SEQUENCE(1, "Number Sequence", "Identify the next number in the mathematical pattern."),
  MISSING_NUMBER(2, "Missing Number", "Complete the number grid using a logical rule."),
  MATH_SPRINT(3, "Math Sprint", "Solve arithmetic questions against a countdown."),
  MEMORY_CARDS(4, "Memory Cards", "Flip cards and find matching pairs."),
  PATTERN_MEMORY(5, "Pattern Memory", "Memorize a sequence of illuminated tiles and repeat it."),
  COLOR_MATCH(6, "Color Match", "Identify colors and follow changing matching rules."),
  ODD_ONE_OUT(7, "Odd One Out", "Find the symbol, number, or object that differs."),
  VISUAL_ROTATION(8, "Visual Rotation", "Select the correctly rotated shape."),
  SHAPE_SEQUENCE(9, "Shape Sequence", "Find the next shape in a visual pattern."),
  LOGIC_GRID(10, "Logic Grid", "Solve small deduction puzzles using clues."),
  RIVER_CROSSING(11, "River Crossing", "Move characters or objects respecting puzzle rules."),
  TOWER_OF_HANOI(12, "Tower of Hanoi", "Move disks according to the game rules."),
  SLIDING_PUZZLE(13, "Sliding Puzzle", "Rearrange numbered tiles into correct order."),
  WORD_SCRAMBLE(14, "Word Scramble", "Rearrange letters to form a valid word."),
  ANAGRAM_CHALLENGE(15, "Anagram Challenge", "Identify words formed from shuffled letters."),
  FIND_DIFFERENCE(16, "Find the Difference", "Compare two original visual patterns."),
  PATH_FINDER(17, "Path Finder", "Guide a character through a grid to reach a goal."),
  BLOCK_PLACEMENT(18, "Block Placement", "Place shapes into a board without overlapping."),
  REACTION_TEST(19, "Reaction Test", "Tap targets when they appear and measure speed."),
  BRAIN_TEASERS(20, "Brain Teasers", "Solve carefully validated riddles and logic questions.")
}

val CHAPTERS = listOf(
  Chapter(1, "Beginner Brain", "Foundations of mental acuity", 1, 100, "ic_brain"),
  Chapter(2, "Pattern Explorer", "Discover hidden numeric and visual rules", 101, 200, "ic_pattern"),
  Chapter(3, "Memory Master", "Sharpen recall and sequence retention", 201, 300, "ic_memory"),
  Chapter(4, "Logic Expert", "Deduce answers from complex clues", 301, 400, "ic_logic"),
  Chapter(5, "Speed Champion", "High-tempo arithmetic and reaction tests", 401, 500, "ic_speed"),
  Chapter(6, "Visual Genius", "Spatial reasoning and rotation puzzles", 501, 600, "ic_visual"),
  Chapter(7, "Puzzle Strategist", "River crossing, Hanoi, and pathfinding", 601, 700, "ic_strategy"),
  Chapter(8, "Brain Master", "Advanced multi-step logic challenges", 701, 800, "ic_master"),
  Chapter(9, "Expert Arena", "Mastery level tests for elite minds", 801, 900, "ic_arena"),
  Chapter(10, "Ultimate Brain Battle", "The pinnacle of cognitive warfare", 901, 1000, "ic_ultimate")
)

fun getGameModeForLevel(level: Int): GameModeType {
  val index = ((level - 1) % 20)
  return GameModeType.entries[index]
}
