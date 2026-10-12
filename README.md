# Brain Battle: Mind Challenge 🧠🎮

**Brain Battle: Mind Challenge** is a complete, polished native Android puzzle and brain-training game built with **Kotlin** and **Jetpack Compose**.

---

## 📥 How to Download the APK from GitHub

Every time code is pushed or a pull request is created, the automated GitHub Actions workflow builds the APK and makes it available for download.

### Steps to Download:
1. Open this repository on GitHub.
2. Click on the **Actions** tab at the top.
3. Select the workflow run named **"Build and Package APK"** (or the latest commit run).
4. Scroll down to the bottom of the page to the **Artifacts** section.
5. Click on **`BrainBattle-debug-apk`** to download the zip file.
6. Extract the zip file to obtain `BrainBattle-debug.apk`.
7. Transfer the APK to your Android device and install it (allow "Install from unknown sources" if prompted).

### Manual Build Trigger:
You can also generate an APK on-demand at any time:
1. Go to **Actions** → **Build and Package APK**.
2. Click **Run workflow** dropdown on the right.
3. Select your branch (e.g. `main`) and click **Run workflow**.
4. Once completed (approx. 2 minutes), download the generated APK from the **Artifacts** section.

---

## ✨ Features & Game Modes

### 20 Real Interactive Game Modes:
1. **Number Sequence** — Identify mathematical progressions.
2. **Missing Number** — Complete logical number grids.
3. **Math Sprint** — Fast-paced arithmetic calculations.
4. **Memory Cards** — Match pairs of hidden symbols.
5. **Pattern Memory** — Memorize and repeat illuminated tile sequences.
6. **Color Match** — Follow changing color and text rules.
7. **Odd One Out** — Spot the outlier symbol or number.
8. **Visual Rotation** — Mentally rotate 2D/3D shapes.
9. **Shape Sequence** — Identify pattern rules in visual sequences.
10. **Logic Grid** — Deductive reasoning from clues.
11. **River Crossing** — Move characters across respecting constraints.
12. **Tower of Hanoi** — Move disks according to classic puzzle rules.
13. **Sliding Puzzle** — Rearrange numbered tiles into order.
14. **Word Scramble** — Unscramble letters into valid words.
15. **Anagram Challenge** — Find matching anagrams.
16. **Find the Difference** — Compare visual pattern pairs.
17. **Path Finder** — Navigate grids through obstacles to the goal.
18. **Block Placement** — Fit geometric shapes without overlap.
19. **Reaction Test** — Rapid response and speed testing.
20. **Brain Teasers** — Clever riddles and lateral thinking challenges.

### Game Progression:
- **10 Chapters & 1,000 Levels**: From "Beginner Brain" to "Ultimate Brain Battle".
- **Local Persistence**: State, XP, unlocked levels, stars, and streaks saved via Jetpack DataStore.
- **Rewards & Streaks**: Earn XP, unlock hints, and track daily login streaks.
- **AdMob Integration**: Google Mobile Ads SDK integrated with official test ad unit IDs for Banner, Interstitial, and Rewarded ads.
- **Audio & Haptics**: Full tactile feedback with sound and vibration toggles.

---

## 🛠️ Local Build Instructions

### Prerequisites:
- Android Studio Ladybug / Meerkat or newer
- JDK 21
- Android SDK 36 (targetSdk 36, minSdk 24)

### Command Line:
```bash
# Make gradlew executable
chmod +x gradlew

# Run unit tests
./gradlew testDebugUnitTest

# Build debug APK
./gradlew assembleDebug
```
The compiled APK will be located at:
`app/build/outputs/apk/debug/app-debug.apk`
