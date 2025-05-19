package pages;

import fragments.SortFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ProductCatalogPage extends BasePage<ProductCatalogPage> {
    private static final String EXCELLENT_POP_UP = "//div[@class='js-trusted-shop-close']";
    private static final String CLOSE_CLEAR_FILTER_BUTTON = "//span[@class='icon icon-x--after']";

    public interface LocatorProvider {
        By getLocator();
    }

    public enum FilterOption implements LocatorProvider {
        NEW_ARRIVALS("(//li[@class='refinement-item refinement-item--is_new'])[1]/label"),
        SALE("(//li[@class='refinement-item refinement-item--is_discount'])[1]/label"),
        BRAND_DROPDOWN("//div[@class='widget-container-brand']//div[@class='ais-Panel-header']"),
        PRICE_DROPDOWN("//div[@class='widget-container-price_UAH_default']"),
        LAST_PIECES("//div[@class='widget-container-ostatnie_sztuki']");

        private final By filter;

        FilterOption(String xpath) {
            this.filter = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return filter;
        }
    }

    public enum ProductCardInfo implements LocatorProvider {
        ACTUAL_PRICE("//div[@class='c-price__current']"),
        NEW_TAG("//span[@class='product-card__badge product-card__badge--new']"),
        REGULAR_DISCOUNT("//span[contains(text(), 'Звичайна ціна')]"),
        PRODUCT_NAME("//a[@class='product-card__name']");

        private final By locator;

        ProductCardInfo(String xpath) {
            this.locator = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }

    public enum PriceFilter implements LocatorProvider{
        MAX_PRICE_INPUT_FIELD("//input[@class='range-slider-input range-slider-input--max input-text']");

        private final By locator;

        PriceFilter(String xpath) {
            this.locator = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }

    public enum BrandFilter implements LocatorProvider{
        LIST_NAME_BRAND("//li[@class='refinement-item refinement-item--brand']"),
        BRANDS_SEARCH_FIELD("//div[@class='widget-container-brand']//input[@placeholder]");

        private final By locator;

        BrandFilter(String xpath) {
            this.locator = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return locator;
        }
    }

    protected SortFragment sortFragment;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        this.sortFragment = new SortFragment(driver);
    }

    @Step("Sorts products by {optionName}")
    public ProductCatalogPage sortByOption(SortFragment.SortOptions optionName) {
        sortFragment
                .clickDropdownButton()
                .selectOption(optionName);
        return this;
    }

    @Step("Select filter {filterOption}")
    public ProductCatalogPage selectFilterOption(FilterOption filterOption) {
        waitElementToBeClickable(filterOption.getLocator()).click();
        return this;
    }

    @Step("Get visible product info texts {productCardInfo}")
    public List<String> getVisibleProductsInfoTexts(ProductCardInfo productCardInfo) {
        return waitElementsAreVisible(productCardInfo.getLocator())
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Wait until product info is updated {productCardInfo}")
    public List<WebElement> waitProductsInfoAreUpdated(ProductCardInfo productCardInfo) {
        return waitElementsAreUpdated(productCardInfo.getLocator());
    }

    @Step("Type brand name '{brandName}' into search field")
    public ProductCatalogPage typeBrandNameInSearch(ManClothingPage.BrandName brandName , BrandFilter brandFilter) {
        WebElement searchInput = waitElementIsVisible(brandFilter.getLocator());
        searchInput.clear();
        searchInput.sendKeys(brandName.getValue());
        return this;
    }

    @Step("Select brand filter option: {brandName}")
    public ProductCatalogPage selectBrandOption(ManClothingPage.BrandName brandName , BrandFilter brandFilter) {
        By brandOptionLocator = By.xpath("//label[@class='refinement-label ']//span[text()='" + brandName.getValue() + "']");

        scrollToElement(brandOptionLocator);
        waitElementsAreUpdated(brandFilter.getLocator());
        waitElementToBeClickable(brandOptionLocator).click();
        return this;
    }

    @Step("Scroll to element {locatorProvider}")
    public ProductCatalogPage scrollToElement(LocatorProvider locatorProvider) {
        scrollToElement(locatorProvider.getLocator());
        return this;
    }

    @Step("Type price '{price}' into field {field}")
    public ProductCatalogPage typePriceInInput(PriceFilter field, String price) {
        WebElement priceInput = waitElementIsVisible(field.getLocator());

        String selectAll = System.getProperty("os.name").toLowerCase().contains("mac")
                ? Keys.chord(Keys.COMMAND, "a")
                : Keys.chord(Keys.CONTROL, "a");

        priceInput.sendKeys(selectAll);
        priceInput.sendKeys(Keys.DELETE);
        priceInput.sendKeys(price);
        priceInput.sendKeys(Keys.ENTER);

        return this;
    }

    @Step("Close Excellent pop up button")
    public ProductCatalogPage clickCloseTrustedShopPopup() {
        WebElement popupCloseBtn = waitElementIsVisible(By.xpath(EXCELLENT_POP_UP));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", popupCloseBtn);
        return this;
    }

    @Step("Click clear filter button")
    public ProductCatalogPage clickClearFilterButton(){
        waitElementToBeClickable(By.xpath(CLOSE_CLEAR_FILTER_BUTTON)).click();
        return this;
    }
}