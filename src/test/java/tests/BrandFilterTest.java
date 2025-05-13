package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;
import static pages.ManClothingPage.BrandFilter.ADIDAS;

import java.util.List;

public class BrandFilterTest extends BaseTest {
    private static final String BRAND_ADIDAS = "adidas";

    @Test
    @Description("Verify only brand-specific products are shown after applying brand filter using search for brand filter")
    public void filteredProductsByBrandViaSearchTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies();
        manClothingPage
                .scrollToBrandDropdown()
                .openBrandDropdown()
                .typeBrandNameInSearch(BRAND_ADIDAS)
                .scrollToBrandItem();
        manClothingPage.selectFilterBrandOption(ADIDAS);
        manClothingPage.waitAreProductsNameAreUpdated();
        List<String> productsName = manClothingPage.getAllProductsName();
        Assert.assertTrue(isProductsHaveBrandName(productsName) , "Product don't have brand name");
    }

    private boolean isProductsHaveBrandName(List<String> productsName){
        return productsName.stream().allMatch(name->name.contains(BRAND_ADIDAS));
    }
}