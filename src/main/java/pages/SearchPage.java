package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchPage extends ProductCatalogPage {
    private static final String PRODUCT_NAMES = "//*[@class ='product-card__name']";
    private static final String SEARCH_QUERY = "//*[@class='breadcrumbs__current-item']";
    private static final String FIRST_PRODUCT_NAME = "(//*[@class ='product-card__name'])[1]";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchQuery() {
        return waitElementIsVisible(By.xpath(SEARCH_QUERY)).getText();
    }

    @Step("Get list of product names")
    public List<String> getSearchedProductsNames() {
        return getTextsFromList(waitElementsAreVisible(By.xpath(PRODUCT_NAMES)));
    }

    @Step("Click on first searched product")
    public SearchPage clickFirstSearchProduct() {
        waitElementsAreVisible(By.xpath(PRODUCT_NAMES)).getFirst().click();
        return this;
    }

    @Step("Scroll to first product")
    public SearchPage scrollToFirstProduct() {
        scrollToElement(By.xpath(FIRST_PRODUCT_NAME));
        return this;
    }

    @Step("Click on first searched product")
    public SearchPage clickFirstProduct() {
        waitElementsAreVisible(By.xpath(FIRST_PRODUCT_NAME)).getFirst().click();
        return this;
    }
}