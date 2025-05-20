package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
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
    public void CheckSearchWithCorrectQueryUkr() {
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
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        List<String> productNames = searchPage.getSearchedProductsNames();
        productNames.forEach(name -> Assert.assertTrue(name.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", name, SEARCH_QUERY_UKRAINIAN)));
    }

    @Description("Checking the search function and search result accuracy by product code")
    @Test
    public void CheckSearchByProductCode() {
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
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String firstProductName = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertTrue(firstProductName.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", firstProductName, SEARCH_QUERY_UKRAINIAN));

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
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String productName2 = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertEquals(firstProductName, productName2,
                format("Expected that product %s will have name %s", productName2, productName2));
    }

    @Description("Checking the last search save function and storage accuracy")
    @Test
    public void CheckLastSearchQuerySaving() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSeachField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String firstProductName = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertTrue(firstProductName.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", firstProductName, SEARCH_QUERY_UKRAINIAN));

        searchPage.clickFirstSearchProduct();
        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);
        homePage.clickSearchField();

        String expectedTitle = "Останні переглянуті продукти";
        String actualTitle = homePage.getLastVievedProductsTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
                format("Expected that Title %s will have name %s", actualTitle, expectedTitle));

        String lastSearchedProductName = homePage.getAllLastSearchedProductsNames().getLast();
        Assert.assertEquals(firstProductName, lastSearchedProductName,
                format("Expected that product %s will have name %s", lastSearchedProductName, firstProductName));
    }

    @Description("Checking the last search brand save function and storage accuracy")
    @Test
    public void CheckLastSearchBrandSaving() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSeachField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String firstProductName = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertTrue(firstProductName.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", firstProductName, SEARCH_QUERY_UKRAINIAN));


        searchPage.clickFirstSearchProduct();
        productPage.scrollToElement(PRODUCT_PARAMETERS);
        productPage.clickOnTheButton(PRODUCT_PARAMETERS);
        productPage.scrollToElement(PRODUCT_BRAND);
        String productBrand = productPage.getTextFrom(PRODUCT_BRAND);
        productPage.scrollToElement(BACK_ON_HOME_PAGE);
        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);
        homePage.clickSearchField();

        String expectedTitle = "Останні переглянуті бренди";
        String actualTitle = homePage.getLastVievedBrandsTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
                format("Expected that Title %s will have name %s", actualTitle, expectedTitle));

        String lastSearchedProductBrand = homePage.getAllLastSearchedProductsBrands().getLast();
        Assert.assertEquals(productBrand, lastSearchedProductBrand,
                format("Expected that brand %s will have name %s", lastSearchedProductBrand, productBrand));
    }
}
