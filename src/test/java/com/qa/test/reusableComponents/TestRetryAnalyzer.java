package com.qa.test.reusableComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

	int counter = 1;
	int retryMaxLimit  = Integer.valueOf(PropertiesOperations.getPropertyValueByKey("retryCount"));
	
	public boolean retry(ITestResult result) {
		if(counter<retryMaxLimit) {
			counter++;
			return true;
		}
		return false;
	}

}

