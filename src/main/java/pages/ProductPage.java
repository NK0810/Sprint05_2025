package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage<ProductPage> {
    public enum ProductInfo {
        NAME_PRODUCT("//span[@class='base']"),
        PRODUCT_NAME_IN_CARD_PREVIEW("//a[@data-bind='attr: {href: product_url}, html: product_name']"),
        PRODUCT_SIZE_IN_POP_UP_REVIEW_SHOPPING_CARD("//span[@data-bind='text: option.value']");

        private final String locator;

        ProductInfo(String locator) {
            this.locator = locator;
        }

        public By getBy() {
            return By.xpath(locator);
        }
    }

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private static final String ADD_TO_SHOPPING_CARD_BUTTON = "//button[@class='button button__addtocart button--jumbo tocart action product__add-to-cart red']";
    private static final String SELECT_SIZE_BUTTON = "(//li[@class='visual-ko-select__option '])[2]";
    private static final String ADD_TO_SHOPPING_CARD_IN_POP_UP_BUTTON = "//a[@class='block--minicart__button button button__addtocart button--jumbo viewcart']";

    @Step("Get text")
    public String getTextFromLocator(ProductInfo locator) {
        return getText(locator.getBy());
    }

    @Step("Click to add to shopping card button")
    public ProductPage clickOnAddToShoppingCardButton() {
        waitElementToBeClickable(By.xpath(ADD_TO_SHOPPING_CARD_BUTTON)).click();
        return this;
    }

    @Step("Click on the first size button")
    public ProductPage clickOnSelectedSizeButton() {
        waitElementToBeClickable(By.xpath(SELECT_SIZE_BUTTON)).click();
        return this;
    }

    @Step("Click on te add to shopping card in pop up")
    public ProductPage clickOnTheAddToShoppingCardButtonInPopUp() {
        waitElementToBeClickable(By.xpath(ADD_TO_SHOPPING_CARD_IN_POP_UP_BUTTON)).click();
        return this;
    }
}
