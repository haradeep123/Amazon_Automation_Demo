package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AmazonHomePage extends BasePage {
    
    // Locators
    private final By searchBox1 = By.id("twotabsearchtextbox");
    private final By searchBox2 = By.name("field-keywords");
    private final By searchBox3 = By.cssSelector("input[type='text'][placeholder*='Search']");
    private final By searchButton = By.id("nav-search-submit-button");
    
    // Constructor
    public AmazonHomePage() {
        // BasePage handles driver initialization
    }
    
    // Page actions
    public void navigateToAmazon() {
        navigateToUrl("https://www.amazon.com");
        waitFor(2000);
    }
    
    public void searchProduct(String searchTerm) {
        WebElement searchBar = getSearchBox();
        searchBar.clear();
        searchBar.sendKeys(searchTerm);
        clickSearchButton();
    }
    
    private WebElement getSearchBox() {
        // Try multiple selectors for the search box
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox1));
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox2));
            } catch (Exception e2) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox3));
            }
        }
    }
    
    private void clickSearchButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        button.click();
    }
}
