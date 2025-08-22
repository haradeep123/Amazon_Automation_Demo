package com.example.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Enhanced RetryTransformer that provides additional functionality
 * for handling retry logic with better reporting and logging
 */
public class RetryTransformer implements IRetryAnalyzer {
    
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 2;
    private String testName;
    
    /**
     * Constructor to initialize with test name for better logging
     */
    public RetryTransformer() {
        // Default constructor
    }
    
    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess() && retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            testName = result.getMethod().getMethodName();
            
            // Enhanced logging
            String retryMessage = String.format(
                "🔄 RETRY ATTEMPT: Test '%s' failed. Retry %d/%d", 
                testName, retryCount, MAX_RETRY_COUNT
            );
            
            System.out.println(retryMessage);
            Reporter.log(retryMessage, true);
            
            // Log failure reason if available
            if (result.getThrowable() != null) {
                String failureReason = "Failure reason: " + result.getThrowable().getMessage();
                System.out.println(failureReason);
                Reporter.log(failureReason, true);
            }
            
            // Add retry information to Extent Report if available
            try {
                if (ExtentReportManager.getTest() != null) {
                    ExtentReportManager.getTest().warning(
                        "Test failed - Retry attempt " + retryCount + "/" + MAX_RETRY_COUNT
                    );
                    
                    if (result.getThrowable() != null) {
                        ExtentReportManager.getTest().warning(
                            "Failure reason: " + result.getThrowable().getMessage()
                        );
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not log to Extent Report: " + e.getMessage());
            }
            
            // Small delay before retry to handle timing issues
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Retry delay interrupted: " + e.getMessage());
            }
            
            return true;
        }
        
        // Log final result if all retries exhausted
        if (retryCount >= MAX_RETRY_COUNT && !result.isSuccess()) {
            String finalMessage = String.format(
                "❌ TEST FAILED: '%s' failed after %d retry attempts", 
                testName, MAX_RETRY_COUNT
            );
            System.out.println(finalMessage);
            Reporter.log(finalMessage, true);
            
            try {
                if (ExtentReportManager.getTest() != null) {
                    ExtentReportManager.getTest().fail(
                        "Test failed after " + MAX_RETRY_COUNT + " retry attempts"
                    );
                }
            } catch (Exception e) {
                System.out.println("Could not log final failure to Extent Report: " + e.getMessage());
            }
        }
        
        retryCount = 0; // Reset for next test
        return false;
    }
    
    /**
     * Get current retry count
     */
    public int getCurrentRetryCount() {
        return retryCount;
    }
    
    /**
     * Get maximum retry count
     */
    public static int getMaxRetryCount() {
        return MAX_RETRY_COUNT;
    }
}
