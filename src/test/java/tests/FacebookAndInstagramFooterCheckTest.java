package tests;

import base.BaseTest;
import fragments.FooterFragment;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class FacebookAndInstagramFooterCheckTest extends BaseTest {

    @Test
    @Description("Перевірка переходів по іконках Facebook та Instagram у футері сайту")
    public void facebookAndInstagramFooters() {
        String originalWindow = driver.getWindowHandle();
        HomePage homePage = new HomePage(driver);
        FooterFragment footerFragment = new FooterFragment(driver);

        homePage.openUrl()
                .acceptCookies();
        footerFragment.scrollToFooter()
                .clickOnTheFacebookButton();
        homePage.switchToNewTab();

        homePage.urlContainsExpected("https://www.facebook.com");

        driver.close();
        driver.switchTo().window(originalWindow);

        footerFragment.clickOnTheInstagramButton();
        homePage.switchToNewTab();

        homePage.urlContainsExpected("https://www.instagram.com");

        driver.close();
        driver.switchTo().window(originalWindow);
    }
}