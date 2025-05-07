package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static constant.Constant.TimeOutVariable.EXPLICIT_WAIT;

public class CookiesFragment {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final String ACCEPT_ALL_COOKIE_BUTTON = "//button[@class='button button--regular button__primary button--submit']";

    public CookiesFragment(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(EXPLICIT_WAIT));
    }

    @Step("Click 'Accept all cookies'")
    public void clickAcceptAllButton() {
        WebElement button = wait.until(driver -> driver.findElement(By.xpath(ACCEPT_ALL_COOKIE_BUTTON)));
        button.click();
    }
}