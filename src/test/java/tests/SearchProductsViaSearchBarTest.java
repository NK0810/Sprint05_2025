package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;

public class SearchProductsViaSearchBarTest extends BaseTest {

    private static final String SEARCH_TEXT_UKRAINIAN = "Кросівки";

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

        Assert.assertTrue(searchPage.isProductsMatchesToSearchText(SEARCH_TEXT_UKRAINIAN),
                "Not all products match the search query");
    }
}
