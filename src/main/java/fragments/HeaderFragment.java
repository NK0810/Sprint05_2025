package fragments;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.HomePage;

public class HeaderFragment extends BasePage {
    private static final String WISHLIST_BUTTON = "//div[@class=\"wishlist__inner\"]";

    public HeaderFragment(WebDriver driver) {
        super(driver);
    }

    public HeaderFragment clickWishlistButton(){
        waitElementToBeClickable(By.xpath(WISHLIST_BUTTON)).click();
        return this;
    }
}
