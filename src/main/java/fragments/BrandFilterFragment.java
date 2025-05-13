package fragments;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class BrandFilterFragment extends BasePage<BrandFilterFragment> {

    private static final String BRANDS_DROPDOWN_LIST = "//div[@class='widget-container-brand']/div";
    private static final String BRANDS_SEARCH_FIELD = "//div[@class='widget-container-brand']//input[@placeholder]";
//    private static final String BRAND_CHECKBOX = "//li[@class='refinement-item refinement-item--brand']//input[@class=\"refinement-checkbox\"]";
    private static final String BRAND_CHECKBOX = "//div[@class='widget-container-brand']//label[@class='refinement-label ']";
    private static final String BRAND_ITEM = "//li[@class='refinement-item refinement-item--brand']";

    public BrandFilterFragment(WebDriver driver) {
        super(driver);
    }

    @Step("Scroll to first brand item in the filter")
    public BrandFilterFragment scrollToFirstBrandItem() {
        waitElementIsVisible(By.xpath(BRAND_ITEM));
        scrollToElement(By.xpath(BRAND_ITEM));
        return this;
    }

    @Step("Type brand name '{brandName}' into search field")
    public BrandFilterFragment typeBrandNameInSearch(String brandName) {
        WebElement searchInput = waitElementIsVisible(By.xpath(BRANDS_SEARCH_FIELD));
        searchInput.clear();
        searchInput.sendKeys(brandName);
        return this;
    }

    @Step("Open brand filter dropdown")
    public BrandFilterFragment openBrandDropdown() {
        waitElementToBeClickable(By.xpath(BRANDS_DROPDOWN_LIST)).click();
        return this;
    }

    @Step("Click the searched brand checkbox in the filter")
    public BrandFilterFragment clickSearchedBrandCheckbox() {
        waitElementToBeClickable(By.xpath(BRAND_CHECKBOX)).click();
        return this;
    }

    @Step("Scroll to brand dropdown")
    public BrandFilterFragment scrollToBrandDropdown() {
        scrollToElement(By.xpath(BRANDS_DROPDOWN_LIST));
        return this;
    }

}
