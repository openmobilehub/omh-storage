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

package screens;

import general.BaseScreen;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class StorageSampleLoginScreen extends BaseScreen {
    public StorageSampleLoginScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(topActionBar);
    }

    /*
    UI ELEMENTS
     */

    // logged out elements
    @AndroidFindBy(id="com.omh.android.storage.sample:id/toolbar")
    private WebElement topActionBar;

    @AndroidFindBy(className="android.widget.TextView")
    private WebElement topActionBarTxt;

    @AndroidFindBy(id="com.omh.android.storage.sample:id/btn_login")
    private WebElement loginBtn;

    // Messages UI

    @AndroidFindBy(id="com.omh.android.storage.sample:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(id="android:id/message")
    private WebElement alertMsg;

    // account picker popup
    @AndroidFindBy(id="com.google.android.gms:id/container")
    private WebElement pickAccount;

    /*
    METHODS
     */

    public boolean verifySignInBtnDisplayed() {
        getTextFromMobElement(loginBtn);
        return waitForMobElementToBeVisible(loginBtn);
    }

    private boolean tapOnLoginBtn() {
        boolean flag;
        flag = tapMobElement(loginBtn) && implicityWaitTimeOnScreenManual(1);
        return flag;
    }

    private boolean printAlertMsgs() {
        boolean flag = false;
        try {
            String text = getTextFromMobElement(alertTitle) + "\n" + getTextFromMobElement(alertMsg);
            System.out.println(" \n==================\n" + text + " \n==================\n");
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}
        return flag;
    }

    public boolean checkAlerMsgPrint() {
        return printAlertMsgs();
    }

    public boolean tapOutsideModal() {
        return tapOnLoginBtn() && implicityWaitTimeOnScreenManual(1) && tapOnScreenXY(880, 2265)
                && printAlertMsgs() && tapOnScreenXY(880, 2265);
    }

    public boolean verifySignInPopUpShown() {
        boolean flag = false;
        if(tapOnLoginBtn()) {
            flag = waitForMobElementToBeVisible(pickAccount) && tapMobElement(pickAccount) && implicityWaitTimeOnScreenManual(3);
        }
        return flag;
    }


    /*
   RETURN-REDIRECT PAGE CALLS
    */
    public WebViewBrowserScreen signInFromBrowser() {
        if(tapOnLoginBtn()) {
            return new WebViewBrowserScreen(this.driver);
        } else {return null;}
    }

    public StorageSampleLoggedScreen signedUser() {
        implicityWaitTimeOnScreenManual(3);
        return new StorageSampleLoggedScreen(this.driver);
    }


    /*
    AFTER SIGNED IN VALIDATIONS
     */


}
