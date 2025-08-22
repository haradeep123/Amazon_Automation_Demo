package com.example.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * RetryAnalyzer implements IRetryAnalyzer to handle flaky tests
 * by automatically retrying failed test cases up to a maximum number of attempts.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2; // Maximum number of retries
    
    /**
     * Determines whether a test should be retried based on the test result
     * 
     * @param result The test result object containing test execution details
     * @return true if the test should be retried, false otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        // Only retry if the test failed and we haven't exceeded max retry count
        if (!result.isSuccess() && retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            
            // Log the retry attempt
            System.out.println("Retrying test: " + result.getMethod().getMethodName() 
                             + " | Attempt: " + retryCount + "/" + MAX_RETRY_COUNT);
            
            // Take screenshot for failed attempt if ExtentReportManager is available
            try {
                if (ExtentReportManager.getTest() != null) {
                    ExtentReportManager.getTest().info("Test failed - Retry attempt " + retryCount);
                }
            } catch (Exception e) {
                System.out.println("Could not log retry attempt to Extent Report: " + e.getMessage());
            }
            
            return true; // Retry the test
        }
        
        // Reset retry count for next test
        retryCount = 0;
        return false; // Don't retry
    }
    
    /**
     * Gets the current retry count
     * 
     * @return Current retry count
     */
    public int getRetryCount() {
        return retryCount;
    }
    
    /**
     * Gets the maximum retry count
     * 
     * @return Maximum retry count
     */
    public static int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }
}
