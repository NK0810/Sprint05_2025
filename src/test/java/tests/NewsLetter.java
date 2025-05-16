package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class NewsLetter extends BaseTest {
    private static final String TEST_EMAIL = "test@email.com";

    @Description("Subscribe on newsletter")
    @Test
    public void SubscribeOnNewsletterTest() {

        HomePage homePage = new HomePage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .scrollToSubscribeOnNewsLetterBlock()
                .sendEmail(TEST_EMAIL)
                .clickRegistrationButton()
                .isPopUpVisible();

        Assert.assertTrue(homePage.isPopUpVisible(),
                "'Newsletter' window is not displayed");
        homePage
                .clickCheckBox()
                .clickRegistrationPopUp();

        Assert.assertTrue(homePage.isConfirmationMessageVisible(),
                        "Confirmation message not displayed");

    }
}

