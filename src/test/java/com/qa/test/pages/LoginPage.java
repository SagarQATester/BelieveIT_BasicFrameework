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


public class LoginPage extends BaseClass
{
	WebDriver driver;
	@FindBy(id="userName")
	WebElement txtUsername;

	@FindBy(id="password")
	WebElement txtPassword;

	@FindBy(css="td>#dbName")
	WebElement drpDomain;

	@FindBy(id="login")
	WebElement btnLogin;

	@FindBy(id="reset")
	WebElement btnReset;

	@FindBy(css = "#loginDtls > table > tbody > tr > td")
	WebElement footer;

	/*
	 * Initialize and Load the Login Page
	 */
	public LoginPage() 
	{
		this.driver=BaseClass.getDriver();
		PageFactory.initElements(driver,this);
		CommonMethods.waitForPageLoad(driver, btnLogin);
		CommonMethods.waitForElement(driver, "Footer", footer);
		CommonMethods.waitForElement(driver, "Reset", btnReset);
	}


	/**Login to NCFE application
	 * @param username 
	 * 				 as String
	 * @param password
	 * 				as String
	 * @param domain
	 * 				as String
	 */
	@Step("User Enter username : [0] ,password :[0],domain :[0]")
	public void login(String username,String password,String domain)
	{
		//Enter  Username
		enterUsername(username);
		//Enter  Password
		enterPassword(password);
		//Select from Domain Drop Down
		selectDomain(domain);
	}

	@Step("Select value from domain")
	public void selectDomain(String domain)
	{
		try {
			//Select from Domain Drop Down
			CommonMethods.displayHiddenElementByStyleDisplay(driver,drpDomain);
			CommonMethods.selectDropDownByVisibleText_custom(drpDomain, "Domain Drop Down", domain);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to select Domain");
			Assert.fail("Not able to select Domain");
		}
	}
	/**Enter Username
	 * @param username
	 * 			as String
	 */			
	@Step("Enter Username :username[0]")
	public void enterUsername(String username)
	{
		try
		{
			CommonMethods.sendKeys_custom(txtUsername, "Username", username);
		}catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to enter username");
			Assert.fail("Not able to enter username");
		}
	}


	/**Enter Password
	 * @param password
	 * 			as String
	 */	
	@Step("Enter password :password[0]")
	public void enterPassword(String password)
	{
		try
		{
			CommonMethods.sendKeys_custom(txtPassword, "Password", password);
		}catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to enter Password");
			Assert.fail("Not able to enter Password");
		}
	}


	//Click on  Login Button
	@Step("Click on Login Button")
	public void clickOnBtnLogin()
	{
		try
		{
			CommonMethods.waitForElement(driver, "Login Button", btnLogin);
			CommonMethods.click_custom(btnLogin,"Login Button");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to Click on Login Button");
			Assert.fail("Not able to Click on Login Button");
		}
	}

	//Click on  Reset Button
	@Step("Click on Button Reset")
	public void clickOnBtnReset()
	{
		try
		{
			CommonMethods.waitForElement(driver, "Reset", btnReset);
			CommonMethods.click_custom(btnReset,"Reset Button");
		}catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to Click on Reset Button");
			Assert.fail("Not able to Click on Reset Button");
		}
	}

	/**verify TextBox Username and Password After Reset
	 * @return
	 * 		flag as true if text clears else return false
	 */
	@Step("Verify Username and Password textbox was clear After Reset")
	public boolean verifyTextBoxAfterReset()
	{
		AllureListener allureListner=new AllureListener();
		boolean flag=false;
		try
		{
			String username=txtUsername.getText();
			String password=txtPassword.getText();
			if(username.isEmpty() && password.isEmpty() )
			{
				flag=true;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Username and Password textbox was clear After Reset");
				Assert.assertEquals(flag, true,"Username and Password textbox was clear After Reset");


			}else
			{
				flag=false;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Username and Password textbox was not clear After Reset");
				Assert.fail("Username and Password textbox was not clear After Reset");

			}
		}catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
			allureListner.saveScreenShot(driver);
			AllureListener.saveTextLog("Username and Password textbox was not clear After Reset");
			Assert.fail("Username and Password textbox was not clear After Reset ");
		}
		return flag;
	}

	/**verify Dropdown selection after reset to original as Select
	 * @return
	 * 		flag as true if Dropdown selection is reset to original as Select else return false
	 */
	@Step("Verify Dropdown was reset")
	public boolean verifyDrpdownAfterReset()
	{
		AllureListener allureListner=new AllureListener();
		boolean flag=false;
		try
		{
			String domainValue = CommonMethods.selectValueWithGettext(drpDomain);

			if(domainValue.equals("Select"))
			{
				flag=true;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Dropdown was reset");
				Assert.assertEquals(flag, true,"Dropdown was reset");
			}else
			{
				flag=false;
				allureListner.saveScreenShot(driver);
				AllureListener.saveTextLog("Dropdown was not reset");
				Assert.fail("Dropdown was not reset");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			allureListner.saveScreenShot(driver);
			AllureListener.saveTextLog("Dropdown was not reset");
			Assert.fail("Dropdown was not reset");

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
