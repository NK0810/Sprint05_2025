package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WishlistPage extends BasePage{
    private static final String DELETE_FROM_WISHLIST_BUTTON = "//i[@class='icon-trash-2--before']";
    private static final String FAVORITE_PRODUCT_CARD = "//a[@class='product-card__image-link']";
    private static final String MASSAGE_WISHLIST_IS_EMPTY = "//div[@class='message info empty']";

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    public WishlistPage clickDeleteFromWishlistButton(){
        waitElementToBeClickable(By.xpath(DELETE_FROM_WISHLIST_BUTTON)).click();
        return this;
    }

    public boolean isProductPresentInWishlist(){
        try {
            WebElement emptyMessage = waitElementIsVisible(By.xpath(FAVORITE_PRODUCT_CARD));
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
