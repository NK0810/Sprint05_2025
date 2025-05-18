package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.UserAccountPage;

public class EditPersonalData extends BaseTest {
    private static final String BASE_NAME = "Anton";
    private static final String BASE_SURNAME = "Skvortsov";
    private static final String ERROR_MESSAGE = "The save message did not appear or is incorrect.";
    private static final String EXPECTED_RESULT = "Дані Вашого Облікового запису збережено.";
    private static final String NEW_NAME = BASE_NAME + generateRandomLetter();
    private static final String NEW_SURNAME = BASE_SURNAME + generateRandomLetter();  private static String generateRandomLetter() {
        return String.valueOf((char) ('A' + (int) (Math.random() * 26)));
    }
    @Description("Edit user name and choose agreements")
    @Test
    public void EditPersonalDataTest() throws InterruptedException {

        HomePage homePage = new HomePage(driver);

        homePage
                .openUrl()
                .acceptCookies();

        LoginPage loginPage = new LoginPage(driver);

        loginPage
                .openLoginPage()
                .enterEmail(TEST_EMAIL)
                .enterPassword(TEST_PASS)
                .clickLogInButton();

        UserAccountPage userAccountPage = new UserAccountPage(driver);
        userAccountPage
                .clickAccountSetting()
                .clickAndClearFirstNameField()
                .sendNewTestName(NEW_NAME)
                .clickAndClearSurnameField()
                .sendNewTestSurname(NEW_SURNAME)
                .scrollToSaveChangeButton();

        boolean mailCheckboxValue = userAccountPage.isCheckBoxMailSelected();

        userAccountPage
                .clickCheckBoxMail();
        Assert.assertEquals(!mailCheckboxValue, userAccountPage.isCheckBoxMailSelected(), "Checkbox Notification Mail is not selected.");

        boolean PhoneCheckBoxValue = userAccountPage.isCheckBoxPhoneSelected();

        userAccountPage
                .clickCheckBoxPhone();
        Assert.assertEquals(!PhoneCheckBoxValue, userAccountPage.isCheckBoxPhoneSelected(), "Checkbox Notification Phone is not selected.");

        userAccountPage
                .clickSaveChange();

        String successMessage = userAccountPage.getSuccessMessageText().trim();
        Assert.assertEquals(successMessage, EXPECTED_RESULT, ERROR_MESSAGE);

        userAccountPage
                .clickAccountSetting();

        String actualName = userAccountPage.getFirstNameFieldText();
        String actualSurname = userAccountPage.getSurnameFieldText();

        Assert.assertEquals(actualName, NEW_NAME, "Name not updated correctly.");
        Assert.assertEquals(actualSurname, NEW_SURNAME, "Last name not updated correctly.");

    }
}

