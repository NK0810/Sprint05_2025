package tests;

import base.BaseTest;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;
import pages.ProductPage;

import java.util.List;
import java.util.stream.Collectors;

import static constant.Constant.Owners.IGOR;
import static constant.Constant.Owners.NAZAR;
import static pages.ManClothingPage.BrandName.*;
import static pages.ManClothingPage.FilterOptionManClothingPage.SEASON_DROP_DOWN_BUTTON;
import static pages.ManClothingPage.FilterOptionManClothingPage.SIZE_DROP_DOWN_BUTTON;
import static pages.ProductCatalogPage.BrandFilter.BRANDS_SEARCH_FIELD;
import static pages.ProductCatalogPage.FilterClearButton.*;
import static pages.ProductCatalogPage.FilterOption.*;
import static pages.ProductCatalogPage.PriceFilter.MAX_PRICE_INPUT_FIELD;
import static pages.ProductCatalogPage.PriceFilter.MIN_PRICE_INPUT_FIELD;
import static pages.ProductCatalogPage.ProductCardInfo.*;
import static pages.ProductPage.ProductPageElements.*;

public class ProductFilterTests extends BaseTest {
    ManClothingPage manClothingPage = new ManClothingPage(driver);

    private static final String MAX_PRICE_INPUT = "1000";
    private static final String MIN_PRICE_INPUT = "200";
    private static final int MAX_ALLOWED_PRICE = 1000;
    private static final int MIN_ALLOWED_PRICE = 200;
    private static final String SIZE_S = "S";
    public static final String SELECTED_CLASS = "refinement-label--checked";
    private final static String SEASON = "Весна/літо";
    private static final String SIZE_2XL = "2XL";

    @Owner(NAZAR)
    @Description("Verify that filtering by 'New arrivals' only displays products marked.")
    @Test
    public void filterByNewArrivalsTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .scrollToElement(NEW_ARRIVALS)
                .selectFilterOption(NEW_ARRIVALS)
                .waitProductsInfoAreUpdated(NEW_TAG);

        List<String> productNewTags = manClothingPage.getVisibleProductsInfoTexts(NEW_TAG);

        List<String> noNewTagProducts = productNewTags.stream()
                .filter(tag -> !tag.contains("Новий"))
                .collect(Collectors.toList());

        Assert.assertEquals(noNewTagProducts.size(), 0,
                "Some products do not have the 'Новий' tag: " + noNewTagProducts);
    }

    @Owner(NAZAR)
    @Description("Verify that the discount filter correctly displays only products with active discounts.")
    @Test
    public void filterBySalesTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .scrollToElement(SALE)
                .selectFilterOption(SALE)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> actualPrices = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);
        List<String> regularPrices = manClothingPage.getVisibleProductsInfoTexts(REGULAR_DISCOUNT);

        boolean discountApplied = isDiscountApplied(regularPrices, actualPrices);
        Assert.assertEquals(discountApplied, true,
                "Discount not applied: discounted price is not lower than the regular price");
    }

    private boolean isDiscountApplied(List<String> actualPrice, List<String> regularPrice) {

        for (int i = 0; i < regularPrice.size(); i++) {
            if (manClothingPage.convertPriceToInt(regularPrice.get(i)) < manClothingPage.convertPriceToInt(actualPrice.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Owner(IGOR)
    @Description("Verify only brand-specific products are shown after applying brand filter using search for brand filter")
    @Test
    public void filteredProductsByBrandViaSearchTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup()
                .scrollToElement(BRAND_DROPDOWN)
                .selectFilterOption(BRAND_DROPDOWN)
                .typeBrandNameInSearch(ALPINUS, BRANDS_SEARCH_FIELD)
                .selectBrandOption(ALPINUS)
                .waitProductsInfoAreUpdated(PRODUCTS_NAME);

        List<String> productsName = manClothingPage.getVisibleProductsInfoTexts(PRODUCTS_NAME);

        List<String> invalidNames = productsName.stream()
                .filter(name -> !name.toLowerCase().contains(ALPINUS.getValue().toLowerCase()))
                .toList();

        Assert.assertEquals(invalidNames.size(), 0,
                "Some products do not contain expected brand name '" + ALPINUS.getValue() + "': " + invalidNames);
    }

    @Owner(NAZAR)
    @Description("Verify that after removing the price filter, the full product list is restored to its original state.")
    @Test
    public void shouldRestoreProductListAfterRemovingPriceFilterTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup();

        List<String> nonFilteredPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        manClothingPage
                .scrollToElement(LAST_PIECES)
                .selectFilterOption(PRICE_DROPDOWN)
                .typePriceInInput(MAX_PRICE_INPUT_FIELD, MAX_PRICE_INPUT)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> filteredPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        Assert.assertTrue(isAllPricesLessThan(filteredPrice, MAX_ALLOWED_PRICE), "Expected at least one price < " + MAX_ALLOWED_PRICE + ", but actual prices were: " + filteredPrice);

        manClothingPage
                .clickClearFilterButton(CLEAR_PRICE_FILTER_BUTTON)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        Assert.assertEquals(nonFilteredPrice, manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE));

    }

    private boolean isAllPricesLessThan(List<String> actualPrice, int price) {
        List<Integer> intPrices = actualPrice.stream()
                .map(s -> s.replaceAll("[^0-9]", ""))
                .map(s -> s.substring(0, s.length() - 2))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return intPrices.stream().allMatch(element -> element < price);
    }

    @Owner(NAZAR)
    @Description("Check that products are correctly filtered by price and brand (Adidas), and that clearing the brand filter removes Adidas products from the results.")
    @Test
    public void checkClearFilterBrandAndLowPriceTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup()
                .scrollToElement(LAST_PIECES)
                .selectFilterOption(PRICE_DROPDOWN)
                .typePriceInInput(MIN_PRICE_INPUT_FIELD, MIN_PRICE_INPUT)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> filteredPrices = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        Assert.assertTrue(isAllPricesMoreThan(filteredPrices, MIN_ALLOWED_PRICE), "Expected at least one price > " + MIN_ALLOWED_PRICE + ", but actual prices were: " + filteredPrices);

        manClothingPage
                .selectFilterOption(BRAND_DROPDOWN)
                .typeBrandNameInSearch(ADIDAS, BRANDS_SEARCH_FIELD)
                .selectBrandOption(ADIDAS)
                .waitProductsInfoAreUpdated(PRODUCTS_NAME);

        Assert.assertTrue(manClothingPage.getVisibleProductsInfoTexts(PRODUCTS_NAME)
                        .stream()
                        .anyMatch(element -> element.contains(ADIDAS.getValue())),
                "Expected at least one product name to contain" + ADIDAS.getValue() + ", but none were found.");

        manClothingPage
                .clickClearFilterButton(CLEAR_BRAND_FILTER_BUTTON);

        Assert.assertFalse(manClothingPage.getVisibleProductsInfoTexts(PRODUCTS_NAME)
                        .stream()
                        .anyMatch(element -> element.contains(ADIDAS.getValue())),
                "Expected no product names to contain" + ADIDAS.getValue() + ", but some were found.");
    }

    private boolean isAllPricesMoreThan(List<String> actualPrice, int price) {
        List<Integer> intPrices = actualPrice.stream()
                .map(s -> s.replaceAll("[^0-9]", ""))
                .map(s -> s.substring(0, s.length() - 2))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return intPrices.stream().allMatch(element -> element > price);
    }

    @Owner(NAZAR)
    @Description("Verify that the selected size filter is correctly applied and that all visible products have the selected size available (not disabled or out of stock).")
    @Test
    public void checkSizeFilterTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);
        ProductPage productPage = new ProductPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup()
                .scrollToElement(LAST_PIECES);
        manClothingPage
                .clickOnDropdownButton(SIZE_DROP_DOWN_BUTTON)
                .selectSizeFilterOption(SIZE_S);

        String selectedSizeLabelClass = manClothingPage.getSelectedSizeLabelClass();

        Assert.assertTrue(selectedSizeLabelClass.contains(SELECTED_CLASS),
                "Expected selected size label to contain class: " + SELECTED_CLASS + " but got: " + selectedSizeLabelClass);

        List<WebElement> productsCard = manClothingPage.waitForVisibleProductCards(PRODUCTS_CARD);

        for (int i = 0; i < productsCard.size(); i++) {
            WebElement currentProduct = manClothingPage.waitForVisibleProductCards(PRODUCTS_CARD).get(i);

            manClothingPage.scrollToElement(currentProduct);
            currentProduct.click();
            productPage.clickOnTheButton(SELECT_SIZE_DROP_DOWN_BUTTON);

            String sizeOptionClass = productPage.getSizeOptionClass(SIZE_S);

            Assert.assertFalse(sizeOptionClass.contains("visual-ko-select__option--disabled stock-notification-trigger"),
                    "Expected size option to be available, but class contains disallowed value: " + sizeOptionClass);

            productPage.goBack();
        }
    }

    @Owner(NAZAR)
    @Description("Verify that after applying the 'Season' filter, all displayed products have the correct season value in their parameters.")
    @Test
    public void seasonFilterTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);
        ProductPage productPage = new ProductPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup()
                .scrollToElement(LAST_PIECES);
        manClothingPage
                .clickOnDropdownButton(SEASON_DROP_DOWN_BUTTON)
                .selectAndScrollSeasonOption(SEASON);

        String selectedSeasonLabelClass = manClothingPage.getSelectedSizeLabelClass();

        Assert.assertTrue(selectedSeasonLabelClass.contains(SELECTED_CLASS)
                , "Expected selected season label to contain class: " + SELECTED_CLASS + " but got: " + selectedSeasonLabelClass);

        List<WebElement> productCard = manClothingPage.waitForVisibleProductCards(PRODUCTS_CARD);

        for (int i = 0; i < productCard.size(); i++) {
            WebElement currentProduct = manClothingPage.waitForVisibleProductCards(PRODUCTS_CARD).get(i);

            manClothingPage
                    .scrollToElement(currentProduct);
            currentProduct.click();
            productPage
                    .scrollToElement(PRODUCT_PARAMETERS)
                    .clickOnTheButton(PRODUCT_PARAMETERS);

            String actualSeason = productPage.getTextFrom(PRODUCT_SEASON_PARAMETER);

            Assert.assertTrue(productPage.getTextFrom(PRODUCT_SEASON_PARAMETER).contains(SEASON)
                    , "Expected season to contain: '" + SEASON + "', but got: '" + actualSeason + "'");

            productPage.goBack();

        }
    }

    @Description("Verify that all applied filters affect the product list as expected, and that clicking clear All Filters restores the original product list.")
    @Owner(NAZAR)
    @Test
    public void closeAllFilterTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);
        ProductPage productPage = new ProductPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup();

        List<String> productsName = manClothingPage.getVisibleProductsInfoTexts(PRODUCTS_NAME);

        manClothingPage.scrollToElement(LAST_PIECES)
                .selectFilterOption(BRAND_DROPDOWN)
                .typeBrandNameInSearch(ASICS, BRANDS_SEARCH_FIELD)
                .selectBrandOption(ASICS)
                .waitProductsInfoAreUpdated(PRODUCTS_NAME);

        Assert.assertTrue(manClothingPage.isBrandPresentInProductNames(ASICS, PRODUCTS_NAME),
                "Expected at least one product name to contain" + ASICS.getValue() + ", but none were found.");

        manClothingPage
                .scrollToElement(LAST_PIECES)
                .selectFilterOption(PRICE_DROPDOWN)
                .typePriceInInput(MAX_PRICE_INPUT_FIELD, MAX_PRICE_INPUT)
                .typePriceInInput(MIN_PRICE_INPUT_FIELD, MIN_PRICE_INPUT)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> filteredPrices = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        Assert.assertTrue(isAllPricesLessThan(filteredPrices, MAX_ALLOWED_PRICE), "Expected at least one price < " + MAX_ALLOWED_PRICE + ", but actual prices were: " + filteredPrices);
        Assert.assertTrue(isAllPricesMoreThan(filteredPrices, MIN_ALLOWED_PRICE), "Expected at least one price > " + MIN_ALLOWED_PRICE + ", but actual prices were: " + filteredPrices);

        manClothingPage
                .clickOnDropdownButton(SIZE_DROP_DOWN_BUTTON)
                .selectSizeFilterOption(SIZE_2XL)
                .scrollToElement(SEASON_DROP_DOWN_BUTTON)
                .clickOnDropdownButton(SEASON_DROP_DOWN_BUTTON)
                .selectSeasonOption(SEASON)
                .scrollToElement(PRODUCTS_CARD);

        List<WebElement> productCard = manClothingPage.waitProductsInfoAreUpdated(PRODUCTS_CARD);


        for (int i = 0; i < productCard.size(); i++) {
            WebElement currentProduct = manClothingPage.waitForVisibleProductCards(PRODUCTS_CARD).get(i);

            manClothingPage
                    .scrollToElement(currentProduct);
            currentProduct.click();

            productPage.clickOnTheButton(SELECT_SIZE_DROP_DOWN_BUTTON);

            String sizeOptionClass = productPage.getSizeOptionClass(SIZE_2XL);

            Assert.assertFalse(sizeOptionClass.contains("visual-ko-select__option--disabled stock-notification-trigger"),
                    "Expected size option to be available, but class contains disallowed value: " + sizeOptionClass);

            productPage.clickOnTheButton(CLOSE_SELECT_SIZE_BUTTON);

            productPage
                    .scrollToElement(PRODUCT_PARAMETERS)
                    .clickOnTheButton(PRODUCT_PARAMETERS);

            String actualSeason = productPage.getTextFrom(PRODUCT_SEASON_PARAMETER);

            Assert.assertTrue(productPage.getTextFrom(PRODUCT_SEASON_PARAMETER).contains(SEASON)
                    , "Expected season to contain: '" + SEASON + "', but got: '" + actualSeason + "'");

            productPage.goBack();
        }

        manClothingPage
                .clickOnTheCloseAllFilter()
                .waitProductsInfoAreUpdated(PRODUCTS_NAME);

        Assert.assertEquals(productsName , manClothingPage.getVisibleProductsInfoTexts(PRODUCTS_NAME));
    }
}
