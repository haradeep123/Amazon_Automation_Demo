package com.example.tests;

import org.testng.annotations.*;
import com.example.pages.BasePage;
import com.example.pages.AmazonHomePage;
import com.example.pages.AmazonSearchResultsPage;
import com.example.pages.AmazonProductPage;
import com.example.utils.ExtentReportManager;
import com.example.utils.CSVDataReader;

public class AmazonAutomationTest {
    // Page Object instances
    private AmazonHomePage homePage;
    private AmazonSearchResultsPage searchResultsPage;
    private AmazonProductPage productPage;
    
    @BeforeClass
    public void setupClass() {
        // Initialize Extent Reports once for the entire class
        ExtentReportManager.initReports();
        System.out.println("🚀 Starting Amazon Automation Test Suite");
    }
    
    @BeforeMethod
    public void setupTest() {
        // Initialize browser and page objects before each test
        BasePage.initializeBrowser();
        
        homePage = new AmazonHomePage();
        searchResultsPage = new AmazonSearchResultsPage();
        productPage = new AmazonProductPage();
        
        ExtentReportManager.logInfo("Browser initialized for test");
    }
    
    @AfterMethod
    public void tearDownTest() {
        // Clean up after each test
        BasePage.quitBrowser();
        ExtentReportManager.logInfo("Browser closed after test");
    }
    
    @AfterClass
    public void tearDownClass() {
        // Generate final report after all tests
        ExtentReportManager.flushReports();
        System.out.println("📊 Test Suite Completed - Report: " + ExtentReportManager.getReportPath());
    }
    
    @DataProvider(name = "amazonSearchData")
    public Object[][] getAmazonSearchData() {
        // Read from test resources directory
        String csvFilePath = "src/test/resources/testdata.csv";
        return CSVDataReader.readCSVData(csvFilePath);
    }
    
    @Test(dataProvider = "amazonSearchData", description = "Amazon product search and navigation test")
    public void testAmazonProductSearch(String searchTerm, String resultIndex) throws InterruptedException {
        int index = Integer.parseInt(resultIndex);
        String testName = "Amazon_Search_" + searchTerm.replace(" ", "_");
        String testDescription = "Search for '" + searchTerm + "' and click result at index " + index;
        
        // Create test in report
        ExtentReportManager.createTest(testName, testDescription);
        ExtentReportManager.addCategory("Amazon Automation");
        ExtentReportManager.addAuthor("Test Framework");
        
        try {
            // Test Steps
            searchProduct(searchTerm);
            clickSearchResult(index);
            scrollToElement("//span[@aria-label='Videos for similar products']");
            scrollBottomAndTop();
            
            // Mark test as passed
            String passScreenshot = BasePage.takePassScreenshot(testName);
            ExtentReportManager.addScreenshotOnPass(passScreenshot, "Test completed successfully");
            ExtentReportManager.markTestPassed("✅ Amazon search test completed successfully");
            
        } catch (Exception e) {
            // Mark test as failed
            String failScreenshot = BasePage.takeFailScreenshot(testName);
            ExtentReportManager.addScreenshotOnFail(failScreenshot, "Test failed: " + e.getMessage());
            ExtentReportManager.markTestFailed("❌ Test failed: " + e.getMessage());
            throw e; // Re-throw to mark TestNG test as failed
        }
    }
    
    @Test(description = "Simple Amazon homepage navigation test")
    public void testAmazonHomepage() {
        ExtentReportManager.createTest("Amazon_Homepage_Test", "Test Amazon homepage loading and basic elements");
        ExtentReportManager.addCategory("Smoke Test");
        
        try {
            homePage.navigateToAmazon();
            
            String screenshotPath = BasePage.takeStepScreenshot("Homepage_Loaded");
            ExtentReportManager.addScreenshot(screenshotPath, "Amazon homepage loaded successfully");
            
            // Verify page title contains Amazon
            String pageTitle = BasePage.getPageTitle();
            if (pageTitle.toLowerCase().contains("amazon")) {
                ExtentReportManager.markTestPassed("✅ Amazon homepage loaded successfully - Title: " + pageTitle);
            } else {
                ExtentReportManager.markTestFailed("❌ Unexpected page title: " + pageTitle);
            }
            
        } catch (Exception e) {
            String errorScreenshot = BasePage.takeFailScreenshot("Homepage_Error");
            ExtentReportManager.addScreenshotOnFail(errorScreenshot, "Homepage test failed");
            ExtentReportManager.markTestFailed("❌ Homepage test failed: " + e.getMessage());
            throw e;
        }
    }
    
    // Helper methods
    private void searchProduct(String searchTerm) {
        ExtentReportManager.logInfo("Navigating to Amazon and searching for: " + searchTerm);
        homePage.navigateToAmazon();
        
        String screenshotPath = BasePage.takeStepScreenshot("Amazon_HomePage");
        ExtentReportManager.addScreenshot(screenshotPath, "Amazon Homepage Loaded");
        
        homePage.searchProduct(searchTerm);
        ExtentReportManager.logPass("Successfully searched for: " + searchTerm);
    }
    
    private void clickSearchResult(int index) throws InterruptedException {
        ExtentReportManager.logInfo("Clicking on search result at index: " + index);
        
        String beforeClickScreenshot = BasePage.takeStepScreenshot("SearchResults_Before_Click");
        ExtentReportManager.addScreenshot(beforeClickScreenshot, "Search Results Page");
        
        searchResultsPage.clickSearchResult(index);
        
        String afterClickScreenshot = BasePage.takeStepScreenshot("ProductPage_After_Click");
        ExtentReportManager.addScreenshot(afterClickScreenshot, "Product Page Loaded");
        ExtentReportManager.logPass("Successfully clicked on search result at index: " + index);
    }
    
    private void scrollToElement(String xpath) {
        ExtentReportManager.logInfo("Scrolling to element: " + xpath);
        try {
            productPage.scrollToElement(xpath);
            String screenshotPath = BasePage.takeStepScreenshot("Element_Found");
            ExtentReportManager.addScreenshot(screenshotPath, "Element found and scrolled to: " + xpath);
            ExtentReportManager.logPass("Successfully scrolled to element: " + xpath);
        } catch (Exception e) {
            String screenshotPath = BasePage.takeFailScreenshot("Element_Not_Found");
            ExtentReportManager.addScreenshotOnFail(screenshotPath, "Failed to scroll to element: " + xpath);
            ExtentReportManager.logWarning("Element not found: " + xpath + " - " + e.getMessage());
            // Don't throw exception, continue with test
        }
    }
    
    private void scrollBottomAndTop() throws InterruptedException {
        ExtentReportManager.logInfo("Performing page scroll - bottom and top");
        
        productPage.scrollBottomAndTop();
        
        String screenshotPath = BasePage.takeStepScreenshot("Page_Scrolled");
        ExtentReportManager.addScreenshot(screenshotPath, "Page scrolled to bottom and top");
        ExtentReportManager.logPass("Successfully performed page scrolling");
    }
}
