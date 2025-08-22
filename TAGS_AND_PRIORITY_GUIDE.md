# TestNG Tags and Priority Implementation Guide

## 🏷️ Overview

This guide explains the comprehensive tags (groups) and priority system implemented in the Amazon Selenium automation framework. The system allows for flexible test execution based on functional categories, priority levels, and test types.

## 📊 Priority Levels

### **Priority 1 - Critical/Blocking** 🔴
- **Purpose**: Essential functionality that must work
- **When to run**: Before every release, daily builds
- **Tests**: Core homepage functionality
- **Examples**: 
  - `testAmazonHomepage()` - Basic site accessibility

### **Priority 2 - High Importance** 🟠
- **Purpose**: Key features that are important for user experience
- **When to run**: Regular regression testing
- **Tests**: Main user flows, important link validation
- **Examples**: 
  - `testAmazonProductSearch()` - Search functionality
  - `testAmazonHomepageLinks()` - Homepage link validation

### **Priority 3 - Medium Importance** 🟡
- **Purpose**: Secondary features and extended validation
- **When to run**: Weekly regression, full test cycles
- **Tests**: Comprehensive link checking
- **Examples**: 
  - `testBrokenLinks()` - Full website link validation

### **Priority 4-5 - Low/Demo** 🟢
- **Purpose**: Demo tests, development validation
- **When to run**: Development testing, feature demos
- **Tests**: Retry demos, development tests

## 🏷️ Group Tags System

### **Test Type Groups**

#### **smoke** 🚀
- **Purpose**: Quick validation of critical functionality
- **Tests**: Essential features that must work
- **Runtime**: ~2-5 minutes
- **Usage**: Daily builds, quick health checks

#### **sanity** ✅
- **Purpose**: Basic functionality validation
- **Tests**: Core features working as expected
- **Runtime**: ~5-10 minutes
- **Usage**: Post-deployment verification

#### **regression** 🔄
- **Purpose**: Comprehensive testing of all features
- **Tests**: Full feature coverage
- **Runtime**: ~15-30 minutes
- **Usage**: Release testing, weekly runs

### **Functional Groups**

#### **homepage** 🏠
- **Purpose**: Homepage-related functionality
- **Tests**: Homepage loading, basic navigation
- **Coverage**: Landing page features

#### **search** 🔍
- **Purpose**: Search functionality testing
- **Tests**: Product search, search results
- **Coverage**: Search features and flows

#### **links** 🔗
- **Purpose**: Link validation and navigation
- **Tests**: Broken link detection, link validation
- **Coverage**: All site navigation elements

#### **validation** ✓
- **Purpose**: Data and content validation
- **Tests**: Content checks, data integrity
- **Coverage**: Validation logic

#### **e2e** 🛤️
- **Purpose**: End-to-end user journeys
- **Tests**: Complete user workflows
- **Coverage**: Full user scenarios

### **Priority Groups**

#### **critical** 🚨
- **Purpose**: Must-have functionality
- **Tests**: Core features that block releases if broken
- **Impact**: High business impact

#### **high** ⚡
- **Purpose**: Important features
- **Tests**: Key user functionality
- **Impact**: Significant user impact

#### **medium** ⚖️
- **Purpose**: Standard features
- **Tests**: Regular functionality
- **Impact**: Moderate user impact

#### **low** 📝
- **Purpose**: Nice-to-have features
- **Tests**: Optional or demo functionality
- **Impact**: Minimal user impact

### **Special Groups**

#### **demo** 🎭
- **Purpose**: Demonstration and testing of framework features
- **Tests**: Retry demos, framework validation

#### **flaky** 🌀
- **Purpose**: Tests that may be unstable
- **Tests**: Tests known to have timing issues

#### **quick** ⚡
- **Purpose**: Fast-running tests
- **Tests**: Tests that complete quickly

#### **stable** 🏛️
- **Purpose**: Reliable, consistent tests
- **Tests**: Tests with high success rates

## 📋 TestNG Configuration Files

### **testng.xml** (Default)
```xml
<!-- Basic configuration with retry listener -->
<suite name="AmazonAutomationTestSuite">
    <listeners>
        <listener class-name="com.example.utils.RetryListener"/>
    </listeners>
    <!-- All tests -->
</suite>
```

### **testng-smoke.xml**
```xml
<!-- Quick smoke tests -->
<groups>
    <include name="smoke"/>
    <include name="critical"/>
</groups>
```

### **testng-regression.xml**
```xml
<!-- Comprehensive regression tests -->
<groups>
    <include name="regression"/>
    <include name="high"/>
    <include name="medium"/>
</groups>
```

### **testng-priority.xml**
```xml
<!-- Priority-based execution -->
<test name="Priority1-Critical">
    <groups><include name="critical"/></groups>
</test>
<test name="Priority2-High">
    <groups><include name="high"/></groups>
</test>
```

### **testng-functional.xml**
```xml
<!-- Functional group testing -->
<test name="SearchFunctionality">
    <groups><include name="search"/></groups>
</test>
<test name="HomepageFunctionality">
    <groups><include name="homepage"/></groups>
</test>
```

### **testng-sanity.xml**
```xml
<!-- Quick sanity checks -->
<groups>
    <include name="sanity"/>
    <include name="quick"/>
</groups>
```

## 🚀 Execution Methods

### **Local Execution**

#### **Run by TestNG Configuration**
```bash
# Smoke tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml

# Regression tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-regression.xml

# Priority-based tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-priority.xml

# Functional tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-functional.xml

# Sanity tests
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-sanity.xml
```

#### **Run by Groups (Command Line)**
```bash
# Run specific groups
mvn test -Dtest.groups=smoke
mvn test -Dtest.groups=regression
mvn test -Dtest.groups=critical,high
mvn test -Dtest.groups=search,homepage

# Exclude specific groups
mvn test -Dexclude.groups=demo,flaky
mvn test -Dtest.groups=regression -Dexclude.groups=low

# Mixed execution
mvn test -Dtest.groups=smoke,sanity -Dexclude.groups=demo
```

### **GitHub Actions Execution**

#### **Manual Workflow Dispatch**
1. Go to **Actions** tab in GitHub
2. Select **"Selenium Automation Tests"**
3. Click **"Run workflow"**
4. Choose from dropdown:
   - `all` - All test suites
   - `smoke` - Smoke tests only
   - `regression` - Regression tests only
   - `priority` - Priority-based execution
   - `functional` - Functional group tests
   - `sanity` - Sanity tests only
   - `broken-links` - Link validation only

#### **Automatic Triggers**
- **Push/PR to main**: Runs smoke tests by default
- **Scheduled runs**: Full regression suite
- **Manual dispatch**: User-selected suite

## 📈 Test Execution Strategy

### **Development Phase**
```bash
# Quick feedback during development
mvn test -Dtest.groups=smoke,sanity
```

### **Feature Testing**
```bash
# Test specific functionality
mvn test -Dtest.groups=search  # For search features
mvn test -Dtest.groups=links   # For navigation features
```

### **Release Preparation**
```bash
# Critical tests first
mvn test -Dtest.groups=critical

# Then high priority
mvn test -Dtest.groups=high

# Full regression before release
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-regression.xml
```

### **Post-Deployment**
```bash
# Quick health check
mvn test -Dtest.groups=smoke

# Sanity validation
mvn test -Dtest.groups=sanity
```

## 🔍 Test Examples by Category

### **Critical Tests (Priority 1)**
```java
@Test(priority = 1, groups = {"smoke", "homepage", "sanity", "critical"})
public void testAmazonHomepage() {
    // Essential homepage functionality
}
```

### **High Priority Tests (Priority 2)**
```java
@Test(priority = 2, groups = {"regression", "search", "e2e", "high"})
public void testAmazonProductSearch() {
    // Important search functionality
}

@Test(priority = 2, groups = {"smoke", "links", "sanity", "high"})
public void testAmazonHomepageLinks() {
    // Critical link validation
}
```

### **Medium Priority Tests (Priority 3)**
```java
@Test(priority = 3, groups = {"regression", "links", "validation", "medium"})
public void testBrokenLinks() {
    // Comprehensive link checking
}
```

## 🛠️ Best Practices

### **Group Assignment Guidelines**
1. **Every test should have**:
   - One priority group (`critical`, `high`, `medium`, `low`)
   - One or more functional groups (`search`, `homepage`, `links`)
   - One test type group (`smoke`, `sanity`, `regression`)

2. **Group naming conventions**:
   - Use lowercase for group names
   - Use descriptive, functional names
   - Keep group names consistent across tests

3. **Priority assignment**:
   - Priority 1: Blocking issues, core functionality
   - Priority 2: Important features, key user flows
   - Priority 3: Extended features, comprehensive validation
   - Priority 4-5: Demo, development, low-impact tests

### **Execution Strategy**
1. **Daily builds**: Run `smoke` and `critical` groups
2. **Feature development**: Run relevant functional groups
3. **Pre-release**: Run `regression` with `high` and `medium` priorities
4. **Post-deployment**: Run `sanity` tests
5. **Weekly**: Full `regression` suite

### **CI/CD Integration**
- **Fast feedback**: Use `smoke` tests for quick validation
- **Comprehensive coverage**: Use `regression` for thorough testing
- **Selective testing**: Use functional groups for feature-specific testing
- **Priority-based**: Use priority groups for risk-based testing

## 📊 Reporting and Monitoring

### **Group-based Metrics**
- Track success rates by group
- Monitor execution time by priority
- Identify flaky tests within groups
- Analyze failure patterns by functional area

### **Extent Reports Integration**
- Test categories automatically set based on groups
- Priority information visible in reports
- Group-based filtering in reports
- Execution time tracking by group

This comprehensive tagging and priority system provides flexible, efficient test execution while maintaining clear organization and traceability of your automation suite.
