package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.ConfigReader;

public class AddAndRemoveFromWishlistTest extends BaseTest {
    private static final String BASE_REMOVED_PRODUCT_MASSAGE = " видалено зі списку бажань.";
    private static final String EMPTY_WISHLIST_PRODUCT_MASSAGE = "У Вашому Списку бажань немає товарів";

    @Description("Verify that a product can be added to the wishlist and then removed from it via the home page")
    @Test
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

        Assert.assertTrue(wishlistPage.isProductPresentInWishlist(),
                "The product did not appear in the wishlist after being added");

        wishlistPage
                .clickDeleteFromWishlistButton();

        Assert.assertTrue(wishlistPage.isWishlistEmptyMessageDisplayed(),
                "Expected empty wishlist message to be displayed, but it was not found");
        Assert.assertFalse(wishlistPage.isProductPresentInWishlist(),
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
                .enterEmail(ConfigReader.getProperty("UserEmail"))
                .enterPassword(ConfigReader.getProperty("UserPassword"))
                .clickLogInButton();

        userAccountPage
                .waitUntilMyAccountPageIsVisible();

        homePage
                .getHeaderFragment()
                .clickOnHeaderLogo();
        homePage
                .scrollToPromotionalProductCarousel()
                .clickAddToWishlistButton()
                .getHeaderFragment()
                .scrollToHeader()
                .clickUserIcon()
                .clickMyProfileButton();

        userAccountPage
                .scrolLToExitButton()
                .selectWishlist();

        String productName = wishlistPage.getProductName();

        wishlistPage
                .clickDeleteFromWishlistButton();

        String expectedMessage = productName + BASE_REMOVED_PRODUCT_MASSAGE;
        String actualMessage = wishlistPage.getRemoveMassageText();
        String actualEmptyMassage = wishlistPage.getEmptyListMassageText();

        Assert.assertEquals(actualMessage, expectedMessage,
                "Incorrect removal message!");
        Assert.assertTrue(wishlistPage.isProductRemovedMassageAppears(),
                "Expected massage doesn't displayed: " + expectedMessage);
        Assert.assertTrue(wishlistPage.isWishlistEmptyMessageProfileDisplayed(),
                "Expected wishlist to be empty!");
        Assert.assertEquals(actualEmptyMassage, EMPTY_WISHLIST_PRODUCT_MASSAGE,
                "Incorrect removal message!");
        Assert.assertTrue(wishlistPage.isWishlistFormEmpty(),
                "Wishlist form is not empty — product items are still present.");
    }
}