package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import pages.UserAccountPage;
import utils.ConfigReader;

public class LoginTest extends base.BaseTest {

    private static final String USER_ACCOUNT_URL = ConfigReader.getProperty("production.baseUrl") + "/customer/account/login";
    private static final String TEXT_OUT = "Вийти";

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
}