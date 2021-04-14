## SmartDeviceLink JavaSE

The JavaSE project is meant to allow SDL compatibility for embedded applications. 

#### Dependency Managers

To compile with the latest release of SDL JavaSE, include the following in your app's `build.gradle` file,

```sh
repositories {
    mavenCentral()
}
dependencies {
    implementation 'com.smartdevicelink:sdl_java_se:5.1.0'
}
```

#### Manually building a JAR

If you prefer making a JAR, simply call:

```sh
gradle build
```
from within the project and a JAR should be generated in the `build/libs` folder