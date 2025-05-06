package tests;

import base.BaseTest;
import fragments.HeaderFragment;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WishlistPage;

public class AddAndRemoveFromWishlistFromHomePageTest extends BaseTest {
    @Test
    public void checkAdditionToWishlist(){
        HomePage homePage = new HomePage(driver);
        HeaderFragment headerFragment = new HeaderFragment(driver);
        WishlistPage wishlistPage = new WishlistPage(driver);

        homePage.openUrl()
                .clickAcceptAllButton()
                .scrollToPromotionalProductCarousel()
                .clickAddToWishlistButton()
                .scrollToHeader();

        headerFragment.clickWishlistButton();

        Assert.assertTrue(wishlistPage.isProductPresentInWishlist() ,
                "The product did not appear in the wishlist after being added.");

        wishlistPage.clickDeleteFromWishlistButton();

        Assert.assertTrue(wishlistPage.isWishlistEmptyMessageDisplayed() ,
                "Expected empty wishlist message to be displayed, but it was not found.");
    }
}
