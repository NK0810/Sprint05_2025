package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class SubscribeOnNewsLetterTest extends BaseTest {
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
                .RegistrationButton()
                .isPopUpVisible();

        Assert.assertTrue(homePage.isPopUpVisible(),
                        "'Newsletter' window is not displayed");
        homePage
                        .clickAgreamentCheckBox()
                        .clickRegistrationPopUp();

        Assert.assertTrue(homePage.isConfirmationMessageVisible(),
                "Confirmation message not displayed");


     }
    }