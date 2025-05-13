package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage<LoginPage> {
    private static final By EMAIL_FIELD = By.xpath("//*[@id='email']");
    private static final By PASSWORD_FIELD = By.xpath("//*[@id='pass']");
    private static final By LOGIN_BUTTON = By.xpath("//*[@id='send2']");
    private static final String LOGIN_URL = BASE_URL + "/customer/account/login";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open login page")
    public LoginPage openLoginPage() {
        driver.get(LOGIN_URL);
        return this;
    }

    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        waitElementIsVisible(EMAIL_FIELD).sendKeys(email);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        waitElementIsVisible(PASSWORD_FIELD).sendKeys(password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLogInButton() {
        waitElementToBeClickable(LOGIN_BUTTON).click();
        return this;
    }
}