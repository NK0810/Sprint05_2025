package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.UserAccountPage;
import utils.ConfigReader;

import static constant.Constant.Owners.IGOR;
import static constant.Constant.Owners.STAS;

public class LoginTest extends base.BaseTest {

    private static final String USER_ACCOUNT_URL = ConfigReader.getProperty("production.baseUrl") + "/customer/account/";
    private static final String LOGIN_URL = ConfigReader.getProperty("production.baseUrl") + "/customer/account/login";
    private static final String TEXT_OUT = "Вийти";
    private static final String EMAIL_ERROR_MESSAGE_TEXT = "Введіть дійсну e-mail адресу (наприклад: johndoe@domain.com.).";

    private final String invalidEmail = ConfigReader.getProperty("UserEmail").replace("@", "");

    @Owner(STAS)
    @Test
    @Description("Login user")
    public void loginUserTest() {
        String email = ConfigReader.getProperty("UserEmail");
        String password = ConfigReader.getProperty("UserPassword");
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);

        SoftAssert softAssert = new SoftAssert();

        loginPage
                .openLoginPage()
                .acceptCookies()
                .enterEmail(email)
                .enterPassword(password)
                .clickLogInButton();

        userAccountPage.waitUntilMyAccountPageIsVisible();

        softAssert.assertEquals(driver.getCurrentUrl(), USER_ACCOUNT_URL, "URL після логіну не відповідає очікуваному.");
        softAssert.assertEquals(userAccountPage.getUserEmail(), email, "Email не співпадає.");
        softAssert.assertEquals(userAccountPage.getTextOut(), TEXT_OUT, "Текст кнопки 'Вийти' не співпадає.");

        softAssert.assertAll();
    }

    @Owner(IGOR)
    @Test
    @Description("Login with invalid email format")
    public void loginViaInvalidEmail() {
        String password = ConfigReader.getProperty("UserPassword");
        LoginPage loginPage = new LoginPage(driver);

        SoftAssert softAssert = new SoftAssert();

        loginPage
                .openLoginPage()
                .acceptCookies()
                .enterEmail(invalidEmail)
                .enterPassword(password)
                .clickLogInButton();

        softAssert.assertEquals(driver.getCurrentUrl(), LOGIN_URL, "User should not leave the login page.");
        String color = loginPage.getEmailErrorColor();
        softAssert.assertTrue(color.contains("255, 0, 0"), "Expected red color, but got: " + color);
        softAssert.assertEquals(loginPage.getEmailErrorText(), EMAIL_ERROR_MESSAGE_TEXT,
                "Email error message text is incorrect.");

        softAssert.assertAll();
    }
}