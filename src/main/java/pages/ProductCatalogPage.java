package pages;

import fragments.SortFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ProductCatalogPage extends BasePage<ProductCatalogPage> {
    private static final String BRANDS_SEARCH_FIELD = "//div[@class='widget-container-brand']//input[@placeholder]";

    public interface LocatorProvider {
        By getLocator();
    }

    public enum FilterDropdown implements LocatorProvider {
        BRAND_DROPDOWN("//div[@class='widget-container-brand']//div[@class='ais-Panel-header']"),
        PRICE_DROPDOWN("//div[@class='widget-container-price_UAH_default']");

        private final By dropdown;

        FilterDropdown(String xpath) {
            this.dropdown = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return dropdown;
        }
    }

    public enum FilterOption implements LocatorProvider {
        NEW_ARRIVALS("(//li[@class='refinement-item refinement-item--is_new'])[1]/label"),
        SALE("(//li[@class='refinement-item refinement-item--is_discount'])[1]/label");

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

    protected SortFragment sortFragment;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        this.sortFragment = new SortFragment(driver);
    }

    @Step("Sorts products by {optionName}")
    public ProductCatalogPage sortByOption(SortFragment.SortOptions optionName) {
        sortFragment.clickDropdownButton()
                .selectOption(optionName);
        return this;
    }

    @Step("Select filter option {filterOption}")
    public ProductCatalogPage selectFilterOption(FilterOption filterOption) {
        waitElementToBeClickable(filterOption.getLocator()).click();
        return this;
    }

    @Step("Open filter dropdown {filterDropdown}")
    public ProductCatalogPage openFilterDropdown(FilterDropdown filterDropdown) {
        waitElementToBeClickable(filterDropdown.getLocator()).click();
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
    public ProductCatalogPage typeBrandNameInSearch(ManClothingPage.BrandName brandName) {
        WebElement searchInput = waitElementIsVisible(By.xpath(BRANDS_SEARCH_FIELD));
        searchInput.clear();
        searchInput.sendKeys(brandName.getValue());
        return this;
    }

    @Step("Select brand filter option: {brandName}")
    public ProductCatalogPage selectBrandOption(ManClothingPage.BrandName brandName) {
        By brandOptionLocator = By.xpath("//label[@class='refinement-label ']//span[text()='" + brandName.getValue() + "']");
        scrollToElement(brandOptionLocator);
        waitElementToBeClickable(brandOptionLocator).click();
        return this;
    }

    @Step("Scroll to element {locatorProvider}")
    public ProductCatalogPage scrollToElement(LocatorProvider locatorProvider) {
        scrollToElement(locatorProvider.getLocator());
        return this;
    }

}