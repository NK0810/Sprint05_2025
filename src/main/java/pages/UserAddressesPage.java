package pages;

import fragments.CustomerSidebarFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import utils.LocatorProvider;


public class UserAddressesPage extends EditAddressPage {
    private final CustomerSidebarFragment customerSidebarFragment;

    public enum UserAddressesPageElements implements LocatorProvider {
        DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        DEFAULT_PAYMENT_ADDRESS_INFO_BLOCK("Платіжна адреса", "address"),
        OTHER_DELIVERY_ADDRESS_INFO_BLOCK("Інші адреси доставки", "address"),
        OTHER_PAYMENT_ADDRESS_INFO_BLOCK("Інші платіжні адреси", "address"),
        EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        EDIT_DEFAULT_PAYMENT_ADDRESS_BUTTON("Платіжна адреса", "edit"),
        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON("Інші адреси доставки", "edit", 1),
        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON_2("Інші адреси доставки", "edit", 2),
        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON("Інші платіжні адреси", "edit", 1),
        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_2("Інші платіжні адреси", "edit", 2),
        DELETE_OTHER_DELIVERY_ADDRESS_BUTTON("Інші адреси доставки", "delete", 1),
        DELETE_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON("Інші платіжні адреси", "delete"),
        ADDRESS_SAVED_MASSAGE("//div[@data-ui-id='message-success']"),
        ADDRESS_SAVED_MASSAGE_TEXT("//div[@data-ui-id='message-success']/div"),
        DELETE_ADDRESS_BUTTON_IN_POP_UP("//button[@class='button__primary button--regular button--delete']"),
        DELETE_ADDRESS_INFO_IN_POP_UP("(//div[@class='modal-content']/div)[2]");

        private final By element;

        UserAddressesPageElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        UserAddressesPageElements(String sectionTitle, String type) {
            String suffix = switch (type) {
                case "address" -> "//address";
                case "edit" -> "//a[@class='action edit dashboard-info-block__link']";
                case "delete" -> "//a[@class='action delete dashboard-info-block__link']";
                default -> throw new IllegalArgumentException("Unsupported type: " + type);
            };
            String xpath = "//span[text()='" + sectionTitle + "']/ancestor::div[@class='dashboard-info-block__info']" + suffix;
            this.element = By.xpath(xpath);
        }

        UserAddressesPageElements(String sectionTitle, String type, int index) {
            String suffix = switch (type) {
                case "address" -> "//address";
                case "edit" -> "//a[@class='action edit dashboard-info-block__link']";
                case "delete" -> "//a[@class='action delete dashboard-info-block__link']";
                default -> throw new IllegalArgumentException("Unsupported type: " + type);
            };
            String xpath = "(//span[text()='" + sectionTitle + "']/ancestor::div[@class='dashboard-info-block__info']" + suffix + ")[" + index + "]";
            this.element = By.xpath(xpath);
        }

        @Override
        public By getLocator() {
            return element;
        }
    }


    public UserAddressesPage(WebDriver driver) {
        super(driver);
        this.customerSidebarFragment = new CustomerSidebarFragment(driver);
    }

    public CustomerSidebarFragment getCustomerSidebarFragment() {
        return customerSidebarFragment;
    }

    @Step("Get user address as list of address parts from info block: {elements}")
    public List<String> getInfoBlockTextAsList(UserAddressesPageElements elements) {
        String addressBlockText = waitElementIsVisible(elements.getLocator()).getText();
        return Arrays.stream(addressBlockText.split("\\R")) // розбити по рядках
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .flatMap(line -> {
                    if (line.startsWith("tel.")) {
                        return Stream.of(line.replace("tel. ", "").trim());
                    } else {
                        return Arrays.stream(line.split("\\s+"));
                    }
                })
                .collect(Collectors.toList());
    }
}