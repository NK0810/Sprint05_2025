package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String PRODUCT_PRICES = "//div[@class='c-price__current']";
    private static final String NEW_TAG_ELEMENTS = "//span[@class='product-card__badge product-card__badge--new']";
    private static final String PRODUCT_PRICES_WITHOUT_DISCOUNT = "//span[contains(text(), 'Звичайна ціна')]";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open man clothing page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }

    @Step("Get visible product prices as text")
    public List<String> getVisiblePriceTexts() {
        By priceLocator = By.xpath(PRODUCT_PRICES);

        return waitElementsAreVisible(priceLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get list of texts 'Новий' product tags")
    public List<String> getVisibleNewTag(){
        By newTagLocator = By.xpath(NEW_TAG_ELEMENTS);

        return waitElementsAreVisible(newTagLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Waits until product prices are updated")
    public List<WebElement> waitUntilProductPricesAreUpdated() {
        return waitElementsAreUpdated(By.xpath(PRODUCT_PRICES));
    }

    @Step("Waits util new tag are updated")
    public List<WebElement> waitUntilTagsAreUpdated(){
        return waitElementsAreUpdated(By.xpath(NEW_TAG_ELEMENTS));
    }

    @Step("Get visible product prices as text")
    public List<String> getVisiblePriceWithoutDiscountTexts() {
        By priceLocator = By.xpath(PRODUCT_PRICES_WITHOUT_DISCOUNT);

        return waitElementsAreVisible(priceLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
