## SmartDeviceLink Android

We're still working on creating documentation for each of these individual repositories, but in the meantime, you can find more information about SmartDeviceLink [here](https://smartdevicelink.com)

### Installation

#### Dependency Managers

To compile with the latest release of SDL Android, include the following in your app's `build.gradle` file,

```
repositories {
    jcenter()
}
dependencies {
    implementation 'com.smartdevicelink:sdl_android:4.+'
}
```

For Maven or Ivy snippets please look at [Bintray](https://bintray.com/smartdevicelink/sdl_android/sdl_android)

#### Manually

If you prefer not to use any of the aforementioned dependency managers, you can integrate SDL Android into your project manually.

### Proguard Rules

Developers using Proguard to shrink and obfuscate their code should be sure to include the following lines in their proguard-rules.pro file:

```
-keep class com.smartdevicelink.** { *; }
-keep class com.livio.** { *; }
# Video streaming apps must add the following line
-keep class ** extends com.smartdevicelink.streaming.video.SdlRemoteDisplay { *; }
```