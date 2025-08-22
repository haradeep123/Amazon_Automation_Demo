package com.example.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import com.example.utils.ExtentReportManager;
import com.example.utils.RetryAnalyzer;

/**
 * Demo test class to demonstrate retry functionality
 * This class contains intentionally flaky tests to showcase the retry analyzer
 */
public class RetryDemoTest {
    
    private static int attempt = 0;
    
    @BeforeClass
    public void setupClass() {
        ExtentReportManager.initReports();
        System.out.println("🔄 Starting Retry Demo Test Suite");
    }
    
    @AfterClass
    public void tearDownClass() {
        ExtentReportManager.flushReports();
        System.out.println("📊 Retry Demo Test Suite Completed");
    }
    
    @BeforeMethod
    public void setupTest() {
        ExtentReportManager.logInfo("Setting up test method");
    }
    
    @AfterMethod
    public void tearDownTest() {
        ExtentReportManager.logInfo("Cleaning up test method");
    }
    
    /**
     * Test that simulates a flaky test - fails first 2 times, passes on 3rd attempt
     */
    @Test(description = "Demo test that passes after retries", 
          retryAnalyzer = RetryAnalyzer.class,
          priority = 4,
          groups = {"demo", "flaky", "retry", "low"})
    public void testFlakyTestThatEventuallyPasses() {
        ExtentReportManager.createTest("Flaky_Test_Eventually_Passes", 
                                     "Demonstrates retry functionality - passes after 2 failures");
        ExtentReportManager.addCategory("Retry Demo");
        ExtentReportManager.addAuthor("Test Framework");
        
        attempt++;
        ExtentReportManager.logInfo("Test attempt number: " + attempt);
        
        try {
            // Simulate flaky behavior - fail first 2 attempts, pass on 3rd
            if (attempt <= 2) {
                ExtentReportManager.logInfo("Simulating test failure on attempt " + attempt);
                Assert.fail("Simulated failure - attempt " + attempt + " (will retry)");
            } else {
                ExtentReportManager.logInfo("Test passed on attempt " + attempt);
                ExtentReportManager.markTestPassed("✅ Test passed after " + (attempt - 1) + " retries");
            }
            
        } catch (Exception e) {
            ExtentReportManager.markTestFailed("❌ Test failed on attempt " + attempt + ": " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test that always passes - no retry needed
     */
    @Test(description = "Test that always passes", 
          retryAnalyzer = RetryAnalyzer.class,
          priority = 4,
          groups = {"demo", "stable", "quick", "low"})
    public void testAlwaysPass() {
        ExtentReportManager.createTest("Always_Pass_Test", "Test that passes on first attempt");
        ExtentReportManager.addCategory("Retry Demo");
        
        try {
            ExtentReportManager.logInfo("This test always passes - no retry needed");
            Assert.assertTrue(true, "This test always passes");
            ExtentReportManager.markTestPassed("✅ Test passed on first attempt");
            
        } catch (Exception e) {
            ExtentReportManager.markTestFailed("❌ Unexpected failure: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Test that always fails - exhausts all retries
     */
    @Test(description = "Test that always fails", 
          retryAnalyzer = RetryAnalyzer.class,
          priority = 5,
          groups = {"demo", "negative", "failure", "low"})
    public void testAlwaysFail() {
        ExtentReportManager.createTest("Always_Fail_Test", 
                                     "Test that fails even after retries - demonstrates retry exhaustion");
        ExtentReportManager.addCategory("Retry Demo");
        
        try {
            ExtentReportManager.logInfo("This test always fails - will exhaust all retries");
            Assert.fail("This test is designed to always fail to demonstrate retry exhaustion");
            
        } catch (Exception e) {
            ExtentReportManager.markTestFailed("❌ Test failed as expected: " + e.getMessage());
            throw e;
        }
    }
    
    @AfterSuite
    public void resetCounter() {
        // Reset attempt counter for next run
        attempt = 0;
    }
}
