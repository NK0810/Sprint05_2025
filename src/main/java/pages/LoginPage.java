package pages;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import utils.ConfigReader;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage<LoginPage> {
    private static final By EMAIL_FIELD = By.xpath("//*[@id='email']");
    private static final By PASSWORD_FIELD = By.xpath("//*[@id='pass']");
    private static final By LOGIN_BUTTON = By.xpath("//*[@id='send2']");
    private static final By PASSWORD_RECOVERY_LINK = By.xpath("//a[@class='action login-container__link link link--underline']");
    private static final By EMAIL_ERROR_MESSAGE = By.xpath("//div[@class='email']//div[@id]");
    private static final String LOGIN_URL = BASE_URL + "/customer/account/login";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open login page")
    public LoginPage openLoginPage() {
        driver.get(LOGIN_URL);
        return this;
    }

    @Step("Enter email")
    public LoginPage enterEmail(String email) {
        WebElement emailField = waitElementIsVisible(EMAIL_FIELD);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", emailField, email);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        WebElement passwordField = waitElementIsVisible(PASSWORD_FIELD);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", passwordField, password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLogInButton() {
        WebElement loginBtn = waitElementToBeClickable(LOGIN_BUTTON);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
        return this;
    }

    @Step("Click forgot your password button")
    public LoginPage clickForgotYourPassword() {
        waitElementToBeClickable(PASSWORD_RECOVERY_LINK).click();
        return this;
    }

    @Step("Get email error text")
    public String getEmailErrorText() {
        return waitElementIsVisible(EMAIL_ERROR_MESSAGE).getText();
    }

    @Step("Get email error message color")
    public String getEmailErrorColor() {
        return waitElementIsVisible(EMAIL_ERROR_MESSAGE).getCssValue("color");
    }

}