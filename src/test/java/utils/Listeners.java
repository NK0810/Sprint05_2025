package utils;

import base.BaseTest;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class Listeners implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listeners.class);

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test FAILED: {}", result.getName());

        WebDriver driver = ((BaseTest) result.getInstance()).getDriver();

        if (driver != null) {
            saveScreenshotLocally(driver);         // необов'язково
            attachScreenshotToAllure(driver);      // обов’язково
            attachLogs(result);
        } else {
            LOGGER.warn("Driver був null — скріншот не зроблено.");
        }
    }

    @Attachment(value = "Screenshot on failure", type = "image/png")
    private byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void saveScreenshotLocally(WebDriver driver) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/screenshot_" + System.currentTimeMillis() + ".png");
            FileUtils.copyFile(src, dest);
            LOGGER.info("Збережено локальний скріншот: {}", dest.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Помилка при збереженні скріншота: {}", e.getMessage());
        }
    }

    @Attachment(value = "Test logs", type = "text/plain")
    private String attachLogs(ITestResult result) {
        return "Test: " + result.getName() + "\n" +
                "Status: " + getStatus(result) + "\n" +
                "Exception: " + getExceptionMessage(result);
    }

    private String getStatus(ITestResult result) {
        return switch (result.getStatus()) {
            case ITestResult.SUCCESS -> "SUCCESS";
            case ITestResult.FAILURE -> "FAILURE";
            case ITestResult.SKIP -> "SKIPPED";
            default -> "UNKNOWN";
        };
    }

    private String getExceptionMessage(ITestResult result) {
        return result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "None";
    }
}


