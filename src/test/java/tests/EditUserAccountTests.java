package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserAccountPage;
import pages.UserAddressesPage;
import utils.ConfigReader;

import static fragments.CustomerSidebarFragment.CustomerSidebarElements.*;
import static pages.EditAddressPage.EditAddressPageElements.*;
import static pages.UserAddressesPage.UserAddressesPageElements.*;
import static constant.Constant.EditAddressTestData.*;

public class EditUserAccountTests extends BaseTest {
    private static final String EMAIL = ConfigReader.getProperty("UserEmail");
    private static final String PASSWORD = ConfigReader.getProperty("UserPassword");
    private static final String DEFAULT_DELIVERY_ADDRESS_MASSEGE = "Це адреса доставки за умовчанням.";
    private static final String DEFAULT_PAYMENT_ADDRESS_MASSEGE = "Це платіжна адреса за умовчанням.";
    private static final String ADDRESS_SAVED = "Адресу збережено.";
    private static final String ADDRESS_DELETED = "Ви видалили адресу.";
    private static final String DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY =
            UserAddressesPage.buildAddress(
                    "NAME", "SURNAME", "STREET", "HOUSE_NUMBER", "POST_CODE", "CITY", "PHONE_NUMBER");
    private static final String OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK =
            UserAddressesPage.buildAddress(
            "NAME", "SURNAME", "STREET", "HOUSE_NUMBER", "APARTMENT_NUMBER", "POST_CODE", "CITY", "PHONE_NUMBER");

    @Test
    @Description("Edit default delivery address required fields only")
    public void editDefaultDeliveryAddressRequiredFieldsTest() {
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        UserAddressesPage userAddressesPage = new UserAddressesPage(driver);

        loginPage.login(EMAIL, PASSWORD);
        userAccountPage.getCustomerSidebarFragment()
                .scrollToElement(ADDRESS_SECTION)
                .clickUserAccountElement(ADDRESS_SECTION);
        userAddressesPage.clickOnElement(EDIT_DEFAULT_DELIVERY_ADDRESS_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON);

        String defaultDeliveryMessage = userAddressesPage.getElementText(DEFAULT_ADDRESS_MESSAGE);
        Assert.assertEquals(defaultDeliveryMessage, DEFAULT_DELIVERY_ADDRESS_MASSEGE,
                String.format("Expected delivery message: '%s', but got: '%s'",
                        DEFAULT_DELIVERY_ADDRESS_MASSEGE, defaultDeliveryMessage));

        userAddressesPage.enterAddressInfo(NAME_INPUT_FIELD, NAME)
                .enterAddressInfo(SURNAME_INPUT_FIELD, SURNAME)
                .enterAddressInfo(STREET_INPUT_FIELD, STREET)
                .enterAddressInfo(HOUSE_NUMBER_INPUT_FIELD, HOUSE_NUMBER)
                .enterAddressInfo(POST_CODE_INPUT_FIELD, POST_CODE)
                .enterAddressInfo(CITY_INPUT_FIELD, CITY)
                .enterAddressInfo(PHONE_NUMBER_INPUT_FIELD, PHONE_NUMBER)
                .clickOnElement(SAVE_ADDRESS_BUTTON);

        String actualAddress = userAddressesPage.convertAddressBlock(
                userAddressesPage.getElementText(DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK)
        );
        Assert.assertEquals(actualAddress, DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY,
                String.format("Expected address: '%s', but got: '%s'",
                        DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY, actualAddress));

        String savedMessage = userAddressesPage.getElementText(ADDRESS_SAVED_MASSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_SAVED, savedMessage));
    }

    @Test
    @Description("Create and delete other private payment address")
    public void createAndDeleteOtherPaymentAddressTest() {
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        UserAddressesPage userAddressesPage = new UserAddressesPage(driver);

        loginPage.login(EMAIL, PASSWORD);
        userAccountPage.getCustomerSidebarFragment()
                .scrollToElement(ADDRESS_SECTION)
                .clickUserAccountElement(ADDRESS_SECTION);
        userAddressesPage.scrollToElement(ADD_DELIVARY_ADDRESS_BUTTON)
                .clickOnElement(ADD_DELIVARY_ADDRESS_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON)
                .enterAddressInfo(SURNAME_INPUT_FIELD, SURNAME)
                .enterAddressInfo(STREET_INPUT_FIELD, STREET)
                .enterAddressInfo(HOUSE_NUMBER_INPUT_FIELD, HOUSE_NUMBER)
                .enterAddressInfo(APARTMENT_NUMBER_INPUT_FIELD, APARTMENT_NUMBER)
                .enterAddressInfo(POST_CODE_INPUT_FIELD, POST_CODE)
                .enterAddressInfo(CITY_INPUT_FIELD, CITY)
                .enterAddressInfo(PHONE_NUMBER_INPUT_FIELD, PHONE_NUMBER)
                .clickOnElement(SAVE_ADDRESS_BUTTON)
                .scrollToElement(ADD_DELIVARY_ADDRESS_BUTTON);
        String actualAddress = userAddressesPage.convertAddressBlock(
                userAddressesPage.getElementText(OTHER_DELIVERY_ADDRESS_INFO_BLOCK_1)
        );
        Assert.assertEquals(actualAddress, OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK,
                String.format("Expected address: '%s', but got: '%s'",
                        OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK, actualAddress));

        String savedMessageCreation = userAddressesPage.getElementText(ADDRESS_SAVED_MASSAGE_TEXT);
        Assert.assertEquals(savedMessageCreation, ADDRESS_SAVED,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_SAVED, savedMessageCreation));

        userAddressesPage.scrollToElement(ADD_DELIVARY_ADDRESS_BUTTON)
                .clickOnElement(DELETE_OTHER_DELIVERY_ADDRESS_BUTTON_1)
                .clickOnElement(DELETE_ADDRESS_BUTTON_IN_POP_UP);

        String savedMessageDelete = userAddressesPage.getElementText(ADDRESS_SAVED_MASSAGE_TEXT);
        Assert.assertEquals(savedMessageDelete, ADDRESS_DELETED,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_DELETED, savedMessageDelete));
    }
}