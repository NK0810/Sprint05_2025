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
    private static final String SUBSCRIPTION_BLOCK_XPATH = "//div[@class='modal-inner-wrap']";
    private static final String CONFIRMATION_MESSAGE_XPATH = "//*[contains(text(), 'Перевірте свою поштову скриньку та підтвердьте, що хочете отримувати Розсилку новин SPORTANO.')]";

    @Description("Subscribe on newsletter")
    @Test
    public void SubscribeOnNewsletterTest() {

        HomePage homePage = new HomePage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .scrollToSubscribeOnNewsLetterBlock()
                .sendEmail(TEST_EMAIL)
                .clickRegistration();

        WebElement newsletterWindow = driver.findElement(By.xpath(SUBSCRIPTION_BLOCK_XPATH));
                Assert.assertTrue(newsletterWindow.isDisplayed(),
                        "'Розсилка новин' вікно не відображається");

                homePage
                        .clickAgreamentCheckBox()
                        .clickRegistrationPopUp();

        WebElement confirmationMessage2 = driver.findElement(By.xpath(CONFIRMATION_MESSAGE_XPATH));

        Assert.assertTrue(confirmationMessage2.isDisplayed(),
                "Повідомлення підтвердження не відображається");


     }
    }