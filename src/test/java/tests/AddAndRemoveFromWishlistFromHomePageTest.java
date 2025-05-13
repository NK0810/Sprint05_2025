package tests;

import base.BaseTest;
import fragments.HeaderFragment;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WishlistPage;

public class AddAndRemoveFromWishlistFromHomePageTest extends BaseTest {
    @Description("Verify that a product can be added to the wishlist and then removed from it via the home page")
    @Test
    public void checkAdditionToWishlist() {
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
}
