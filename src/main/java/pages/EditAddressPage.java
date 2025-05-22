package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LocatorProvider;


public abstract class EditAddressPage extends BasePage<EditAddressPage> {

    public enum EditAddressPageElements implements LocatorProvider {
//        PRIVATE_PERSON_RADIO_BUTTON("//label[@for='invoice_private']"),
//        COMPANY_RADIO_BUTTON("//label[@for='invoice_company']"),
        NAME_INPUT_FIELD("//input[@name='firstname']"),
        SURNAME_INPUT_FIELD("//input[@name='lastname']"),
//        TAX_IDENTIFICATION_NUMBER_INPUT_FIELD("//input[@name='vat_id']"),
//        COMPANY_NAME_INPUT_FIELD("//input[@name='company']"),
        STREET_INPUT_FIELD("//input[@name='street[1]']"),
        HOUSE_NUMBER_INPUT_FIELD("//input[@name='street[2]']"),
        APARTMENT_NUMBER_INPUT_FIELD("//input[@name='street[3]']"),
        POST_CODE_INPUT_FIELD("//input[@name='postcode']"),
        CITY_INPUT_FIELD("//input[@name='city']"),
        PHONE_NUMBER_INPUT_FIELD("//input[@name='telephone']"),
        SAVE_ADDRESS_BUTTON("//button[@data-action='save-address']"),
//        USE_AS_DEFAULT_DELIVERY_ADDRESS_CHECKBOX("//label[@for='primary_shipping']"),
//        USE_AS_DEFAULT_PAYMENT_ADDRESS_CHECKBOX("//label[@for='primary_billing']"),
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

    @Step("Enter value '{value}' into address field: {element}")
    public EditAddressPage enterAddressInfo(EditAddressPageElements element, String value) {
        WebElement field = waitElementIsVisible(element.getLocator());
        field.clear();
        field.sendKeys(value);
        return this;
    }

    @Step("Click on element: {locatorProvider}")
    public EditAddressPage clickOnElement(LocatorProvider locatorProvider) {
        waitElementToBeClickable(locatorProvider.getLocator()).click();
        return this;
    }

    @Step("Scroll to element: {locatorProvider}")
    public EditAddressPage scrollToElement(LocatorProvider locatorProvider) {
        scrollToElement(locatorProvider.getLocator());
        return this;
    }

    @Step("Get text from element: {locatorProvider}")
    public String getElementText(LocatorProvider locatorProvider) {
        return waitElementIsVisible(locatorProvider.getLocator()).getText();
    }
}