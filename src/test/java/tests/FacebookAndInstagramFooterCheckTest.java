package tests;

import base.BaseTest;
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
        homePage.openUrl()
                .acceptCookies()
                .scrollToFooter()
                .clickOnTheFacebookButton()
                .switchToNewTab();

        Assert.assertTrue(homePage.urlFacebook());

        driver.close();
        driver.switchTo().window(originalWindow);

        homePage.clickOnTheInstagramButton()
                .switchToNewTab();

        Assert.assertTrue(homePage.urlInstagram());

        driver.close();
        driver.switchTo().window(originalWindow);
    }
}