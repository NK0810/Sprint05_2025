package pages;

import fragments.CustomerSidebarFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserAddressesPage extends EditAddressPage {
    private final CustomerSidebarFragment customerSidebarFragment;

    public enum UserAddressesPageElements {
        DEAFAULT_DELIVERY_ADDRESS_INFO_BLOCK("Адреса доставки за умовчанням", "address"),
        DEAFAULT_PAYMENT_ADDRESS_INFO_BLOCK("Платіжна адреса", "address"),
        OTHER_DELIVERY_ADDRESS_INFO_BLOCK("Інші адреси доставки", "address"),
        OTHER_PAYMENT_ADDRESS_INFO_BLOCK("Інші платіжні адреси", "address"),
        EDIT_DEAFAULT_DELIVERY_ADDRESS_BUTTON("Адреса доставки за умовчанням", "edit"),
        EDIT_DEAFAULT_PAYMENT_ADDRESS_BUTTON("Платіжна адреса", "edit"),
        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON("Інші адреси доставки", "edit"),
        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON("Інші платіжні адреси", "edit"),
        ADDRESS_SAVED_MASSAGE("//div[@data-ui-id='message-success']"),
        ADDRESS_SAVED_MASSAGE_TEXT("//div[@data-ui-id='message-success']/div");

        private final By element;

        UserAddressesPageElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        UserAddressesPageElements(String sectionTitle, String type) {
            String suffix = type.equals("address")
                    ? "//address"
                    : "//a[@class='action edit dashboard-info-block__link']";

            String xpath = "//span[text()='" + sectionTitle + "']/ancestor::div[@class='dashboard-info-block__info']" + suffix;
            this.element = By.xpath(xpath);
        }

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

    public UserAddressesPage clickOnUserAddressPageElement(UserAddressesPageElements elements){
        waitElementToBeClickable(elements.getLocator()).click();
        return this;
    }

    public String getUserAddressElementsText(UserAddressesPageElements elements) {
        return waitElementIsVisible(elements.getLocator()).getText();
    }

    public List<String> getUserAddressInfoText(UserAddressesPageElements elements) {
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

    @Step("Scroll to UserAddress element: {elements}")
    public UserAddressesPage scrollToElement(UserAddressesPageElements elements) {
        scrollToElement(elements.getLocator());
        return this;
    }
}