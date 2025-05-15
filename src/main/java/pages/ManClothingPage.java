package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ManClothingPage extends ProductCatalogPage {
    public ManClothingPage(WebDriver driver) {
        super(driver);
    }
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String CLOSE_TRUSTBADGE_BUTTON = "//div[@class='js-trusted-shop-close']";
    private static final String BRANDS_SEARCH_FIELD = "//div[@class='widget-container-brand']//input[@placeholder]";

    public enum BrandName {
        ADIDAS("adidas"),
        ALPINUS("Alpinus"),
        CCM("CCM");

        private final String value;

        BrandName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
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

    @Step("Type brand name '{brandName}' into search field")
    public ProductCatalogPage typeBrandNameInSearch(BrandName brandName) {
        WebElement searchInput = waitElementIsVisible(By.xpath(BRANDS_SEARCH_FIELD));
        searchInput.clear();
        searchInput.sendKeys(brandName.getValue());
        return this;
    }

    @Step("Select brand filter option: {brandName}")
    public ProductCatalogPage selectBrandOption(BrandName brandName) {
        By brandOptionLocator = By.xpath("//label[@class='refinement-label ']//span[text()='" + brandName.getValue() + "']");
        scrollToElement(brandOptionLocator);
        waitElementToBeClickable(brandOptionLocator).click();
        return this;
    }
}