package base;

import common.CommonActions;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    protected static final Properties properties = new Properties();
    public WebDriver getDriver() {
        return driver;
    }

    static {
        try (InputStream input = BaseTest.class.getClassLoader().getResourceAsStream("common.properties")) {
            if (input == null) {
                LOGGER.error("Не вдалося знайти common.properties");
            } else {
                properties.load(input);
                LOGGER.info("Завантажено common.properties");
            }
        } catch (IOException ex) {
            LOGGER.error("Помилка завантаження common.properties", ex);
        }

        LOGGER.info("ЧАС ПОЧАТКУ: {}", LocalTime.now());
        cleanStaticDirectories();
    }

    private static void cleanStaticDirectories() {
        File allureResults = new File("target/allure-results");
        if (allureResults.isDirectory()) {
            for (File file : Objects.requireNonNull(allureResults.listFiles())) {
                if (file.delete()) {
                    LOGGER.debug("Видалено файл: {}", file.getName());
                }
            }
            LOGGER.info("Очищено target/allure-results");
        }
    }

    @BeforeMethod
    public void setUp() {
        driver = CommonActions.createDriver();
        LOGGER.info("Driver created");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        LOGGER.info("tearDown: driver = " + driver);
        if (driver != null) {
            try {
                driver.quit();
                LOGGER.info("Driver quit");
            } catch (Exception e) {
                LOGGER.warn("Не вдалося закрити драйвер: {}", e.getMessage());
            }
        } else {
            LOGGER.warn("Driver був null — нічого не закриваємо");
        }
    }
}