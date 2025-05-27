package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.LocatorProvider;

import static pages.EditAddressPage.EditAddressPageElements.*;

public class UserAddressesPage extends EditAddressPage {

    public UserAddressesPage(WebDriver driver) {
        super(driver);
    }

    public enum UserAddressesPageElements implements LocatorProvider {
        DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
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
    public UserAddressesPage enterInfo(EditAddressPageElements element, String value) {
        WebElement field = waitElementIsVisible(element.getLocator());
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

    @Step("Enter only required fields for delivery address and save")
    public UserAddressesPage enterDeliveryAddressOnlyRequiredFields(
            String name, String surname, String street, String houseNumber, String postCode, String city, String phoneNumber) {
        return this.enterInfo(EditAddressPageElements.NAME_INPUT_FIELD, name)
                .enterInfo(EditAddressPageElements.SURNAME_INPUT_FIELD, surname)
                .enterInfo(EditAddressPageElements.STREET_INPUT_FIELD, street)
                .enterInfo(EditAddressPageElements.HOUSE_NUMBER_INPUT_FIELD, houseNumber)
                .enterInfo(EditAddressPageElements.POST_CODE_INPUT_FIELD, postCode)
                .enterInfo(EditAddressPageElements.CITY_INPUT_FIELD, city)
                .enterInfo(EditAddressPageElements.PHONE_NUMBER_INPUT_FIELD, phoneNumber)
                .clickOnElement(EditAddressPageElements.SAVE_ADDRESS_BUTTON);
    }
    @Step("Enter only required fields for delivery address and save")
    public UserAddressesPage enterPaymentAddress(
            String name, String surname, String street, String houseNumber, String apartmentNumber, String postCode, String city) {
        return this.enterInfo(NAME_INPUT_FIELD, name)
                .enterInfo(SURNAME_INPUT_FIELD, surname)
                .enterInfo(STREET_INPUT_FIELD, street)
                .enterInfo(HOUSE_NUMBER_INPUT_FIELD, houseNumber)
                .enterInfo(APARTMENT_NUMBER_INPUT_FIELD, apartmentNumber)
                .enterInfo(POST_CODE_INPUT_FIELD, postCode)
                .enterInfo(CITY_INPUT_FIELD, city)
                .clickOnElement(SAVE_ADDRESS_BUTTON);
    }
}