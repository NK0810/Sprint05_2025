package pages;

import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage extends BasePage<HomePage> {
    private final HeaderFragment headerFragment;

    private static final String HOME_URL = BASE_URL;
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String SUBSCRIBE_ON_NEWSLETTER = "//*[@class='block-newsletter']";
    public static final String NEWSLETTER_EMAIL_INPUT = "//input[@id='newsletter']";
    public static final String CLICK_REGISTRATION = "//*[@class='button button__primary button--jumbo action subscribe']";
    private static final String CHECK_BOX_LABEL_CLICK = "//label[@class='checkbox-label']";
    private static final String CLICK_REGISTRATION_AFTER_CHECK_BOX = "//button[@class='button button__primary button--jumbo action subscribe-send']";
    public static final String CONFIRMATION_MESSAGE = "//*[contains(text(), 'Перевірте свою поштову скриньку та підтвердьте, що хочете отримувати Розсилку новин SPORTANO.')]";

    private static final String ADD_TO_WISHLIST_BUTTON =
            "(//button[@class='product-wishlist action-to-wishlist product-card__image-wishlist'])[1]";


    public HomePage(WebDriver driver) {
        super(driver);
        this.headerFragment = new HeaderFragment(driver);
    }

    public HeaderFragment getHeaderFragment() {
        return headerFragment;
    }

    @Step("Open home page")
    public HomePage openUrl() {
        driver.get(HOME_URL);
        return this;
    }

    @Step("Scroll to product carousel")
    public HomePage scrollToPromotionalProductCarousel() {
        scrollToElement(By.xpath(LIST_OF_GOODS));
        return this;
    }

    @Step("Scroll To Subscribe On Newsletter")
    public HomePage scrollToSubscribeOnNewsletter() {
        scrollToElement(By.xpath(SUBSCRIBE_ON_NEWSLETTER));
        return this;

    }

    @Step("Click add to wishlist button")
    public HomePage clickAddToWishlistButton() {
        waitElementToBeClickable(By.xpath(ADD_TO_WISHLIST_BUTTON)).click();
        return this;
    }

    //Change name method and Step
    @Step("Send EMAIL")
    public HomePage sendEmailInNewsletterField(String email) {
        waitElementToBeVisible(By.xpath(NEWSLETTER_EMAIL_INPUT)).sendKeys(email);
        return this;
    }

    @Step("Click registration button")
    public HomePage clickRegistrationButton(){
        waitElementToBeClickable(By.xpath(CLICK_REGISTRATION)).click();
        return this;
    }

    @Step("Click check box label")
    public HomePage clickCheckBoxLabel() {
        waitElementToBeClickable(By.xpath(CHECK_BOX_LABEL_CLICK)).click();
        return this;
    }
    @Step("Click registration button in pop up")
    public HomePage clickRegistrationAfterCheckBox(){
        waitElementToBeClickable(By.xpath(CLICK_REGISTRATION_AFTER_CHECK_BOX)).click();
        return this;
    }
    @Step("Get confirmation message after subscribing")
    public String getNewsletterConfirmationMessage() {
        WebElement messageElement = waitElementToBeVisible(By.xpath(CONFIRMATION_MESSAGE));
        return messageElement.getText();
    }

}