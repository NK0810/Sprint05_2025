package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static constant.Constant.TimeOutVariable.EXPLICIT_WAIT;


public class BasePage {
    protected WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(EXPLICIT_WAIT));
    }

    public WebElement waitElementIsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitPresenceOfElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean waitElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public List<WebElement> waitElementsAreVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public BasePage scrollToElement(By locator) {
        WebElement listGoods = waitElementIsVisible(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(listGoods).perform();
        return this;
    }
}
