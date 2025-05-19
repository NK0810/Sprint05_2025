package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.By.xpath;

public class UserAccountPage extends BasePage<UserAccountPage> {
    private static final By MY_ACCOUNT_HEADER = xpath("//*[@data-ui-id='page-title-wrapper']");
    private static final By MY_ACCOUNT_EMAIL = xpath("//p[@class='dashboard-info-block__email']");
    private static final By TEXT_OUT = xpath("//*[@class='nav items']/li[10]/a");
    private static final String ACCOUNT_SETTING = "//*[@class='nav item'][4]/a";
    private static final String FIRST_NAME_FIELD = "//input[@name='firstname']";
    private static final String SUR_NAME_FIELD = "//input[@name='lastname']";
    private static final String NOTIFICATION_CHECK_BOX_MAIL_LABEL = "//label[@for='gdpr-agreement-f56754fe-fa50-409a-9ec2-8706631db48c']";
    private static final String NOTIFICATION_CHECK_BOX_PHONE_LABEL = "//label[@for='gdpr-agreement-acab0796-e6e9-41d1-bbd3-9af8c46e277f']";
    private static final String NOTIFICATION_CHECK_BOX_MAIL_INPUT = "//input[@id='gdpr-agreement-f56754fe-fa50-409a-9ec2-8706631db48c']";
    private static final String NOTIFICATION_CHECK_BOX_PHONE_INPUT = "//input[@id='gdpr-agreement-acab0796-e6e9-41d1-bbd3-9af8c46e277f']";
    private static final String SAVE_CHANGE_BUTTON = "//button[@class='action save account-edit-info__button button button--regular button__primary']";
    private static final String SUCCESS_MESSAGE ="//div[@class='message-text' and contains(text(), 'Дані Вашого Облікового запису збережено')]";

    public UserAccountPage(WebDriver driver) {
        super(driver);
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

    @Step("Click My Account Setting")
    public UserAccountPage clickAccountSetting() {
        waitElementToBeClickable(By.xpath(ACCOUNT_SETTING)).click();
        return this;
    }

    @Step("Input New Test Name")
    public UserAccountPage sendNewTestName(String name) {
        waitElementIsVisible(By.xpath(FIRST_NAME_FIELD)).clear();
        waitElementIsVisible(By.xpath(FIRST_NAME_FIELD)).sendKeys(name);
        return this;
    }
    @Step("Input New Sur Name")
    public UserAccountPage sendNewTestSurname(String surname) {
        waitElementIsVisible(By.xpath(SUR_NAME_FIELD)).clear();
        waitElementIsVisible(By.xpath(SUR_NAME_FIELD)).sendKeys(surname);
        return this;
    }

    @Step("Scroll to save change button")
    public UserAccountPage scrollToSaveChangeButton() {
        scrollToElement(By.xpath(SAVE_CHANGE_BUTTON));
        return null;
    }

    @Step("Click Check Box Mail Notification")
    public UserAccountPage clickCheckBoxMail() {
        waitElementIsVisible(By.xpath(NOTIFICATION_CHECK_BOX_MAIL_LABEL)).click();
        return this;
    }

    @Step("Click Check Box Phone Notification")
    public UserAccountPage clickCheckBoxPhone() {
        waitElementIsVisible(By.xpath(NOTIFICATION_CHECK_BOX_PHONE_LABEL)).click();
        return this;
    }

    @Step("Click save change")
    public UserAccountPage clickSaveChange() {
        waitElementToBeClickable(By.xpath(SAVE_CHANGE_BUTTON)).click();
        return this;
    }

    @Step("Get current first name")
    public String getFirstNameFieldText() {
        return waitElementIsVisible(By.xpath(FIRST_NAME_FIELD)).getAttribute("value");
    }

    @Step("Get current surname")
    public String getSurnameFieldText() {
        return waitElementIsVisible(By.xpath(SUR_NAME_FIELD)).getAttribute("value");
    }

    @Step("Get success message text after saving changes")
    public String getSuccessMessageText() {
        return waitElementIsVisible(By.xpath(SUCCESS_MESSAGE)).getText();
    }

    @Step("Check if checkbox 1 is selected")
    public boolean isCheckBoxMailSelected() {
        return waitPresenceOfElement(By.xpath(NOTIFICATION_CHECK_BOX_MAIL_INPUT)).isSelected();
    }

    @Step("Check if checkbox 2 is selected")
    public boolean isCheckBoxPhoneSelected(){
        return waitPresenceOfElement(By.xpath(NOTIFICATION_CHECK_BOX_PHONE_INPUT)).isSelected();
    }
}