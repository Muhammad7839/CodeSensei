**Code Sensei**

Code Sensei is an Android app that helps beginners understand common Kotlin coding mistakes.
The user pastes code, presses Analyze, and the app shows clear explanations of what is wrong and how to fix it.

It is built using **Kotlin**, **Jetpack Compose**, **Room Database**, **DataStore**, and **ViewModel**.



**Purpose of the Project**

The purpose of Code Sensei is to teach basic debugging skills.
It helps new programmers notice common mistakes, understand why errors happen, and learn how to correct them.

This project demonstrates skills from CSC 371, including:
- Kotlin and Android Studio
- Jetpack Compose user interfaces
- Navigation between screens
- Persistent storage with Room
- App preferences with DataStore
- ViewModel and state management



**Main Features**

**Code analysis**

The app checks the user’s Kotlin code for many common mistakes, such as:
- Spelling mistakes in function names or keywords
- Missing `fun main()`
- Unmatched parentheses or braces
- Using `print` or `println` without parentheses
- Using variables or functions that are not declared
- Assigning values without `val` or `var`
- Incorrect function declarations (for example `fun main {`)
- Suspicious characters such as `///`
- Some simple MaterialTheme usage problems

For each issue, the app shows:
- A short title
- A clear explanation
- A suggestion for how to fix it

The analyzer is simple and safe. It does not crash even if the user types nonsense.



**History (Room database)**

- Every time the user runs an analysis, a record is saved in the database.
- The History screen shows a list of past analysis sessions.
- Room and Kotlin Flow are used so the list updates automatically.



**Settings (DataStore)**

- The user can switch between light mode and dark mode.
- The theme choice is saved using DataStore.
- When the app is opened again, it remembers the previous theme.
- DataStore is used instead of SharedPreferences because it is the modern, recommended option.



**User Interface (Jetpack Compose)**

The app uses Jetpack Compose with Material 3.
It has four main screens:

- **Home screen** – paste code, run analysis, open history, open settings
- **Results screen** – shows detected issues, explanations, and fixes
- **History screen** – shows saved analysis sessions from Room
- **Settings screen** – theme switch stored using DataStore



**App Flow**

Home → Results → back to Home  
Home → History → back to Home  
Home → Settings → back to Home



**Technology Used**

- Kotlin
- Jetpack Compose (Material 3)
- ViewModel
- Navigation-Compose
- Room Database
- DataStore Preferences
- Coroutines



**Project Structure**

com.example.codesensei  
- data  
  - settings  (DataStore files)  
- local  
  - AnalysisSessionEntity  
  - AnalysisSessionDao  
  - CodeSenseiDatabase  
- ui  
  - navigation  
  - theme  
- HomeScreen.kt  
- ResultsScreen.kt  
- HistoryScreen.kt  
- SettingsScreen.kt  
- AnalyzerViewModel.kt  
- MainActivity.kt  



**How to Run the App**

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle sync.
4. Run the app on an emulator or device (API level 24 or higher).



**Screenshots (coming soon)**

- Home screen
- Results screen
- History screen
- Settings screen
- Dark mode example
- Example of error detection



**Future Improvements**

- Support more programming languages
- Add more advanced analysis rules
- Offer automatic fixes for simple mistakes
- Allow the user to export the analysis history



**Author**

Muhammad Imran  
CSC 371 – Mobile Application Development  
Farmingdale State College
