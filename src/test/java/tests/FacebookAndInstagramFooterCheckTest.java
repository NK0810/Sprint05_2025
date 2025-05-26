package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import pages.HomePage;
import static constant.Constant.Owners.MAKS;

public class FacebookAndInstagramFooterCheckTest extends BaseTest {
    private static final String FACEBOOK_URL = "https://www.facebook.com";
    private static final String INSTAGRAM_URL = "https://www.instagram.com";

    @Owner(MAKS)
    @Test
    @Description("Checking clicks on Facebook and Instagram icons in the footer of a website")
    public void facebookAndInstagramFooters() {
        String originalWindow = driver.getWindowHandle();
        HomePage homePage = new HomePage(driver);

        homePage.openUrl()
                .acceptCookies()
                .getFooterFragment().scrollToFooter()
                .clickOnTheFacebookButton();
        homePage.switchToNewTab();

        homePage.urlContainsExpected(FACEBOOK_URL);

        driver.close();
        driver.switchTo().window(originalWindow);

        homePage.getFooterFragment().clickOnTheInstagramButton();
        homePage.switchToNewTab();

        homePage.urlContainsExpected(INSTAGRAM_URL);

        driver.close();
        driver.switchTo().window(originalWindow);
    }
}