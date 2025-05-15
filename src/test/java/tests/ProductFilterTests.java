package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;
import java.util.stream.Collectors;

import static pages.ProductCatalogPage.FilterOption.*;
import static pages.ProductCatalogPage.ProductCardInfo.*;

public class ProductFilterTests extends BaseTest {
    ManClothingPage manClothingPage = new ManClothingPage(driver);

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
                .closeTrustbadgePopUp();
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
}
