package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ManClothingPage extends BaseProductPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String PRODUCT_CARDS = "//div[@class='product-card product-card--type-default ']";
    private static final String PRODUCT_NAME = "//a[@class='product-card__name']";
    private static final String PRODUCTS_PRICE = "//div[@class='c-price__current']";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open home page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }

    public ManClothingPage waitUpdateProductCard() {
        By cardsLocator = By.xpath(PRODUCT_CARDS);
        wait.until(driver -> driver.findElements(cardsLocator).size() > 0);
        return this;
    }


    public List<String> getVisiblePriceTexts() {
        By priceLocator = By.xpath(PRODUCTS_PRICE);

        return waitElementsAreVisible(priceLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<WebElement> waitAreProductPricesAreUpdated() {
        return waitElementsAreUpdated(By.xpath(PRODUCTS_PRICE));
    }

    public List<String> getAllProductNames() {
        By nameLocator = By.xpath(PRODUCT_NAME);

        return wait.until(driver -> {
            List<WebElement> elements = driver.findElements(nameLocator);
            try {
                return elements.stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());
            } catch (StaleElementReferenceException e) {
                return null; // повторить до стабільного DOM
            }
        });
    }

}