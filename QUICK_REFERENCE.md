# Quick Reference - Tags and Priority

## 🚀 Quick Commands

### **Smoke Tests (2-5 mins)**
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-smoke.xml
mvn test -Dtest.groups=smoke
```

### **Sanity Tests (5-10 mins)**
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-sanity.xml
mvn test -Dtest.groups=sanity
```

### **Regression Tests (15-30 mins)**
```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-regression.xml
mvn test -Dtest.groups=regression
```

### **Priority-based Execution**
```bash
# Critical tests only
mvn test -Dtest.groups=critical

# High priority tests
mvn test -Dtest.groups=high

# Critical + High priority
mvn test -Dtest.groups=critical,high

# All priorities using config
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-priority.xml
```

### **Functional Group Testing**
```bash
# Search functionality
mvn test -Dtest.groups=search

# Homepage tests
mvn test -Dtest.groups=homepage

# Link validation
mvn test -Dtest.groups=links

# End-to-end tests
mvn test -Dtest.groups=e2e

# All functional groups
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-functional.xml
```

### **Exclude Specific Groups**
```bash
# Run regression but exclude demo tests
mvn test -Dtest.groups=regression -Dexclude.groups=demo

# Run all except flaky tests
mvn test -Dexclude.groups=flaky,demo

# Run high priority excluding links
mvn test -Dtest.groups=high -Dexclude.groups=links
```

## 📋 Test Groups Reference

### **Priority Groups**
- `critical` - Must work (Priority 1)
- `high` - Important (Priority 2)
- `medium` - Standard (Priority 3)
- `low` - Optional (Priority 4-5)

### **Test Type Groups**
- `smoke` - Quick critical tests
- `sanity` - Basic functionality
- `regression` - Comprehensive testing

### **Functional Groups**
- `homepage` - Homepage functionality
- `search` - Search features
- `links` - Link validation
- `validation` - Data validation
- `e2e` - End-to-end flows

### **Special Groups**
- `demo` - Framework demos
- `flaky` - Potentially unstable
- `quick` - Fast execution
- `stable` - Reliable tests

## 🎯 Common Use Cases

### **Daily Development**
```bash
mvn test -Dtest.groups=smoke,sanity
```

### **Feature Development**
```bash
# For search feature work
mvn test -Dtest.groups=search,smoke

# For homepage work
mvn test -Dtest.groups=homepage,sanity
```

### **Pre-Release Testing**
```bash
# Step 1: Critical tests
mvn test -Dtest.groups=critical

# Step 2: High priority
mvn test -Dtest.groups=high

# Step 3: Full regression
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-regression.xml
```

### **Post-Deployment**
```bash
mvn test -Dtest.groups=smoke,critical
```

### **Debug/Development**
```bash
# Exclude problematic tests
mvn test -Dtest.groups=smoke -Dexclude.groups=flaky
```

## 🔧 GitHub Actions

### **Manual Trigger Options**
- `all` - All test suites
- `smoke` - Smoke tests
- `regression` - Regression tests
- `priority` - Priority-based
- `functional` - Functional groups
- `sanity` - Sanity tests
- `broken-links` - Link validation

### **Browser Selection**
- `chrome` (default)
- `firefox`
- `edge`

## 📊 Test Distribution

### **By Priority**
- Priority 1: 1 test (critical homepage)
- Priority 2: 2 tests (search + homepage links)
- Priority 3: 1 test (broken links)
- Priority 4-5: 3 tests (demo tests)

### **By Groups**
- `smoke`: 2 tests
- `regression`: 3 tests
- `homepage`: 2 tests
- `search`: 1 test
- `links`: 2 tests
- `critical`: 1 test
- `high`: 2 tests
- `medium`: 1 test
