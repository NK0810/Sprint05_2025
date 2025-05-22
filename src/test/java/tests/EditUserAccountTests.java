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
    private static final String NAME = "Шмек";
    private static final String SURNAME = "Мельник";
    private static final String STREET = "Травнева";
    private static final String HOUSE_NUMBER = "8";
    private static final String POST_CODE = "10987";
    private static final String CITY = "Харків";
    private static final String PHONE_NUMBER = "0967693586";
    private static final List<String> DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_only = List.of(
            NAME, SURNAME, STREET, HOUSE_NUMBER, POST_CODE, CITY, PHONE_NUMBER);

    @Test
    @Description("Edit default delivery address required fields only")
    public void editDefaultDeliveryAddressRequiredFields() {
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        UserAddressesPage userAddressesPage = new UserAddressesPage(driver);

        loginPage
                .login(EMAIL, PASSWORD);

        userAccountPage
                .getCustomerSidebarFragment()
                .scrollToElement(ADDRESS_SECTION)
                .clickUserAccountElement(ADDRESS_SECTION);

        userAddressesPage
                .clickOnElementINUserAddressPage(EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON);

        String defaultDeliveryMessage = userAddressesPage.getEditElementInfo(DEFAULT_ADDRESS_MESSAGE);
        Assert.assertEquals(defaultDeliveryMessage, DEFAULT_DELIVERY_ADDRESS_MASSEGE,
                "Default delivery message is not visible or incorrect!");

        userAddressesPage
                .enterAddressInfo(NAME_INPUT_FIELD, NAME)
                .enterAddressInfo(SURNAME_INPUT_FIELD, SURNAME)
                .enterAddressInfo(STREET_INPUT_FIELD, STREET)
                .enterAddressInfo(HOUSE_NUMBER_INPUT_FIELD, HOUSE_NUMBER)
                .enterAddressInfo(POST_CODE_INPUT_FIELD, POST_CODE)
                .enterAddressInfo(CITY_INPUT_FIELD, CITY)
                .enterAddressInfo(PHONE_NUMBER_INPUT_FIELD, PHONE_NUMBER)
                .clickOnElementInEditAddress(SAVE_ADDRESS_BUTTON);

        List<String> actualAddress = userAddressesPage.getUserAddressInfoBlockTextAsList(DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK);
        Assert.assertEquals(actualAddress, DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_only, "Address data does not match expected!");

        String savedMessage = userAddressesPage.getUserAddressElementsText(ADDRESS_SAVED_MASSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED, "Address save message not shown!");
    }
}