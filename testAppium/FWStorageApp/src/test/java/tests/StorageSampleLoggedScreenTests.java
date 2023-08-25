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
import org.testng.annotations.Test;
import screens.StorageSampleLoggedScreen;
import screens.StorageSampleLoginScreen;

public class StorageSampleLoggedScreenTests extends MobileDriverManager {

    private StorageSampleLoginScreen storageSampleLoginScreen;
    private StorageSampleLoggedScreen storageSampleLoggedScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        storageSampleLoginScreen = new StorageSampleLoginScreen(getDriver());
        assertTrue(storageSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        storageSampleLoggedScreen = storageSampleLoginScreen.signedUser();
        assertTrue(storageSampleLoggedScreen.verifyLoads(), basicErrorMsg("The logged in user was not loaded"));
        assertAll();
    }

    @Test
    public void FW_102_FW_103_verifyUserCanChangeGridOrLinearLayoutViewInDrive() {
        assertTrue(storageSampleLoggedScreen.listFilesInDrive(), basicErrorMsg("The list of files has failed to be tapped"));
        assertAll();
    }

    @Test
    public void FW_43_FW_116_verifyUserCanNavigateBetweenFolders() {
        assertTrue(storageSampleLoggedScreen.navigateBackAndForthInsideAFolder(270, 585), basicErrorMsg("Unable to navigate between folders"));
        assertAll();
    }

    @Test
    public void FW_47_FW_49_FW_117_verifyUserCanSeeAddDeleteFolders() {
        assertTrue(storageSampleLoggedScreen.tapAndCreateFolderOrFile(true), basicErrorMsg("The folder can't be created"));
        assertTrue(storageSampleLoggedScreen.deleteAFileFolder(true), basicErrorMsg("The folder can't be deleted"));
        assertAll();
    }

    @Test //(invocationCount = 10)
    public void FW_19_FW_20_FW_22_verifyUserCanSeeAddDeleteFiles() {
        assertTrue(storageSampleLoggedScreen.tapAndCreateFolderOrFile(false), basicErrorMsg("The file can't be created"));
        assertTrue(storageSampleLoggedScreen.deleteAFileFolder(false), basicErrorMsg("The file can't be deleted"));
        assertAll();
    }

}
