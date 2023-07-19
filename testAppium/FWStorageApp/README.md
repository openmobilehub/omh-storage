# ANDROID AUTOMATION WITH APPIUM

## Getting started

This guide will show how to use the automation scripts written in Java using Appium.
The basics configuration needed as well as common tools that need to be modified in
order to do a proper setup.

### ====================================================

#### SETUP REQUIREMENTS
Appium server installation v1:

FOR MACOS

_**NOTE**: Homebrew must be set previously. If is not, follow this link for instructions:
[Homebrew Setup](https://docs.brew.sh/Installation)_
* brew install node
* npm install -g appium
* npm install wd
* appium &

The needed dependencies are located in the ```pom.xml``` file.
To take in consideration:
1. TestNG v7.5 still works with Java v8, newer versions require Java v11 or above.
2. Selenium v4.8.1 still works with Java v8. Newer version are backward compatible.
3. For more info check this link [Java Client migration from 7 to 8](https://github.com/appium/java-client/blob/master/docs/v7-to-v8-migration-guide.md).
4. This works with IntelliJ IDE as default. If another is used like Eclipse or VSCode, an effort research is required.

#### HOW TO EXECUTE THE CODE?

* Launch IntelliJ IDE
* Open the project
* Look for  ```pom.xml``` file, right click then click "Reload project"
* Once is finished, look in "test>java>configMaps.xml" file
* Modify the section ```<!--  Mobile Main Config Parameters  -->``` with your devices
     ```
     <parameter name="platformVersion" value="ANDROID_VERSION"/>  
     <parameter name="deviceName" value="DEVICE_ID"/>
     ```
* Save the file
* Right click then click "Run '.../src/test/'configMaps.xml'"


#### ADDITIONAL NOTES

Keep in mind that this is for macOS supported configuration. If you are on windows,
the easiest way is to execute Appium GUI app.


#### HELPFUL LINKS
* http://appium.io/docs/en/2.0/
* https://github.com/appium
* https://www.browserstack.com/guide/download-and-install-appium
* https://testng.org/doc/documentation-main.html
* https://www.selenium.dev/