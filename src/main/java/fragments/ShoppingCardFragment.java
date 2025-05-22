package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class ShoppingCardFragment extends BasePage {
    public ShoppingCardFragment(WebDriver driver) {
        super(driver);
    }

    public enum ShoppingCardElements {
        PRODUCT_NAME_IN_CARD_PREVIEW("//a[@data-bind='attr: {href: product_url}, html: product_name']"),
        PRODUCT_SIZE_IN_POP_UP_REVIEW_SHOPPING_CARD("//span[@data-bind='text: option.value']"),
        CONFIRM_ADD_TO_CART_BUTTON("//a[@class='block--minicart__button button button__addtocart button--jumbo viewcart']");

        private String locator;

        ShoppingCardElements(String locator) {
            this.locator = locator;
        }

        public By getLocator() {
            return By.xpath(locator);
        }
    }

    @Step("Get text: {locator}")
    public String getTextFrom(ShoppingCardElements locator) {
        return waitElementIsVisible(locator.getLocator()).getText();
    }

    @Step("Click on the button: {locator}")
    public ShoppingCardFragment clickOnTheButton(ShoppingCardElements locator) {
        waitElementIsVisible(locator.getLocator());
        waitElementToBeClickable(locator.getLocator()).click();
        return this;
    }

    @Step("Select size {size}")
    public ShoppingCardFragment selectSize(String size) {
        waitElementToBeClickable(By.xpath("//li[@data-title='" + size + "']")).click();
        return this;
    }
}
