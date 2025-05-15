package pages;

import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage<HomePage> {
    private final HeaderFragment headerFragment;
    private WebElement WebElement;
    private static final String HOME_URL = BASE_URL;
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String LOGIN_BUTTON = "//*[@class='button button__primary button--regular button__primary--red']";
    private static final String TEST_EMAIL_FIELD = "//input[@name='login[username]']";
    private static final String TEST_PASS_FIELD = "//input[@name='login[password]']";
    private static final String ADD_TO_WISHLIST_BUTTON =
            "(//button[@class='product-wishlist action-to-wishlist product-card__image-wishlist'])[1]";
    private static final String MY_ACCOUNT = "//*[@class='tabler-icon-user-thin']";
    private static final String ACCOUNT_SETTING = "//*[@class='nav item'][4]/a";
    private static final String LOGIN_CLICK = "//*[@id='send2']";
    private static final String FIRST_PRODUCT = "(//a[@class='product-card__image-link'])[1]";
    private static final String FIRST_NAME_PRODUCT = "(//a[@class='product-card__name'])[1]";
    private static final String PRICE_FIRST_PRODUCT = "(//div[@class='c-price__current'])[1]";
    private static final String SEARCH_FIELD = "//*[@id='autocomplete-input']";
    private static final String SEARCH_BUTTON = "//*[@class='autocomplete__actions']/button";

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

    @Step("Click My Account")
    public HomePage clickMyAccount() {
        waitElementToBeClickable(By.xpath(MY_ACCOUNT)).click();
        return this;
    }

    @Step("Click Login")
    public HomePage clickLogin() {
        waitElementToBeClickable(By.xpath(LOGIN_BUTTON)).click();
        return this;
    }

    @Step("Click Email Input Field")
    public HomePage clickEmailInputField() {
        waitElementToBeClickable(By.xpath(TEST_EMAIL_FIELD)).click();
        return this;
    }

    @Step("Input EMAIL Test")
    public HomePage sendTestEmail(String email) {
        waitElementIsVisible(By.xpath(TEST_EMAIL_FIELD)).sendKeys(email);
        return this;
    }

    @Step("Click Pass Input Field")
    public HomePage clickTestPassField() {
        waitElementToBeClickable(By.xpath(TEST_PASS_FIELD)).click();
        return this;
    }

    @Step("Input PASS Test")
    public HomePage sendTestPass(String email) {
        waitElementIsVisible(By.xpath(TEST_PASS_FIELD)).sendKeys(email);
        return this;
    }

    @Step("Click Login Button")
    public HomePage clickLoginButton() {
        waitElementToBeClickable(By.xpath(LOGIN_CLICK)).click();
        return this;
    }

    @Step("Click My Account Setting")
    public HomePage clickAccountSetting() {
        waitElementToBeClickable(By.xpath(ACCOUNT_SETTING)).click();
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
    public String getProductPrice(){
        return waitElementIsVisible(By.xpath(PRICE_FIRST_PRODUCT)).getText();
    }

    @Step("Click search field")
    public HomePage clickSearchField(){
        waitElementToBeClickable(By.xpath(SEARCH_FIELD)).click();
        return this;
    }

    @Step("Enter search query")
    public HomePage enterTextInSeachField(String searchQuery){
        waitElementToBeClickable(By.xpath(SEARCH_FIELD)).sendKeys(searchQuery);
        return this;
    }

    @Step("Click search button")
    public HomePage clickSearchButton(){
        waitElementToBeClickable(By.xpath(SEARCH_BUTTON)).click();
        return this;
    }

}