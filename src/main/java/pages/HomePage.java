package pages;

import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

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

    @Step("Get list of names of last searched product")
    public List<String> getAllLastSearchedProductsNames() {
        return waitElementsAreVisible(By.xpath(LAST_VIEVED_PRODUCTS_NAMES))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Get title of last searched products")
    public String getLastVievedProductsTitle() {
        return waitElementIsVisible(By.xpath(LAST_VIEWED_PRODUCTS_TITLE)).getText();
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
