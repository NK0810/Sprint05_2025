package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserAccountPage;
import pages.UserAddressesPage;
import utils.ConfigReader;

import java.util.List;

import static fragments.CustomerSidebarFragment.CustomerSidebarElements.*;
import static pages.EditAddressPage.EditAddressPageElements.*;
import static pages.UserAddressesPage.UserAddressesPageElements.*;

public class EditUserAccountTests extends BaseTest {
    private static final String EMAIL = ConfigReader.getProperty("UserEmail");
    private static final String PASSWORD = ConfigReader.getProperty("UserPassword");
    private static final String DEFAULT_DELIVERY_ADDRESS_MASSEGE = "Це адреса доставки за умовчанням.";
    private static final String DEFAULT_PAYMENT_ADDRESS_MASSEGE = "Це платіжна адреса за умовчанням.";
    private static final String ADDRESS_SAVED = "Адресу збережено.";
    private static final List<String> DEFAULT_DELIVERY_ADDRESS_DATA = List.of(
            "Шмек", "Мельник", "Травнева", "8", "10987", "Харків", "0967693586"
    );

    @Test
    @Description("Edit default delivery address required fields only")
    public void editDefaultDeliveryAddressRequiredFields() {
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        UserAddressesPage userAddressesPage = new UserAddressesPage(driver);

        List<String> expectedAddress = DEFAULT_DELIVERY_ADDRESS_DATA;

        loginPage.openLoginPage()
                .acceptCookies()
                .enterEmail(EMAIL)
                .enterPassword(PASSWORD)
                .clickLogInButton();

        userAccountPage.waitUntilMyAccountPageIsVisible();

        userAccountPage
                .getCustomerSidebarFragment()
                .scrollToElement(ADDRESS_SECTION)
                .clickUserAccountElement(ADDRESS_SECTION);

        userAddressesPage
                .clickOnUserAddressPageElement(EDIT_DEAFAULT_DELIVERY_ADDRESS_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON);

        String defaultDeliveryMessage = userAddressesPage.getEditElementInfo(DEFAULT_ADDRESS_MESSAGE);
        Assert.assertEquals(defaultDeliveryMessage, DEFAULT_DELIVERY_ADDRESS_MASSEGE,
                "Default delivery message is not visible or incorrect!");

        userAddressesPage
                .enterAddressInfo(NAME_INPUT_FIELD, expectedAddress.get(0))
                .enterAddressInfo(SURNAME_INPUT_FIELD, expectedAddress.get(1))
                .enterAddressInfo(STREET_INPUT_FIELD, expectedAddress.get(2))
                .enterAddressInfo(HOUSE_NUMBER_INPUT_FIELD, expectedAddress.get(3))
                .enterAddressInfo(POST_CODE_INPUT_FIELD, expectedAddress.get(4))
                .enterAddressInfo(CITY_INPUT_FIELD, expectedAddress.get(5))
                .enterAddressInfo(PHONE_NUMBER_INPUT_FIELD, expectedAddress.get(6))
                .clickOnEditAddressElement(SAVE_ADDRESS_BUTTON);

        List<String> actualAddress = userAddressesPage.getUserAddressInfoText(DEAFAULT_DELIVERY_ADDRESS_INFO_BLOCK);
        Assert.assertEquals(actualAddress, expectedAddress, "Address data does not match expected!");

        String savedMessage = userAddressesPage.getUserAddressElementsText(ADDRESS_SAVED_MASSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED, "Address save message not shown!");
    }
}