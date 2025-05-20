package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.xpath;

public class UserAccountPage extends BasePage<UserAccountPage> {

    private static final By MY_ACCOUNT_HEADER = xpath("//*[@data-ui-id='page-title-wrapper']");
    private static final By MY_ACCOUNT_EMAIL = xpath("//p[@class='dashboard-info-block__email']");
    private static final By TEXT_OUT = xpath("//*[@class='nav items']/li[10]/a");

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    public enum UserAccountElements {
        EXIT_SECTION ("//li[contains(a,'Вийти ')]"),
        WISHLIST_SECTION ("//li[contains(a,'Список бажань')]");

        private final By element;

        UserAccountElements(String xpath) {
            this.element = By.xpath(xpath);
        }

        public By getLocator() {
            return element;
        }
    }

    @Step("Scroll to  whishlist section button")
    public UserAccountPage scrollToElement(UserAccountElements elements) {
        scrollToElement(elements.getLocator());
        return this;
    }

    @Step("Select Wishlist section")
    public UserAccountPage clickUserAccountElement(UserAccountElements elements) {
        waitElementToBeClickable(elements.getLocator()).click();
        return this;
    }

    @Step("Wait until My account header is visible")
    public void waitUntilMyAccountPageIsVisible() {
        waitElementIsVisible(MY_ACCOUNT_HEADER);
    }

    @Step("Get email text")
    public String getUserEmail() {
        String email = waitElementIsVisible(MY_ACCOUNT_EMAIL).getText();
        return email;
    }

    @Step("Get text out")
    public String getTextOut() {
        String text = waitElementIsVisible(TEXT_OUT).getText();
        return text;
    }
}