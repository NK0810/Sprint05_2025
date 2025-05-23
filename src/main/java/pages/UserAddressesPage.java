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
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        ADDRESS_SAVED_MASSAGE_TEXT("//div[@data-ui-id='message-success']/div");

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
    private static String пуеSuffixByType(String type) {
        return switch (type) {
            case "address" -> "//address";
            case "edit" -> "//a[@class='action edit dashboard-info-block__link']";
            case "delete" -> "//a[@class='action delete dashboard-info-block__link']";
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    @Step("Build XPath for section: '{sectionTitle}' and type: '{type}'")
    private static String buildXpath(String sectionTitle, String type) {
        return String.format(
                "//span[text()='%s']/ancestor::div[@class='dashboard-info-block__info']%s",
                sectionTitle, пуеSuffixByType(type)
        );
    }

    @Step("Build XPath with index for section: '{sectionTitle}', type: '{type}', index: {index}")
    private static String buildXpathWithIndex(String sectionTitle, String type, int index) {
        return String.format("(%s)[%d]", buildXpath(sectionTitle, type), index);
    }
}