package pages;

import fragments.HeaderFragment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends BasePage<HomePage> {
    private final HeaderFragment headerFragment;

    private static final String HOME_URL = BASE_URL;
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String ADD_TO_WISHLIST_BUTTON =
            "(//button[@class='product-wishlist action-to-wishlist product-card__image-wishlist'])[1]";
    private static final String FIRST_PRODUCT = "(//a[@class='product-card__image-link'])[1]";
    private static final String FIRST_NAME_PRODUCT = "(//a[@class='product-card__name'])[1]";
    private static final String PRICE_FIRST_PRODUCT = "(//div[@class='c-price__current'])[1]";
    private static final String SEARCH_FIELD = "//*[@id='autocomplete-input']";
    private static final String SEARCH_BUTTON = "//*[@class='autocomplete__actions']/button";
    private static final String LAST_VIEVED_PRODUCTS_NAMES = "//*[@class='result-column']/a";
    private static final String LAST_VIEWED_PRODUCTS_TITLE = "//*[@class='autocomplete-results__products']//span";
    private static final String FACEBOOK_FOOTER_BUTTON = "//img[@src='https://sportano.ua/media/footer/facebook.png']";
    private static final String INSTAGRAM_FOOTER_BUTTON = "//img[@src='https://sportano.ua/media/footer/instagram.png']";
    private static final String FOOTER = "//div[@class='footer-bottom__socials']";
    private static final String NEWS_LETTER_BLOCK = "//*[@class='block-newsletter']";
    private static final String SEND_EMAIL_INPUT_FIELD = "//input[@id='newsletter']";
    private static final String REGISTRATION_BUTTON = "//div[@class='block-newsletter__field']/div/button";
    private static final String CLICK_POPUP_REGISTRATION = "//div[@class='modal-newsletter__control']/button";
    private static final String NEWS_LETTER_CHECK_BOX = "//*[@class='checkbox-label']";
    private static final String REGISTRATION_POP_UP_TITLE = "//header[@class='modal-header']";
    private static final String REGISTRATION_CONFIRMATION_MESSAGE = "//*[@data-ui-id='message-success']";
    private static final String LAST_VIEVED_BRANDS_NAMES = "//*[@class='autocomplete-results__popular-brand-item']//span";
    private static final String LAST_VIEWED_BRANDS_TITLE = "//*[@class='autocomplete-results__popular-brands']/*/span";
    private static final String BRANDS_DROP_DOWN = "//*[@id='brands-menu']";
    private static final String ALL_BRANDS_BUTTON = "//*[@class='brands-menu__popular-button']";

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

    @Step("Click add to wishlist button")
    public HomePage clickAddToWishlistButton() {
        waitElementToBeClickable(By.xpath(ADD_TO_WISHLIST_BUTTON)).click();
        return this;
    }

    @Step("Click on the first product in list product")
    public void clickFirstProduct() {
        waitElementToBeClickable(By.xpath(FIRST_PRODUCT)).click();
    }

    @Step("Get text first product in list product")
    public String getNameFirstProduct() {
        return waitElementIsVisible(By.xpath(FIRST_NAME_PRODUCT)).getText();
    }

    @Step("Get price in home page")
    public String getProductPrice() {
        return waitElementIsVisible(By.xpath(PRICE_FIRST_PRODUCT)).getText();
    }

    @Step("Click search field")
    public HomePage clickSearchField() {
        waitElementToBeClickable(By.xpath(SEARCH_FIELD)).click();
        return this;
    }

    @Step("Enter search query")
    public HomePage enterTextInSearchField(String searchQuery) {
        waitElementToBeClickable(By.xpath(SEARCH_FIELD)).sendKeys(searchQuery);
        return this;
    }

    @Step("Click search button")
    public HomePage clickSearchButton() {
        waitElementToBeClickable(By.xpath(SEARCH_BUTTON)).click();
        return this;
    }

    @Step("Scroll on subscribe on newsletter block")
    public HomePage scrollToSubscribeOnNewsLetterBlock() {
        scrollToElement(By.xpath(NEWS_LETTER_BLOCK));
        return this;
    }

    @Step("Click to registration button ")
    public HomePage clickRegistrationButton() {
        waitElementToBeClickable(By.xpath(REGISTRATION_BUTTON)).click();
        return this;
    }

    @Step("Click checkbox on pop up")
    public HomePage clickCheckBoxOnPopUp() {
        waitElementToBeClickable(By.xpath(NEWS_LETTER_CHECK_BOX)).click();
        return this;
    }

    @Step("Click registration on popup ")
    public HomePage clickRegistratiOnPopUp() {
        waitElementToBeClickable(By.xpath(CLICK_POPUP_REGISTRATION)).click();
        return this;
    }

    @Step("Is registration pop-up visible")
    public boolean isPopUpVisible() {
        return waitElementIsVisible(By.xpath(REGISTRATION_POP_UP_TITLE)).isDisplayed();
    }

    @Step("Is confirmation registration message visible")
    public boolean isConfirmationRegistrationMessageVisible() {
        return waitElementIsVisible(By.xpath(REGISTRATION_CONFIRMATION_MESSAGE)).isDisplayed();
    }

    @Step("Get list of names of last searched product")
    public List<String> getAllLastSearchedProductsNames() {
        return getTextsFromList(waitElementsAreVisible(By.xpath(LAST_VIEVED_PRODUCTS_NAMES)));
    }

    @Step("Get title of last searched products")
    public String getLastVievedProductsTitle() {
        return waitElementIsVisible(By.xpath(LAST_VIEWED_PRODUCTS_TITLE)).getText();
    }

    @Step("Get title of last searched brands")
    public String getLastVievedBrandsTitle() {
        return waitElementIsVisible(By.xpath(LAST_VIEWED_BRANDS_TITLE)).getText();
    }

    @Step("Get list of names of last searched brand")
    public List<String> getAllLastSearchedProductsBrands() {
        return getTextsFromList(waitElementsAreVisible(By.xpath(LAST_VIEVED_BRANDS_NAMES)));
    }

    @Step
    @Description("User Email Input To Subscribe Field")
    public HomePage enterEmail(String email) {
        waitElementIsVisible(By.xpath(SEND_EMAIL_INPUT_FIELD)).sendKeys(email);
        return this;
    }

    @Step("Click brands drop down trigger")
    public HomePage clickBrandsDropDown() {
        waitElementIsVisible(By.xpath(BRANDS_DROP_DOWN)).click();
        return this;
    }

    @Step("Click all brands list link")
    public HomePage clickAllBrandsLink() {
        waitElementIsVisible(By.xpath(ALL_BRANDS_BUTTON)).click();
        return this;
    }

    @Step("Click on the facebook button")
    public HomePage clickOnTheFacebookButton() {
        waitElementToBeClickable(By.xpath(FACEBOOK_FOOTER_BUTTON)).click();
        return this;
    }

    @Step("Click on the instagram button")
    public HomePage clickOnTheInstagramButton() {
        waitElementToBeClickable(By.xpath(INSTAGRAM_FOOTER_BUTTON)).click();
        return this;
    }

    @Step("Scroll to the footer")
    public HomePage scrollToFooter() {
        scrollToElement(By.xpath(FOOTER));
        return this;
    }

    @Step("Check when the url be https://www.facebook.com/sportano.ua/")
    public boolean urlFacebook() {
        return wait.until(ExpectedConditions.urlToBe("https://www.facebook.com/sportano.ua/"));
    }

    @Step("Check when the url be https://www.instagram.com/accounts/login/?next=https%3A%2F%2Fwww.instagram.com%2Fsportano.ua%2F&is_from_rle")
    public boolean urlInstagram() {
        return wait.until(ExpectedConditions.urlToBe("https://www.instagram.com/accounts/login/?next=https%3A%2F%2Fwww.instagram.com%2Fsportano.ua%2F&is_from_rle"));
    }

    @Step("Switch to the new tab")
    public void switchToNewTab() {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
}
