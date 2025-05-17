package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class WishlistPage extends BasePage<WishlistPage> {
    private static final String DELETE_FROM_WISHLIST_BUTTON = "//i[@class='icon-trash-2--before']";
    private static final String FAVORITE_PRODUCT_CARDS = "//a[@class='product-card__image-link']";
    private static final String MASSAGE_WISHLIST_IS_EMPTY = "//div[@class='message info empty']";
    private static final String MASSAGE_WISHLIST_IS_EMPTY_PROFILE = "//div[@class='message info empty with-button']/span";
    private static final String MASSAGE_PRODUCT_REMOVED_FROM_WISHLIST = "//div[@data-ui-id]/div";
    private static final String WISHLIST_PRODUCT_NAME = "//div[@class='product-card__content']/a";
    private static final String WISHLIST = "//form[@class='form-wishlist-items']";
    private static final String WISHLIST_PRODUCTS = "//li[contains(@class, 'products-grid__list-item')]";

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get removed product massage text")
    public String getRemoveMassageText() {
        return waitElementIsVisible(By.xpath(MASSAGE_PRODUCT_REMOVED_FROM_WISHLIST)).getText();
    }

    @Step("Get empty wishlist massage text")
    public String getEmptyListMassageText() {
        return waitElementIsVisible(By.xpath(MASSAGE_WISHLIST_IS_EMPTY_PROFILE)).getText();
    }

    @Step("Get product name in whishlist")
    public String getProductName() {
        return waitElementIsVisible(By.xpath(WISHLIST_PRODUCT_NAME)).getText();
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

    public boolean isWishlistEmptyMessageProfileDisplayed() {
        try {
            WebElement emptyMessage = waitElementIsVisible(By.xpath(MASSAGE_WISHLIST_IS_EMPTY_PROFILE));
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

    public boolean isProductRemovedMassageAppears() {
        try {
            WebElement emptyMessage = waitElementIsVisible(By.xpath(MASSAGE_PRODUCT_REMOVED_FROM_WISHLIST));
            return emptyMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check that wishlist form contains no product items")
    public boolean isWishlistFormEmpty() {
        try {
            WebElement form = driver.findElement(By.xpath(WISHLIST));
            List<WebElement> productItems = form.findElements(By.xpath(WISHLIST_PRODUCTS));
            return productItems.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}
