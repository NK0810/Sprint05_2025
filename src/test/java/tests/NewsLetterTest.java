package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;
import static constant.Constant.Owners.TARAS;

public class NewsLetterTest extends BaseTest {

    @Owner(TARAS)
    @Description("Subscribe on newsletter")
    @Test
    public void NewsletterTest() {

        HomePage homePage = new HomePage(driver);

        homePage.openUrl()
                .acceptCookies()
                .scrollToSubscribeOnNewsLetterBlock()
                .enterEmail(ConfigReader.getProperty("email.test"))
                .clickRegistrationButton()
                .isPopUpVisible();

        Assert.assertTrue(homePage.isPopUpVisible(),"'Newsletter' window is not displayed");
        homePage.clickCheckBoxOnPopUp()
                .clickRegistratiOnPopUp();

        Assert.assertTrue(homePage.isConfirmationRegistrationMessageVisible(),
                        "Confirmation message not displayed");

    }
}

