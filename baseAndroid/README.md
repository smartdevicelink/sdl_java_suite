## SmartDeviceLink BaseAndroid

The Base Android folder symbolically links files used by the Android project that are in the Base folder. 

This folder does not need to be imported. Please refer to the installation instructions in the Android, JavaSE, or JavaEE README's.

### Windows

The original links were created for a unix based operating system. The Windows versions of those same symbolic links follow the same path structure as this root folder, but under the `Windows` folder. The `build.gradle` file contains the necessary conditional that will pick the correct set of links to use.

If you are not building the project with the supplied gradle files, you will need to point to the correct path based on the operating system in which you are building the project.