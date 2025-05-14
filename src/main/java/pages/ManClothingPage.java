package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ManClothingPage extends ProductCatalogPage {
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
    private static final String PRODUCT_ACTUAL_PRICES = "//div[@class='c-price__current']";
    private static final String NEW_TAG_ELEMENTS = "//span[@class='product-card__badge product-card__badge--new']";
    private static final String PRODUCT_PRICES_REGULAR_DISCOUNT = "//span[contains(text(), 'Звичайна ціна')]";
    private static final String PRODUCTS_CARDS = "//div[@class='product-card product-card--type-default ']";
    private static final String PRODUCTS_NAME = "//a[@class='product-card__name']";
    private static final String BRANDS_DROPDOWN_LIST = "//div[@class='widget-container-brand']/div";
    private static final String BRANDS_SEARCH_FIELD = "//div[@class='widget-container-brand']//input[@placeholder]";
    private static final String LIST_BRAND_NAMES = "//li[@class='refinement-item refinement-item--brand']";

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
        return waitElementsAreVisible(By.xpath(PRODUCT_PRICES_REGULAR_DISCOUNT))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}

    @Step("Wait until product names are updated")
    public List<WebElement> waitProductNamesAreUpdated() {
        return waitElementsAreUpdated(By.xpath(PRODUCTS_NAME));
    }

    @Step("Get list of product names")
    public List<String> getAllProductsName() {
        By nameLocator = By.xpath(PRODUCTS_NAME);

        return waitElementsAreVisible(nameLocator)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Select brand from filter")
    public ManClothingPage selectFilterBrandOption(BrandFilter option) {
        waitElementToBeClickable(By.xpath(option.getListNameBrand())).click();
        return this;
    }

    @Step("Scroll to brand name the filter")
    public ManClothingPage scrollToBrandItem() {
        scrollToElement(By.xpath(LIST_BRAND_NAMES));
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

    @Step("Scroll to brand list dropdown")
    public ManClothingPage scrollToBrandDropdown() {
        scrollToElement(By.xpath(BRANDS_DROPDOWN_LIST));
        return this;
    }
