package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LocatorProvider;

import java.util.Map;

import static pages.UserAddressesPage.EditAddressPageElements.*;

public class UserAddressesPage extends BasePage<UserAddressesPage> {

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


    public enum UserAddressesPageElements implements LocatorProvider {
        DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        DEFAULT_PAYMENT_ADDRESS_INFO_BLOCK("Платіжна адреса", "address"),
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        EDIT_DEFAULT_PAYMENT_ADDRESS_BUTTON("Платіжна адреса", "edit"),
        ADDRESS_SAVED_MESSAGE_TEXT("//div[@data-ui-id='message-success']/div"),
        ADD_PAYMENT_ADDRESS_BUTTON("Інші платіжні адреси", "add"),
        CLOSE_MESSAGE_BUTTON("//span[@data-role='message-close']"),
        OTHER_PAYMENT_ADDRESS_INFO_BLOCK_1("Інші платіжні адреси", "address", 1),
        DELETE_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_1("Інші платіжні адреси", "delete", 1),
        DELETE_ADDRESS_BUTTON_IN_POP_UP("//button[@class='button__primary button--regular button--delete']");

        private final By element;

        UserAddressesPageElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        UserAddressesPageElements(String sectionTitle, String type) {
            this.element = By.xpath(buildXpath(sectionTitle, type));
        }

        UserAddressesPageElements(String sectionTitle, String type, int index) {
            this.element = By.xpath(buildXpathWithIndex(sectionTitle, type, index));
        }

        @Override
        public By getLocator() {
            return element;
        }
    }

    public UserAddressesPage(WebDriver driver) {
        super(driver);
    }

    @Step("get suffix for type: {type}")
    private static String getSuffixByType(String type) {
        return switch (type) {
            case "address" -> "//address";
            case "edit" -> "//a[@class='action edit dashboard-info-block__link']";
            case "delete" -> "//a[@class='action delete dashboard-info-block__link']";
            case "add" -> "//a[@class='address-new']";
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    @Step("Build XPath for section: '{sectionTitle}' and type: '{type}'")
    private static String buildXpath(String sectionTitle, String type) {
        return String.format(
                "//span[text()='%s']/ancestor::div[@class='dashboard-info-block__info']%s",
                sectionTitle, getSuffixByType(type)
        );
    }

    @Step("Build XPath with index for section: '{sectionTitle}', type: '{type}', index: {index}")
    private static String buildXpathWithIndex(String sectionTitle, String type, int index) {
        return String.format("(%s)[%d]", buildXpath(sectionTitle, type), index);
    }

    @Step("Enter value '{value}' into field: {element}")
    public UserAddressesPage enterInfo(LocatorProvider locatorProvider, String value) {
        WebElement field = waitElementIsVisible(locatorProvider.getLocator());
        field.clear();
        field.sendKeys(value);
        return this;
    }

    @Step("Click on element: {locatorProvider}")
    public UserAddressesPage clickOnElement(LocatorProvider locatorProvider) {
        waitElementToBeClickable(locatorProvider.getLocator()).click();
        return this;
    }

    @Step("Scroll to element: {locatorProvider}")
    public UserAddressesPage scrollToElement(LocatorProvider locatorProvider) {
        scrollToElement(locatorProvider.getLocator());
        return this;
    }

    @Step("Get text from element: {locatorProvider}")
    public String getElementText(LocatorProvider locatorProvider) {
        return waitElementIsVisible(locatorProvider.getLocator()).getText();
    }

    @Step("Enter address fields from map and save")
    public UserAddressesPage enterAddressInfo(Map<EditAddressPageElements, String> addressData) {
        for (Map.Entry<EditAddressPageElements, String> entry : addressData.entrySet()) {
            String value = entry.getValue();
            if (value != null && !value.isBlank()) {
                enterInfo(entry.getKey(), value);
            }
        }
        return clickOnElement(SAVE_ADDRESS_BUTTON);
    }

    public static Map<EditAddressPageElements, String> deliveryAddressRequieredFields(
            String name, String surname, String street, String houseNumber, String postCode, String city, String phoneNumber) {
        return Map.of(
                NAME_INPUT_FIELD, name,
                SURNAME_INPUT_FIELD, surname,
                STREET_INPUT_FIELD, street,
                HOUSE_NUMBER_INPUT_FIELD, houseNumber,
                POST_CODE_INPUT_FIELD, postCode,
                CITY_INPUT_FIELD, city,
                PHONE_NUMBER_INPUT_FIELD, phoneNumber
        );
    }

    public static Map<EditAddressPageElements, String> paymentAddress(
            String name, String surname, String street, String houseNumber, String apartmentNumber, String postCode, String city) {
        return Map.of(
                NAME_INPUT_FIELD, name,
                SURNAME_INPUT_FIELD, surname,
                STREET_INPUT_FIELD, street,
                HOUSE_NUMBER_INPUT_FIELD, houseNumber,
                APARTMENT_NUMBER_INPUT_FIELD, apartmentNumber,
                POST_CODE_INPUT_FIELD, postCode,
                CITY_INPUT_FIELD, city
        );
    }
}