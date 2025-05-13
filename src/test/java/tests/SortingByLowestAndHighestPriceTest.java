package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static fragments.SortFragment.SortOptions.HIGHEST_PRICE;
import static fragments.SortFragment.SortOptions.LOWEST_PRICE;

public class SortingByLowestAndHighestPriceTest extends BaseTest {
    @Description("Verifies that products are correctly sorted by lowest and highest price on the category page.")
    @Test
    public void checkSortPriceLowestAndHighest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .sortByOption(LOWEST_PRICE);
        manClothingPage
                .waitAreProductPricesAreUpdated();

        List<String> productsPriceSortedByLowestPrice = manClothingPage.getVisiblePriceTexts();

        Assert.assertTrue(arePricesSortedLowToHigh(productsPriceSortedByLowestPrice)
                , "Product don't sorted by lowest");

        manClothingPage
                .sortByOption(HIGHEST_PRICE);
        manClothingPage
                .waitAreProductPricesAreUpdated();

        List<String> productPriceSortedByHighestPrice = manClothingPage.getVisiblePriceTexts();

        Assert.assertTrue(arePricesSortedHighToLow(productPriceSortedByHighestPrice)
                , "Product don't sorted");
    }

    public boolean arePricesSortedLowToHigh(List<String> rawPriceTexts) {
        List<Integer> prices = rawPriceTexts.stream()
                .map(text -> text.replaceAll("[^\\d,]", ""))
                .map(price -> price.contains(",") ? price.split(",")[0] : price)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return IntStream.range(0, prices.size() - 1)
                .allMatch(i -> prices.get(i) <= prices.get(i + 1));
    }

    public boolean arePricesSortedHighToLow(List<String> rawPriceTexts) {
        List<Integer> prices = rawPriceTexts.stream()
                .map(text -> text.replaceAll("[^\\d,]", ""))
                .map(price -> price.contains(",") ? price.split(",")[0] : price)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return IntStream.range(0, prices.size() - 1)
                .allMatch(i -> prices.get(i) >= prices.get(i + 1));
    }
}

