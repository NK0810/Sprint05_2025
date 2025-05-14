package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ManClothingPage extends BaseProductPage {
    public enum BrandFilter {
        ADIDAS("adidas");

        private String dataValue;
        private final String listNameBrand;

        BrandFilter(String dataValue) {
            this.dataValue = dataValue;
            this.listNameBrand = "//label[@class='refinement-label ']//span[text()='" + dataValue + "']";
        }

        public String getDataValue() {
            return dataValue;
        }

        public String getListNameBrand() {
            return listNameBrand;
        }

    }

    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";
    private static final String PRODUCTS_CARDS = "//div[@class='product-card product-card--type-default ']";
    private static final String PRODUCTS_NAME = "//a[@class='product-card__name']";
    private static final String PRODUCTS_PRICE = "//div[@class='c-price__current']";
    private static final String BRANDS_DROPDOWN_LIST = "//div[@class='widget-container-brand']/div";
    private static final String BRANDS_SEARCH_FIELD = "//div[@class='widget-container-brand']//input[@placeholder]";
    private static final String BRAND_ITEM = "//li[@class='refinement-item refinement-item--brand']";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open home page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }

    public ManClothingPage waitUpdateProductCard() {
        waitElementsAreUpdated(By.xpath(PRODUCTS_CARDS));
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

    public List<WebElement> waitProductNamesAreUpdated() {
        return waitElementsAreUpdated(By.xpath(PRODUCTS_NAME));
    }

    public List<String> getAllProductsName() {
        By nameLocator = By.xpath(PRODUCTS_NAME);

        return waitElementsAreVisible(nameLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public ManClothingPage selectFilterBrandOption(BrandFilter option) {
        waitElementToBeClickable(By.xpath(option.getListNameBrand())).click();
        return this;
    }

    @Step("Scroll to first brand item in the filter")
    public ManClothingPage scrollToBrandItem() {
        scrollToElement(By.xpath(BRAND_ITEM));
        return this;
    }

    @Step("Type brand name '{brandName}' into search field")
    public ManClothingPage typeBrandNameInSearch(String brandName) {
        WebElement searchInput = waitElementIsVisible(By.xpath(BRANDS_SEARCH_FIELD));
        searchInput.clear();
        searchInput.sendKeys(brandName);
        waitUpdateBrandList();
        return this;
    }

    @Step("Open brand filter dropdown")
    public ManClothingPage openBrandDropdown() {
        waitElementToBeClickable(By.xpath(BRANDS_DROPDOWN_LIST)).click();
        return this;
    }

    @Step("Scroll to brand dropdown")
    public ManClothingPage scrollToBrandDropdown() {
        scrollToElement(By.xpath(BRANDS_DROPDOWN_LIST));
        return this;
    }

    @Step("Wait until brand list is updated")
    public ManClothingPage waitUpdateBrandList() {
        By brandItemsLocator = By.xpath(BRAND_ITEM);
        waitElementsAreUpdated(brandItemsLocator);
        return this;
    }
}