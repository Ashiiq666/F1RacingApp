# F1 Racing App

A modern Android application built with Jetpack Compose for Formula 1 racing enthusiasts. The app provides real-time race information, driver statistics, circuit details, and countdown timers for upcoming sessions.

## 📱 Screenshots

<!-- Add your app screenshots here -->

*Note: Please add screenshots of your app to the `screenshots/` directory and update this path.*

## ✨ Features

- **Home Screen**: 
  - Interactive slider showcasing top driver information and banners
  - Race session cards with countdown timers
  - Quick access to Formula 1 Education and social media links
  
- **Race Details Screen**:
  - Upcoming race information with circuit details
  - Live countdown timer for next session (FP1, FP2, Qualifying, Race)
  - Circuit facts and descriptions
  - Beautiful gradient background with race track visualization

- **Navigation**:
  - Smooth slide transitions between screens
  - Bottom navigation bar for easy access to main sections
  - Back navigation with animated transitions

- **Splash Screen**:
  - Logo display on app launch
  - Smooth transition to home screen

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Compose
- **Networking**: Ktor Client
- **Serialization**: Kotlinx Serialization
- **Image Loading**: Coil
- **Dependency Injection**: Manual (Repository pattern)
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 11 or higher
- **Android SDK**: API 24 or higher
- **Gradle**: 8.12.3 or compatible version

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone <https://github.com/Ashiiq666/F1RacingApp.git>
cd F1RacingApp
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select **File** → **Open**
3. Navigate to the project directory and select it
4. Wait for Gradle sync to complete

### 3. Configure Local Properties (if needed)

The `local.properties` file should be automatically generated. If not, create it in the root directory:




The APK will be located at: `app/build/outputs/apk/release/app-release.apk`

## 📁 Project Structure

```
F1RacingApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/arkade/f1racing/
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/          # Data models (Race, Driver, Session)
│   │   │   │   │   ├── network/        # API service and networking
│   │   │   │   │   └── repository/    # Data repository
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── home/           # Home screen and components
│   │   │   │   │   ├── details/        # Race details screen
│   │   │   │   │   ├── splash/         # Splash screen
│   │   │   │   │   └── navigations/    # Navigation setup
│   │   │   │   ├── ui/
│   │   │   │   │   └── theme/          # App theme, colors, fonts
│   │   │   │   └── utils/              # Utility functions
│   │   │   ├── res/
│   │   │   │   ├── drawable/          # Vector drawables and images
│   │   │   │   ├── values/            # Strings, colors, themes
│   │   │   │   └── font/              # Custom fonts
│   │   │   └── AndroidManifest.xml
│   │   └── test/                      # Unit tests
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml            # Dependency version catalog
├── build.gradle.kts
└── README.md
```

## 🎨 Key Features Implementation

### Navigation
- Slide animations for smooth page transitions
- Bottom navigation bar with 5 main sections
- Deep linking support for race details

### UI/UX
- Dark theme with custom color scheme
- Custom fonts (Montserrat, Space Grotesk)
- Responsive layouts with proper spacing
- Edge-to-edge design with transparent system bars

### Data Management
- MVVM architecture pattern
- StateFlow for reactive UI updates
- Repository pattern for data abstraction
- Error handling with user-friendly messages

## 🔧 Configuration

### API Configuration
The app uses a mock API service. Update the base URL in:
- `app/src/main/java/com/arkade/f1racing/data/network/ApiService.kt`

### String Resources
All user-facing text is externalized in:
- `app/src/main/res/values/strings.xml`

## 📝 Notes

- The app requires Android 7.0 (API 24) or higher
- Internet permission is required for API calls
- The app uses edge-to-edge design with transparent system bars
- All strings are localized and can be easily translated


## 👤 Author

**Arkade**

## 🙏 Acknowledgments

- Formula 1 for the inspiration
- Jetpack Compose team for the amazing UI framework
- All open-source contributors whose libraries made this project possible

