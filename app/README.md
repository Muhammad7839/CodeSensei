Code Sensei - A Beginner’s Debugging Companion

Topic Overview:
Many beginners quit coding because error messages are confusing. Code Sensei is an Android app that helps students understand their mistakes by explaining them in plain English. The app lets users paste code, find simple errors, and see how to fix them.

Repository Link:
(Paste your GitHub repository link here)

Screens:
- Home Screen: Paste code and click Analyze.
- Results Screen: Shows issues with explanations and fixes.
- Planned Features: Checklist view, Rubber Duck voice notes, and simple rewards.

Technologies Used:
Kotlin, Android Studio, Jetpack Compose, Navigation-Compose, ViewModel.
Planned additions include Room Database and MediaRecorder for audio.

Progress So Far:
- Home and Results screens are complete.
- Navigation and data sharing work correctly.
- The app detects basic beginner mistakes such as “pritn” instead of “print,” missing main() function, and unmatched parentheses.
- Code is simple, well-commented, and beginner friendly.

Four Week Plan (until December 6):
Week 1: Expand error analysis rules.
Week 2: Add Rubber Duck voice feature.
Week 3: Add Room Database for saving history and rewards.
Week 4: Final testing and prepare presentation.

How to Run:
1. Open the project in Android Studio.
2. Run it on an emulator or physical Android device.
3. On the Home screen, paste this code:
   fun main() { pritn("Hello") }
4. Tap the Analyze button.
5. View the explanation on the Results screen.