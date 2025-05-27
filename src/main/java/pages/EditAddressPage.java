package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LocatorProvider;


public abstract class EditAddressPage extends BasePage<EditAddressPage> {

    public enum EditAddressPageElements implements LocatorProvider {
        NAME_INPUT_FIELD("//input[@name='firstname']"),
        SURNAME_INPUT_FIELD("//input[@name='lastname']"),
        STREET_INPUT_FIELD("//input[@name='street[1]']"),
        HOUSE_NUMBER_INPUT_FIELD("//input[@name='street[2]']"),
        APARTMENT_NUMBER_INPUT_FIELD("//input[@name='street[3]']"),
        POST_CODE_INPUT_FIELD("//input[@name='postcode']"),
        CITY_INPUT_FIELD("//input[@name='city']"),
        PHONE_NUMBER_INPUT_FIELD("//input[@name='telephone']"),
        SAVE_ADDRESS_BUTTON("//button[@data-action='save-address']"),
        DEFAULT_ADDRESS_MESSAGE("//div[@class='message info']/span");

        private final By element;

        EditAddressPageElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return element;
        }
    }

    public EditAddressPage(WebDriver driver) {
        super(driver);
    }
}