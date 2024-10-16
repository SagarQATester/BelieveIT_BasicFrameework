package com.qa.test.suite;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.test.allureReports.AllureListener;
import com.qa.test.allureReports.BaseClass;
import com.qa.test.pages.HomePage;
import com.qa.test.pages.LoginPage;
import com.qa.test.reusableComponents.PropertiesOperations;
import com.qa.test.reusableComponents.ReadExcelSheetData;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Listeners({AllureListener.class})
public class LoginSuite extends BaseClass {

	@Severity(SeverityLevel.BLOCKER)	
	@Description("TS_001-TC_001: To check the User Login screen in NCMS system with valid credentials")
	@Epic("Login Screen")
	@Feature("Feature : TS_001-TC_001: To check the User Login screen in NCMS system with valid credentials")
	@Story("Story:Login Screen")
	@Test(groups={"Regression","LoginScreen","TS_001-TC_001","ToBeExecutedAlways"},priority=1,enabled=true,description="TS_001-TC_001: To check the User Login screen in NCMS system with valid credentials")
	public void TS_001_TC_001() throws Exception
	{
		try {

			final String username=PropertiesOperations.getPropertyValueByKey("username");
			final String password=PropertiesOperations.getPropertyValueByKey("password");
			final String domain=PropertiesOperations.getPropertyValueByKey("domain");

			BaseClass baseClass=new BaseClass();
			baseClass.initialize_driver();
			//Create Object of Login Page
			LoginPage loginpage=new LoginPage();
			//Pass Login Credentials 
			loginpage.login(username, password, domain);
			//Click on Login Button
			loginpage.clickOnBtnLogin();
			HomePage homePage=new HomePage();
			//Verify Login Successful message
			Assert.assertTrue(homePage.verifyLoginSuccessFul(), "User was able to login Successfully");

		}catch (Exception e) {
			e.printStackTrace();
			Assert.fail("User was not able to login ");
		} finally {

		}
	}
	
}
