
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
    public void onTestStart(ITestResult result) {
        LOGGER.info("Test STARTED: {}", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test PASSED: {}", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test FAILED: {}", result.getName());

        WebDriver driver = ((BaseTest) result.getInstance()).driver;

        if (driver != null) {
            takeScreenshot(driver);
            attachScreenshotToAllure(driver);
        } else {
            LOGGER.warn("Driver was null — cannot take screenshot.");
        }

        attachLogs(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test SKIPPED: {}", result.getName());
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private void takeScreenshot(WebDriver driver) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = "screenshot_" + System.currentTimeMillis() + ".png";
            File dest = new File("target/screenshots/" + fileName);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);
            LOGGER.info("Screenshot saved: {}", dest.getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Failed to save screenshot locally: {}", e.getMessage());
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


