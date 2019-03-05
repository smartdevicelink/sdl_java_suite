[![Build Status](https://travis-ci.org/smartdevicelink/sdl_android.svg?branch=master)](https://travis-ci.org/smartdevicelink/sdl_android)
[![codecov](https://codecov.io/gh/smartdevicelink/sdl_android/branch/master/graph/badge.svg)](https://codecov.io/gh/smartdevicelink/sdl_android)
[![Slack Status](http://sdlslack.herokuapp.com/badge.svg)](http://slack.smartdevicelink.com)
[ ![Download](https://api.bintray.com/packages/smartdevicelink/sdl_android/sdl_android/images/download.svg) ](https://bintray.com/smartdevicelink/sdl_android/sdl_android/_latestVersion)
# SmartDeviceLink (SDL)

SmartDeviceLink (SDL) is a standard set of protocols and messages that connect applications on a smartphone to a vehicle head unit. This messaging enables a consumer to interact with their application using common in-vehicle interfaces such as a touch screen display, embedded voice recognition, steering wheel controls and various vehicle knobs and buttons. There are three main components that make up the SDL ecosystem.

  * The [Core](https://github.com/smartdevicelink/sdl_core) component is the software which Vehicle Manufacturers (OEMs)  implement in their vehicle head units. Integrating this component into their head unit and HMI based on a set of guidelines and templates enables access to various smartphone applications.
  * The optional [SDL Server](https://github.com/smartdevicelink/sdl_server) can be used by Vehicle OEMs to update application policies and gather usage information for connected applications.
  * The [iOS](https://github.com/smartdevicelink/sdl_ios) and [Android](https://github.com/smartdevicelink/sdl_android) libraries are implemented by app developers into their applications to enable command and control via the connected head unit.

Pull Requests Welcome!

To understand if a contribution should be entered as an Android Pull Request (or issue), or an SDL Evolution Proposal, please reference [this document](https://github.com/smartdevicelink/sdl_evolution/blob/master/proposals_versus_issues.md).

<a href="http://www.youtube.com/watch?feature=player_embedded&v=AzdQdSCS24M" target="_blank"><img src="http://i.imgur.com/nm8UujD.png?1" alt="SmartDeviceLink" border="10" /></a>

## Mobile Proxy

The mobile library component of SDL is meant to run on the end user’s smart-device from within SDL enabled apps. The library allows the apps to connect to SDL enabled head-units and hardware through bluetooth, USB, and TCP. Once the library establishes a connection between the smart device and head-unit through the preferred method of transport, the two components are able to communicate using the SDL defined protocol. The app integrating this library project is then able to expose its functionality to the head-unit through text, media, and other interactive elements.

## SmartDeviceLink Android

We're still working on creating documentation for each of these individual repositories, but in the meantime, you can find more information about SmartDeviceLink [here](https://smartdevicelink.com)

You can also find some branches that have yet to be merged into this GitHub project on the GENIVI page [here](http://git.projects.genivi.org/?p=smartdevicelink_android.git;a=summary).

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