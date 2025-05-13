package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WishlistPage extends BasePage<WishlistPage> {
    private static final String DELETE_FROM_WISHLIST_BUTTON = "//i[@class='icon-trash-2--before']";
    private static final String FAVORITE_PRODUCT_CARDS = "//a[@class='product-card__image-link']";
    private static final String MASSAGE_WISHLIST_IS_EMPTY = "//div[@class='message info empty']";

    private static final String WISH_LIST_URL = BASE_URL + "/zhinka";

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    public WishlistPage openWishListPage() {
        driver.get(WISH_LIST_URL);
        return this;
    }

    @Step("Delete product from wishlist")
    public WishlistPage clickDeleteFromWishlistButton() {
        waitElementToBeClickable(By.xpath(DELETE_FROM_WISHLIST_BUTTON)).click();
        return this;
    }

    public boolean isProductPresentInWishlist() {
        try {
            WebElement emptyMessage = waitElementIsVisible(By.xpath(FAVORITE_PRODUCT_CARDS));
            return emptyMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isWishlistEmptyMessageDisplayed() {
        try {
            WebElement emptyMessage = waitElementIsVisible(By.xpath(MASSAGE_WISHLIST_IS_EMPTY));
            return emptyMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
