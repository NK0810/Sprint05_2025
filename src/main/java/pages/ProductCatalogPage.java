package pages;

import fragments.SortFragment;
import fragments.SortOptions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class ProductCatalogPage extends BasePage<ProductCatalogPage>{
    protected SortFragment sortFragment;

    public ProductCatalogPage(WebDriver driver) {
        super(driver);
        this.sortFragment = new SortFragment(driver);
    }

    @Step("Sorts products by lowest price")
    public ProductCatalogPage clickSortByLowestPriceButton() {
        sortFragment.clickDropdownButton()
                .selectOption(SortOptions.LOWEST_PRICE);
        return this;
    }

    @Step("Sorts products by highest price")
    public ProductCatalogPage clickSortByHighestPriceButton(){
        sortFragment.clickDropdownButton()
                .selectOption(SortOptions.HIGHEST_PRICE);
        return this;
    }
}
