package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String CLOSE_TRUSTBADGE_BUTTON = "//div[@class='js-trusted-shop-close']";
    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open man clothing page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }

    @Step("Close Trustbadge pop-up")
    public void closeTrustbadgePopUp() {
        WebElement button = wait.until(driver ->
                driver.findElement(By.xpath(CLOSE_TRUSTBADGE_BUTTON))
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    }

}