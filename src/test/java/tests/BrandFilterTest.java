package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;
import static constant.Constant.FilterTestData.*;
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
                .getBrandFilterFragment()
                .scrollToBrandDropdown()
                .openBrandDropdown()
                .typeBrandNameInSearch(TEST_BRAND)
                .scrollToBrandItem()
                .clickSearchedBrandCheckbox();
        manClothingPage.waitAreProductsNameAreUpdated();

        List<String> productsName = manClothingPage.getAllProductsName();

        Assert.assertFalse(productsName.isEmpty(), "No products displayed after selecting brand filter.");
        boolean allMatch = productsName.stream()
                .allMatch(name -> name.toLowerCase().contains(TEST_BRAND.toLowerCase()));
        Assert.assertTrue(allMatch, "Not all product names contain the brand: " + TEST_BRAND);
    }
}