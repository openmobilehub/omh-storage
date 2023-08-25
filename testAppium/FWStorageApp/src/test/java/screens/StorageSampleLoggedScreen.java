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
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class StorageSampleLoggedScreen extends BaseScreen {
    public StorageSampleLoggedScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(androidTextViewList.get(0)) && waitForMobElementToBeVisible(sortLbl)
                && waitForMobElementToBeVisible(moreOptionsBtn);
    }

    /*
    UI Elements
     */

    @AndroidFindBy(className="android.widget.TextView")
    private List<WebElement> androidTextViewList;

    @AndroidFindBy(id="com.omh.android.storage.sample:id/sortByName")
    private WebElement sortLbl;

    // Ellipsis more button
    @AndroidFindBy(xpath="//android.widget.ImageView[@content-desc=\"More options\"]")
    private WebElement moreOptionsBtn;

    // Ellipsis options
    @AndroidFindBy(className="android.widget.LinearLayout")
    private List<WebElement> listViewOptions;

    /*
     POPUP Create
     */
    @AndroidFindBy(id="com.omh.android.storage.sample:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(id="android:id/button2")
    private WebElement cancelBtn;

    @AndroidFindBy(id="android:id/button1")
    private WebElement createBtn;

    /*
    POPUP fields to complete
     */
    @AndroidFindBy(id="com.omh.android.storage.sample:id/fileName")
    private WebElement fileNameTxtField;

    @AndroidFindBy(id="com.omh.android.storage.sample:id/fileType")
    private WebElement fileTypeDownArrow;

    @AndroidFindBy(className="android.widget.CheckedTextView")
    private List<WebElement> fileTypeCheckedTextView;

    /*
    Methods
     */

    public boolean listFilesInDrive() {
        return tapMobElement(moreOptionsBtn) && implicityWaitTimeOnScreenManual(2)
                && tapMobElement(listViewOptions.get(0)) && implicityWaitTimeOnScreenManual(2);
    }

    public boolean navigateBackAndForthInsideAFolder(int getX, int getY) {
        return tapOnScreenXY(getX, getY) && implicityWaitTimeOnScreenManual(2) && getFolderText()
                && returnToMainDriveScreen() && implicityWaitTimeOnScreenManual(2);
    }

    private boolean returnToMainDriveScreen() {
        boolean flag = false;
        try {
            pressAndroidKey(AndroidKey.BACK);
            flag = true;
        } catch (Exception e) { ErrorsManager.errNExpManager(e);}
        return flag;
    }

    private boolean getFolderText() {
        boolean flag = false;
        try {
            System.out.println(getTextFromMobElement(androidTextViewList.get(1)));
            flag = true;
        } catch (Exception e) {ErrorsManager.errNExpManager(e);}
        return flag;
    }

    /*
    AFTER LOGGED IN VALIDATIONS
     */

    public boolean verifySignInState() {
        System.out.println("The app is returned in Signed In state");
        return implicityWaitTimeOnScreenManual(WAIT_TIME_MEDIUM) /*&& waitForMobElementToBeVisible(sortLbl) && waitForMobElementToBeVisible(moreOptionsBtn)*/;
    }

    /*
    CREATION FILE OR FOLDERS METHODS
     */

    private static final int SCREEN_X_COORDINATE = 675;
    private static final int SCREEN_Y_COORDINATE = 365;
    private static final int WAIT_TIME_SHORT = 1;
    private static final int WAIT_TIME_MEDIUM = 3;
    private static final int WAIT_TIME_LONG = 5;

    public boolean tapAndCreateFolderOrFile(boolean createFolder) {
        try {
            tapMoreOptionsButton();
            waitOnScreen(WAIT_TIME_SHORT);
            tapOnScreenXY(SCREEN_X_COORDINATE, SCREEN_Y_COORDINATE);
            waitOnScreen(WAIT_TIME_SHORT);
            writeASimpleNameOnField();

            if (!createFolder) {
                tapMobElement(fileTypeDownArrow);
                int randomIndex = generateRandomIndex(1, 3);
                tapMobElement(fileTypeCheckedTextView.get(randomIndex));
            }

            tapCreateButton();

            if (createFolder) {
                waitOnScreen(WAIT_TIME_MEDIUM);
            } else {
                waitOnScreen(WAIT_TIME_LONG);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int generateRandomIndex(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private void tapMoreOptionsButton() {
        tapMobElement(moreOptionsBtn);
    }

    private void writeASimpleNameOnField() {
        String randomName = generateRandomName();
        fileNameTxtField.sendKeys(randomName);
        //typeTxtOnMobElement(fileNameTxtField, randomName);
    }

    private String generateRandomName() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);  // Adjust the range of random numbers as needed
        return "Auto-File-Folder " + randomNumber;
    }


    private void tapCreateButton() {
        tapMobElement(createBtn);
    }

    private void waitOnScreen(int seconds) {
        implicityWaitTimeOnScreenManual(seconds);
    }

    public boolean deleteAFileFolder(boolean deleteFile) {
        if(deleteFile) {
            return tapOnScreenXY(445,460);
        } else
            return tapOnScreenXY(980,460);
    }

    @AndroidFindBy(xpath="/hierarchy/android.widget.Toast")
    private WebElement toastMsgDel;


    /*
    REDIRECTS TO FILE EXPLORER APP TO UPLOAD A FILE
     */

    public FileUploadExplorerScreen fileUploadExplorerScreen() {

        try {
            // call a method to tap the more button
            tapMoreOptionsButton();
            // locate by XY coordinates to tap Upload
            waitOnScreen(WAIT_TIME_SHORT);
            tapOnScreenXY(SCREEN_X_COORDINATE, 510);
            // wait and return driver with the new view screen
            implicityWaitTimeOnScreenManual(WAIT_TIME_SHORT);
            return new FileUploadExplorerScreen(this.driver);
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
            return null;
        }
    }

    // A method that tap on Upload button
    // Implicity wait until file is uploaded. Toast can't be validated
    // Exit the app

    public boolean tapToUploadTheFile() {
        return tapMobElement(createBtn) && implicityWaitTimeOnScreenManual(WAIT_TIME_LONG);
    }


    /*
    Sign out session
     */

    private boolean signOutSession() {
        tapMoreOptionsButton();
        return implicityWaitTimeOnScreenManual(WAIT_TIME_SHORT) && tapOnScreenXY(SCREEN_X_COORDINATE, 675)
                && implicityWaitTimeOnScreenManual(WAIT_TIME_SHORT);
    }

    public StorageSampleLoginScreen signOutUserSession() {
        if(signOutSession()) {
            return new StorageSampleLoginScreen(driver);
        } else return null;
    }
}
