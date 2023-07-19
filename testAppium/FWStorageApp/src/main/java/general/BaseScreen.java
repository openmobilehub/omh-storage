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

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;


public abstract class BaseScreen {

    protected AndroidDriver driver;
    private final AppiumFluentWait<AndroidDriver> wait;
    private final Actions touchAction;
    private final int staticTimeOut;

    // CONSTRUCTOR - Receiving web driver as a parameter to save it on a global variable to be used later
    public BaseScreen(AndroidDriver driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        int dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new AppiumFluentWait<>(driver);
        this.wait.withTimeout(Duration.ofSeconds(dynamicTimeOut));
        touchAction = new Actions(this.driver);
    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    // method to wait for the visibility of an element
    protected boolean waitForMobElementToBeVisible(WebElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element != null && element.isDisplayed());
        return flag;
    }

    protected boolean waitForMobElementToBeTappable(WebElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element.isEnabled());
        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapMobElement(WebElement element) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) && waitForMobElementToBeTappable(element) &&
                this.wait.until(arg0 -> {
                    touchAction.click(element).perform();
                    return true;
                    });
        return flag;
    }

    // method to tap on element by coordinates
    protected boolean tapOnScreenXY(int getX, int getY) {
        boolean flag = false;
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX, getY));
            tap.addAction(finger.createPointerDown(0));
            tap.addAction(finger.createPointerUp(0));
            driver.perform(Collections.singletonList(tap));
            implicityWaitTimeOnScreen();
            flag = true;
        }catch (Exception e){ErrorsManager.errNExpManager(e);}
        return flag;
    }

    // method to enter text on a specific field
    protected boolean sendTextOnEmptyMobElement(WebElement element, String txt) {

        boolean validationReturn = false;

        if (waitForMobElementToBeTappable(element)) {
            touchAction.click(element).perform();
            element.clear();
            validationReturn = typeTxtOnMobElement(element, txt);
        }
        return validationReturn;
    }

    private boolean typeTxtOnMobElement(WebElement element, String txt) {
        element.sendKeys(txt);
        return element.isEnabled();
    }


    // method to verify text on a certain element
    protected boolean verifyTextOnMobElement(WebElement element, String text) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) &&
                this.wait.until(arg0 -> {
                    element.getText().equals(text);
                    //System.out.println(text);
                    return true;
                });
        return flag;
    }


    protected String getTextFromMobElement(WebElement element) {
        String flag = "";
        if (waitForMobElementToBeVisible(element))
        {
            flag = element.getText();
        }
        return flag;
    }

    // halt wait execution via xml config file
    protected boolean implicityWaitTimeOnScreen() {
        try {
            TimeUnit.SECONDS.sleep(this.staticTimeOut);
            return true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
            return false;
        }
    }

    // halt wait execution set manually by the user
    protected boolean implicityWaitTimeOnScreenManual(int secs) {
        try {
            TimeUnit.SECONDS.sleep(secs);
            return true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
            return false;
        }
    }

    // generic method to press any android key
    protected boolean pressAndroidKey(AndroidKey keyValue) {
        boolean flag = false;
        if(keyValue != null) {
            driver.pressKey(new KeyEvent(keyValue));
            flag = true;
        } else {
            System.out.println(TestUtilities.basicErrorMsg("There is a problem with the Key pressed"));
        }
        return flag;
    }

    /*****   ANDROID GESTURES  *****/

    protected boolean horizontalSwipeOnScreenXY(WebElement element) {
        boolean flag = false;


        int centerY = element.getRect().y + (element.getSize().height/2);
        double stStartXcc = element.getRect().x + (element.getSize().width * 0.9);
        double stEndXcc = element.getRect().x + (element.getSize().width * 0.1);


        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) stStartXcc, centerY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), (int) stEndXcc, centerY));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}
        return flag;
    }

    protected boolean verticalSwipeOnScreenXY(WebElement element) {
        boolean flag = false;

        int centerX = element.getRect().x + (element.getSize().width/2);
        double stStartYcc = element.getRect().y + (element.getSize().height * 0.8);
        double stEndYcc = element.getRect().y + (element.getSize().height * 0.1);

        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) stStartYcc));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, (int) stEndYcc));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}
        return flag;
    }

    protected boolean customUsersSwipeXLoc(WebElement element, int getStartX, int getEndX) {
        boolean flag = false;

        int centerY = element.getRect().y + (element.getSize().height/2);
        double stStartXcc = getStartX;
        double stEndXcc = getEndX;

        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) stStartXcc, centerY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), (int) stEndXcc, centerY));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}

        return flag;
    }

    protected boolean customUsersSwipeYLoc(WebElement element, int getStartY, int getEndY) {
        boolean flag = false;

        int centerX = element.getRect().x + (element.getSize().width/2);
        double stStartYcc = getStartY;
        double stEndYcc = getEndY;

        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) stStartYcc));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, (int) stEndYcc));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}

        return flag;
    }


    // method to double tap by coordinates
    protected boolean doubleTapWithOneFingerOnScreenXY(int getX, int getY) {
        boolean flag = false;
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX, getY));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(tap));
            implicityWaitTimeOnScreenManual(1);
            System.out.println("ZOOM IN => Double Tap with one finger");
            flag = true;
        }catch (Exception e){ErrorsManager.errNExpManager(e);}
        return flag;
    }

    // method to single tap with two fingers by coordinates
    protected boolean singleTapWithTwoFingersOnScreenXY(int getX, int getY) {
        boolean flag = false;
        try {
            PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
            PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

            Sequence finger1Sequence = new Sequence(finger1, 1);
            finger1Sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX - 50, getY - 50));
            finger1Sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            finger1Sequence.addAction(new Pause(finger1, Duration.ofMillis(100))); // Adding a pause between tap down and tap up
            finger1Sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            Sequence finger2Sequence = new Sequence(finger2, 1);
            finger2Sequence.addAction(finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX + 50, getY + 50));
            finger2Sequence.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            finger2Sequence.addAction(new Pause(finger2, Duration.ofMillis(100))); // Adding a pause between tap down and tap up
            finger2Sequence.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Arrays.asList(finger1Sequence, finger2Sequence));
            implicityWaitTimeOnScreenManual(1);
            System.out.println("ZOOM OUT => Single Tap with two fingers");
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }


    protected boolean doubleTapWithOneFingerHoldAndSwipeUpOnScreenXY(int getX, int getY) {
        boolean flag = false;
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tapAndSwipe = new Sequence(finger, 1);
            tapAndSwipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX, getY));
            tapAndSwipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tapAndSwipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tapAndSwipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

            // Holding the second tap
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(200))); // Adding a pause for holding the second tap

            // Swiping up
            tapAndSwipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), getX, getY - 600));
            tapAndSwipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(tapAndSwipe));
            implicityWaitTimeOnScreenManual(1);
            System.out.println("ZOOM OUT => Double Tap with one finger and swipe up");
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }


    protected boolean doubleTapWithOneFingerHoldAndSwipeDownOnScreenXY(int getX, int getY) {
        boolean flag = false;
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tapAndSwipe = new Sequence(finger, 1);
            tapAndSwipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getX, getY));
            tapAndSwipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tapAndSwipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(50))); // Adding a short delay between taps
            tapAndSwipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

            // Holding the second tap
            tapAndSwipe.addAction(new Pause(finger, Duration.ofMillis(200))); // Adding a pause for holding the second tap

            // Swiping down
            tapAndSwipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), getX, getY + 500));
            tapAndSwipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(tapAndSwipe));
            implicityWaitTimeOnScreenManual(1);
            System.out.println("ZOOM IN => Double Tap with one finger and swipe down");
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }


    /*
    UNDER CONSTRUCTION
    */


}