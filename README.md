Code Sensei - A Beginner’s Debugging Companion

Topic Overview:
Code Sensei is an Android app that helps beginners understand mistakes by explaining them in plain English. Paste code, tap Analyze, and see issues with simple fixes.

Repository Link:
https://github.com/Muhammad7839/CodeSensei

Screens:
- Home: paste code and tap Analyze
- Results: issues with explanations and fixes
- Planned: Checklist view, Rubber Duck voice notes, simple rewards

Technologies:
Kotlin, Android Studio, Jetpack Compose, Navigation-Compose, ViewModel
Planned: Room (persistence), MediaRecorder (audio)

Progress:
- Home and Results screens working
- Navigation + shared ViewModel
- Simple rules: "pritn" typo, missing main(), unmatched parentheses
- Beginner-friendly comments

Four-Week Plan (to Dec 6):
1) Expand analysis rules
2) Add Rubber Duck voice feature
3) Add Room for history/rewards
4) Testing and presentation polish

How to Run:
Open in Android Studio → Run on emulator (API 34+).
On Home, paste:
  fun main() { pritn("Hello") }
Tap Analyze → view results.
