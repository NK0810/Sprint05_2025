package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserAccountPage;
import pages.UserAddressesPage;
import utils.ConfigReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static fragments.CustomerSidebarFragment.CustomerSidebarElements.*;
import static pages.UserAddressesPage.EditAddressPageElements.*;
import static pages.UserAddressesPage.UserAddressesPageElements.*;

public class EditUserAccountTests extends BaseTest {
    private static final String EMAIL = ConfigReader.getProperty("UserEmail");
    private static final String PASSWORD = ConfigReader.getProperty("UserPassword");
    private static final String DEFAULT_DELIVERY_ADDRESS_MESSEGE = "Це адреса доставки за умовчанням.";
    private static final String DEFAULT_PAYMENT_ADDRESS_MASSEGE = "Це платіжна адреса за умовчанням.";
    private static final String ADDRESS_SAVED_MESSEGE = "Адресу збережено.";
    private static final String ADDRESS_DELETED_MESSEGE = "Ви видалили адресу.";
    private static final String NAME = "Шмек";
    private static final String SURNAME = "Мельник";
    private static final String TAX_IDENTIFICATION_NUMBER = "7815516551";
    private static final String COMPANY_NAME = "MONSTER.INC";
    private static final String STREET = "Травнева";
    private static final String HOUSE_NUMBER = "8";
    private static final String APARTMENT_NUMBER = "16";
    private static final String POST_CODE = "10987";
    private static final String CITY = "Харків";
    private static final String PHONE_NUMBER = "0967693586";
    private static final String DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY = buildAddress(
                    "NAME", "SURNAME", "STREET", "HOUSE_NUMBER", "POST_CODE", "CITY", "PHONE_NUMBER");
    private static final String DEFAULT_COMPANY_PAYMENT_ADDRESS_IN_INFO_BLOCK = buildAddress(
            "NAME", "SURNAME", "COMPANY_NAME", "TAX_IDENTIFICATION_NUMBER", "STREET", "HOUSE_NUMBER", "APARTMENT_NUMBER", "POST_CODE", "CITY");
    private static final String OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK = buildAddress(
                    "NAME", "SURNAME", "STREET", "HOUSE_NUMBER", "APARTMENT_NUMBER", "POST_CODE", "CITY");

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
        Assert.assertEquals(defaultDeliveryMessage, DEFAULT_DELIVERY_ADDRESS_MESSEGE,
                String.format("Expected delivery message: '%s', but got: '%s'",
                        DEFAULT_DELIVERY_ADDRESS_MESSEGE, defaultDeliveryMessage));

        userAddressesPage.enterAddressInfo(UserAddressesPage.deliveryAddressRequieredFields(
                NAME, SURNAME, STREET, HOUSE_NUMBER, POST_CODE, CITY, PHONE_NUMBER));

        String actualAddress = userAddressesPage.convertAddressBlock(
                userAddressesPage.getElementText(DEFAULT_DELIVERY_ADDRESS_INFO_BLOCK)
        );
        Assert.assertEquals(actualAddress, DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY,
                String.format("Expected address: '%s', but got: '%s'",
                        DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY, actualAddress));

        String savedMessage = userAddressesPage.getElementText(ADDRESS_SAVED_MESSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED_MESSEGE,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_SAVED_MESSEGE, savedMessage));
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
        userAddressesPage.scrollToElement(ADD_PAYMENT_ADDRESS_BUTTON)
                .clickOnElement(ADD_PAYMENT_ADDRESS_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON)
                .enterAddressInfo(UserAddressesPage.paymentAddress(
                        NAME, SURNAME, STREET, HOUSE_NUMBER, APARTMENT_NUMBER, POST_CODE, CITY))
                .scrollToElement(ADD_PAYMENT_ADDRESS_BUTTON);
        String actualAddress = userAddressesPage.convertAddressBlock(
                userAddressesPage.getElementText(OTHER_PAYMENT_ADDRESS_INFO_BLOCK_1)
        );
        Assert.assertEquals(actualAddress, OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK,
                String.format("Expected address: '%s', but got: '%s'",
                        OTHER_PAYMENT_ADDRESS_IN_INFO_BLOCK, actualAddress));

        String savedMessage = userAddressesPage.getElementText(ADDRESS_SAVED_MESSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED_MESSEGE,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_SAVED_MESSEGE, savedMessage));

        userAddressesPage.clickOnElement(CLOSE_MESSAGE_BUTTON)
                .scrollToElement(ADD_PAYMENT_ADDRESS_BUTTON)
                .clickOnElement(DELETE_OTHER_PAYMENT_ADDRESS_ADDRESS_BUTTON_1)
                .clickOnElement(DELETE_ADDRESS_BUTTON_IN_POP_UP);

        String deletedMessage = userAddressesPage.getElementText(ADDRESS_SAVED_MESSAGE_TEXT);
        Assert.assertEquals(deletedMessage, ADDRESS_DELETED_MESSEGE,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_DELETED_MESSEGE, deletedMessage));
    }

    @Test
    @Description("Edit default company payment address")
    public void editDefaultCompanyPaymentAddressTest() {
        LoginPage loginPage = new LoginPage(driver);
        UserAccountPage userAccountPage = new UserAccountPage(driver);
        UserAddressesPage userAddressesPage = new UserAddressesPage(driver);

        loginPage.login(EMAIL, PASSWORD);
        userAccountPage.getCustomerSidebarFragment()
                .scrollToElement(ADDRESS_SECTION)
                .clickUserAccountElement(ADDRESS_SECTION);
        userAddressesPage.clickOnElement(EDIT_DEFAULT_PAYMENT_ADDRESS_BUTTON)
                .clickOnElement(COMPANY_RADIO_BUTTON)
                .scrollToElement(SAVE_ADDRESS_BUTTON);

        String defaultDeliveryMessage = userAddressesPage.getElementText(DEFAULT_ADDRESS_MESSAGE);
        Assert.assertEquals(defaultDeliveryMessage, DEFAULT_PAYMENT_ADDRESS_MASSEGE,
                String.format("Expected delivery message: '%s', but got: '%s'",
                        DEFAULT_PAYMENT_ADDRESS_MASSEGE, defaultDeliveryMessage));

                userAddressesPage.enterAddressInfo(NAME_INPUT_FIELD, NAME)
                .enterAddressInfo(SURNAME_INPUT_FIELD, SURNAME)
                .enterAddressInfo(COMPANY_NAME_INPUT_FIELD, COMPANY_NAME)
                .enterAddressInfo(TAX_IDENTIFICATION_NUMBER_INPUT_FIELD, TAX_IDENTIFICATION_NUMBER)
                .enterAddressInfo(SURNAME_INPUT_FIELD, SURNAME)
                .enterAddressInfo(STREET_INPUT_FIELD, STREET)
                .enterAddressInfo(HOUSE_NUMBER_INPUT_FIELD, HOUSE_NUMBER)
                .enterAddressInfo(APARTMENT_NUMBER_INPUT_FIELD, APARTMENT_NUMBER)
                .enterAddressInfo(POST_CODE_INPUT_FIELD, POST_CODE)
                .enterAddressInfo(CITY_INPUT_FIELD, CITY)
                .clickOnElement(SAVE_ADDRESS_BUTTON);

        String actualAddress = userAddressesPage.convertAddressBlock(
                userAddressesPage.getElementText(DEFAULT_PAYMENT_ADDRESS_INFO_BLOCK));

        Assert.assertEquals(actualAddress, DEFAULT_COMPANY_PAYMENT_ADDRESS_IN_INFO_BLOCK,
                String.format("Expected address: '%s', but got: '%s'",
                        DEFAULT_COMPANY_PAYMENT_ADDRESS_IN_INFO_BLOCK, actualAddress));

        String savedMessage = userAddressesPage.getElementText(ADDRESS_SAVED_MESSAGE_TEXT);
        Assert.assertEquals(savedMessage, ADDRESS_SAVED,
                String.format("Expected save message: '%s', but got: '%s'",
                        ADDRESS_SAVED, savedMessage));
    }

    @Step("Building specific expected address in String")
    public static String buildAddress(String... fieldNames) {
        List<String> values = new ArrayList<>();
        for (String name : fieldNames) {
            try {
                Field field = EditUserAccountTests.class.getDeclaredField(name);
                values.add((String) field.get(null));
            } catch (Exception e) {
                throw new RuntimeException("Can't read field: " + name, e);
            }
        }
        return String.join(" ", values);
    }
}