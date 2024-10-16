package com.qa.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.qa.test.allureReports.AllureListener;
import com.qa.test.allureReports.BaseClass;
import com.qa.test.reusableComponents.CommonMethods;

import io.qameta.allure.Step;

public class HomePage extends BaseClass
{
	WebDriver driver;
	//Find the WebElements using Locators
	@FindBy(css="td:nth-child(2) > span.User > a > img")
	WebElement btnLogout;

	/*
	 *Initialize and Load the Home Page 
	 */
	public HomePage() 
	{
		this.driver=BaseClass.getDriver();
		PageFactory.initElements(driver,this);
		CommonMethods.waitForPageLoad(driver, btnLogout);

	}

	/*
	 * Click on Logout button
	 */
	@Step("Click on Logout")
	public void clickOnLogout()
	{
		try
		{
			CommonMethods.click_custom(btnLogout,"Logout Button");
		}catch (Exception e) {
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to click Logout button");
			Assert.fail("Not able to click Logout button");
		}
	}

	/**
	 * Verify Login Successful
	 * @return flag
	 * 			as Boolean as true if Home page is displayed else will return false
	 */
	@Step("Verify Home Page is displayed")
	public boolean verifyLoginSuccessFul()
	{
		AllureListener allureListner=new AllureListener();
		boolean flag=false;
		try
		{
			CommonMethods.waitForElement(driver, "Logout", btnLogout);
			if(btnLogout.isDisplayed())
			{
				flag=true;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Home Page is Displayed");
				Assert.assertEquals(flag, true,"Home Page is Displayed");
			}else

			{
				flag=false;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Home Page is not Displayed");
				Assert.fail("Home Page is not Displayed");
			}
		}catch(Exception e)
		{
			allureListner.saveScreenShot(driver);
			AllureListener.saveTextLog("Home Page is not Displayed");
			Assert.fail("Home Page is not Displayed : "+e);
		}
		return flag;
	}
	/**Verify Alert Text Message
	 * @param alertTextMessage
	 * 				as String
	 * @return flag
	 */
	@Step("Verify Alert Text :alertTextMessage[0]")
	public boolean verifyAlertText(String alertTextMessage)
	{
		AllureListener allureListner=new AllureListener();
		boolean flag =CommonMethods.verifyAlertText(driver, alertTextMessage);
		CommonMethods.acceptAlert(driver);
		if(flag==true)
		{
			allureListner.saveScreenShot(driver);
			AllureListener.saveTextLog(alertTextMessage+" is displayed");
		}else
		{
			allureListner.saveScreenShot(driver);
			AllureListener.saveTextLog(alertTextMessage+" is not displayed");
		}
		return flag;
	}
}
