package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class ManClothingPage extends ProductCatalogPage {
    private static final String URL = BASE_URL + "/cholovik/cholovichij-odjag";

    public ManClothingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open man clothing page")
    public ManClothingPage openUrl() {
        driver.get(URL);
        return this;
    }
}