package pages;

import fragments.HeaderFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.ConfigReader;

public class HomePage extends BasePage {
    private final HeaderFragment header;
    private static final String HOME_URL = ConfigReader.getProperty("home.page");
    private static final String ACCEPT_ALL_BUTTON = "//button[@class=\"button button--regular button__primary button--submit\"]";
    private static final String LIST_OF_GOODS = "//li[@aria-label]";
    private static final String ADD_TO_WISHLIST_BUTTON = "(//button[@class=\"product-wishlist action-to-wishlist product-card__image-wishlist\"])[1]";
    private static final String HEADER = "//div[@class=\"header__content header__container container\"]";

    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderFragment(driver);
    }

    public HomePage openUrl() {
        driver.get(HOME_URL);
        return this;
    }

    public HomePage clickAcceptAllButton() {
        waitElementToBeClickable(By.xpath(ACCEPT_ALL_BUTTON)).click();
        return this;
    }

    public HomePage scrollToPromotionalProductCarousel() {
        scrollToElement(By.xpath(LIST_OF_GOODS));
        return this;
    }

    public HomePage clickAddToWishlistButton(){
        waitElementToBeClickable(By.xpath(ADD_TO_WISHLIST_BUTTON)).click();
        return this;
    }

    public HomePage scrollToHeader(){
        scrollToElement(By.xpath(HEADER));
        return this;
    }
}
