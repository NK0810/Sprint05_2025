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
    private static final String ACCOUNT_SETTING = "//li/a[@href='https://sportano.ua/customer/account/edit/']";
    private static final String FIRST_NAME_FIELD = "//input[@name='firstname']";
    private static final String SURNAME_FIELD = "//input[@name='lastname']";
    private static final String CHECK_BOX1 = "//label[@for='gdpr-agreement-f56754fe-fa50-409a-9ec2-8706631db48c']";
    private static final String CHECK_BOX2 = "//label[@for='gdpr-agreement-acab0796-e6e9-41d1-bbd3-9af8c46e277f']";
    private static final String SAVE_CHANGE_BUTTON = "//button[@class='action save account-edit-info__button button button--regular button__primary']/span";
    private static final By SUCCESS_MESSAGE = xpath("//div[@class='message-text' and contains(text(), 'Дані Вашого Облікового запису збережено')]");

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

    @Step("Click and clear first name field")
    public UserAccountPage clickAndClearFirstNameField() {
        WebElement firstNameInput = waitElementIsVisible(By.xpath(FIRST_NAME_FIELD));
        firstNameInput.clear();
        firstNameInput.click();
        return this;
    }

    @Step("Input New Test Name")
    public UserAccountPage sendNewTestName(String name) {
        waitElementIsVisible(By.xpath(FIRST_NAME_FIELD)).sendKeys(name);
        return this;
    }

    @Step("Click and clear surname field")
    public UserAccountPage clickAndClearSurnameField() {
        WebElement firstNameInput = waitElementIsVisible(By.xpath(SURNAME_FIELD));
        firstNameInput.clear();
        firstNameInput.click();
        return this;
    }

    @Step("Input New Test Surname")
    public UserAccountPage sendNewTestSurname(String name) {
        waitElementIsVisible(By.xpath(SURNAME_FIELD)).sendKeys(name);
        return this;
    }

    @Step("Click Check Box1")
    public UserAccountPage clickCheckBox1(){
        waitElementToBeClickable(By.xpath(CHECK_BOX1)).click();
        return this;
    }

    @Step("Click Check Box2")
    public UserAccountPage clickCheckBox2(){
        waitElementToBeClickable(By.xpath(CHECK_BOX2)).click();
        return this;
    }

    @Step("Click save change")
    public UserAccountPage clickSaveChange(){
        waitElementToBeClickable(By.xpath(SAVE_CHANGE_BUTTON)).click();
        return this;
    }

    @Step("Get current first name")
    public String getFirstNameFieldText() {
        WebElement firstNameInput = waitElementIsVisible(By.xpath(FIRST_NAME_FIELD));
        return firstNameInput.getAttribute("value");
    }

    @Step("Get current surname")
    public String getSurnameFieldText() {
        WebElement surnameInput = waitElementIsVisible(By.xpath(SURNAME_FIELD));
        return surnameInput.getAttribute("value");
    }

    @Step("Get success message text after saving changes")
    public String getSuccessMessageText() {
        return waitElementIsVisible(SUCCESS_MESSAGE).getText();
    }


}