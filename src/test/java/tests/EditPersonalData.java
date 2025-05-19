package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.UserAccountPage;
import utils.ConfigReader;

    public class EditPersonalData extends BaseTest {
    private static final String BASE_NAME = "Anton";
    private static final String BASE_SURNAME = "Skvortsov";
    private static final String ERROR_MESSAGE = "The save message did not appear or is incorrect.";
    private static final String EXPECTED_RESULT = "Дані Вашого Облікового запису збережено.";
    private static final String NEW_NAME = BASE_NAME + generateRandomLetter();

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
                .enterEmail(ConfigReader.getProperty("test.email"))
                .enterPassword(ConfigReader.getProperty("test.password"))
                .clickLogInButton();

        UserAccountPage userAccountPage = new UserAccountPage(driver);
        userAccountPage
                .clickAccountSetting()
                .sendNewTestName(NEW_NAME)
                .sendNewTestSurname(NEW_SURNAME)
                .scrollToSaveChangeButton()
                .clickCheckBoxMail()
                .clickCheckBoxPhone()
                .clickSaveChange();

        String successMessage = userAccountPage.getSuccessMessageText().trim();
        Assert.assertEquals(successMessage, EXPECTED_RESULT, ERROR_MESSAGE);

        userAccountPage
                .clickAccountSetting();

        String actualName = userAccountPage.getFirstNameFieldText().trim();
        String actualSurname = userAccountPage.getSurnameFieldText().trim();
        Assert.assertEquals(actualName, NEW_NAME, "Ім'я не оновлено правильно.");
        Assert.assertEquals(actualSurname, NEW_SURNAME, "Прізвище не оновлено правильно.");

        Assert.assertTrue(userAccountPage.isCheckBoxMailSelected(), "Чекбокс Email не збережено.");
        Assert.assertTrue(userAccountPage.isCheckBoxPhoneSelected(), "Чекбокс Phone не збережено.");
    }
    private static final String NEW_SURNAME = BASE_SURNAME + generateRandomLetter();
    private static String generateRandomLetter() {
        return String.valueOf((char) ('A' + (int) (Math.random() * 26)));
    }
        @Test
        @Description("Login user")
        public void loginUserTest() {
            String Email = ConfigReader.getProperty("test.email");
            String Password = ConfigReader.getProperty("test.password");
            LoginPage loginPage = new LoginPage(driver);
            UserAccountPage userAccountPage = new UserAccountPage(driver);
        }
}


