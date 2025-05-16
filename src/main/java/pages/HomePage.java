package pages;

import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage<HomePage> {
    private final HeaderFragment headerFragment;

    private static final String HOME_URL = BASE_URL;
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String ADD_TO_WISHLIST_BUTTON = "(//button[@class='product-wishlist action-to-wishlist product-card__image-wishlist'])[1]";
    private static final String FIRST_PRODUCT = "(//a[@class='product-card__image-link'])[1]";
    private static final String FIRST_NAME_PRODUCT = "(//a[@class='product-card__name'])[1]";
    private static final String PRICE_FIRST_PRODUCT = "(//div[@class='c-price__current'])[1]";
    private static final String SEARCH_FIELD = "//*[@id='autocomplete-input']";
    private static final String SEARCH_BUTTON = "//*[@class='autocomplete__actions']/button";
    private static final String LAST_VIEVED_PRODUCTS_NAMES = "//*[@class='result-column']/a";
    private static final String LAST_VIEWED_PRODUCTS_TITLE = "//*[@class='autocomplete-results__products']//span";
    private static final String NEWS_LETTER_FIELD = "//*[@class='block-newsletter']";
    private static final String SEND_EMAIL = "//input[@id='newsletter']";
    private static final String REGISTRATION_BUTTON = "//*[@class='button button__primary button--jumbo action subscribe']";
    private static final String CLICK_POPUP_REGISTRATION = "//button[@class='button button__primary button--jumbo action subscribe-send']";
    private static final String CHECK_BOX = "//*[@class='checkbox-label']";
    private WebElement WebElement;
    private static final String POP_UP = "//header[@class='modal-header']";
    private static final String CONFIRMATION_MESSAGE = "//*[@data-ui-id='message-success']";

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
    public HomePage enterTextInSeachField(String searchQuery) {
        waitElementToBeClickable(By.xpath(SEARCH_FIELD)).sendKeys(searchQuery);
        return this;
    }

    @Step("Click search button")
    public HomePage clickSearchButton() {
        waitElementToBeClickable(By.xpath(SEARCH_BUTTON)).click();
        return this;
    }

    @Step("Scroll to save change button")
    public HomePage scrollToSubscribeOnNewsLetterBlock() {
        scrollToElement(By.xpath(NEWS_LETTER_FIELD));
        return this;
    }

    @Step("Enter Email")
    public HomePage sendEmail(String email) {
        waitElementToBeClickable(By.xpath(SEND_EMAIL)).sendKeys(email);
        return this;
    }

    @Step("Click subscribe button")
    public HomePage clickRegistrationButton() {
        waitElementToBeClickable(By.xpath(REGISTRATION_BUTTON)).click();
        return this;
    }

    @Step("Click checkbox")
    public HomePage clickCheckBox() {
        waitElementToBeClickable(By.xpath(CHECK_BOX)).click();
        return this;
    }

    @Step("Click subscribe on popup ")
    public HomePage clickRegistrationPopUp() {
        waitElementToBeClickable(By.xpath(CLICK_POPUP_REGISTRATION)).click();
        return this;
    }

    @Step("Is pop-up visible")
    public boolean isPopUpVisible() {
        WebElement popUpWindow = waitElementIsVisible(By.xpath(POP_UP));
        return popUpWindow.isDisplayed();
    }

    public boolean isConfirmationMessageVisible() {
        try {
            return driver.findElement(By.xpath(CONFIRMATION_MESSAGE)).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Get list of names of last searched product")
    public List<String> getAllLastSearchedProductsNames(){
        return waitElementsAreVisible(By.xpath(LAST_VIEVED_PRODUCTS_NAMES))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get title of last searched products")
    public String getLastVievedProductsTitle(){
        return waitElementIsVisible(By.xpath(LAST_VIEWED_PRODUCTS_TITLE)).getText();
    }
}