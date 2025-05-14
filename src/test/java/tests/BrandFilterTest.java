package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;

import static pages.ProductCatalogPage.FilterOption.*;
import static pages.ProductCatalogPage.ProductCardInfo.*;

public class BrandFilterTest extends BaseTest {
    private static final String BRAND_NAME = "adidas";
    private static final String BRAND_NAME_LIST = "//label[@class='refinement-label ']//span[text()='" + BRAND_NAME + "']";

    @Test
    @Description("Verify only brand-specific products are shown after applying brand filter using search for brand filter")
    public void filteredProductsByBrandViaSearchTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .scrollToElement(BRAND.getFilter());
        manClothingPage
                .openFilterDropdown(BRAND.getFilter())
                .typeBrandNameInSearch(BRAND_NAME)
                .scrollToElement(BRAND_NAME_LIST);
        manClothingPage
                .selectFilterOption(BRAND_NAME_LIST);

        manClothingPage.waitProductsInfoAreUpdated(PRODUCT_NAME.getInfo());

        List<String> productsName = manClothingPage.getVisibleProductsInfoTexts(PRODUCT_NAME.getInfo());

        List<String> invalidNames = productsName.stream()
                .filter(name -> !name.toLowerCase().contains(BRAND_NAME))
                .toList();

        Assert.assertEquals(invalidNames.size(), 0,
                "Some products do not contain expected brand name '" + BRAND_NAME + "': " + invalidNames);
    }
}
