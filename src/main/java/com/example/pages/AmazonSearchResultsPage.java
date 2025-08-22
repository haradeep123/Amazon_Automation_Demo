package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class AmazonSearchResultsPage extends BasePage {
    
    // Locators
    private final By searchResults = By.xpath("//div[@data-cy='title-recipe']");
    private final By productTitles = By.xpath("//h2[@class='a-size-mini']//span");
    private final By productPrices = By.xpath("//span[@class='a-price-whole']");
    
    // Constructor
    public AmazonSearchResultsPage() {
        // BasePage handles driver initialization
    }
    
    // Page actions
    public void waitForSearchResults() {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchResults));
    }
    
    public void clickSearchResult(int index) throws InterruptedException {
        waitForSearchResults();
        
        List<WebElement> results = driver.findElements(searchResults);
        System.out.println("Total search results found: " + results.size());
        
        if (results.size() > index) {
            WebElement targetResult = results.get(index);
            System.out.println("Clicking on search result at index " + index + "...");
            targetResult.click();
        } else {
            System.out.println("Not enough search results. Only " + results.size() + " results found.");
            // Click on the last available result if no result at specified index
            if (results.size() > 0) {
                results.get(results.size() - 1).click();
            }
        }
        waitFor(5000);
    }
    
    public int getSearchResultsCount() {
        waitForSearchResults();
        List<WebElement> results = driver.findElements(searchResults);
        return results.size();
    }
    
    public List<WebElement> getSearchResults() {
        waitForSearchResults();
        return driver.findElements(searchResults);
    }
    
    public boolean areSearchResultsDisplayed() {
        try {
            waitForSearchResults();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
