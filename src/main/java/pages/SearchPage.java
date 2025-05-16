package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends ProductCatalogPage {
    private static final String PRODUCT_NAMES = "//*[@class ='product-card__name']";
    private static final String SEARCH_QUERY = "//*[@class='breadcrumbs__current-item']";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public String getSearchQuery(){
        return waitElementIsVisible(By.xpath(SEARCH_QUERY)).getText();
    }

    @Step("Get list of product names")
    public List<String> getSearchedProductsNames() {
        return waitElementsAreVisible(By.xpath(PRODUCT_NAMES))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Click on first searched product")
    public SearchPage clickFirstSearchProduct(){
        waitElementsAreVisible(By.xpath(PRODUCT_NAMES)).getFirst().click();
        return this;
    }
}