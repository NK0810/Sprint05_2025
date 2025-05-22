package pages;

import constant.Constant;
import fragments.CustomerSidebarFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utils.LocatorProvider;


public class UserAddressesPage extends EditAddressPage {

    public UserAddressesPage(WebDriver driver) {
        super(driver);
    }

    public enum UserAddressesPageElements implements LocatorProvider {
        DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        ADDRESS_SAVED_MASSAGE_TEXT("//div[@data-ui-id='message-success']/div"),
        ADD_DELIVARY_ADDRESS_BUTTON("//span[text()='Інші адреси доставки']/ancestor::div[contains(@class,'dashboard-info-block__info')]//div[contains(@class,'block-addresses-actions-toolbar')]//a[contains(@class,'address-new') and span[text()='Додати адресу доставки']]"),
        //For future use
//        DEFAULT_PAYMENT_ADDRESS_INFO_BLOCK("Платіжна адреса", "address"),
        OTHER_DELIVERY_ADDRESS_INFO_BLOCK_1("Інші адреси доставки", "address", 1),
//        OTHER_PAYMENT_ADDRESS_INFO_BLOCK("Інші платіжні адреси", "address"),
//        EDIT_DEFAULT_PAYMENT_ADDRESS_BUTTON("Платіжна адреса", "edit"),
//        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON_1("Інші адреси доставки", "edit", 1);
//        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON_2("Інші адреси доставки", "edit", 2),
//        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_1("Інші платіжні адреси", "edit", 1),
//        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_2("Інші платіжні адреси", "edit", 2),
        DELETE_OTHER_DELIVERY_ADDRESS_BUTTON_1("Інші адреси доставки", "delete", 1),
//        DELETE_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_1("Інші платіжні адреси", "delete", 1),
//        ADDRESS_SAVED_MASSAGE("//div[@data-ui-id='message-success']"),
        DELETE_ADDRESS_BUTTON_IN_POP_UP("//button[@class='button__primary button--regular button--delete']");
//        DELETE_ADDRESS_INFO_IN_POP_UP("(//div[@class='modal-content']/div)[2]");

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

    @Step("Set suffix for type: {type}")
    private static String suffixByType(String type) {
        return switch (type) {
            case "address" -> "//address";
            case "edit" -> "//a[@class='action edit dashboard-info-block__link']";
            case "delete" -> "//a[@class='action delete dashboard-info-block__link']";
            default -> throw new IllegalArgumentException("Unsupported type: " + type);
        };
    }

    @Step("Build xpath for section: '{sectionTitle}' and type: '{type}'")
    private static String buildXpath(String sectionTitle, String type) {
        return "//span[text()='" + sectionTitle + "']/ancestor::div[@class='dashboard-info-block__info']"
                + suffixByType(type);
    }

    @Step("Build xpath with index for section: '{sectionTitle}', type: '{type}', index: {index}")
    private static String buildXpathWithIndex(String sectionTitle, String type, int index) {
        return "(" + buildXpath(sectionTitle, type) + ")[" + index + "]";
    }

    public static String buildAddress(String... fieldNames) {
        List<String> values = new ArrayList<>();

        for (String name : fieldNames) {
            try {
                Field field = Constant.EditAddressTestData.class.getField(name);
                values.add((String) field.get(null));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Cannot access field: " + name, e);
            }
        }
        return String.join(" ", values);
    }

    @Step("Convert raw address block to normalized form")
    public static String convertAddressBlock(String rawAddress) {
        return rawAddress.replace("tel. ", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}