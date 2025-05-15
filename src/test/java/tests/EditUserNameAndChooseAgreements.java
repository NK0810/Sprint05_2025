package tests;
import base.BaseTest;
import fragments.CookiesFragment;
import fragments.HeaderFragment;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.WishlistPage;
public class EditUserNameAndChooseAgreements extends BaseTest {
    private static final String TEST_EMAIL =  "tsdrk094@gmail.com";
private static final String TEST_PASS = "Test123!!!";
    @Description("Edit user name and choose agreements")
    @Test
    public void checkAdditionToWishlist() {
        HomePage homePage = new HomePage(driver);
        WishlistPage wishlistPage = new WishlistPage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickMyAccount()
                .clickLogin()
                .clickEmailInputField()
                .sendTestEmail(TEST_EMAIL)
                .clickTestPassField()
                .sendTestPass(TEST_PASS)
                .clickLoginButton()
                .clickAccountSetting();
    }
}


