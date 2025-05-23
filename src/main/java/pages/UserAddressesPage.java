package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorProvider;

public class UserAddressesPage extends EditAddressPage {

    public UserAddressesPage(WebDriver driver) {
        super(driver);
    }

    public enum UserAddressesPageElements implements LocatorProvider {
        DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        DEFAULT_PAYMENT_ADDRESS_INFO_BLOCK("Платіжна адреса", "address"),
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        EDIT_DEFAULT_PAYMENT_ADDRESS_BUTTON("Платіжна адреса", "edit"),
        ADDRESS_SAVED_MESSAGE_TEXT("//div[@data-ui-id='message-success']/div"),
        ADD_PAYMENT_ADDRESS_BUTTON("Інші платіжні адреси", "add-payment"),
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
            case "add-payment" -> "//a[@class='address-new' and span[text()='Додайте адресу рахунку-фактури']]";
            case "add-delivery" -> "//a[@class='address-new' and span[text()='Додати адресу доставки']]";
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
}