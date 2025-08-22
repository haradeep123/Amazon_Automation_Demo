# TestNG Retry Implementation for Flaky Tests

## 🔄 Overview

This implementation provides comprehensive retry functionality for handling flaky tests in the Amazon Selenium automation framework using TestNG's IRetryAnalyzer interface.

## 📋 Components Implemented

### 1. **RetryAnalyzer.java**
- Core implementation of `IRetryAnalyzer` interface
- Handles test retry logic with configurable max retry count (currently set to 2)
- Integrates with Extent Reports for retry logging
- Provides detailed console logging for retry attempts

### 2. **RetryListener.java**
- Implements `IAnnotationTransformer` interface
- Automatically applies RetryAnalyzer to ALL test methods
- Eliminates need to manually add `retryAnalyzer` to each `@Test` annotation

### 3. **RetryTransformer.java**
- Enhanced retry analyzer with additional features:
  - Better logging and reporting
  - Failure reason capture
  - Automatic delay between retry attempts
  - Integration with Extent Reports

### 4. **Updated TestNG Configuration**
- `testng.xml` - Updated with RetryListener
- `testng-with-retry.xml` - Enhanced configuration for CI/CD with retry support

## 🚀 How It Works

### Automatic Retry Application
```xml
<!-- In testng.xml -->
<listeners>
    <listener class-name="com.example.utils.RetryListener"/>
</listeners>
```

### Manual Retry Application
```java
@Test(retryAnalyzer = RetryAnalyzer.class)
public void testMethod() {
    // Test implementation
}
```

### Retry Flow
1. **Test Execution**: Test runs normally
2. **Failure Detection**: If test fails, RetryAnalyzer.retry() is called
3. **Retry Decision**: Checks if retry count < MAX_RETRY_COUNT
4. **Retry Execution**: If yes, increments counter and re-runs test
5. **Final Result**: After max retries, test is marked as failed

## 📊 Logging & Reporting

### Console Output
```
🔄 RETRY ATTEMPT: Test 'testAmazonProductSearch' failed. Retry 1/2
Failure reason: Element not found: //button[@id='submit']
🔄 RETRY ATTEMPT: Test 'testAmazonProductSearch' failed. Retry 2/2
❌ TEST FAILED: 'testAmazonProductSearch' failed after 2 retry attempts
```

### Extent Reports Integration
- Retry attempts logged as warnings
- Failure reasons captured
- Final results properly marked
- Screenshots taken for each attempt

## ⚙️ Configuration

### Current Settings
- **Max Retry Count**: 2
- **Retry Delay**: 1 second between attempts
- **Auto-Apply**: Yes (via RetryListener)
- **Extent Integration**: Yes

### Customization Options
```java
// In RetryAnalyzer.java
private static final int MAX_RETRY_COUNT = 2; // Change this value

// In RetryTransformer.java
Thread.sleep(1000); // Adjust delay between retries
```

## 🎯 Test Classes Updated

### 1. AmazonAutomationTest.java
- Added RetryAnalyzer import
- Added `retryAnalyzer = RetryAnalyzer.class` to both test methods:
  - `testAmazonProductSearch()`
  - `testAmazonHomepage()`

### 2. BrokenLinksTest.java
- Added RetryAnalyzer import
- Added retry analyzer to both test methods:
  - `testBrokenLinks()`
  - `testAmazonHomepageLinks()`

### 3. RetryDemoTest.java (New)
- Demonstrates retry functionality
- Contains intentionally flaky tests
- Shows retry exhaustion scenarios

## 🔧 GitHub Actions Integration

### Updated Workflow Features
- Uses `testng-with-retry.xml` configuration
- Sets `RETRY_ENABLED=true` environment variable
- Enhanced reporting shows retry status
- Proper artifact collection for retry logs

### Workflow Command
```yaml
mvn test -Dbrowser=${{ matrix.browser }} 
         -Djava.awt.headless=true 
         -Dsurefire.suiteXmlFiles=src/test/resources/testng-with-retry.xml
```

## 📈 Benefits

### 1. **Reduced False Failures**
- Handles network timeouts
- Manages element loading delays
- Addresses browser inconsistencies

### 2. **Better Test Stability**
- Automatic retry for transient failures
- Configurable retry count
- Proper failure classification

### 3. **Enhanced Reporting**
- Clear retry attempt logging
- Failure reason tracking
- Integration with existing reports

### 4. **Zero Code Impact**
- Automatic application via listener
- No need to modify existing tests
- Backward compatible

## 🏃‍♂️ Running Tests with Retry

### Local Execution
```bash
# Using default testng.xml (with retry listener)
mvn test

# Using enhanced retry configuration
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-with-retry.xml

# Run specific test to see retry in action
mvn test -Dtest=RetryDemoTest
```

### GitHub Actions
- Automatically enabled in CI/CD pipeline
- Retry status shown in PR comments
- Artifacts include retry logs

## 🔍 Monitoring Retry Usage

### Key Metrics to Track
- Number of tests that required retries
- Tests that consistently fail after retries
- Average retry count per test run
- Success rate after retries

### Logs to Review
- Console output for retry attempts
- Extent Reports for detailed failure analysis
- Surefire reports for statistical data

## 🛠 Troubleshooting

### Common Issues
1. **Tests not retrying**: Check if RetryListener is in testng.xml
2. **Too many retries**: Adjust MAX_RETRY_COUNT in RetryAnalyzer
3. **Missing retry logs**: Verify Extent Reports initialization

### Best Practices
- Use retries for truly flaky tests only
- Don't rely on retries for broken test logic
- Monitor retry patterns to identify root causes
- Keep retry count low (2-3 maximum)

## 📝 Example Usage

See `RetryDemoTest.java` for practical examples of:
- Tests that pass after retries
- Tests that always pass (no retry needed)
- Tests that exhaust all retries

This retry implementation provides a robust solution for handling flaky tests while maintaining clear visibility into test execution patterns and failure modes.
