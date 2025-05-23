package pages;

import fragments.ShoppingCardFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage<ProductPage> {
    private final ShoppingCardFragment shopingCardFragment;

    public enum ProductPageElements {
        PRODUCT_NAME("//span[@class='base']"),
        ADD_TO_SHOPPING_CART_BUTTON("//button[@class='button button__addtocart button--jumbo tocart action product__add-to-cart red']"),
        PRODUCT_PARAMETERS("//*[@id='tab-label-additional_attributes-title']"),
        PRODUCT_CODE("//*[text()='Код']/following-sibling::*"),
        PRODUCT_BRAND("//*[text()='Бренд']/following-sibling::*"),
        BACK_ON_HOME_PAGE("//*[contains(@class,'home')]/*"),
        SELECT_SIZE_DROP_DOWN_BUTTON("//div[@class='visual-ko-select visual-ko-select--rozmiar ']"),
        PRODUCT_SEASON_PARAMETER("(//div[@class='c-product-attribute'])[4]/span[@class='c-product-attribute__value']"),
        REGULAR_PRICE("//form//*[@Class='c-price__omnibus']"),
        CLOSE_SELECT_SIZE_BUTTON("//p[@class='visual-ko-select__list-options-wrapper__header heading6']/following-sibling::i"),
        CURRENT_PRICE("//form//*[@data-price-type='finalPrice']/span"),
        ADD_TO_WISHLIST_BUTTON("//a[@class='action action-to-wishlist add']"),
        SUCCESS_MASSAGE("//div[@data-ui-id='message-success']");

        private final String locator;

        ProductPageElements(String locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return By.xpath(locator);
        }
    }

    public ShoppingCardFragment getShoppingCardFragment() {
        return shopingCardFragment;
    }

    public ProductPage(WebDriver driver) {
        super(driver);
        this.shopingCardFragment = new ShoppingCardFragment(driver);
    }

    @Step("Get text: {locator}")
    public String getTextFrom(ProductPageElements locator) {
        return waitElementIsVisible(locator.getLocator()).getText();
    }

    @Step("Click on the button: {locator}")
    public ProductPage clickOnTheButton(ProductPageElements locator) {
        waitElementIsVisible(locator.getLocator());
        waitElementToBeClickable(locator.getLocator()).click();
        return this;
    }

    @Step("Scroll to element {locator}")
    public ProductPage scrollToElement(ProductPageElements locator) {
        scrollToElement(locator.getLocator());
        return this;
    }

    @Step("Get class attribute for size option '{size}'")
    public String getSizeOptionClass(String size) {
        return getClassValueFromElement(By.xpath("//li[@data-title='" + size + "']"));
    }
}
