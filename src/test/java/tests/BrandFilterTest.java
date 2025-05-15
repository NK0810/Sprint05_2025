package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;
import static pages.ManClothingPage.BrandName.*;
import static pages.ProductCatalogPage.FilterDropdown.*;
import static pages.ProductCatalogPage.ProductCardInfo.*;

public class BrandFilterTest extends BaseTest {

    @Test
    @Description("Verify only brand-specific products are shown after applying brand filter using search for brand filter")
    public void filteredProductsByBrandViaSearchTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .scrollToElement(BRAND_DROPDOWN);
        manClothingPage
                .openFilterDropdown(BRAND_DROPDOWN);
        manClothingPage
                .typeBrandNameInSearch(ALPINUS);
        manClothingPage
                .selectBrandOption(ALPINUS);

        manClothingPage.waitProductsInfoAreUpdated(PRODUCT_NAME);

        List<String> productsName = manClothingPage.getVisibleProductsInfoTexts(PRODUCT_NAME);

        List<String> invalidNames = productsName.stream()
                .filter(name -> !name.toLowerCase().contains(ALPINUS.getValue().toLowerCase()))
                .toList();

        Assert.assertEquals(invalidNames.size(), 0,
                "Some products do not contain expected brand name '" + ALPINUS.getValue() + "': " + invalidNames);
    }
}