package pages;

import fragments.HeaderFragment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage<HomePage> {
    private final HeaderFragment headerFragment;

    private static final String HOME_URL = BASE_URL;
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String ADD_TO_WISHLIST_BUTTON = "(//button[@class='product-wishlist action-to-wishlist product-card__image-wishlist'])[1]";
    private static final String FIRST_PRODUCT = "(//a[@class='product-card__image-link'])[1]";
    private static final String FIRST_NAME_PRODUCT = "(//a[@class='product-card__name'])[1]";
    private static final String PRICE_FIRST_PRODUCT = "(//div[@class='c-price__current'])[1]";

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
    public HomePage clickFirsProduct() {
        waitElementToBeClickable(By.xpath(FIRST_PRODUCT)).click();
        return this;
    }

    @Step("Get text first prodict in list product")
    public String getNameFirstProduct() {
        return waitElementIsVisible(By.xpath(FIRST_NAME_PRODUCT)).getText();
    }

    @Step("Get price in home page")
    public String getProductPrice(){
        return waitElementIsVisible(By.xpath(PRICE_FIRST_PRODUCT)).getText();
    }
}