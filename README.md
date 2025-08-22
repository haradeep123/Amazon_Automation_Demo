# Amazon Automation Framework with Proper Test Structure

This project demonstrates a well-structured test automation framework using Selenium WebDriver with Page Object Model (POM), TestNG, Extent Reports, and proper Maven directory structure.

## 🏗️ Project Structure

```
Practice26/
├── pom.xml                                    # Maven configuration
├── reports/                                   # Generated test reports
│   ├── AutomationReport_*.html               # Extent Reports HTML
│   └── screenshots/                          # Test screenshots
├── src/
│   ├── main/java/com/example/                # Framework/Utility Code
│   │   ├── CSVDataReader.java                # CSV utility for test data
│   │   ├── pages/                           # Page Object Model classes
│   │   │   ├── BasePage.java                # Base page with browser management
│   │   │   ├── AmazonHomePage.java          # Amazon home page object
│   │   │   ├── AmazonSearchResultsPage.java # Search results page object
│   │   │   └── AmazonProductPage.java       # Product page object
│   │   └── utils/                           # Utility classes
│   │       ├── ExtentReportManager.java     # Extent Reports management
│   │       └── ScreenshotUtils.java         # Screenshot utilities
│   └── test/                                # Test Code (Separate from main)
│       ├── java/com/example/tests/          # Test classes
│       │   ├── AmazonAutomationTest.java    # Main Amazon tests
│       │   ├── BrokenLinksTest.java         # Link validation tests
│       │   └── AmazonIntegrationTest.java   # Integration tests
│       └── resources/                       # Test resources
│           ├── testdata.csv                 # Test data
│           └── testng.xml                   # TestNG configuration
└── README.md                                # This file
```

## ✨ Key Features

### 🔄 **Proper Separation of Concerns:**
- **`src/main/java`**: Framework code, utilities, page objects
- **`src/test/java`**: Test classes only
- **`src/test/resources`**: Test data and configuration files

### 🎯 **Test Framework Benefits:**
- **Clean Architecture**: Tests separated from framework code
- **Reusable Components**: Page objects and utilities can be used across tests
- **Data-Driven Testing**: CSV test data in test resources
- **Professional Reporting**: Extent Reports with screenshots
- **Multiple Test Types**: Unit tests, integration tests, broken links tests

## 🚀 How to Run Tests

### **Run All Tests:**
```bash
mvn test
```

### **Run Specific Test Class:**
```bash
mvn test -Dtest=AmazonAutomationTest
```

### **Run Specific Test Method:**
```bash
mvn test -Dtest=AmazonAutomationTest#testAmazonHomepage
```

### **Run with TestNG Suite:**
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### **Run Integration Tests:**
```bash
mvn failsafe:integration-test
```

### **Run with Different Browser:**
```bash
mvn test -Dbrowser=firefox
```

## 📊 **Generated Reports:**

After running tests, check:
- **HTML Report**: `reports/AutomationReport_YYYY-MM-DD_HH-mm-ss.html`
- **Screenshots**: `reports/screenshots/` directory
- **Console Output**: Real-time test execution logs

## 🎯 **Test Types Available:**

### **1. Amazon Automation Tests (`AmazonAutomationTest.java`):**
- **Data-driven tests** using CSV data
- **Homepage navigation test**
- **Product search and navigation**
- **Extent Reports integration**

### **2. Broken Links Tests (`BrokenLinksTest.java`):**
- **URL validation tests**
- **Link checking with HTTP status codes**
- **Comprehensive reporting**

### **3. Integration Tests (`AmazonIntegrationTest.java`):**
- **End-to-end navigation tests**
- **Performance tests**
- **System integration validation**

## 🔧 **Maven Commands:**

### **Development:**
```bash
mvn clean compile                # Compile main code
mvn compile test-compile         # Compile test code
mvn clean                        # Clean target directory
```

### **Testing:**
```bash
mvn test                         # Run all tests
mvn surefire-report:report       # Generate test reports
mvn site                         # Generate project site
```

### **Build:**
```bash
mvn package                      # Build JAR file
mvn install                      # Install to local repository
```

## 📁 **Test Data Management:**

### **CSV Test Data (`src/test/resources/testdata.csv`):**
```csv
SearchTerm,ResultIndex
iphone,0
laptop,1
headphones,2
```

### **TestNG Configuration (`src/test/resources/testng.xml`):**
- Smoke tests suite
- Full automation tests suite
- Broken links tests suite

## 🎨 **Page Object Model:**

### **BasePage (`src/main/java/.../pages/BasePage.java`):**
- Browser initialization and management
- Screenshot utilities
- Common page operations

### **Page Objects:**
- **AmazonHomePage**: Search functionality
- **AmazonSearchResultsPage**: Results interaction
- **AmazonProductPage**: Product page operations

## 📈 **Reporting Features:**

### **Extent Reports Include:**
- Test execution timeline
- Pass/fail statistics
- Screenshots for each step
- Error details with stack traces
- System environment information
- Test categorization and authorship

## 🔍 **Best Practices Implemented:**

1. **Maven Standard Directory Layout**
2. **Separation of Test and Production Code**
3. **Page Object Model Pattern**
4. **Data-Driven Testing**
5. **Comprehensive Test Reporting**
6. **Proper Exception Handling**
7. **Reusable Utility Classes**
8. **TestNG Annotations and Configuration**

## 📚 **Dependencies Used:**

- **Selenium WebDriver 4.15.0**: Web automation
- **TestNG 7.8.0**: Test framework
- **Extent Reports 5.1.1**: HTML reporting
- **OpenCSV 5.9**: CSV data reading
- **Apache Commons IO 2.11.0**: File operations
- **WebDriverManager 5.6.2**: Driver management

## 🎯 **Getting Started:**

1. **Clone/Download the project**
2. **Install Java 11+ and Maven**
3. **Run**: `mvn clean test`
4. **View Reports**: Open generated HTML report in browser

This structure follows Maven best practices and provides a professional, maintainable test automation framework! 🎉