package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SearchPage extends ProductCatalogPage {
    private static final String PRODUCT_NAMES = "//*[@class ='product-card__name']";
    private static final String SEARCH_QUERY = "//*[@class='breadcrumbs__current-item']";

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
}
