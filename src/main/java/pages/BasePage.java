package pages;

import fragments.CookiesFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public int convertPriceToInt(String listPrice) {
        return Integer.parseInt(listPrice.replaceAll(",.*", "").replaceAll("[^\\d]", ""));
    }

    public List<String> getTextsFromList(List<WebElement> elements) {
        return elements.stream().map(WebElement::getText).toList();
    }

    public void dispatchInputAndChangeEvents(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", element);
    }

    public String getClassValueFromElement(By locator){
        return waitElementIsVisible(locator).getAttribute("class");
    }

    public void goBack() {
        driver.navigate().back();
    }

    public void scrollToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    @Step("Convert raw address block to normalized form")
    public static String convertAddressBlock(String rawAddress) {
        return rawAddress.replace("tel. ", "")
                .replace(",", "")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public String getSiteUrl(){
        return driver.getCurrentUrl();
    }

    public WebDriver switchToOriginalPage(String originalWindowHandle) {
        driver.close();
        return driver.switchTo().window(originalWindowHandle);
    }
}