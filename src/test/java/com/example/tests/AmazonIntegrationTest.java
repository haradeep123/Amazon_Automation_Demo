package com.example.tests;

import org.testng.annotations.*;
import com.example.pages.BasePage;
import com.example.pages.AmazonHomePage;
import com.example.utils.ExtentReportManager;

/**
 * Integration tests for Amazon automation
 * These tests follow the naming convention *IT.java for integration tests
 */
public class AmazonIntegrationTest {
    
    private AmazonHomePage homePage;
    
    @BeforeClass
    public void setupClass() {
        ExtentReportManager.initReports();
        System.out.println("🔧 Starting Amazon Integration Tests");
    }
    
    @BeforeMethod
    public void setupTest() {
        BasePage.initializeBrowser();
        homePage = new AmazonHomePage();
    }
    
    @AfterMethod
    public void tearDownTest() {
        BasePage.quitBrowser();
    }
    
    @AfterClass
    public void tearDownClass() {
        ExtentReportManager.flushReports();
        System.out.println("📊 Integration Tests Completed");
    }
    
    @Test(description = "End-to-end Amazon navigation test")
    public void testAmazonEndToEndNavigation() {
        ExtentReportManager.createTest("E2E_Amazon_Navigation", "End-to-end navigation test");
        ExtentReportManager.addCategory("Integration Test");
        
        try {
            // Navigate to Amazon
            homePage.navigateToAmazon();
            String screenshotPath = BasePage.takeStepScreenshot("Amazon_Loaded");
            ExtentReportManager.addScreenshot(screenshotPath, "Amazon homepage loaded");
            
            // Verify page title
            String title = BasePage.getPageTitle();
            if (title.toLowerCase().contains("amazon")) {
                ExtentReportManager.markTestPassed("✅ E2E Navigation successful - Title: " + title);
            } else {
                ExtentReportManager.markTestFailed("❌ Unexpected page title: " + title);
            }
            
        } catch (Exception e) {
            String errorScreenshot = BasePage.takeFailScreenshot("E2E_Error");
            ExtentReportManager.addScreenshotOnFail(errorScreenshot, "E2E test failed");
            ExtentReportManager.markTestFailed("❌ E2E test failed: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(description = "Amazon page performance test", enabled = false)
    public void testAmazonPagePerformance() {
        ExtentReportManager.createTest("Amazon_Performance", "Test Amazon page load performance");
        ExtentReportManager.addCategory("Performance Test");
        
        try {
            long startTime = System.currentTimeMillis();
            
            homePage.navigateToAmazon();
            
            long endTime = System.currentTimeMillis();
            long loadTime = endTime - startTime;
            
            ExtentReportManager.logInfo("Page load time: " + loadTime + " ms");
            
            if (loadTime < 10000) { // 10 seconds
                ExtentReportManager.markTestPassed("✅ Page loaded within acceptable time: " + loadTime + "ms");
            } else {
                ExtentReportManager.markTestFailed("❌ Page load time too slow: " + loadTime + "ms");
            }
            
        } catch (Exception e) {
            ExtentReportManager.markTestFailed("❌ Performance test failed: " + e.getMessage());
            throw e;
        }
    }
}
