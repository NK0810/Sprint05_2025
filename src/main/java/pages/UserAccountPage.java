package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserAccountPage extends BasePage<UserAccountPage> {
    private static final By MY_ACCOUNT_HEADER = By.xpath("//*[@data-ui-id='page-title-wrapper']");
    private static final By MY_ACCOUNT_EMAIL = By.xpath("//p[@class='dashboard-info-block__email']");
    private static final By TEXT_OUT = By.xpath("//*[@class='nav items']/li[10]/a");

    public UserAccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait until My account header is visible")
    public void waitUntilMyAccountPageIsVisible() {
        WebElement myAccountHeader = waitElementIsVisible(MY_ACCOUNT_HEADER);
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