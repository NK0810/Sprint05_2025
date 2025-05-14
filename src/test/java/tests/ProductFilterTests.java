package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;

import static pages.ProductCatalogPage.FilterOption.NEW_ARRIVALS;
import static pages.ProductCatalogPage.FilterOption.SALE;

public class ProductFilterTests extends BaseTest {
    ManClothingPage manClothingPage = new ManClothingPage(driver);

    @Description("Verify that filtering by 'New arrivals' only displays products marked.")
    @Test
    public void filterByNewArrivalsTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .filterByOption(NEW_ARRIVALS);
        manClothingPage
                .waitUntilTagsAreUpdated();

        List<String> productNewTagsFilterByNewArrivals = manClothingPage.getVisibleNewTag();

        Assert.assertTrue(
                productNewTagsFilterByNewArrivals.stream().allMatch(tag -> tag.contains("Новий")), "Not all product tags contain 'Новий'"
        );
    }

    @Description("Verify that the discount filter correctly displays only products with active discounts.")
    @Test
    public void filterBySalesTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .filterByOption(SALE);
        manClothingPage
                .waitUntilProductPricesAreUpdated();

        List<String> priceProductsWithDiscount = manClothingPage.getVisibleActualPriceTexts();
        List<String> priceProductsWithoutDiscount = manClothingPage.getVisibleRegularPriceTexts();

        Assert.assertTrue(isDiscountApplied(priceProductsWithoutDiscount, priceProductsWithDiscount) , "Discount not applied: discounted price is not lower than the regular price");
    }

    private boolean isDiscountApplied(List<String> actualPrice, List<String> regularPrice) {
        for (int i = 0; i < regularPrice.size(); i++) {
            if (manClothingPage.convertPriceToInt(regularPrice.get(i)) < manClothingPage.convertPriceToInt(actualPrice.get(i))) {
                return true;
            }
        }
        return false;
    }
}
