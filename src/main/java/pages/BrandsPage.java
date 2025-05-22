package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class BrandsPage extends BasePage<BrandsPage>{
    private static final String LIST_SECTION_TITLES = "//section//h5";
    private static final String BRAND_SECTION = "//section[.//h5[text()='%s']]";

    public BrandsPage(WebDriver driver) {super(driver);}

    @Step("Get list of title names of letter sections")
    public List<String> getAllBrandsTitleNames() {
        return getTextsFromList(waitElementsAreVisible(By.xpath(LIST_SECTION_TITLES)));
    }

    @Step("Get list of brand names of {title} section")
    public List<String> getAllBrandNamesOfSection(String title){
        String section = String.format(BRAND_SECTION,title);
        return getTextsFromList(waitElementsAreVisible(By.xpath(section+"//a")));
    }
}
