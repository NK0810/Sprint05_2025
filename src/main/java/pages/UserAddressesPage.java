package pages;

import fragments.CustomerSidebarFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserAddressesPage extends BasePage<UserAddressesPage>{
    private final CustomerSidebarFragment customerSidebarFragment;

    public enum UserAddressesPageElements {
        DEAFAULT_DELIVERY_ADDRESS_INFO_BLOCK("(//div/address)[1]"),
        DEAFAULT_PAYMENT_ADDRESS_INFO_BLOCK("(//div/address)[2]"),
        OTHER_DELIVERY_ADDRESS_INFO_BLOCK("(//div/address)[3]"),
        OTHER_PAYMENT_ADDRESS_INFO_BLOCK("(//div/address)[4]"),
        EDIT_DEAFAULT_DELIVERY_ADDRESS_BUTTON("(//a[@class='action edit dashboard-info-block__link'])[1]"),
        EDIT_DEAFAULT_PAYMENT_ADDRESS_BUTTON("(//a[@class='action edit dashboard-info-block__link'])[2]"),
        EDIT_OTHER_DELIVERY_ADDRESS_BUTTON("(//a[@class='action edit dashboard-info-block__link'])[3]"),
        EDIT_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON("(//a[@class='action edit dashboard-info-block__link'])[4]");


        private final By element;

        UserAddressesPageElements(String xpath) {
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
}