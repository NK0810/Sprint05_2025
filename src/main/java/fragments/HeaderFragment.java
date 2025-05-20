package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import pages.ProductCatalogPage;

public class HeaderFragment extends BasePage {
    private static final String WISHLIST_BUTTON = "//div[@class='wishlist__inner']";
    private static final String HEADER = "//div[@class='header__content header__container container']";

    public HeaderFragment(WebDriver driver) {
        super(driver);
    }

    public enum HeaderElements {
        USER_ICON_BUTTON ("//i[@class='tabler-icon-user-thin']"),
        MY_PROFILE_BUTTON ("//a[@title='Мій Обліковий запис']"),
        HEADER_LOGO ("//a[@class='header__logo']");

        private final By element;

        HeaderElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        public By getLocator() {
            return element;
        }
    }

    @Step("Go to wishlist")
    public HeaderFragment clickWishlistButton() {
        waitElementToBeClickable(By.xpath(WISHLIST_BUTTON)).click();
        return this;
    }

    @Step("Click on element: {element}")
    public HeaderFragment clickHeaderElement(HeaderElements element) {
        waitElementToBeClickable(element.getLocator()).click();
        return this;
    }


    @Step("Scroll to header fragment")
    public HeaderFragment scrollToHeader() {
        scrollToElement(By.xpath(HEADER));
        return this;
    }
}