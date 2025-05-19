package tests;

import org.testng.annotations.Test;
import io.qameta.allure.Description;
import org.testng.Assert;
import pages.LoginPage;
import pages.PasswordRecoveryPage;
import utils.ConfigReader;

public class PasswordRecoveryTest extends base.BaseTest {

    private static final String FORGOT_PASSWORD_URL = ConfigReader.getProperty("production.baseUrl") + "/customer/account/forgotpassword/";
    private static final String NOTIFICATION_SEND_MASSAGE_TEXT = "Якщо вказана e-mail адреса є в базі даних наших клієнтів, ми надішлемо Вам повідомлення.";

    @Test
    @Description("Password recovery")
    public void passwordRecoveryTest() {
        String email = ConfigReader.getProperty("EmailRecovery");

        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .openLoginPage()
                .acceptCookies()
                .clickForgotYourPassword();
        Assert.assertEquals(driver.getCurrentUrl(), FORGOT_PASSWORD_URL, "User should not leave the login page.");

        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        passwordRecoveryPage
                .enterEmail(email)
                .clickResetPassword();
        Assert.assertEquals(passwordRecoveryPage.getNotificationSendingMassage(), NOTIFICATION_SEND_MASSAGE_TEXT, "Message text is incorrect.");
    }
}
