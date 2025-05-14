package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

import java.util.List;

import static java.lang.String.format;

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
                format("Очікувалось повідомленняЬф %s, актуальний %s", expectedSearchQuery, actualSearchQuery));

        List<String> productNames = searchPage.getSearchedProductsNames();
        productNames.forEach(name -> Assert.assertTrue(name.toLowerCase().contains(SEARCH_QUERY_UKRAINIAN.toLowerCase()),
                format("Очікувалось що %s містить %s", name, SEARCH_QUERY_UKRAINIAN)));
    }
}
