package pages;

import fragments.SortFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class ProductCatalogPage extends BasePage<ProductCatalogPage> {
    public enum FilterOption {
        NEW_ARRIVALS("new"),
        SALE("discount");

        private String dataValue;
        private final String filterList;

        FilterOption(String dataValue) {
            this.dataValue = dataValue;
            this.filterList = "//div[@class='ais-Panel']//li[@class='refinement-item refinement-item--is_" + dataValue + "']";
        }

        public String getFilterList() {
            return filterList;
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

    @Step("Select filter option: {option}")
    public ProductCatalogPage selectFilterOption(FilterOption option) {
        waitElementToBeClickable(By.xpath(option.getFilterList())).click();
        return this;
    }

    @Step("Filter products by {optionName}")
    public ProductCatalogPage filterByOption(FilterOption optionName) {
        selectFilterOption(optionName);
        return this;
    }
}
