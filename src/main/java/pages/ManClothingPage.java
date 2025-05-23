package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String SELECT_FILTER_LABEL = "//label[@class='refinement-label refinement-label--checked']";
    private static final String FILTERS_LABEL = "//li[@class='refinement-item refinement-item--sezon']";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    public enum BrandName {
        ADIDAS("adidas"),
        ALPINUS("Alpinus"),
        ASICS("ASICS");

        private final String value;

        BrandName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum FilterOptionManClothingPage {
        SIZE_DROP_DOWN_BUTTON("//div[@data-attr='rozmiar_wyswietlany']"),
        SEASON_DROP_DOWN_BUTTON("//div[@data-attr='sezon']");

        private final String locator;

        FilterOptionManClothingPage(String locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return By.xpath(locator);
        }

    }

    @Step("Open man clothing page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }

    @Step("Click on the {locator} dropdown button")
    public ManClothingPage clickOnDropdownButton(FilterOptionManClothingPage locator) {
        waitElementIsVisible(locator.getLocator());
        waitElementToBeClickable(locator.getLocator()).click();
        return this;
    }

    @Step("Select size filter option: {size}")
    public ManClothingPage selectSizeFilterOption(String size) {
        By sizeButton = By.xpath("//span[text()='" + size + "']");
        scrollToElement(sizeButton);
        waitElementIsVisible(sizeButton);
        waitElementToBeClickable(sizeButton).click();
        return this;
    }

    @Step("Get class attribute from the selected size label")
    public String getSelectedSizeLabelClass() {
        return getClassValueFromElement(By.xpath(SELECT_FILTER_LABEL));
    }

    @Step("Select season filter and option: {season}")
    public ManClothingPage selectSeasonOption(String season) {
        By seasonButton = By.xpath("//span[text()='" + season + "']");
        waitElementsAreUpdated(By.xpath(FILTERS_LABEL));
        waitElementIsVisible(seasonButton);
        waitElementToBeClickable(seasonButton).click();
        return this;
    }

    @Step("Select and scroll season filter and option: {season}")
    public ManClothingPage selectAndScrollSeasonOption(String season) {
        By seasonButton = By.xpath("//span[text()='" + season + "']");
        scrollToElement(seasonButton);
        waitElementIsVisible(seasonButton);
        waitElementToBeClickable(seasonButton).click();
        return this;
    }

    @Step("Scroll to element {locator}")
    public ManClothingPage scrollToElement(FilterOptionManClothingPage locator) {
        scrollToElement(locator.getLocator());
        return this;
    }
}