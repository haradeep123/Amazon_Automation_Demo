package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AmazonProductPage extends BasePage {
    
    // Locators
    private final By productTitle = By.id("productTitle");
    private final By productPrice = By.xpath("//span[@class='a-price-whole']");
    private final By addToCartButton = By.id("add-to-cart-button");
    private final By buyNowButton = By.id("buy-now-button");
    private final By productDescription = By.xpath("//div[@id='feature-bullets']");
    private final By productImages = By.xpath("//div[@id='imgTagWrapperId']");
    private final By customerReviews = By.xpath("//div[@data-hook='review-body']");
    private final By videosSection = By.xpath("//span[@aria-label='Videos for similar products']");
    private final By productDetails = By.xpath("//div[@id='detailBullets_feature_div']");
    private final By specifications = By.xpath("//div[@id='techSpecs_feature_div']");
    
    // Constructor
    public AmazonProductPage() {
        // BasePage handles driver initialization
    }
    
    // Page actions
    public void scrollToElement(String xpath) {
        try {
            WebElement element = driver.findElement(By.xpath(xpath));
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            
            String text = element.getText();
            System.out.println("Text of the element: " + text);
        } catch (Exception e) {
            System.out.println("Element not found with xpath: " + xpath);
            throw e;
        }
    }
    
    public void scrollToVideosSection() {
        scrollToElement("//span[@aria-label='Videos for similar products']");
    }
    
    public void scrollToProductDetails() {
        scrollToElement("//div[@id='detailBullets_feature_div']");
    }
    
    public void scrollToCustomerReviews() {
        scrollToElement("//div[@data-hook='review-body']");
    }
    
    public void scrollToProductDescription() {
        scrollToElement("//div[@id='feature-bullets']");
    }
    
    public void scrollPageToBottom() throws InterruptedException {
        BasePage.scrollToBottom();
        waitFor(3000);
    }
    
    public void scrollPageToTop() throws InterruptedException {
        BasePage.scrollToTop();
        waitFor(3000);
    }
    
    public void scrollBottomAndTop() throws InterruptedException {
        scrollPageToBottom();
        scrollPageToTop();
    }
    
    public String getProductTitle() {
        try {
            WebElement title = driver.findElement(productTitle);
            return title.getText();
        } catch (Exception e) {
            return "Product title not found";
        }
    }
    
    public String getProductPrice() {
        try {
            WebElement price = driver.findElement(productPrice);
            return price.getText();
        } catch (Exception e) {
            return "Price not found";
        }
    }
    
    public boolean isProductPageLoaded() {
        try {
            return driver.findElement(productTitle).isDisplayed() || 
                   driver.findElement(productImages).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
