package tests;

import base.BaseTest;
import io.qameta.allure.Owner;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ManClothingPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static constant.Constant.Owners.NAZAR;
import static pages.ProductCatalogPage.ProductCardInfo.*;
import static fragments.SortFragment.SortOptions.*;

public class SortingByLowestAndHighestPriceTest extends BaseTest {

    @Owner(NAZAR)
    @Description("Verifies that products are correctly sorted by lowest and highest price on the category page.")
    @Test
    public void checkSortPriceLowestAndHighestTest() {
        ManClothingPage manClothingPage = new ManClothingPage(driver);

        manClothingPage
                .openUrl()
                .acceptCookies()
                .sortByOption(LOWEST_PRICE);
        manClothingPage
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> productsPriceSortedByLowestPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        List<String> invalidLowToHigh = findInvalidLowToHighPairs(productsPriceSortedByLowestPrice);

        Assert.assertEquals(invalidLowToHigh.size(), 0,
                "Prices not sorted from low to high. Invalid pairs: " + invalidLowToHigh
        );

        manClothingPage
                .sortByOption(HIGHEST_PRICE);
        manClothingPage
                .waitProductsInfoAreUpdated(ACTUAL_PRICE);

        List<String> productPriceSortedByHighestPrice = manClothingPage.getVisibleProductsInfoTexts(ACTUAL_PRICE);

        List<String> invalidHighToLow = findInvalidHighToLowPairs(productPriceSortedByHighestPrice);

        Assert.assertEquals(invalidHighToLow.size(), 0,
                "Prices not sorted from high to low. Invalid pairs: " + invalidHighToLow
        );
    }

    private List<String> findInvalidLowToHighPairs(List<String> rawPrices) {
        List<Integer> prices = convertRawPrices(rawPrices);
        return IntStream.range(0, prices.size() - 1)
                .filter(i -> prices.get(i) > prices.get(i + 1))
                .mapToObj(i -> prices.get(i) + " > " + prices.get(i + 1))
                .collect(Collectors.toList());
    }

    private List<String> findInvalidHighToLowPairs(List<String> rawPrices) {
        List<Integer> prices = convertRawPrices(rawPrices);
        return IntStream.range(0, prices.size() - 1)
                .filter(i -> prices.get(i) < prices.get(i + 1))
                .mapToObj(i -> prices.get(i) + " < " + prices.get(i + 1))
                .collect(Collectors.toList());
    }

    private List<Integer> convertRawPrices(List<String> rawPrices) {
        return rawPrices.stream()
                .map(text -> text.replaceAll("[^\\d,]", ""))
                .map(price -> price.contains(",") ? price.split(",")[0] : price)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}