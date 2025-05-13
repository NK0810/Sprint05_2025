package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

import java.util.List;

public class SearchProductsViaSearchBarTest extends BaseTest {
    private static final String SEARCH_TEXT_UKRAINIAN = "Кросівки";

    @Description("Checking the search function and search result accuracy")
    @Test
    public void CheckSearchWithCorrectQueryUkr(){
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickSearchField()
                .enterTextInSeachField(SEARCH_TEXT_UKRAINIAN)
                .clickSearchButton();

        List<String> productNames = searchPage.getSearchedProductsNames();

        Assert.assertTrue(
                    productNames.stream().allMatch( tag -> tag.toLowerCase().contains(SEARCH_TEXT_UKRAINIAN.toLowerCase())), "Not all products match the search query"
        );
    }
}
