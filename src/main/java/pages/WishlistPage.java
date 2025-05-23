package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WishlistPage extends BasePage<WishlistPage> {
    private static final String DELETE_FROM_WISHLIST_BUTTON = "//i[@class='icon-trash-2--before']";

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    public enum WishlistElements {
        MESSAGE_WISHLIST_IS_EMPTY_PROFILE("//div[@class='message info empty with-button']/span"),
        MESSAGE_PRODUCT_REMOVED_FROM_WISHLIST("//div[@data-ui-id]/div"),
        MESSAGE_WISHLIST_IS_EMPTY("//div[@class='message info empty']"),
        WISHLIST_PRODUCT_NAME ("//div[@class='product-card__content']/a"),
        WISHLIST_PRODUCT_CURRENT_PRICE ("//span[@data-price-type='finalPrice']//span"),
        WISHLIST_PRODUCT_REGULAR_PRICE ("//span[@class='c-price__omnibus']/span"),
        WISHLIST_FORM ("//form[@class='form-wishlist-items']"),
        WISHLIST_PRODUCTS ("//li[contains(@class, 'products-grid__list-item')]"),
        FAVORITE_PRODUCT_CARDS ("//a[@class='product-card__image-link']");
        private final By element;

        WishlistElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        public By getLocator() {
            return element;
        }
    }

    @Step("Scroll to wishlist element: {element}")
    public WishlistPage scrollToElement(WishlistElements elements) {
        scrollToElement(elements.getLocator());
        return this;
    }

    @Step("Get wishlist element info: {elements}")
    public String getWishlistElementInfo(WishlistElements elements) {
        return waitElementIsVisible(elements.getLocator()).getText();
    }

    @Step("Delete product from wishlist")
    public WishlistPage clickDeleteFromWishlistButton() {
        waitElementToBeClickable(By.xpath(DELETE_FROM_WISHLIST_BUTTON)).click();
        return this;
    }
}
