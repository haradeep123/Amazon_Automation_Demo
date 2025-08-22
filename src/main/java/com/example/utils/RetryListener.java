package com.example.utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * RetryListener implements IAnnotationTransformer to automatically apply
 * RetryAnalyzer to all test methods without manually adding it to each @Test annotation.
 */
public class RetryListener implements IAnnotationTransformer {
    
    /**
     * Transforms test annotations to automatically add RetryAnalyzer
     * 
     * @param annotation The test annotation to transform
     * @param testClass The test class
     * @param testConstructor The test constructor
     * @param testMethod The test method
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, 
                         Constructor testConstructor, Method testMethod) {
        
        // Apply RetryAnalyzer to all test methods
        if (annotation.getRetryAnalyzer() == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
