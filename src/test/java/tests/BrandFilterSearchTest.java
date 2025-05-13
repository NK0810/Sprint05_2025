package tests;

import base.BaseTest;
import fragments.BrandFilterFragment;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;
import static constant.Constant.FilterTestData.*;
import java.util.List;

public class BrandFilterSearchTest extends BaseTest {

    @Test
    @Description("Verify only brand-specific products are shown after applying brand filter")
    public void shouldDisplayOnlyFilteredProductsByBrand() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);
        BrandFilterFragment brandFilterFragment = new BrandFilterFragment(driver);

        manClothingPage
                .openUrl()
                .acceptCookies();

        brandFilterFragment
                .scrollToBrandDropdown()
                .openBrandDropdown()
                .typeBrandNameInSearch(TEST_BRAND)
                .scrollToFirstBrandItem()
                .clickSearchedBrandCheckbox();
        manClothingPage.waitUpdateProductCard();

        // Отримуємо список назв товарів після застосування фільтру
        List<String> productNames = manClothingPage.getAllProductNames();

        Assert.assertFalse(productNames.isEmpty(), "No products displayed after selecting brand filter.");
        boolean allMatch = productNames.stream()
                .allMatch(name -> name.toLowerCase().contains(TEST_BRAND.toLowerCase()));
        Assert.assertTrue(allMatch, "Not all product names contain the brand: " + TEST_BRAND);
    }
}
