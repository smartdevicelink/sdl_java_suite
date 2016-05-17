[ ![Download](https://api.bintray.com/packages/alangonzalez/SDL_test/com.smartdevicelink/images/download.svg) ](https://bintray.com/alangonzalez/SDL_test/com.smartdevicelink/_latestVersion)

# Creating a Maven dependency package in Eclipse

There are a couple of tools needed to manage maven dependencies:
* [Bintray's JCenter] (https://bintray.com/) 
* [Eclipse Maven Plugin] (https://marketplace.eclipse.org/content/gradle-sts-integration-eclipse)

We will start with setting up Jcenter:

##1. JCenter account and configuration:

Setup your account:
* Create account on bintray:  bintray.com
* Create an API key so that you can deploy packages  Edit Profile -> API Key

There is an opportunity to create an organization who can own packages, under your profile you can create an organization.  Then this organization can manage the repositories.

###Create repos:
In order to publish to a repository, you'll need to first create the repo in bintray. You can create a public repository or a private repository. For testing a private repo is a good way to see if the configuration is correct.  Then you can switch to publishing to the public repo. 

* Create a Maven repository of a given name: either public or private.  For smartdevicelink, a repository name of 'SmartDeviceLink' works. 
* Go into that repository and create package named 'com.smartdevicelink'.  Naming of packages needs to match the groupID in the POM.xml file.  Refer to the [Maven docs] (https://maven.apache.org/guides/mini/guide-naming-conventions.html) to read more about naming conventions

##2. Setup eclipse
* You'll need to configure Eclipse to work with Maven.  This requires the Maven Eclipse plugin.  Download this plugin and install using the Eclipse marketplace.  [Eclipse Maven Plugin] (https://marketplace.eclipse.org/content/gradle-sts-integration-eclipse)
* Convert the project to a Maven project.  Right click on the sdl_android_lib project and pick Configure -> Convert to Maven


##3. Building Maven targets

You can build the .jar file and sources .jar by:
* Create a settings.xml file in your local home directory/.m2/settings.xml file.
* Add in a servers location similar to this, but edit the attributes to different appropriate values.
```
  <servers>
     <server>
     	<id>SOME_ID_NAME</id>
     	<username>SOME_USERNAME</username>
        <password>SOME_APIKEY</password>
     </server>
  </servers>
```
* Tell Maven to re-read the settings file by clicking Preferences -> Maven -> User Settings -> Update settings
* Now edit pom.xml
* Update the \<version\> tag
* Update the repository location id, name and url

* Go into Run -> Run As -> Maven build...
* Type in a goal of 'package'
* Click run
* The build will create some artifacts in the 'target' subdirectory of sdl-android-lib-<VERSION>.jar and sdl-android-lib-<VERSION>-sources.jar

##4. Publishing to Jcenter
* Now once you have the targets successfully built, we can then deploy to Jcenter
* Make sure You have the login credentials correct in your settings.xml file.
* Then click Run-> Run As -> Maven build...
* Type in a goal of 'deploy' and click Run
* Logs will indicate the package is deploying similar to these:<br>
[INFO] --- maven-deploy-plugin:2.8.1:deploy (default-deploy) @ sdl-android-lib ---<br>
[INFO] Uploading: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1.jar<br>
[INFO] Uploaded: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1.jar (1123 KB at 409.2 KB/sec)<br>
[INFO] Uploading: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1.pom<br>
[INFO] Uploaded: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1.pom (2 KB at 1.8 KB/sec)<br>
[INFO] Downloading: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/maven-metadata.xml<br>
[INFO] Uploading: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/maven-metadata.xml<br>
[INFO] Uploaded: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/maven-metadata.xml (314 B at 0.8 KB/sec)<br>
[INFO] Uploading: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1-sources.jar<br>
[INFO] Uploaded: https://api.bintray.com/maven/XXXXX/com.smartdevicelink/;publish=1/com/smartdevicelink/sdl-android-lib/4.0.1/sdl-android-lib-4.0.1-sources.jar (342 KB at 209.6 KB/sec)<br>
[INFO] ------------------------------------------------------------------------<br>
[INFO] BUILD SUCCESS<br>
[INFO] ------------------------------------------------------------------------<br>

##5 Using the Maven package in a project
In order to use this package in an android project you can do the following:
* In Android Studio:
- Update the build.gradle dependencies with:
dependencies {
	compile 'com.smartdevicelink:sdl-android-lib:4.0.1'
}

If this is a private repository you'll need to specificy the location of the maven repo as well with something like:
maven {
        url 'http://dl.bintray.com/XXXXXX'
}
