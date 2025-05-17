package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class HeaderFragment extends BasePage {
    private static final String WISHLIST_BUTTON = "//div[@class='wishlist__inner']";
    private static final String USER_ICON_BUTTON = "//i[@class='tabler-icon-user-thin']";
    private static final String SIGN_IN_BUTTON = "//a[@title='Увійти']";
    private static final String MY_PROFILE_BUTTON = "//a[@title='Мій Обліковий запис']";
    private static final String HEADER_LOGO = "//a[@class='header__logo']";
    private static final String HEADER = "//div[@class='header__content header__container container']";

    public HeaderFragment(WebDriver driver) {
        super(driver);
    }

    @Step("Go to wishlist")
    public HeaderFragment clickWishlistButton() {
        waitElementToBeClickable(By.xpath(WISHLIST_BUTTON)).click();
        return this;
    }

    @Step("Open home page via header logo")
    public HeaderFragment clickOnHeaderLogo() {
        waitElementToBeClickable(By.xpath(HEADER_LOGO)).click();
        return this;
    }

    @Step("Open User sidebar to wishlist")
    public HeaderFragment clickUserIcon() {
        waitElementToBeClickable(By.xpath(USER_ICON_BUTTON)).click();
        return this;
    }

    @Step("Open Login page via sidebar")
    public HeaderFragment clickSignInButton() {
        waitElementToBeClickable(By.xpath(SIGN_IN_BUTTON)).click();
        return this;
    }

    @Step("Open Profile page")
    public HeaderFragment clickMyProfileButton() {
        waitElementToBeClickable(By.xpath(MY_PROFILE_BUTTON)).click();
        return this;
    }

    @Step("Scroll to header fragment")
    public HeaderFragment scrollToHeader() {
        scrollToElement(By.xpath(HEADER));
        return this;
    }
}