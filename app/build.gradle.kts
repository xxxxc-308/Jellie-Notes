name: Android APK CI

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - name: Checkout code
      uses: actions/checkout@v4

    - name: Set up JDK 17
      uses: actions/setup-java@v4
      with:
        java-version: '17' # 从您的 libs.versions.toml 确认 javaVersion = "17"
        distribution: 'temurin'
        cache: gradle

    - name: Cache Gradle packages
      uses: actions/cache@v4
      with:
        path: ~/.gradle/caches
        key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
        restore-keys: |
          ${{ runner.os }}-gradle-

    - name: Set up Android SDK
      uses: android-actions/setup-android@v3
      with:
        cmdline-tools-version: 'latest'
        # 根据您的 libs.versions.toml: compileSdk = "36"
        # buildToolsVersion 未明确定义，兼容版本使用 36.0.0
        packages: |
          - platforms;android-36
          - build-tools;36.0.0
          - platform-tools
          # 您的项目未显示使用 NDK，此行保持注释
          # - ndk;23.1.7779620
        accept-android-sdk-licenses: true

    - name: Grant execute permission for gradlew
      run: chmod +x gradlew

    - name: Build debug APK
      run: ./gradlew assembleDebug

    - name: Upload debug APK
      uses: actions/upload-artifact@v4
      with:
        name: app-debug.apk
        # **您最终的确认点：请确保此路径与您的项目实际 APK 输出路径完全一致**
        path: app/build/outputs/apk/debug/app-debug.apk
