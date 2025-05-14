package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String PRODUCT_ACTUAL_PRICES = "//div[@class='c-price__current']";
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
    public List<String> getVisibleActualPriceTexts() {
        return waitElementsAreVisible(By.xpath(PRODUCT_ACTUAL_PRICES))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get list of texts 'Новий' product tags")
    public List<String> getVisibleNewTag(){
        return waitElementsAreVisible(By.xpath(NEW_TAG_ELEMENTS))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Waits until product prices are updated")
    public List<WebElement> waitUntilProductPricesAreUpdated() {
        return waitElementsAreUpdated(By.xpath(PRODUCT_ACTUAL_PRICES));
    }

    @Step("Waits util new tag are updated")
    public List<WebElement> waitUntilTagsAreUpdated(){
        return waitElementsAreUpdated(By.xpath(NEW_TAG_ELEMENTS));
    }

    @Step("Get visible product prices as text")
    public List<String> getVisibleRegularPriceTexts() {
        return waitElementsAreVisible(By.xpath(PRODUCT_PRICES_WITHOUT_DISCOUNT))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
