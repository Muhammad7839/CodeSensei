**Code Sensei**

https://youtu.be/Di1P4Hz05n4

Code Sensei is an Android app that helps beginners understand their Kotlin code more clearly.  
The user pastes code, taps Analyze, and the app explains what is happening, what might be wrong, and how it can be improved.  
The goal is to make learning less confusing and help students build confidence while writing simple programs.



**Purpose of the Project**

This project focuses on creating a beginner-friendly coding companion.  
It guides new programmers by showing clear messages instead of overwhelming errors, and it organizes everything into a clean, easy layout that is simple to navigate.  
The app includes helpful screens for analyzing code, reviewing past attempts, adjusting the appearance, and seeing progress.



**Main Features**

**Code analysis**

The app reviews the user’s Kotlin code and points out things such as:
- Missing or incomplete function declarations  
- Mismatched symbols like parentheses or braces  
- Mistyped keywords  
- Incorrect main function structure  
- Undefined variables or functions  
- General formatting or structure issues  

Each finding includes a short description and a clear explanation so beginners understand what to fix.



**Error detail view**

Tapping on a specific issue opens a detailed page.  
This view breaks the problem down step by step, helping the user understand the mistake and how to correct it.



**Progress history**

Every analysis attempt is saved and displayed in a History screen.  
This helps users see how their understanding improves over time and lets them review past work whenever they want.



**Appearance settings**

The app includes a simple settings page where users can choose the visual style they prefer.  
The chosen appearance stays the same the next time the app is opened.



**Rewards and levels**

To make learning more encouraging, the app keeps track of points earned from each analysis.  
These points determine the user’s level, which appears in the settings page as a small motivational feature.



**User Interface**

The layout is built with a clean structure and modern components.  
There are four main screens:

- **Home screen** – enter code and start analysis  
- **Results screen** – see explanations for each issue  
- **History screen** – view previous analysis attempts  
- **Settings screen** – adjust appearance and see progress  
- **Error detail screen** – deeper explanation of a single issue  

The home screen uses themed background images with a frosted-glass effect to keep the text readable while still looking polished.



**Splash Screen**

When the app starts, a custom splash screen appears briefly with the Code Sensei logo before transitioning smoothly to the home screen.



**App Flow**

Home → Results → Error Detail → Back  
Home → History → Back  
Home → Settings → Back  
App Icon → Splash Screen → Home



**Project Structure**

com.example.codesensei  
- data  
  - settings (stores simple app preferences and points)  
- local  
  - database files for saving history  
- ui  
  - navigation  
  - theme  
- HomeScreen.kt  
- ResultsScreen.kt  
- ErrorDetailScreen.kt  
- HistoryScreen.kt  
- SettingsScreen.kt  
- AnalyzerViewModel.kt  
- MainActivity.kt  
- SplashActivity.kt  



**How to Run the App**

1. Clone the repository.  
2. Open it in Android Studio.  
3. Allow the project to sync.  
4. Run on a device or emulator (Android 7.0 or above).  



**Screenshots**
Home Screen:
(Light Mode):

<img width="292" height="648" alt="1" src="https://github.com/user-attachments/assets/c40b7924-79d7-4fc8-99cb-20ea058789d4" />

 
(Dark Mode):
 
<img width="292" height="648" alt="2" src="https://github.com/user-attachments/assets/fefdfb6a-53a3-4fec-a515-890e52f47b6f" />

Results Screen:

(Light Mode):

<img width="292" height="648" alt="3" src="https://github.com/user-attachments/assets/bfc64913-a57b-4845-bd51-2a0939515148" />
 
(Dark Mode):

 <img width="292" height="648" alt="4" src="https://github.com/user-attachments/assets/8eceab76-a5b3-4c05-aa09-9d8f8469364f" />

History Screen:

(Light Mode):

 <img width="292" height="648" alt="5" src="https://github.com/user-attachments/assets/4a44054b-0391-4563-9a3e-467adee35300" />

(Dark Mode):

 <img width="292" height="648" alt="6" src="https://github.com/user-attachments/assets/ff635014-b2b0-45fe-a927-67fb40da96d2" />

Settings Screen

(Light Mode):

 <img width="292" height="648" alt="7" src="https://github.com/user-attachments/assets/9381f981-9bd5-4962-bf2f-b9f6f28c95f1" />

(Dark Mode):

 <img width="292" height="648" alt="8" src="https://github.com/user-attachments/assets/f2fa2682-7ed9-4d11-bdc9-02287c408738" />

Splash Screen:

<img width="292" height="648" alt="9" src="https://github.com/user-attachments/assets/4a45f7f1-4c7b-4b31-9d0a-cc2dc6a1d240" />


**Future Enhancements**

- Support for additional languages  
- More advanced analysis rules  
- Optional voice-note debugging mode  
- Exportable history and reports  
- More level badges and rewards  



**Author**

Muhammad Imran  
CSC 371 – Mobile Application Development  
Farmingdale State College
