package pages;

import fragments.CookiesFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

import static constant.Constant.TimeOutVariable.EXPLICIT_WAIT;


public class BasePage<T extends BasePage<T>> {
    protected static final String BASE_URL = ConfigReader.getProperty("production.baseUrl");
    private final CookiesFragment cookiesFragment;
    protected WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(EXPLICIT_WAIT));
        this.cookiesFragment = new CookiesFragment(driver);
    }

    @SuppressWarnings("unchecked")
    public T acceptCookies() {
        cookiesFragment.clickAcceptAllButton();
        return (T) this;
    }
    public WebElement waitElementToBeVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
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

    public boolean isElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public List<WebElement> waitElementsAreVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void scrollToElement(By locator) {
        WebElement element = waitElementIsVisible(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public List<WebElement> waitElementsAreUpdated(By locator) {
        List<WebElement> oldElements = driver.findElements(locator);
        wait.until(ExpectedConditions.stalenessOf(oldElements.get(0)));
        return driver.findElements(locator);
    }
}
