/*
 * Copyright 2023 Open Mobile Hub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package general;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;


public class MobileDriverManager extends TestUtilities {

    private static final ThreadLocal<AndroidDriver> mobAndroidDriver = new ThreadLocal<>();

    private static int staticTime;
    private static int dynamicTime;

    public static int getStaticTime() {
        return staticTime;
    }
    public static int getDynamicTime() {
        return dynamicTime;
    }

    @Parameters({"tmStatic", "tmDynamic"})
    @BeforeMethod
    public void setMobDriverTimes(int tmStatic, int tmDynamic) {
        MobileDriverManager.staticTime = tmStatic;
        MobileDriverManager.dynamicTime = tmDynamic;
    }


    @Parameters({"deviceType","platformName", "platformVersion", "deviceName", "automationName", "appPackage","appActivity", "noReset", "appiumServer"})
    @BeforeMethod(alwaysRun = true)
    public final void setMobDriver(String deviceType, String platformName, String platformVersion, String deviceName,
                                   String automationName, String appPackage, String appActivity,
                                   boolean noReset, String appiumServer) {
        List<String> platformVersionList = Arrays.asList(platformVersion.split(","));
        List<String> deviceNameList = Arrays.asList(deviceName.split(","));

        try {
            System.out.println("[DRIVER MSG]  ----> The mobile test driver is being initialized now");

            UiAutomator2Options capability = new UiAutomator2Options();

            // Android Device capabilities
            capability.setPlatformName(platformName);

            if (deviceType.equals("GMS")) {
                capability.setPlatformVersion(platformVersionList.get(0)); //
                capability.setDeviceName(deviceNameList.get(0)); //
                capability.setAutomationName(automationName);

            } else {
                capability.setPlatformVersion(platformVersionList.get(1));
                capability.setDeviceName(deviceNameList.get(1));
            }
            capability.setAppPackage(appPackage);
            capability.setAppActivity(appActivity);
            capability.setNoReset(noReset);
            capability.setNewCommandTimeout(Duration.ofSeconds(0));

            mobAndroidDriver.set(new AndroidDriver(new URL(appiumServer), capability));

        } catch (Exception e) {ErrorsManager.errNExpManager(e);}

    }

    // driver initiator which gets ready in the @BeforeMethod and does not require to be passed
    // as parameter in the ScreenTests classes
    public AndroidDriver getDriver() {
        return mobAndroidDriver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteDriver() throws NullPointerException{
        try {
            System.out.println("[DRIVER MSG]  ----> The browser driver is being close now");
            getDriver().quit();
        } catch (NullPointerException e) {ErrorsManager.errNExpManager(e);}
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getInstance().getClass().getSimpleName();
        if(iTestResult.getStatus() == 2) {
            System.out.println("[FAILED TEST NAME:]  ----->  " + testName);
            takeDeviceSnapshot(mobAndroidDriver, testName);
        }
    }
}