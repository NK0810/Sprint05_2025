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
    private static final String DEFAULT_DELIVERY_ADDRESS_IN_INFO_BLOCK_REQUIRED_FIELDS_ONLY = buildAddress(
                    "NAME", "SURNAME", "STREET", "HOUSE_NUMBER", "POST_CODE", "CITY", "PHONE_NUMBER");

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