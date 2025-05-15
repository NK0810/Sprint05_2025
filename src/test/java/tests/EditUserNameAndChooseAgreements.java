package tests;
import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserAccountPage;
import pages.WishlistPage;
public class EditUserNameAndChooseAgreements extends BaseTest {
    private static final String TEST_EMAIL = "tsdrk094@gmail.com";
    private static final String TEST_PASS = "Test123!!!";
    private static final String BASE_NAME = "Anton";
    private static final String BASE_SURNAME = "Skvortsov";
    private static final String ERROR_MESSAGE = "The save message did not appear or is incorrect.";
    private static final String EXPECTED_RESULT = "Your Account data has been saved.";

    private static String generateRandomLetter() {
        return String.valueOf((char) ('A' + (int) (Math.random() * 26)));
    }

    private static final String NEW_TEST_NAME = BASE_NAME + generateRandomLetter();
    private static final String NEW_TEST_SURNAME = BASE_SURNAME + generateRandomLetter();

    @Description("Edit user name and choose agreements")
    @Test
    public void ChangeNameAndSurnameInProfile() {
        HomePage homePage = new HomePage(driver);

        homePage
                .openUrl()
                .acceptCookies()
                .clickMyAccount()
                .clickLogin()
                .clickEmailInputField()
                .sendTestEmail(TEST_EMAIL)
                .clickTestPassField()
                .sendTestPass(TEST_PASS)
                .clickLoginButton();


        UserAccountPage userAccountPage = new UserAccountPage(driver);

        userAccountPage

                .clickAccountSetting()
                .clickAndClearFirstNameField()
                .sendNewTestName(NEW_TEST_NAME)
                .clickAndClearSurnameField()
                .sendNewTestSurname(NEW_TEST_SURNAME)
                .clickCheckBox1()
                .clickCheckBox2()
                .clickSaveChange();
        String successMessage = userAccountPage.getSuccessMessageText().trim();

        Assert.assertEquals(successMessage,EXPECTED_RESULT,ERROR_MESSAGE);

        userAccountPage
                .clickAccountSetting();

        String actualName = userAccountPage.getFirstNameFieldText();
        String actualSurname = userAccountPage.getSurnameFieldText();

        Assert.assertEquals(actualName, NEW_TEST_NAME, "Name not updated correctly.");
        Assert.assertEquals(actualSurname, NEW_TEST_SURNAME, "Last name not updated correctly.");
    }
}

