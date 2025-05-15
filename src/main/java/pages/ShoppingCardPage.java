package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingCardPage extends BasePage<ShoppingCardPage> {
    public enum ShoppingCartElements {
        PRODUCT_NAME("//div[@class='cart-container__name']//a"),
        PRODUCT_PRICE("//span[@class='price-excluding-tax']"),
        PRODUCT_SIZE("//div[@class='item-options__attribute']"),
        PRODUCT_PRICE_UPDATE("//td[@data-th='Кошик']"),
        CART_EMPTY_TEXT("//h2[@class='empty-cart__heading']"),
        ADD_ONE_PRODUCT_BUTTON("//button[@data-action='button-qty-inc']"),
        DELETE_PRODUCT_FROM_SHOPPING_CARD_BUTTON("//a[@class='action action-delete']"),
        ACCEPT_TO_DELETE_PRODUCT_FROM_SHOPPING_CARD("//button[@class='action-primary action-accept']");

        private final String locator;

        ShoppingCartElements(String locator) {
            this.locator = locator;
        }

        public By getBy() {
            return By.xpath(locator);
        }
    }

    private static final String LIST_PRICES = "//span[@class='price']";

    public ShoppingCardPage(WebDriver driver) {
        super(driver);
    }


    @Step("Get text: {locator}")
    public String getTextFromLocator(ShoppingCartElements locator) {
        return waitElementIsVisible(locator.getBy()).getText();
    }

    @Step("Wait for product update and get text: {locator}")
    public String waitForProductInfoAndGetText(ShoppingCartElements locator) {
        waitElementsAreUpdated(By.xpath(LIST_PRICES));
        return waitElementIsVisible(locator.getBy()).getText();
    }

    @Step("Get product size and clean text: {locator}")
    public String getCleanProductSizeText(ShoppingCartElements locator) {
        return waitElementIsVisible(locator.getBy()).getText().replace("Розмір:", "").trim();
    }

    @Step("Click on the button: {locator}")
    public ShoppingCardPage clickOnTheButton(ShoppingCartElements locator){
        waitElementToBeClickable(locator.getBy()).click();
        return this;
    }
}
