package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage<ProductPage> {
    public enum ProductPageElements {
        PRODUCT_NAME("//span[@class='base']"),
        PRODUCT_NAME_IN_CARD_PREVIEW("//a[@data-bind='attr: {href: product_url}, html: product_name']"),
        PRODUCT_SIZE_IN_POP_UP_REVIEW_SHOPPING_CARD("//span[@data-bind='text: option.value']"),
        ADD_TO_SHOPPING_CART_BUTTON("//button[@class='button button__addtocart button--jumbo tocart action product__add-to-cart red']"),
        SELECT_SIZE_BUTTON("(//li[@class='visual-ko-select__option '])[2]"),
        CONFIRM_ADD_TO_CART_BUTTON("//a[@class='block--minicart__button button button__addtocart button--jumbo viewcart']"),
        PRODUCT_PARAMETERS("//*[@id='tab-label-additional_attributes-title']"),
        PRODUCT_CODE("//*[text()='Код']/following-sibling::*"),
        PRODUCT_BRAND("//*[text()='Бренд']/following-sibling::*"),
        BACK_ON_HOME_PAGE("//*[contains(@class,'home')]/*"),
        SELECT_SIZE_DROP_DOWN_BUTTON("//div[@class='visual-ko-select visual-ko-select--rozmiar ']");

        REGULAR_PRICE_FIRST_PRODUCT("//span[@class='c-price__omnibus']/span"),
        CURRENT_PRICE_FIRST_PRODUCT("(//span[@class='price'])[2]");

        private final String locator;

        ProductPageElements(String locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return By.xpath(locator);
        }
    }

    public ProductPage(WebDriver driver) {
        super(driver);
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
