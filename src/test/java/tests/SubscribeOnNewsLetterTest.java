package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;

public class SubscribeOnNewsLetterTest extends BaseTest {
    private static final String TEST_EMAIL = "test@email.com";

    @Description("Subscribe on newsletter")
    @Test
    public void SubscribeOnNewsletter() {

        HomePage homePage = new HomePage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .scrollToSubscribeOnNewsletter()
                .sendEmailInNewsletterField(TEST_EMAIL)
                .clickRegistrationButton()
                .clickCheckBoxLabel()
                .clickRegistrationAfterCheckBox();

        String actualMessage = homePage.getNewsletterConfirmationMessage();

        Assert.assertEquals(
                actualMessage,
                "Перевірте свою поштову скриньку та підтвердьте, що хочете отримувати Розсилку новин SPORTANO.",
                "Expected newsletter confirmation message is not shown"
        );
    }


}