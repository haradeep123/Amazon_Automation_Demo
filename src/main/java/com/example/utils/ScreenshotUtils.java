package com.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {
    private static final String SCREENSHOT_DIR = "reports/screenshots/";
    
    /**
     * Initialize screenshot directory
     */
    public static void initScreenshotDirectory() {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("📁 Screenshot directory created: " + SCREENSHOT_DIR);
        }
    }
    
    /**
     * Capture full page screenshot
     * @param driver WebDriver instance
     * @param screenshotName Name for the screenshot
     * @return Path to the captured screenshot
     */
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            initScreenshotDirectory();
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = screenshotName + "_" + timestamp + ".png";
            String destinationPath = SCREENSHOT_DIR + fileName;
            
            File destinationFile = new File(destinationPath);
            FileUtils.copyFile(sourceFile, destinationFile);
            
            System.out.println("📸 Screenshot captured: " + fileName);
            return destinationPath;
            
        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot with automatic naming
     * @param driver WebDriver instance
     * @return Path to the captured screenshot
     */
    public static String captureScreenshot(WebDriver driver) {
        return captureScreenshot(driver, "screenshot");
    }
    
    /**
     * Capture screenshot of a specific element
     * @param element WebElement to capture
     * @param screenshotName Name for the screenshot
     * @return Path to the captured screenshot
     */
    public static String captureElementScreenshot(WebElement element, String screenshotName) {
        try {
            initScreenshotDirectory();
            
            File sourceFile = element.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = screenshotName + "_element_" + timestamp + ".png";
            String destinationPath = SCREENSHOT_DIR + fileName;
            
            File destinationFile = new File(destinationPath);
            FileUtils.copyFile(sourceFile, destinationFile);
            
            System.out.println("📸 Element screenshot captured: " + fileName);
            return destinationPath;
            
        } catch (IOException e) {
            System.out.println("❌ Failed to capture element screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot for passed test
     * @param driver WebDriver instance
     * @param testName Test name
     * @return Path to the captured screenshot
     */
    public static String capturePassScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName + "_PASS");
    }
    
    /**
     * Capture screenshot for failed test
     * @param driver WebDriver instance
     * @param testName Test name
     * @return Path to the captured screenshot
     */
    public static String captureFailScreenshot(WebDriver driver, String testName) {
        return captureScreenshot(driver, testName + "_FAIL");
    }
    
    /**
     * Capture screenshot for info/step
     * @param driver WebDriver instance
     * @param stepName Step name
     * @return Path to the captured screenshot
     */
    public static String captureStepScreenshot(WebDriver driver, String stepName) {
        return captureScreenshot(driver, stepName + "_STEP");
    }
    
    /**
     * Get relative path for report (removes full system path)
     * @param fullPath Full screenshot path
     * @return Relative path for report
     */
    public static String getRelativeScreenshotPath(String fullPath) {
        if (fullPath != null && fullPath.contains(SCREENSHOT_DIR)) {
            return fullPath.substring(fullPath.indexOf(SCREENSHOT_DIR));
        }
        return fullPath;
    }
    
    /**
     * Clean up old screenshots (older than specified days)
     * @param daysOld Number of days old
     */
    public static void cleanupOldScreenshots(int daysOld) {
        try {
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (screenshotDir.exists()) {
                File[] files = screenshotDir.listFiles();
                if (files != null) {
                    long cutoffTime = System.currentTimeMillis() - (daysOld * 24 * 60 * 60 * 1000L);
                    
                    for (File file : files) {
                        if (file.isFile() && file.lastModified() < cutoffTime) {
                            if (file.delete()) {
                                System.out.println("🗑️ Deleted old screenshot: " + file.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error cleaning up screenshots: " + e.getMessage());
        }
    }
    
    /**
     * Get screenshot directory path
     * @return Screenshot directory path
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }
    
    /**
     * Check if screenshot directory exists
     * @return true if directory exists, false otherwise
     */
    public static boolean screenshotDirectoryExists() {
        return new File(SCREENSHOT_DIR).exists();
    }
    
    /**
     * Get screenshot file size in KB
     * @param screenshotPath Path to screenshot
     * @return File size in KB
     */
    public static long getScreenshotSize(String screenshotPath) {
        try {
            File file = new File(screenshotPath);
            return file.length() / 1024; // Convert to KB
        } catch (Exception e) {
            return 0;
        }
    }
}
