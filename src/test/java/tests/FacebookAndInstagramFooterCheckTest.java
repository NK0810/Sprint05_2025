package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import static constant.Constant.Owners.MAKS;

public class FacebookAndInstagramFooterCheckTest extends BaseTest {
    private static final String FACEBOOK_URL = "https://www.facebook.com";
    private static final String INSTAGRAM_URL = "https://www.instagram.com";

    @Owner(MAKS)
    @Test
    @Description("Checking clicks on Facebook and Instagram icons in the footer of a website")
    public void facebookAndInstagramFooters(){
        HomePage homePage = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();

        homePage.openUrl()
                .acceptCookies()
                .getFooterFragment()
                .scrollToFooterSocials()
                .clickOnTheFacebookButton();
        homePage.switchToNewTab();

        Assert.assertTrue(homePage.getSiteUrl().contains(FACEBOOK_URL));

        homePage.switchToOriginalPage(originalWindow);
        homePage.getFooterFragment()
                .clickOnTheInstagramButton();
        homePage.switchToNewTab();

        Assert.assertTrue(homePage.getSiteUrl().contains(INSTAGRAM_URL));
    }
}