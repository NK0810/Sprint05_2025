package fragments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class SortFragment extends BasePage {
    private static final String DROPDOWN_BUTTON = "//span[@class=\"button-desktop\"]";


    public SortFragment(WebDriver driver) {
        super(driver);
    }

    public SortFragment clickDropdownButton(){
        waitElementToBeClickable(By.xpath(DROPDOWN_BUTTON)).click();
        return this;
    }

    public void selectOption(SortOptions option) {
        String xpath = "//li[@data-value='" + option.getDataValue() + "']";
        waitElementToBeClickable(By.xpath(xpath)).click();
    }
}

