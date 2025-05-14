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

    public enum FilterOption {
        NEW_ARRIVALS("(//li[@class='refinement-item refinement-item--is_new'])[1]"),
        SALE("(//li[@class='refinement-item refinement-item--is_discount'])[1]"),
        BRAND("//div[@class='widget-container-brand']");

        private final String filter;

        FilterOption(String filter) {
            this.filter = filter;
        }

        public String getFilter() {
            return filter;
        }
    }

    public enum ProductCardInfo{
        ACTUAL_PRICE("//div[@class='c-price__current']"),
        NEW_TAG("//span[@class='product-card__badge product-card__badge--new']"),
        REGULAR_DISCOUNT("//span[contains(text(), 'Звичайна ціна')]"),
        PRODUCT_NAME("//a[@class='product-card__name']");

        private final String info;

        ProductCardInfo(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
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

    public ProductCatalogPage selectFilterOption(String filterOption) {
        waitElementToBeClickable(By.xpath(filterOption)).click();
        return this;
    }

    @Step("Type brand name '{brandName}' into search field")
    public ProductCatalogPage typeBrandNameInSearch(String brandName) {
        WebElement searchInput = waitElementIsVisible(By.xpath(BRANDS_SEARCH_FIELD));
        searchInput.clear();
        searchInput.sendKeys(brandName);
        return this;
    }

    @Step("Open filter dropdown")
    public ProductCatalogPage openFilterDropdown(String filter) {
        waitElementToBeClickable(By.xpath(filter)).click();
        return this;
    }

    @Step("Get visible product prices as text")
    public List<String> getVisibleProductsInfoTexts(String info) {
        return waitElementsAreVisible(By.xpath(info))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }


    @Step("Waits util new tag are updated")
    public List<WebElement> waitProductsInfoAreUpdated(String info){
        return waitElementsAreUpdated(By.xpath(info));
    }
}
