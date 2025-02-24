package com.qa.test.allureReports;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import io.qameta.allure.Attachment;

public class AllureListener implements ITestListener {

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Attachment
	public byte[] saveFailureScreenShot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment
	public byte[] saveScreenShot(WebDriver driver) {
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}


	@Override
	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in on Start method " + iTestContext.getName());
		iTestContext.setAttribute("WebDriver", BaseClass.getDriver());
	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in on Finish method and Closed Browser" + iTestContext.getName());
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in on Test Start method " + getTestMethodName(iTestResult) + " start");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		Object testClass = iTestResult.getInstance();
		WebDriver driver = BaseClass.getDriver();
		// Allure ScreenShot and SaveTestLog
		if (driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveScreenShot(driver);
		}
		saveTextLog(getTestMethodName(iTestResult) + " screenshot taken!");
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		driver.close();

	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver = BaseClass.getDriver();
		// Allure ScreenShot and SaveTestLog
		if (driver instanceof WebDriver) {
			System.out.println("Screenshot captured for test case:" + getTestMethodName(iTestResult));
			saveFailureScreenShot(driver);
		}
		saveTextLog(getTestMethodName(iTestResult) + " failed and screenshot taken!");	
		driver.close();
	}


	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}

}
