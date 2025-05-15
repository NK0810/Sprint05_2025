package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.ShoppingCardPage;

import static pages.ProductPage.ProductPageElements.*;
import static pages.ShoppingCardPage.ShoppingCartElements.*;
import static pages.ShoppingCardPage.ShoppingCartElements.PRODUCT_NAME;

public class ShoppingCardTest extends BaseTest {
    ShoppingCardPage shoppingCardPage = new ShoppingCardPage(driver);

    @Description("Verify Add, Remove, and Update Quantity in Shopping Cart")
    @Test
    public void addDeleteAndUpdateQuantity() {
        HomePage homePage = new HomePage(driver);
        ProductPage productPage = new ProductPage(driver);
        ShoppingCardPage shoppingCardPage = new ShoppingCardPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .scrollToPromotionalProductCarousel();

        String nameProductInHomePage = homePage.getNameFirstProduct();
        String priceProductInHomePage = homePage.getProductPrice();

        homePage
                .clickFirstProduct();

        Assert.assertEquals(nameProductInHomePage, productPage.getTextFrom(ProductPage.ProductPageElements.PRODUCT_NAME), "Product name in cart does not match product on homepage.");

        productPage
                .clickOnTheButton(ADD_TO_SHOPPING_CART_BUTTON)
                .clickOnTheButton(SELECT_SIZE_BUTTON);

        Assert.assertEquals(nameProductInHomePage, productPage.getTextFrom(PRODUCT_NAME_IN_CARD_PREVIEW), "Product name in cart preview does not match product on homepage.");

        String sizeProduct = productPage.getTextFrom(PRODUCT_SIZE_IN_POP_UP_REVIEW_SHOPPING_CARD);

        productPage
                .clickOnTheButton(CONFIRM_ADD_TO_CART_BUTTON);

        Assert.assertEquals(nameProductInHomePage, shoppingCardPage.getTextFromLocator(PRODUCT_NAME), "Product name in shopping cart does not match the one on the homepage.");
        Assert.assertEquals(priceProductInHomePage, shoppingCardPage.getTextFromLocator(PRODUCT_PRICE), "Product price in shopping cart does not match the one on the homepage.");
        Assert.assertEquals(sizeProduct, shoppingCardPage.getCleanProductSizeText(PRODUCT_SIZE), "Product size in shopping cart does not match the one on the homepage.");

        shoppingCardPage
                .clickOnTheButton(ADD_ONE_PRODUCT_BUTTON);

        String regularPriceProduct = shoppingCardPage.getTextFromLocator(PRODUCT_PRICE);
        String updatePriceProduct = shoppingCardPage.waitForProductInfoAndGetText(PRODUCT_PRICE_UPDATE);

        Assert.assertTrue(shoppingCardPage.convertPriceToInt(regularPriceProduct) * 2 == shoppingCardPage.convertPriceToInt(updatePriceProduct));

        shoppingCardPage.clickOnTheButton(DELETE_PRODUCT_FROM_SHOPPING_CARD_BUTTON)
                .clickOnTheButton(ACCEPT_TO_DELETE_PRODUCT_FROM_SHOPPING_CARD);

        Assert.assertEquals(shoppingCardPage.getTextFromLocator(CART_EMPTY_TEXT), "Кошик порожній");
    }
}