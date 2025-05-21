package pages;

import fragments.CustomerSidebarFragment;
import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.By.xpath;

public class UserAccountPage extends BasePage<UserAccountPage> {
    private final CustomerSidebarFragment customerSidebarFragment;

    private static final By MY_ACCOUNT_HEADER = xpath("//*[@data-ui-id='page-title-wrapper']");
    private static final By MY_ACCOUNT_EMAIL = xpath("//p[@class='dashboard-info-block__email']");
    private static final By TEXT_OUT = xpath("//*[@class='nav items']/li[10]/a");

    public UserAccountPage(WebDriver driver) {
        super(driver);
        this.customerSidebarFragment = new CustomerSidebarFragment(driver);
    }

    public CustomerSidebarFragment getCustomerSidebarFragment() {
        return customerSidebarFragment;
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