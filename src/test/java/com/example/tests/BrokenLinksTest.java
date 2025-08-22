package com.example.tests;

import org.testng.annotations.*;
import com.example.pages.BasePage;
import com.example.utils.ExtentReportManager;
import com.example.utils.RetryAnalyzer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

public class BrokenLinksTest {
    private List<String> brokenLinks = new ArrayList<>();
    private List<String> workingLinks = new ArrayList<>();
    
    @BeforeClass
    public void setupClass() {
        ExtentReportManager.initReports();
        System.out.println("🔗 Starting Broken Links Test Suite");
    }
    
    @BeforeMethod
    public void setupTest() {
        BasePage.initializeBrowser();
        brokenLinks.clear();
        workingLinks.clear();
        ExtentReportManager.logInfo("Browser initialized for broken links test");
    }
    
    @AfterMethod
    public void tearDownTest() {
        BasePage.quitBrowser();
        ExtentReportManager.logInfo("Browser closed after test");
    }
    
    @AfterClass
    public void tearDownClass() {
        ExtentReportManager.flushReports();
        System.out.println("📊 Broken Links Test Suite Completed - Report: " + ExtentReportManager.getReportPath());
    }
    
    @DataProvider(name = "testUrls")
    public Object[][] getTestUrls() {
        return new Object[][] {
            {"https://www.amazon.com"},
            {"https://www.example.com"},
            // Add more URLs as needed
        };
    }
    
    @Test(dataProvider = "testUrls", description = "Check for broken links on web pages", retryAnalyzer = RetryAnalyzer.class)
    public void testBrokenLinks(String url) {
        String testName = "Broken_Links_Test_" + url.replace("https://", "").replace(".", "_");
        ExtentReportManager.createTest(testName, "Check for broken links on: " + url);
        ExtentReportManager.addCategory("Link Validation");
        ExtentReportManager.addAuthor("Test Framework");
        
        try {
            checkLinksOnUrl(url);
            
            // Generate summary
            generateLinkCheckSummary();
            
            // Determine test status
            if (brokenLinks.size() == 0) {
                String passScreenshot = BasePage.takePassScreenshot(testName);
                ExtentReportManager.addScreenshotOnPass(passScreenshot, "No broken links found");
                ExtentReportManager.markTestPassed("✅ All links are working properly");
            } else {
                double successRate = workingLinks.size() > 0 ? 
                    (double) workingLinks.size() / (workingLinks.size() + brokenLinks.size()) * 100 : 0;
                
                if (successRate >= 90) {
                    ExtentReportManager.logWarning("Test passed with warnings - " + brokenLinks.size() + " broken links found");
                } else {
                    String failScreenshot = BasePage.takeFailScreenshot(testName);
                    ExtentReportManager.addScreenshotOnFail(failScreenshot, "Too many broken links found");
                    ExtentReportManager.markTestFailed("❌ Too many broken links found: " + brokenLinks.size());
                }
            }
            
        } catch (Exception e) {
            String errorScreenshot = BasePage.takeFailScreenshot(testName);
            ExtentReportManager.addScreenshotOnFail(errorScreenshot, "Error during link checking");
            ExtentReportManager.markTestFailed("❌ Link checking failed: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(description = "Simple link validation test on Amazon homepage", retryAnalyzer = RetryAnalyzer.class)
    public void testAmazonHomepageLinks() {
        ExtentReportManager.createTest("Amazon_Homepage_Links", "Validate links on Amazon homepage");
        ExtentReportManager.addCategory("Smoke Test");
        
        try {
            BasePage.navigateToUrl("https://www.amazon.com");
            
            String screenshotPath = BasePage.takeStepScreenshot("Amazon_Homepage");
            ExtentReportManager.addScreenshot(screenshotPath, "Amazon homepage loaded");
            
            // Get first 5 links only for quick test
            List<WebElement> links = BasePage.getDriver().findElements(By.tagName("a"));
            int linksToCheck = Math.min(5, links.size());
            
            ExtentReportManager.logInfo("Checking first " + linksToCheck + " links on Amazon homepage");
            
            for (int i = 0; i < linksToCheck; i++) {
                String href = links.get(i).getAttribute("href");
                if (isValidLink(href)) {
                    boolean isBroken = isLinkBroken(href);
                    if (!isBroken) {
                        workingLinks.add(href);
                    } else {
                        brokenLinks.add(href);
                    }
                }
            }
            
            if (brokenLinks.size() == 0) {
                ExtentReportManager.markTestPassed("✅ All checked links are working");
            } else {
                ExtentReportManager.logWarning("Found " + brokenLinks.size() + " broken links out of " + linksToCheck + " checked");
            }
            
        } catch (Exception e) {
            String errorScreenshot = BasePage.takeFailScreenshot("Homepage_Links_Error");
            ExtentReportManager.addScreenshotOnFail(errorScreenshot, "Error checking homepage links");
            ExtentReportManager.markTestFailed("❌ Homepage links test failed: " + e.getMessage());
            throw e;
        }
    }
    
    // Helper methods
    private void checkLinksOnUrl(String url) {
        ExtentReportManager.logInfo("Navigating to URL for link testing: " + url);
        BasePage.navigateToUrl(url);
        
        String navigationScreenshot = BasePage.takeStepScreenshot("Navigation_Complete");
        ExtentReportManager.addScreenshot(navigationScreenshot, "Successfully navigated to: " + url);
        
        checkLinksOnCurrentPage();
    }
    
    private void checkLinksOnCurrentPage() {
        try {
            String currentUrl = BasePage.getCurrentUrl();
            ExtentReportManager.logInfo("Checking links on page: " + currentUrl);
            
            BasePage.waitFor(3000);
            
            List<WebElement> links = BasePage.getDriver().findElements(By.tagName("a"));
            ExtentReportManager.logInfo("Total links found on page: " + links.size());
            
            int checkedLinks = 0;
            for (WebElement link : links) {
                String href = link.getAttribute("href");
                
                if (isValidLink(href)) {
                    checkedLinks++;
                    ExtentReportManager.logInfo("Checking link " + checkedLinks + ": " + href);
                    
                    if (isLinkBroken(href)) {
                        brokenLinks.add(href);
                    } else {
                        workingLinks.add(href);
                    }
                    
                    BasePage.waitFor(100);
                    
                    // Limit to first 20 links for performance
                    if (checkedLinks >= 20) {
                        ExtentReportManager.logInfo("Limited check to first 20 links for performance");
                        break;
                    }
                }
            }
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Error during link checking: " + e.getMessage());
            throw new RuntimeException("Link checking failed", e);
        }
    }
    
    private boolean isLinkBroken(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) link.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("HEAD");
            httpURLConnection.connect();
            
            int responseCode = httpURLConnection.getResponseCode();
            
            if (responseCode >= 400) {
                ExtentReportManager.logFail("Broken Link: " + url + " (Response Code: " + responseCode + ")");
                return true;
            } else {
                ExtentReportManager.logPass("Working Link: " + url + " (Response Code: " + responseCode + ")");
                return false;
            }
        } catch (Exception e) {
            ExtentReportManager.logFail("Exception for Link: " + url + " - " + e.getMessage());
            return true;
        }
    }
    
    private boolean isValidLink(String href) {
        return href != null && 
               !href.isEmpty() && 
               !href.startsWith("javascript:") && 
               !href.startsWith("mailto:") &&
               !href.startsWith("tel:") &&
               !href.startsWith("#") &&
               (href.startsWith("http://") || href.startsWith("https://"));
    }
    
    private void generateLinkCheckSummary() {
        ExtentReportManager.logInfo("=== Link Check Summary ===");
        ExtentReportManager.logInfo("Working links: " + workingLinks.size());
        ExtentReportManager.logInfo("Broken links: " + brokenLinks.size());
        
        if (brokenLinks.size() > 0) {
            ExtentReportManager.logWarning("Broken links found:");
            for (int i = 0; i < Math.min(brokenLinks.size(), 5); i++) {
                ExtentReportManager.logFail("Broken Link " + (i + 1) + ": " + brokenLinks.get(i));
            }
            if (brokenLinks.size() > 5) {
                ExtentReportManager.logInfo("... and " + (brokenLinks.size() - 5) + " more broken links");
            }
        }
        
        String summaryScreenshot = BasePage.takeStepScreenshot("Link_Check_Complete");
        ExtentReportManager.addScreenshot(summaryScreenshot, "Link checking completed");
    }
}
