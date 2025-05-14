package pages;

import fragments.SortFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public abstract class BaseProductPage extends BasePage<BaseProductPage> {
    protected SortFragment sortFragment;

    public BaseProductPage(WebDriver driver) {
        super(driver);
        this.sortFragment = new SortFragment(driver);
    }

    @Step("Sorts products by lowest price")
    public BaseProductPage clickSortByLowestPriceButton() {
        sortFragment.clickDropdownButton()
                .selectOption(SortFragment.SortOptions.LOWEST_PRICE);
        return this;
    }

    @Step("Sorts products by highest price")
    public BaseProductPage clickSortByHighestPriceButton() {
        sortFragment.clickDropdownButton()
                .selectOption(SortFragment.SortOptions.HIGHEST_PRICE);
        return this;
    }
}
