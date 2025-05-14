package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SearchPage extends BasePage<SearchPage> {
    private static final String PRODUCT_NAMES = "//a[contains(@class,'product-card__name')]";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of product names")
    public List<String> getSearchedProductsNames() {
        By productName = By.xpath(PRODUCT_NAMES);

        return waitElementsAreVisible(productName)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
