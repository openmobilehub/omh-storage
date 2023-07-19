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

package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import screens.StorageSampleLoggedScreen;
import screens.StorageSampleLoginScreen;
import screens.WebViewBrowserScreen;

public class StorageSampleLoginScreenTests extends MobileDriverManager {

    private StorageSampleLoginScreen storageSampleLoginScreen;
    private WebViewBrowserScreen webViewBrowserScreen;
    private StorageSampleLoggedScreen storageSampleLoggedScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        storageSampleLoginScreen = new StorageSampleLoginScreen(getDriver());
        assertTrue(storageSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        assertAll();
    }

    @Test
    public void FW_17_FW_18_verifyThatStorageLoginBtnIsDisplayed() {
        assertTrue(storageSampleLoginScreen.verifySignInBtnDisplayed(), basicErrorMsg("Unable to get the button info"));
        assertAll();
    }

    @Test @Parameters({"deviceType"})
    public void FW_98_FW_114_FW_115_verifyThatUserCanTapXInBrowserOrTapOusideModal(String deviceType) {
        if(deviceType.equals("GMS")) {
            assertTrue(storageSampleLoginScreen.tapOutsideModal(), basicErrorMsg("It can't be tapped outside"));
        } else {
            webViewBrowserScreen = storageSampleLoginScreen.signInFromBrowser();
            assertTrue(webViewBrowserScreen.verifySignPageLoads(), basicErrorMsg("The signin web view was not loaded correctly"));
            assertTrue(webViewBrowserScreen.clickTheXOnBrowser(), basicErrorMsg("Unable to tap on the X close browser"));
            assertTrue(storageSampleLoginScreen.checkAlerMsgPrint(), basicErrorMsg("Alert message has not been triggered"));
        }
        assertAll();
    }


    @Test @Parameters({"deviceType"})
    public void FW_23_FW_97_verifyThatUserIsReturnedToSampleAppInLoggedInState(String deviceType) {
        if(deviceType.equals("nonGMS")) {
            webViewBrowserScreen = storageSampleLoginScreen.signInFromBrowser();
            assertTrue(webViewBrowserScreen.verifySignPageLoads(), basicErrorMsg("The signIn web view was not loaded correctly"));
            assertTrue(webViewBrowserScreen.clickLoggedInAccountXY(540,700), basicErrorMsg("Unable to click on the XY location given"));
            storageSampleLoginScreen = webViewBrowserScreen.returnAsSignInState(800,1920);
        } else {
            assertTrue(storageSampleLoginScreen.verifySignInPopUpShown(), basicErrorMsg("Unable to shown the pop up account"));
        }
        storageSampleLoggedScreen = storageSampleLoginScreen.signedUser();
        assertTrue(storageSampleLoggedScreen.verifySignInState(), basicErrorMsg("The signed in state fails the validation"));
        assertAll();
    }


}
