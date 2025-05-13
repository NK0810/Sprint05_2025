package fragments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class SortFragment extends BasePage {
    private static final String DROPDOWN_BUTTON = "//span[@class=\"button-desktop\"]";

    public enum SortOptions {
        RECOMMENDED("ua_products"),
        LOWEST_PRICE("ua_products_price_default_asc"),
        HIGHEST_PRICE("ua_products_price_default_desc"),
        NEWEST("ua_products_czas_wejscia_na_magazyn_desc"),
        MAX_DISCOUNT("ua_products_discount_percent_desc");

        private String dataValue;

        SortOptions(String dataValue) {
            this.dataValue = dataValue;
        }

        public String getDataValue() {
            return dataValue;
        }
    }

    public SortFragment(WebDriver driver) {
        super(driver);
    }

    public SortFragment clickDropdownButton(){
        waitElementToBeClickable(By.xpath(DROPDOWN_BUTTON)).click();
        return this;
    }

    public SortFragment selectOption(SortOptions option) {
        String xpath = "//li[@data-value='" + option.getDataValue() + "']";
        waitElementToBeClickable(By.xpath(xpath)).click();
        return this;
    }
}

