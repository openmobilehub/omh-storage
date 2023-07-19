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
import screens.FileUploadExplorerScreen;
import screens.StorageSampleLoggedScreen;
import screens.StorageSampleLoginScreen;

public class FileUploadExplorerScreenTests extends MobileDriverManager {

    private StorageSampleLoginScreen storageSampleLoginScreen;
    private StorageSampleLoggedScreen storageSampleLoggedScreen;
    private FileUploadExplorerScreen fileUploadExplorerScreen;

    @BeforeMethod  //Launch app and confirm that user is signed in
    public void setAuthSampleLoginScreen() {
        storageSampleLoginScreen = new StorageSampleLoginScreen(getDriver());
        assertTrue(storageSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        storageSampleLoggedScreen = storageSampleLoginScreen.signedUser();
        assertTrue(storageSampleLoggedScreen.verifyLoads(), basicErrorMsg("The logged in user was not loaded"));
        assertAll();
    }

    @Test
    public void FW_29_verifyThatAFileCanBeUploadedCorrectly() {
        fileUploadExplorerScreen = storageSampleLoggedScreen.fileUploadExplorerScreen();
        storageSampleLoggedScreen = fileUploadExplorerScreen.storageSampleLoggedScreen();
        assertTrue(storageSampleLoggedScreen.tapToUploadTheFile(), basicErrorMsg("Unable to tap to upload the file"));
        assertTrue(storageSampleLoggedScreen.deleteAFileFolder(false), basicErrorMsg("Unable to delete the file"));
        assertAll();
    }

}
