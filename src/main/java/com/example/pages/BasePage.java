package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import com.example.utils.ScreenshotUtils;
import java.time.Duration;

public class BasePage {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor js;
    
    // Browser types enum
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE,
        SAFARI
    }
    
    /**
     * Initialize browser with specified type
     * @param browserType Type of browser to launch
     */
    public static void initializeBrowser(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case SAFARI:
                driver = new SafariDriver();
                break;
            default:
                driver = new ChromeDriver(); // Default to Chrome
        }
        
        setupDriver();
    }
    
    /**
     * Initialize browser with Chrome (default)
     */
    public static void initializeBrowser() {
        initializeBrowser(BrowserType.CHROME);
    }
    
    /**
     * Initialize browser based on system property
     */
    public static void initializeBrowserFromProperty() {
        String browserName = System.getProperty("browser", "chrome").toLowerCase();
        
        switch (browserName) {
            case "firefox":
                initializeBrowser(BrowserType.FIREFOX);
                break;
            case "edge":
                initializeBrowser(BrowserType.EDGE);
                break;
            case "safari":
                initializeBrowser(BrowserType.SAFARI);
                break;
            default:
                initializeBrowser(BrowserType.CHROME);
        }
    }
    
    /**
     * Setup driver configurations
     */
    private static void setupDriver() {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
        
        System.out.println("Browser initialized: " + driver.getClass().getSimpleName());
    }
    
    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public static void navigateToUrl(String url) {
        driver.get(url);
        System.out.println("Navigated to: " + url);
    }
    
    /**
     * Get current page title
     * @return Page title
     */
    public static String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     * @return Current URL
     */
    public static String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Wait for specified time
     * @param milliseconds Time to wait in milliseconds
     */
    public static void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Wait interrupted: " + e.getMessage());
        }
    }
    
    /**
     * Scroll to top of page
     */
    public static void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
        System.out.println("Scrolled to top of page");
    }
    
    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        System.out.println("Scrolled to bottom of page");
    }
    
    /**
     * Refresh the current page
     */
    public static void refreshPage() {
        driver.navigate().refresh();
        System.out.println("Page refreshed");
    }
    
    /**
     * Navigate back
     */
    public static void navigateBack() {
        driver.navigate().back();
        System.out.println("Navigated back");
    }
    
    /**
     * Navigate forward
     */
    public static void navigateForward() {
        driver.navigate().forward();
        System.out.println("Navigated forward");
    }
    
    /**
     * Close current browser window
     */
    public static void closeBrowser() {
        if (driver != null) {
            driver.close();
            System.out.println("Browser window closed");
        }
    }
    
    /**
     * Quit browser and end session
     */
    public static void quitBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
            js = null;
            System.out.println("Browser session terminated");
        }
    }
    
    /**
     * Get WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Get WebDriverWait instance
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait() {
        return wait;
    }
    
    /**
     * Get JavascriptExecutor instance
     * @return JavascriptExecutor instance
     */
    public static JavascriptExecutor getJsExecutor() {
        return js;
    }
    
    /**
     * Check if browser is initialized
     * @return true if browser is initialized, false otherwise
     */
    public static boolean isBrowserInitialized() {
        return driver != null;
    }
    
    /**
     * Take screenshot
     * @param screenshotName Name for the screenshot file
     * @return Path to saved screenshot
     */
    public static String takeScreenshot(String screenshotName) {
        if (driver != null) {
            return ScreenshotUtils.captureScreenshot(driver, screenshotName);
        } else {
            System.out.println("❌ Cannot take screenshot: Driver not initialized");
            return null;
        }
    }
    
    /**
     * Take screenshot with automatic naming
     * @return Path to saved screenshot
     */
    public static String takeScreenshot() {
        return takeScreenshot("screenshot");
    }
    
    /**
     * Take screenshot for passed test
     * @param testName Test name
     * @return Path to saved screenshot
     */
    public static String takePassScreenshot(String testName) {
        if (driver != null) {
            return ScreenshotUtils.capturePassScreenshot(driver, testName);
        } else {
            System.out.println("❌ Cannot take pass screenshot: Driver not initialized");
            return null;
        }
    }
    
    /**
     * Take screenshot for failed test
     * @param testName Test name
     * @return Path to saved screenshot
     */
    public static String takeFailScreenshot(String testName) {
        if (driver != null) {
            return ScreenshotUtils.captureFailScreenshot(driver, testName);
        } else {
            System.out.println("❌ Cannot take fail screenshot: Driver not initialized");
            return null;
        }
    }
    
    /**
     * Take screenshot for test step
     * @param stepName Step name
     * @return Path to saved screenshot
     */
    public static String takeStepScreenshot(String stepName) {
        if (driver != null) {
            return ScreenshotUtils.captureStepScreenshot(driver, stepName);
        } else {
            System.out.println("❌ Cannot take step screenshot: Driver not initialized");
            return null;
        }
    }
}
