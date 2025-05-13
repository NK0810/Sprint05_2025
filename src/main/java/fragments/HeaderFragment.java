package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class HeaderFragment extends BasePage {
    private static final String WISHLIST_BUTTON = "//div[@class='wishlist__inner']";
    private static final String HEADER = "//div[@class='header__content header__container container']";

    public HeaderFragment(WebDriver driver) {
        super(driver);
    }

    @Step("Go to wishlist")
    public HeaderFragment clickWishlistButton() {
        waitElementToBeClickable(By.xpath(WISHLIST_BUTTON)).click();
        return this;
    }

    @Step("Scroll to header fragment")
    public HeaderFragment scrollToHeader() {
        scrollToElement(By.xpath(HEADER));
        return this;
    }
}