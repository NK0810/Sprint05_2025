package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class SortFragment extends BasePage {
    private static final String DROPDOWN_BUTTON = "//span[@class='button-desktop']";

    public enum SortOptions {
        RECOMMENDED("ua_products"),
        LOWEST_PRICE("ua_products_price_default_asc"),
        HIGHEST_PRICE("ua_products_price_default_desc"),
        NEWEST("ua_products_czas_wejscia_na_magazyn_desc"),
        MAX_DISCOUNT("ua_products_discount_percent_desc");

        private String dataValue;
        private final By locator;

        SortOptions(String dataValue) {
            this.dataValue = dataValue;
            this.locator = By.xpath("//li[@data-value='" + dataValue + "']");
        }

        public By getLocator() {
            return locator;
        }

        public String getDataValue() {
            return dataValue;
        }
    }

    public SortFragment(WebDriver driver) {
        super(driver);
    }

    @Step("Click sort dropdown button")
    public SortFragment clickDropdownButton(){
        waitElementToBeClickable(By.xpath(DROPDOWN_BUTTON)).click();
        return this;
    }

    @Step("Select sort option: {option}")
    public SortFragment selectOption(SortOptions option) {
        waitElementToBeClickable(option.getLocator()).click();
        return this;
    }
}

