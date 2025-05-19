package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage extends BasePage<PasswordRecoveryPage> {
    private static final By PASSWORD_RECOVERY_FIELD = By.xpath("//input[@class='input-text required-entry']");
    private static final By RESET_PASSWORD = By.xpath("//button[@class='action login-container__button button button__addtocart button--jumbo']");
    private static final By NOTIFICATION_SEND_MASSAGE = By.xpath("//div[@class='message-text']");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter email for password recovery")
    public PasswordRecoveryPage enterEmail(String email) {
        waitElementIsVisible(PASSWORD_RECOVERY_FIELD).sendKeys(email);
        return this;
    }

    @Step("Click reset password button")
    public void clickResetPassword() {
        waitElementToBeClickable(RESET_PASSWORD).click();
    }

    @Step("Notification of sending a message")
    public static String getNotificationSendingMassage() {
        return waitElementIsVisible(NOTIFICATION_SEND_MASSAGE).getText();
    }
}
