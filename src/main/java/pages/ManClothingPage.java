package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String SIZE_DROP_DOWN_BUTTON = "//div[@data-attr='rozmiar_wyswietlany']";
    private static final String SELECT_SIZE_LABEL = "//label[@class='refinement-label refinement-label--checked']";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    public enum BrandName {
        ADIDAS("adidas"),
        ALPINUS("Alpinus");

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

    @Step("Click on the size dropdown button")
    public ManClothingPage clickOnSizeDropdownButton() {
        waitElementIsVisible(By.xpath(SIZE_DROP_DOWN_BUTTON));
        waitElementToBeClickable(By.xpath(SIZE_DROP_DOWN_BUTTON)).click();
        return this;
    }

    @Step("Select size filter option: {size}")
    public ManClothingPage selectSizeFilterOption(String size) {
        By sizeButton = By.xpath("//span[text()='" + size + "']");
        waitElementIsVisible(sizeButton);
        waitElementToBeClickable(sizeButton).click();
        return this;
    }

    @Step("Get class attribute from the selected size label")
    public String getSelectedSizeLabelClass() {
        return getClassValueFromElement(By.xpath(SELECT_SIZE_LABEL));
    }
}