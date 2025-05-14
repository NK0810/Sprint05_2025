package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import static pages.ManClothingPage.BrandFilter.ADIDAS;

import java.util.List;

public class BrandFilterTest extends BaseTest {

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
                .typeBrandNameInSearch(ADIDAS.getDataValue())
                .scrollToBrandItem();

        manClothingPage.selectFilterBrandOption(ADIDAS);
        manClothingPage.waitProductNamesAreUpdated();

        List<String> productsName = manClothingPage.getAllProductsName();

        Assert.assertTrue(isProductsHaveBrandName(productsName), "Products don't have specified brand name");
    }

    private boolean isProductsHaveBrandName(List<String> productsName) {
        return productsName.stream().allMatch(name -> name.contains(ADIDAS.getDataValue()));
    }
}