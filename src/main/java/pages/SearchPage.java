package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchPage extends BasePage<SearchPage> {
    private static final String SEARCHED_PRODUCT = "//div[contains(@class,'product-card--type-default')]//a[contains(@class,'product-card__name')]";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductsMatchesToSearchText(String searchQuery){
        try {
            List<WebElement> SearchedProducts = waitElementsAreVisible(By.xpath(SEARCHED_PRODUCT));
            boolean matches = false;
            for (WebElement element : SearchedProducts) {
                String productName = element.getText().toLowerCase();
                System.out.println(productName);
                if (productName.contains(searchQuery.toLowerCase()))
                    matches = true;
                else {
                    matches = false;
                    break;
                }
            }
            return matches;
        }
        catch (TimeoutException e){
            return false;
        }
    }
}
