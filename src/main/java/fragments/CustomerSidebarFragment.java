package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class CustomerSidebarFragment extends BasePage<CustomerSidebarFragment> {

    public CustomerSidebarFragment(WebDriver driver) {
        super(driver);
    }

    public enum CustomerSidebarElements {
        EXIT_SECTION("//a[text()='Вийти ']"),
        WISHLIST_SECTION("//a[text()='Список бажань']"),
        ADDRESS_SECTION("//a[text()='Мої адреси']");

        private final By element;

        CustomerSidebarElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        public By getLocator() {
            return element;
        }
    }

    @Step("Scroll to UserAccount element: {elements}")
    public CustomerSidebarFragment scrollToElement(CustomerSidebarElements elements) {
        scrollToElement(elements.getLocator());
        return this;
    }

    @Step("Click on UserAccount element: {elements}")
    public CustomerSidebarFragment clickUserAccountElement(CustomerSidebarElements elements) {
        waitElementToBeClickable(elements.getLocator()).click();
        return this;
    }
}