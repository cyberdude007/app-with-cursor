# PaisaSplit Android App

A comprehensive Android application that combines personal finance tracking with group expense splitting. Built with modern Android development practices and smooth animations.

## ✨ Features

- **Personal Finance**: Track multiple accounts (Cash, Bank, Wallet, Card)
- **Group Expenses**: Split bills with friends and family
- **Accurate Accounting**: Real-time balance updates with ledger entries
- **Offline First**: Works completely offline with local SQLite database
- **Secure**: Database encryption with SQLCipher
- **Smooth Animations**: Polished UI with spring animations and transitions
- **Material Design 3**: Modern, accessible interface

## 🎨 Enhanced UI Features

- **Staggered Animations**: Cards appear with smooth, staggered timing
- **Spring Animations**: Natural, bouncy interactions using spring physics
- **Press Feedback**: Visual feedback on button presses and interactions
- **Smooth Transitions**: Fade, slide, and scale animations throughout
- **Responsive Design**: Adapts beautifully to different screen sizes
- **Accessibility**: Screen reader support and high contrast themes

## 🏗️ Tech Stack

- **Language**: Kotlin 100%
- **UI**: Jetpack Compose (Material Design 3)
- **Architecture**: MVVM + Repository pattern + Clean Architecture
- **Database**: Room + SQLite with SQLCipher encryption
- **DI**: Hilt
- **Async**: Coroutines + Flow
- **Navigation**: Jetpack Navigation Compose
- **Animations**: Compose Animation APIs with spring physics

## 📋 Requirements

- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android API 24+ (Android 7.0)

## 🚀 Setup

### **Local Development**

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/PaisaSplit.git
cd PaisaSplit
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the `PaisaSplit` folder
   - Wait for Gradle sync to complete

3. **Run the app**
   - Connect an Android device or start an emulator (API 24+)
   - Click the "Run" button (green play icon)
   - Select your device/emulator

### **GitHub Actions Setup**

The repository includes GitHub Actions that automatically build and release debug APKs:

1. **Actions are automatically enabled** when you push the code
2. **Create a release** by:
   - **Option A**: Push a tag (e.g., `git tag v1.0.0 && git push origin v1.0.0`)
   - **Option B**: Use GitHub Actions manually (Actions tab → Run workflow)

3. **APKs are automatically uploaded** to the Releases section

## 📁 Project Structure

```
PaisaSplit/
├── .github/
│   └── workflows/
│       └── build-and-release.yml    # GitHub Actions for auto-builds
├── app/
│   ├── src/main/java/com/paisasplit/app/
│   │   ├── data/
│   │   │   ├── database/          # Room entities, DAOs, database
│   │   │   ├── repository/        # Data repository
│   │   │   └── seeding/          # Initial data seeding
│   │   ├── domain/
│   │   │   ├── ledger/           # Ledger calculation engine
│   │   │   └── split/            # Split calculation logic
│   │   ├── di/                   # Hilt dependency injection
│   │   ├── navigation/           # Navigation setup
│   │   ├── presentation/
│   │   │   ├── ui/
│   │   │   │   ├── screens/      # App screens with animations
│   │   │   │   ├── components/   # Reusable UI components
│   │   │   │   └── theme/        # App theme and typography
│   │   │   └── viewmodel/        # ViewModels for screens
│   │   └── PaisaSplitApp.kt    # Application class
│   ├── build.gradle.kts          # App module configuration
│   └── proguard-rules.pro        # ProGuard rules
├── gradle/
│   └── wrapper/                  # Gradle wrapper files
├── gradlew                       # Unix/Linux Gradle wrapper
├── gradlew.bat                   # Windows Gradle wrapper
├── build.gradle.kts              # Project configuration
├── settings.gradle.kts           # Project settings
└── README.md                     # This file
```

## 🔑 Key Components

### **Database**
- **Entities**: Account, Group, Member, Transaction, Split, LedgerEntry
- **Encryption**: SQLCipher for database security
- **Type Converters**: BigDecimal, enums, lists

### **Split Engine**
- **Equal Split**: Automatic equal distribution with remainder handling
- **Custom Amounts**: Manual amount assignment
- **Ledger Entries**: Double-entry bookkeeping for accuracy

### **UI Components**
- **Material 3**: Modern Material Design implementation
- **Responsive**: Adapts to different screen sizes
- **Accessible**: Screen reader support and high contrast themes
- **Animations**: Spring-based animations for natural feel

## 📱 Usage

1. **First Run**: App automatically creates sample data (Cash account, Friends group)
2. **Home Screen**: View total balance and recent transactions with smooth animations
3. **Add Split**: Create group expense splits with animated UI
4. **Groups**: Manage expense groups and members
5. **Analytics**: View spending patterns (coming soon)

## 🔄 GitHub Actions Workflow

The `.github/workflows/build-and-release.yml` file automatically:

- **Triggers on**: Tag pushes (`v*`) or manual workflow dispatch
- **Builds**: Debug APK only (as requested)
- **Uploads**: APK to GitHub Releases
- **Uses**: JDK 17, Android SDK, Gradle wrapper

### **Manual Release**
1. Go to Actions tab
2. Click "Build and Release Debug APK"
3. Click "Run workflow"
4. Enter version (e.g., `v1.0.0`)
5. Wait for build completion

### **Automatic Release**
```bash
git tag v1.0.0
git push origin v1.0.0
# GitHub Actions automatically builds and releases
```

## 🎯 Development Notes

- **Database Encryption**: Currently uses static passphrase; replace with Keystore-backed secret before production
- **Error Handling**: Basic error handling implemented; enhance for production use
- **Testing**: Unit tests for core logic; add UI tests for critical flows
- **Animations**: All animations use spring physics for natural feel
- **Performance**: Optimized for 60fps with proper animation specs

## 🚀 Building for Release

1. **Update signing config** in `app/build.gradle.kts`
2. **Replace database passphrase** with secure implementation
3. **Test thoroughly** on multiple devices
4. **Use GitHub Actions** for automated builds
5. **Build APK**: `./gradlew assembleDebug` (local) or GitHub Actions

## 🔧 Troubleshooting

### **Build Fails in GitHub Actions**
- Check Actions logs for specific errors
- Verify Gradle wrapper files exist
- Ensure JDK 17 is properly configured

### **APK Not Uploading**
- Check repository permissions
- Verify `GITHUB_TOKEN` secret exists
- Check workflow file syntax

### **App Crashes on Device**
- Check logcat for crash details
- Verify minimum SDK version (API 24+)
- Test on different Android versions

### **Animations Feel Laggy**
- Check device performance
- Verify animation specs in components
- Use Android Studio profiler

## 📝 Contributing

1. Follow Kotlin coding standards
2. Use meaningful commit messages
3. Test changes thoroughly
4. Update documentation as needed
5. Maintain smooth animations and performance

## 📄 License

[Add your license here]

---

Built with ❤️ using modern Android development practices and smooth animations
