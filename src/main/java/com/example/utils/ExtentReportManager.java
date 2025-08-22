package com.example.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static String reportPath;
    
    /**
     * Initialize Extent Reports with configuration
     */
    public static void initReports() {
        if (extent == null) {
            // Create reports directory if it doesn't exist
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            // Create screenshots directory
            File screenshotsDir = new File("reports/screenshots");
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            
            // Generate timestamp for report name
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            reportPath = "reports/AutomationReport_" + timestamp + ".html";
            
            // Initialize Spark Reporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            
            // Configure Spark Reporter
            sparkReporter.config().setDocumentTitle("Amazon Automation Test Report");
            sparkReporter.config().setReportName("Amazon Automation Results");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
            
            // Initialize Extent Reports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // Add system information
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Browser", "Chrome"); // Default browser
            extent.setSystemInfo("Environment", "Test");
            
            System.out.println("✅ Extent Reports initialized: " + reportPath);
        }
    }
    
    /**
     * Create a new test in the report
     * @param testName Name of the test
     * @param description Description of the test
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName, String description) {
        test = extent.createTest(testName, description);
        return test;
    }
    
    /**
     * Create a new test in the report
     * @param testName Name of the test
     * @return ExtentTest instance
     */
    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }
    
    /**
     * Get current test instance
     * @return Current ExtentTest instance
     */
    public static ExtentTest getTest() {
        return test;
    }
    
    /**
     * Log info message
     * @param message Message to log
     */
    public static void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
        System.out.println("ℹ️ " + message);
    }
    
    /**
     * Log pass message
     * @param message Message to log
     */
    public static void logPass(String message) {
        if (test != null) {
            test.log(Status.PASS, message);
        }
        System.out.println("✅ " + message);
    }
    
    /**
     * Log fail message
     * @param message Message to log
     */
    public static void logFail(String message) {
        if (test != null) {
            test.log(Status.FAIL, message);
        }
        System.out.println("❌ " + message);
    }
    
    /**
     * Log warning message
     * @param message Message to log
     */
    public static void logWarning(String message) {
        if (test != null) {
            test.log(Status.WARNING, message);
        }
        System.out.println("⚠️ " + message);
    }
    
    /**
     * Log skip message
     * @param message Message to log
     */
    public static void logSkip(String message) {
        if (test != null) {
            test.log(Status.SKIP, message);
        }
        System.out.println("⏭️ " + message);
    }
    
    /**
     * Add screenshot to the report
     * @param screenshotPath Path to the screenshot
     * @param description Description for the screenshot
     */
    public static void addScreenshot(String screenshotPath, String description) {
        if (test != null && screenshotPath != null) {
            try {
                test.addScreenCaptureFromPath(screenshotPath, description);
                logInfo("Screenshot attached: " + description);
            } catch (Exception e) {
                logWarning("Failed to attach screenshot: " + e.getMessage());
            }
        }
    }
    
    /**
     * Add screenshot with pass status
     * @param screenshotPath Path to the screenshot
     * @param message Pass message
     */
    public static void addScreenshotOnPass(String screenshotPath, String message) {
        if (test != null && screenshotPath != null) {
            try {
                test.pass(message).addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logWarning("Failed to attach screenshot on pass: " + e.getMessage());
            }
        }
    }
    
    /**
     * Add screenshot with fail status
     * @param screenshotPath Path to the screenshot
     * @param message Fail message
     */
    public static void addScreenshotOnFail(String screenshotPath, String message) {
        if (test != null && screenshotPath != null) {
            try {
                test.fail(message).addScreenCaptureFromPath(screenshotPath);
            } catch (Exception e) {
                logWarning("Failed to attach screenshot on fail: " + e.getMessage());
            }
        }
    }
    
    /**
     * Mark test as passed
     * @param message Pass message
     */
    public static void markTestPassed(String message) {
        if (test != null) {
            test.pass(message);
        }
        logPass(message);
    }
    
    /**
     * Mark test as failed
     * @param message Fail message
     */
    public static void markTestFailed(String message) {
        if (test != null) {
            test.fail(message);
        }
        logFail(message);
    }
    
    /**
     * Mark test as failed with exception
     * @param exception Exception to log
     */
    public static void markTestFailed(Exception exception) {
        if (test != null) {
            test.fail(exception);
        }
        logFail("Test failed with exception: " + exception.getMessage());
    }
    
    /**
     * Flush the reports (write to disk)
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            System.out.println("📊 Reports generated: " + reportPath);
        }
    }
    
    /**
     * Get the report file path
     * @return Report file path
     */
    public static String getReportPath() {
        return reportPath;
    }
    
    /**
     * Close reports and cleanup
     */
    public static void closeReports() {
        if (extent != null) {
            extent.flush();
            extent = null;
            test = null;
            System.out.println("📊 Extent Reports closed");
        }
    }
    
    /**
     * Add test category/tag
     * @param category Category name
     */
    public static void addCategory(String category) {
        if (test != null) {
            test.assignCategory(category);
        }
    }
    
    /**
     * Add test author
     * @param author Author name
     */
    public static void addAuthor(String author) {
        if (test != null) {
            test.assignAuthor(author);
        }
    }
    
    /**
     * Add test device information
     * @param device Device name
     */
    public static void addDevice(String device) {
        if (test != null) {
            test.assignDevice(device);
        }
    }
}
