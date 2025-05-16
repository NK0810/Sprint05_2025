package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;

import java.util.List;

import static java.lang.String.format;
import static pages.ProductPage.ProductPageElements.*;

public class SearchProductsViaSearchBarTest extends BaseTest {
    private static final String SEARCH_QUERY_UKRAINIAN = "Кросівки";
    @Description("Checking the search function and search result accuracy")
    @Test
    public void CheckSearchWithCorrectQueryUkr(){
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSeachField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message %s, actual %s", expectedSearchQuery, actualSearchQuery));

        List<String> productNames = searchPage.getSearchedProductsNames();
        productNames.forEach(name -> Assert.assertTrue(name.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", name, SEARCH_QUERY_UKRAINIAN)));
    }

    @Description("Checking the search function and search result accuracy by product code")
    @Test
    public void CheckSearchByProductCode(){
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSeachField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message %s, actual %s", expectedSearchQuery, actualSearchQuery));

        String productName = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertTrue(productName.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", productName, SEARCH_QUERY_UKRAINIAN));

        searchPage.clickFirstSearchProduct();

        productPage.scrollToElement(PRODUCT_PARAMETERS.getBy());
        productPage.clickOnTheButton(PRODUCT_PARAMETERS);

        productPage.scrollToElement(PRODUCT_CODE.getBy());
        String productCode = productPage.getTextFrom(PRODUCT_CODE);
        productPage.scrollToElement(BACK_ON_HOME_PAGE.getBy());
        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);

        homePage
                .clickSearchField()
                .enterTextInSeachField(productCode)
                .clickSearchButton();

        actualSearchQuery = searchPage.getSearchQuery();
        expectedSearchQuery = format("Результати пошуку для: '%s'", productCode);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message %s, actual %s", expectedSearchQuery, actualSearchQuery));

        String productName2 = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertEquals(productName,productName2,
                format("Expected that product %s will have name %s", productName2, productName2));
    }

    @Test
    @Description("Open last viewed product via search")
    public void openLastViewedProductTest() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        SoftAssert softAssert = new SoftAssert();

        homePage
                .openUrl()
                .acceptCookies()
                .clickSearchField();

        homePage.enterTextInSeachField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        searchPage.clickFirstSearchProduct();

        String productName = productPage.getTextFrom(PRODUCT_NAME);
        String productRegularPrice = productPage.getTextFrom(REGULAR_PRICE_FIRST_PRODUCT);
        String productCurrentPrice = productPage.getTextFrom(CURRENT_PRICE_FIRST_PRODUCT);

        productPage.clickOnTheButton(ProductPage.ProductPageElements.BACK_ON_HOME_PAGE);

        homePage.clickSearchField();

        String lastViewedName = homePage.getLastViewewProductName();
        String lastViewedCurrentPrice = homePage.getLastViewewProductCurrentPrice();
        String lastViewedRegularPrice = homePage.getLastViewewProductActualPrice();

        softAssert.assertEquals(lastViewedName, productName, "Product name does not match last viewed.");
        softAssert.assertEquals(lastViewedCurrentPrice, productCurrentPrice, "Current price does not match.");
        softAssert.assertEquals(lastViewedRegularPrice, productRegularPrice, "Regular price does not match.");
        softAssert.assertAll();
    }
}