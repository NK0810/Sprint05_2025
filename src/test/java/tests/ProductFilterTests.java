package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;
import java.util.stream.Collectors;

import static pages.ManClothingPage.BrandName.ALPINUS;
import static pages.ProductCatalogPage.BrandFilter.BRANDS_SEARCH_FIELD;
import static pages.ProductCatalogPage.BrandFilter.LIST_NAME_BRAND;
import static pages.ProductCatalogPage.FilterOption.*;
import static pages.ProductCatalogPage.PriceFilter.MAX_PRICE_INPUT_FIELD;
import static pages.ProductCatalogPage.ProductCardInfo.*;

public class ProductFilterTests extends BaseTest {
    ManClothingPage manClothingPage = new ManClothingPage(driver);

    private static final String MAX_PRICE_INPUT = "1000";
    private static final int MAX_ALLOWED_PRICE = 1000;

    @Description("Verify that filtering by 'New arrivals' only displays products marked.")
    @Test
    public void filterByNewArrivalsTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .scrollToElement(NEW_ARRIVALS);
        manClothingPage
                .selectFilterOption(NEW_ARRIVALS);
        manClothingPage
                .waitProductsInfoAreUpdated(NEW_TAG);

        List<String> productNewTags = manClothingPage.getVisibleProductsInfoTexts(NEW_TAG);

        List<String> noNewTagProducts = productNewTags.stream()
                .filter(tag -> !tag.contains("Новий"))
                .collect(Collectors.toList());

        Assert.assertEquals(noNewTagProducts.size(), 0,
                "Some products do not have the 'Новий' tag: " + noNewTagProducts);
    }

    @Description("Verify that the discount filter correctly displays only products with active discounts.")
    @Test
    public void filterBySalesTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies();
        manClothingPage
                .scrollToElement(SALE);
        manClothingPage
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

    @Description("Verify only brand-specific products are shown after applying brand filter using search for brand filter")
    @Test
    public void filteredProductsByBrandViaSearchTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .clickCloseTrustedShopPopup()
                .scrollToElement(BRAND_DROPDOWN);
        manClothingPage
                .selectFilterOption(BRAND_DROPDOWN);
        manClothingPage
                .typeBrandNameInSearch(ALPINUS, BRANDS_SEARCH_FIELD);
        manClothingPage
                .selectBrandOption(ALPINUS, LIST_NAME_BRAND);

        manClothingPage.waitProductsInfoAreUpdated(PRODUCT_NAME);

        List<String> productsName = manClothingPage.getVisibleProductsInfoTexts(PRODUCT_NAME);

        List<String> invalidNames = productsName.stream()
                .filter(name -> !name.toLowerCase().contains(ALPINUS.getValue().toLowerCase()))
                .toList();

        Assert.assertEquals(invalidNames.size(), 0,
                "Some products do not contain expected brand name '" + ALPINUS.getValue() + "': " + invalidNames);
    }

    @Description("Verify that after removing the price filter, the full product list is restored to its original state.")
    @Test
    public void shouldRestoreProductListAfterRemovingPriceFilterTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies();

        List<String> nonFilteredPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        manClothingPage
                .clickCloseTrustedShopPopup()
                .scrollToElement(LAST_PIECES)
                .selectFilterOption(PRICE_DROPDOWN)
                .typePriceInInput(MAX_PRICE_INPUT_FIELD, MAX_PRICE_INPUT)
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> filteredPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        Assert.assertTrue(isAllPricesLessThan(filteredPrice), "Expected at least one price < 1000, but actual prices were: %s" + filteredPrice);

        manClothingPage
                .clickClearFilterButton()
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        Assert.assertEquals(nonFilteredPrice, manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE));

    }

    private boolean isAllPricesLessThan(List<String> actualPrice) {
        List<Integer> intPrices = actualPrice.stream()
                .map(s -> s.replaceAll("[^0-9]", ""))
                .map(s -> s.substring(0, s.length() - 2))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return intPrices.stream().allMatch(element -> element < MAX_ALLOWED_PRICE);
    }
}
