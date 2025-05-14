package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingCardPage extends BasePage<ShoppingCardPage> {
    public enum ProductInfo {
        PRODUCT_NAME("//div[@class='cart-container__name']//a"),
        PRODUCT_PRICE("//span[@class='price-excluding-tax']"),
        PRODUCT_SIZE("//div[@class='item-options__attribute']"),
        PRODUCT_PRICE_UPDATE("//td[@data-th='Кошик']"),
        CART_EMPTY_TEXT("//h2[@class='empty-cart__heading']");

        private final String locator;

        ProductInfo(String locator) {
            this.locator = locator;
        }

        public By getBy() {
            return By.xpath(locator);
        }
    }

    private static final String ADD_ONE_PRODUCT_BUTTON = "//button[@data-action='button-qty-inc']";
    private static final String DELETE_PRODUCT_FROM_SHOPPING_CARD_BURRON = "//a[@class='action action-delete']";
    private static final String LIST_PRICES = "//span[@class='price']";
    private static final String ACCEPT_TO_DELETE_PRODUCT_FROM_SHOPPING_CARD = "//button[@class='action-primary action-accept']";

    public ShoppingCardPage(WebDriver driver) {
        super(driver);
    }


    @Step("Get text")
    public String getTextFromLocator(ProductInfo locator) {
        return waitElementIsVisible(locator.getBy()).getText();
    }

    @Step("Wait for product update and get text")
    public String waitForProductInfoAndGetText(ProductInfo locator) {
        waitElementsAreUpdated(By.xpath(LIST_PRICES));
        return waitElementIsVisible(locator.getBy()).getText();
    }

    @Step("Get product size and clean text")
    public String getCleanProductSizeText(ProductInfo locator) {
        return getText(locator.getBy()).replace("Розмір:", "").trim();
    }

    @Step("Add 1 product in shopping card page")
    public ShoppingCardPage clickOnTheAddOneProductButton() {
        waitElementToBeClickable(By.xpath(ADD_ONE_PRODUCT_BUTTON)).click();
        return this;
    }

    @Step("Click on the delete from shopping card button")
    public ShoppingCardPage clickOnTheDeleteFromShoppingCardButton() {
        waitElementToBeClickable(By.xpath(DELETE_PRODUCT_FROM_SHOPPING_CARD_BURRON)).click();
        return this;
    }

    @Step("Accept delete product from shopping card")
    public ShoppingCardPage clickAcceptToDeleteProductFromShoppingCardButton(){
        waitElementToBeClickable(By.xpath(ACCEPT_TO_DELETE_PRODUCT_FROM_SHOPPING_CARD)).click();
        return this;
    }
}
