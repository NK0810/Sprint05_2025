package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;

import static pages.ProductCatalogPage.FilterOption.NEW_ARRIVALS;

public class ProductFilterTests extends BaseTest {
    @Description("Verify that filtering by 'New arrivals' only displays products marked.")
    @Test
    public void filterByNewArrivals() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .filterByOption(NEW_ARRIVALS);
        manClothingPage
                .waitAreNewTagAreUpdated();

        List<String> productNewTagsFilterByNewArrivals = manClothingPage.getVisibleNewTag();

        Assert.assertTrue(
                productNewTagsFilterByNewArrivals.stream().allMatch(tag -> tag.contains("Новий"))
                , "Not all product tags contain 'Новий'"
        );
    }
}
