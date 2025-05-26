package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.BasePage;

public class FooterFragment extends BasePage<FooterFragment> {
    public FooterFragment(WebDriver driver) { super(driver); }

    private static final String FACEBOOK_FOOTER_BUTTON = ("//img[@src='https://sportano.ua/media/footer/facebook.png']");
    private static final String INSTAGRAM_FOOTER_BUTTON = ("//img[@src='https://sportano.ua/media/footer/instagram.png']");
    private static final String FOOTER_SOCIALS = ("//div[@class='footer-bottom__socials']");

    @Step("Click on the facebook button")
    public FooterFragment clickOnTheFacebookButton () {
        waitElementToBeClickable(By.xpath(FACEBOOK_FOOTER_BUTTON)).click();
        return this;
    }

    @Step("Click on the instagram button")
    public FooterFragment clickOnTheInstagramButton() {
        waitElementToBeClickable(By.xpath(INSTAGRAM_FOOTER_BUTTON)).click();
        return this;
    }

    @Step("Scroll to the footer")
    public FooterFragment scrollToFooterSocials() {
        scrollToElement(By.xpath(FOOTER_SOCIALS));
        return this;
    }
}
