package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;
import java.util.List;
import java.util.NoSuchElementException;

import static fragments.CustomerSidebarFragment.CustomerSidebarElements.*;
import static fragments.HeaderFragment.HeaderElements.*;
import static pages.ProductPage.ProductPageElements.*;
import static pages.WishlistPage.WishlistElements.*;

public class WishlistTests extends BaseTest {

    private static final String BASE_REMOVED_PRODUCT_MASSAGE = " видалено зі списку бажань.";
    private static final String ADDED_PRODUCT_MASSAGE = "Товар додано до Списку бажань.";
    private static final String EMPTY_WISHLIST_PRODUCT_MASSAGE = "У Вашому Списку бажань немає товарів";
    private static final String EMAIL = ConfigReader.getProperty("UserEmail");
    private static final String PASSWORD = ConfigReader.getProperty("UserPassword");

    @Test
    @Description("Verify that a product can be added to the wishlist and then removed from it via the home page")
    public void checkAdditionToWishlistTest() {
        HomePage homePage = new HomePage(driver);
        WishlistPage wishlistPage = new WishlistPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .scrollToPromotionalProductCarousel()
                .clickAddToWishlistButton()
                .getHeaderFragment()
                .scrollToHeader()
                .clickWishlistButton();

        Assert.assertTrue(isProductPresentInWishlist(FAVORITE_PRODUCT_CARDS),
                "The product did not appear in the wishlist after being added");

        wishlistPage.clickDeleteFromWishlistButton();

        Assert.assertTrue(isWishlistEmptyMessageDisplayed(MASSAGE_WISHLIST_IS_EMPTY),
                "Expected empty wishlist message to be displayed, but it was not found");
        Assert.assertFalse(isProductPresentInWishlist(FAVORITE_PRODUCT_CARDS),
                "The product is appear in the wishlist after being deleted");
    }

    @Test
    @Description("Remove from favorites on the favorites page in your personal account")
    public void removeFromFavoritesInProfilePage() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        WishlistPage wishlistPage = new WishlistPage(driver);

        loginPage
                .openLoginPage()
                .acceptCookies()
                .enterEmail(EMAIL)
                .enterPassword(PASSWORD)
                .clickLogInButton();

        userAccountPage.waitUntilMyAccountPageIsVisible();

        homePage
                .getHeaderFragment()
                .clickHeaderElement(HEADER_LOGO);
        homePage
                .scrollToPromotionalProductCarousel()
                .clickAddToWishlistButton()
                .getHeaderFragment()
                .scrollToHeader()
                .clickHeaderElement(USER_ICON_BUTTON)
                .clickHeaderElement(MY_PROFILE_BUTTON);

        userAccountPage
                .getCustomerSidebarFragment()
                .scrollToElement(EXIT_SECTION)
                .clickUserAccountElement(WISHLIST_SECTION);

        String productName = wishlistPage.getWishlistElementInfo(WISHLIST_PRODUCT_NAME);

        wishlistPage.clickDeleteFromWishlistButton();

        String expectedMessage = productName + BASE_REMOVED_PRODUCT_MASSAGE;
        String actualMessage = wishlistPage.getWishlistElementInfo(MASSAGE_PRODUCT_REMOVED_FROM_WISHLIST);
        String actualEmptyMassage = wishlistPage.getWishlistElementInfo(MASSAGE_WISHLIST_IS_EMPTY_PROFILE);

        Assert.assertEquals(actualMessage, expectedMessage,
                "Incorrect removal message!");
        Assert.assertTrue(isWishlistElementInUserAccountDisplayed(MASSAGE_PRODUCT_REMOVED_FROM_WISHLIST),
                "Expected message doesn't display: " + expectedMessage);
        Assert.assertTrue(isWishlistElementInUserAccountDisplayed(MASSAGE_WISHLIST_IS_EMPTY_PROFILE),
                "Expected wishlist to be empty!");
        Assert.assertEquals(actualEmptyMassage, EMPTY_WISHLIST_PRODUCT_MASSAGE,
                "Incorrect empty wishlist message!");
        Assert.assertTrue(isWishlistEmpty(WISHLIST_FORM, WISHLIST_PRODUCTS),
                "Wishlist form is not empty — product items are still present.");
    }

    @Test
    @Description("Add product to wishlist from product page and verify it appears in wishlist")
    public void addToWishlistFromProductPage() {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        WishlistPage wishlistPage = new WishlistPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .scrollToPromotionalProductCarousel()
                .clickFirstProduct();

        String productName = productPage.getTextFrom(PRODUCT_NAME);
        int productCurrentPrice = homePage.convertPriceToInt(productPage.getTextFrom(CURRENT_PRICE));
        int productRegularPrice = homePage.convertPriceToInt(productPage.getTextFrom(REGULAR_PRICE));

        productPage.scrollToElement(ADD_TO_WISHLIST_BUTTON)
                .clickOnTheButton(ADD_TO_WISHLIST_BUTTON);

        String successMessage = productPage.getTextFrom(SUCCESS_MASSAGE);
        String expectedMessage = ADDED_PRODUCT_MASSAGE;
        Assert.assertEquals(successMessage, expectedMessage,
                String.format("Expected success message: '%s', but got: '%s'", expectedMessage, successMessage));

        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);

        homePage.getHeaderFragment()
                .scrollToHeader()
                .clickWishlistButton();

        Assert.assertTrue(isProductPresentInWishlist(FAVORITE_PRODUCT_CARDS),
                "The product did not appear in the wishlist after being added");

        wishlistPage.scrollToElement(WISHLIST_PRODUCT_REGULAR_PRICE);

        String wishlistProductName = wishlistPage.getWishlistElementInfo(WISHLIST_PRODUCT_NAME);
        int wishlistCurrentPrice = wishlistPage.convertPriceToInt(wishlistPage.getWishlistElementInfo(WISHLIST_PRODUCT_CURRENT_PRICE));
        int wishlistRegularPrice = wishlistPage.convertPriceToInt(wishlistPage.getWishlistElementInfo(WISHLIST_PRODUCT_REGULAR_PRICE));

        Assert.assertEquals(wishlistProductName, productName,
                String.format("Expected product name: '%s', but got: '%s'", productName, wishlistProductName));
        Assert.assertEquals(wishlistCurrentPrice, productCurrentPrice,
                String.format("Expected current price: %s, but got: %s", productCurrentPrice, wishlistCurrentPrice));
        Assert.assertEquals(wishlistRegularPrice, productRegularPrice,
                String.format("Expected regular price: %s, but got: %s", productRegularPrice, wishlistRegularPrice));
    }

    @Step("Check if product is present in wishlist")
    public boolean isProductPresentInWishlist(WishlistPage.WishlistElements elements) {
        try {
            WishlistPage wishlistPage = new WishlistPage(driver);
            WebElement product = wishlistPage.waitElementIsVisible(elements.getLocator());
            return product.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }


    @Step("Check if wishlist element '{elements}' is visible in user account")
    public boolean isWishlistElementInUserAccountDisplayed(WishlistPage.WishlistElements elements) {
        try {
            WebElement element = driver.findElement(elements.getLocator());
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check if empty wishlist message is displayed")
    public boolean isWishlistEmptyMessageDisplayed(WishlistPage.WishlistElements elements) {
        try {
            WebElement emptyMessage = driver.findElement(elements.getLocator());
            return emptyMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Check that wishlist form contains no product items")
    public boolean isWishlistEmpty(WishlistPage.WishlistElements body, WishlistPage.WishlistElements elements) {
        try {
            WebElement form = driver.findElement(body.getLocator());
            List<WebElement> productItems = form.findElements(elements.getLocator());
            return productItems.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}