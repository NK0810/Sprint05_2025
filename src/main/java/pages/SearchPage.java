package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchPage extends BasePage<SearchPage> {
    private static final String SEARCHED_PRODUCT = "//a[contains(@class,'product-card__name')]";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get list of product names")
    public List<String> getSearchedProductsNames() {
        By productName = By.xpath(SEARCHED_PRODUCT);

        return waitElementsAreVisible(productName)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }
}
