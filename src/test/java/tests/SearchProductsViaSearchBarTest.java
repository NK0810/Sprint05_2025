package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BrandsPage;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchPage;

import java.util.List;

import static constant.Constant.Owners.BOHDAN;
import static constant.Constant.Owners.IGOR;
import static fragments.HeaderFragment.PopUpLastViewedProductSection.*;
import static java.lang.String.format;
import static pages.ProductPage.ProductPageElements.*;

public class SearchProductsViaSearchBarTest extends BaseTest {
    private static final String SEARCH_QUERY_UKRAINIAN = "Кросівки";
    private static final String LAST_VIEWED_PRODUCT_TITLE = "Останні переглянуті продукти";

    @Owner(BOHDAN)
    @Description("Checking the search function and search result accuracy")
    @Test
    public void CheckSearchWithCorrectQueryUkr() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSearchField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        List<String> productNames = searchPage.getSearchedProductsNames();
        productNames.forEach(name -> Assert.assertTrue(name.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", name, SEARCH_QUERY_UKRAINIAN)));
    }

    @Owner(BOHDAN)
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
                .enterTextInSearchField(SEARCH_QUERY_UKRAINIAN)
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

        productPage.scrollToElement(PRODUCT_CODE);
        String productCode = productPage.getTextFrom(PRODUCT_CODE);
        productPage.scrollToElement(BACK_ON_HOME_PAGE);
        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);

        homePage
                .clickSearchField()
                .enterTextInSearchField(productCode)
                .clickSearchButton();

        actualSearchQuery = searchPage.getSearchQuery();
        expectedSearchQuery = format("Результати пошуку для: '%s'", productCode);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String productName2 = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertEquals(firstProductName, productName2,
                format("Expected that product %s will have name %s", productName2, productName2));
    }

    @Owner(BOHDAN)
    @Description("Checking the last search save function and storage accuracy")
    @Test
    public void CheckLastSearchQuerySaving() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSearchField(SEARCH_QUERY_UKRAINIAN)
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

    @Owner(BOHDAN)
    @Description("Checking the last search brand save function and storage accuracy")
    @Test
    public void CheckLastSearchBrandSaving() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSearchField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        String actualSearchQuery = searchPage.getSearchQuery();
        String expectedSearchQuery = format("Результати пошуку для: '%s'", SEARCH_QUERY_UKRAINIAN);
        Assert.assertEquals(actualSearchQuery, expectedSearchQuery,
                format("Expected message: %s, actual: %s", expectedSearchQuery, actualSearchQuery));

        String firstProductName = searchPage.getSearchedProductsNames().getFirst();
        Assert.assertTrue(firstProductName.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Expected that %s contains %s", firstProductName, SEARCH_QUERY_UKRAINIAN));


        searchPage.clickFirstSearchProduct();
        productPage.scrollToElement(PRODUCT_PARAMETERS)
                .clickOnTheButton(PRODUCT_PARAMETERS)
                .scrollToElement(PRODUCT_BRAND);

        String productBrand = productPage.getTextFrom(PRODUCT_BRAND);

        productPage.scrollToElement(BACK_ON_HOME_PAGE)
                .clickOnTheButton(BACK_ON_HOME_PAGE);
        homePage.clickSearchField();

        String expectedTitle = "Останні переглянуті бренди";
        String actualTitle = homePage.getLastVievedBrandsTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
                format("Expected that Title %s will have name %s", actualTitle, expectedTitle));

        String lastSearchedProductBrand = homePage.getAllLastSearchedProductsBrands().getLast();
        Assert.assertEquals(productBrand, lastSearchedProductBrand,
                format("Expected that brand %s will have name %s", lastSearchedProductBrand, productBrand));
    }

    @Owner(BOHDAN)
    @Description("Checking brand matching to his section on brands page list")
    @Test
    public void CheckBrandList() {
        HomePage homePage = new HomePage(driver);
        BrandsPage brandsPage = new BrandsPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickBrandsDropDown()
                .clickAllBrandsLink();

        List<String> sectionTitles = brandsPage.getAllBrandsTitleNames();

        for (String title : sectionTitles) {
            List<String> sectionBrands = brandsPage.getAllBrandNamesOfSection(title);
            sectionBrands.forEach(name -> Assert.assertTrue(name.toUpperCase().matches(String.format("^[%s].*", title)),
                    String.format("Expected that brand %s starts with %s", name, title)));
        }
    }

    @Owner(BOHDAN)
    @Description("Checking brand matching to his section on home page")
    @Test
    public void CheckBrandListHomePage() {
        HomePage homePage = new HomePage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickBrandsDropDown();

        List<String> sectionTitles = homePage.getAllBrandsTitleNames();

        for (String title : sectionTitles) {
            List<String> sectionBrands = homePage.getAllBrandNamesOfSection(title);
            sectionBrands.forEach(name -> Assert.assertTrue(name.toUpperCase().matches(String.format("^[%s].*", title)),
                    String.format("Expected that brand %s starts with %s", name, title)));
        }
    }

    @Owner(IGOR)
    @Test
    @Description("Open last viewed product via search")
    public void openLastViewedProductTest() {
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        ProductPage productPage = new ProductPage(driver);

        homePage.openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSearchField(SEARCH_QUERY_UKRAINIAN)
                .clickSearchButton();

        searchPage.scrollToFirstProduct()
                .clickFirstProduct();

        String expectedProductName = productPage.getTextFrom(PRODUCT_NAME);
        int expectedCurrentPrice = homePage.convertPriceToInt(productPage.getTextFrom(CURRENT_PRICE));
        int expectedRegularPrice = homePage.convertPriceToInt(productPage.getTextFrom(REGULAR_PRICE));

        productPage.clickOnTheButton(BACK_ON_HOME_PAGE);

        homePage.clickSearchField();

        String actualViewedProductTitle = homePage.getLastVievedProductsTitle();
        String actualViewedName = homePage.getHeaderFragment().getLastViewedProductPopUpInfo(LAST_VIEWED_PRODUCT_NAME);
        int actualCurrentPrice = homePage.convertPriceToInt(
                homePage.getHeaderFragment().getLastViewedProductPopUpInfo(LAST_VIEWED_PRODUCT_CURRENT_PRICE));
        int actualRegularPrice = homePage.convertPriceToInt(
                homePage.getHeaderFragment().getLastViewedProductPopUpInfo(LAST_VIEWED_PRODUCT_REGULAR_PRICE));

        Assert.assertEquals(actualViewedProductTitle, LAST_VIEWED_PRODUCT_TITLE,
                format("Expected title: %s, actual: %s", LAST_VIEWED_PRODUCT_TITLE, actualViewedProductTitle));
        Assert.assertEquals(actualViewedName, expectedProductName,
                format("Expected product name: %s, actual: %s", expectedProductName, actualViewedName));
        Assert.assertEquals(actualCurrentPrice, expectedCurrentPrice,
                format("Expected current price: %d, actual: %d", expectedCurrentPrice, actualCurrentPrice));
        Assert.assertEquals(actualRegularPrice, expectedRegularPrice,
                format("Expected regular price: %d, actual: %d", expectedRegularPrice, actualRegularPrice));
    }
}