package pages;

import fragments.SortFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class ProductCatalogPage extends BasePage<ProductCatalogPage>{
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
}
