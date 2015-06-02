##### Note: Please use the [develop](https://github.com/smartdevicelink/sdl_android/tree/develop) branch of sdl_android for the most stable versions.

#SmartDeviceLink

SmartDeviceLink (SDL) is a standard set of protocols and messages that connect applications on a smartphone to a vehicle which enable the consumer to safely interact with their applications while driving.  There are three main components that make up the SDL ecosystem. Vehicle Manufacturers (OEMs) implement their vehicle HMI with the  [Core](https://github.com/smartdevicelink/sdl_core) component based on a set of guidelines and templates. OEMs use the [SDL Server](https://github.com/smartdevicelink/sdl_server) to update application policies and gather usage information for connected applications. App developers implement the [iOS](https://github.com/smartdevicelink/sdl_ios) and [Android](https://github.com/smartdevicelink/sdl_android) libraries into their applications to enable command and control via the connected head unit.

##Mobile Proxy

The mobile library component of SDL is meant to run on the end user’s smart-device from within SDL enabled apps. The library allows the apps to connect to SDL enabled head-units and hardware through bluetooth, USB, and TCP. Once the library establishes a connection between the smart device and head-unit through the preferred method of transport, the two components are able to communicate using the SDL defined protocol. The app integrating this library project is then able to expose its functionality to the head-unit through text, media, and other interactive elements.

##SmartDeviceLink Android

We're still working on creating documentation for each of these individual repositories, but in the meantime, you can find more information about SmartDeviceLink [here](https://github.com/smartdevicelink/sdl_core/blob/master/README.md) and [here](http://projects.genivi.org/smartdevicelink/about).

You can also find some branches that have yet to be merged into this GitHub project on the GENIVI page [here](http://git.projects.genivi.org/?p=smartdevicelink_android.git;a=summary).
