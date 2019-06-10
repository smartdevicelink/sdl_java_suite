## SmartDeviceLink BaseAndroid

The Base Android folder symbolically links files used by the Android project that are in the Base folder. 

This folder does not need to be imported. Please refer to the installation instructions in the Android, JavaSE, or JavaEE README's.

### Windows

The original symbolic links were created for a Unix based operating system. If compiling with Windows the symbolic links will have to be created before compiling the SDL Android project. There is a gradle task added to the `build.gradle` file in the `sdl_android` project that contains a task called `buildWindowSymLinks`. Please note, this task and the supplied script require admin privileges and python to be installed to run. After running this task, Android Studio will recognize the new folder path as the `build.gradle` file contains the necessary conditional that will pick the correct source set to use.

If you are not building the project with the supplied gradle files, you will need to point to the correct path based on the operating system in which you are building the project.