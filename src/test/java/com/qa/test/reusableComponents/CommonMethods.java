package com.qa.test.reusableComponents;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qa.test.allureReports.AllureListener;
import com.qa.test.allureReports.BaseClass;

import io.qameta.allure.Step;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileInputStream;
import java.io.IOException;

public class CommonMethods
{
	WebDriver driver;
	public static int MaxElementWait = 5;

	/*
	 * Common method to select radio button
	 */
	public CommonMethods() {
		// TODO Auto-generated constructor stub
		this.driver=BaseClass.getDriver();
	}

	public void selectRadiobutton(List<WebElement> element,String value)
	{
		for (WebElement ref : element)
		{
			if(ref.getText().equalsIgnoreCase(value))
			{
				ref.click();
				break;
			}

		}

	}

	/*
	 * Common method to select CheckBoxes
	 */

	public void selectCheckBoxes(List<WebElement> element,String check)
	{
		String[] checksArray=check.split(",");
		for (String str : checksArray)
		{
			for (WebElement  ele : element) 
			{
				if(ele.getText().equalsIgnoreCase(str))
				{
					ele.click();
					break;
				}
			}

		}

	}

	//Customized sendkeys method-> To log sendkeys message for every occ.
	public static void sendKeys_custom(WebElement element, String fieldName, String valueToBeSent) {
		try {
		element.sendKeys(valueToBeSent);
		}catch (Exception e) {
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to enter text "+valueToBeSent+" in "+fieldName);
			Assert.fail("Not able to enter text "+valueToBeSent+" in "+fieldName);
		}
	}

	//custom click method to log evey click action in to extent report
	public static void click_custom(WebElement element, String fieldName) {
		try {
			element.click();
		}catch (Exception e) {
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to Click on "+fieldName);
			Assert.fail("Not able to Click on "+fieldName);
		}
	}

	


	//clear data from field
	public static void clear_custom(WebElement element,String fieldName) {
		element.clear();
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to clear "+fieldName);
		}
	}
	
	//custom mouseHover 
	public void moveToElement_custom(WebElement element,String fieldName){

		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
		Actions actions = new Actions(driver);		
		actions.moveToElement(element).build().perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	//check if element is Present
	public boolean isElementPresent_custom(WebElement element,String fieldName){
		boolean flag = false;

		flag = element.isDisplayed();
		return flag;
	}


	//Select dropdown value value by visibleText
	public static void selectDropDownByVisibleText_custom(WebElement element, String fieldName, String ddVisibleText) throws Throwable {
		try
		{
		Select s = new Select(element);
		s.selectByVisibleText(ddVisibleText);
		}catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to select text "+ddVisibleText+" from "+fieldName);
			Assert.fail("Not able to select text "+ddVisibleText+" from "+fieldName);
		}
	}
	
	/**Display hidden Element by setAttribute of style display:true;
	 * @param driver
	 * 				as Webdriver
	 * @param element
	 * 				as WebElement
	 */
	public static void displayHiddenElementByStyleDisplay(WebDriver driver, WebElement element)
	{
		String js = "arguments[0].setAttribute('style','display:true;')";

		((JavascriptExecutor) driver).executeScript(js, element);
	}

	//Select dropdown value value by value
	public void selectDropDownByValue_custom(WebElement element, String fieldName, String ddValue) throws Throwable {
		try
		{
			Select s = new Select(element);
			s.selectByValue(ddValue);
		}catch(Exception e)
		{
			e.printStackTrace();
			AllureListener.saveTextLog("Not able to select value "+ddValue+" from "+fieldName);
			Assert.fail("Not able to select value "+ddValue+" from "+fieldName);
		}
		
	}


	//Get text from webelement
	public String getText_custom(WebElement element, String fieldName) {
		String text = "";

		text = element.getText();

		return text;
	}


	/**
	 * To wait for the specific element on the page if element is visible or Enabled 
	 * 
	 * @param driver -
	 * @param element - webelement to wait for to appear
	 * @param maxWait - how long to wait for
	 * @return boolean - return true if element is present else return false
	 */
	public static boolean waitForElement(WebDriver driver,String fieldName, WebElement element, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
			}
		} catch (Exception ex) {
			statusOfElementToBeReturned = false;
			AllureListener.saveTextLog(fieldName+ " not found");
		}
		return statusOfElementToBeReturned;
	}


	/**
	 * To wait for the specific element on the page as per configured Max wait
	 * 
	 * @param driver -
	 * @param element - webelement to wait for to appear
	 * @param maxWait - how long to wait for
	 * @return boolean - return true if element is present else return false
	 */
	public static void waitForElement(WebDriver driver,String fieldName,WebElement element) {
		waitForElement(driver, fieldName, element, MaxElementWait);
	}
	////////////////////////////////////////////////////////////////

	//  CHECK - MANDATORY
	public static boolean chkMandatory(String dValue) throws Exception {
		if(dValue.isEmpty()) {
			return false;
			//Log.message("Clicked on Go Back arrow");
		}	
		else {
			return true;	
		}
	}




	/**waitForPageLoad waits for the page load with custom page load wait time un
	 * @param driver
	 * @param element
	 * 				as element which is displayed on page
	 * 			
	 */
	public static void waitForPageLoad(final WebDriver driver,WebElement element) {
		FluentWait<WebDriver> wait = new WebDriverWait(driver, MaxElementWait)
				.pollingEvery(Duration.ofMillis(1000))
				.ignoring(StaleElementReferenceException.class)
				.withMessage("Page Load Timed Out");
		try {

			wait.until(ExpectedConditions.visibilityOf(element));

			String title = driver.getTitle().toLowerCase();
			String url = driver.getCurrentUrl().toLowerCase();

			if ("the page cannot be found".equalsIgnoreCase(title)
					|| title.contains("is not available")
					|| url.contains("/error/")
					|| url.toLowerCase().contains("/errorpage/")
					||driver.getPageSource().contains("No webpage was found for the web address")) {
				Assert.fail("Page is Not loaded. [Title: " + title + ", URL:" + url
						+ "]");
			}
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOf(element));
		}

	} 

	//  CHECK - DATE FORMAT
	public static boolean isValidFormat(String format, String value, Locale locale) {

		System.out.println("In isValidFormat Method. Format is -   "+ format);	
		System.out.println("In isValidFormat Method. Value is -   "+ value);	

		LocalDateTime ldt = null;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format, locale);
		System.out.println("11111111111");
		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			System.out.println("22222222222");
			return result.equals(value);
		} catch (DateTimeParseException e) {
			try {
				LocalDate ld = LocalDate.parse(value, fomatter);
				System.out.println("3333333333333");
				String result = ld.format(fomatter);
				return result.equals(value);
			} catch (DateTimeParseException exp) {
				try {
					LocalTime lt = LocalTime.parse(value, fomatter);
					System.out.println("444444444444");
					String result = lt.format(fomatter);
					return result.equals(value);
				} catch (DateTimeParseException e2) {
					// Debugging purposes
					e2.printStackTrace();
				}
			}
		}

		return false;
	}

	/**Verify Valid Format
	 * @param format
	 * 		as String
	 * @param value
	 * 		as String
	 * @return date
	 * @throws java.text.ParseException
	 */
	public static boolean isValidFormat1(String format, String value) throws java.text.ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.parse(value);
		if (!value.equals(sdf.format(date))) {
			date = null;
		}
		return date != null;
	}


	/**verify Alert Text
	 * @param text
	 * 			as STring to be verify
	 * @return
	 * 		flag as true if text matches else return false
	 */
	public static boolean verifyAlertText(WebDriver driver,String expText)
	{
		boolean flag=false;

		WebDriverWait wait = new WebDriverWait(driver, 2000);
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert= driver.switchTo().alert();
		//Click on alert Pop up Ok button
		String textAlert=alert.getText().trim();
		AllureListener.saveTextLog("Actual   : "+textAlert);
		AllureListener.saveTextLog("Expected : "+expText);
		if(textAlert.equalsIgnoreCase(expText.trim()))
		{
			flag=true;

		}else
		{
			flag=false;

		}

		return flag;
	}


	/**Displayed Alert will be closed
	 * 
	 */
	public static void acceptAlert(WebDriver driver)
	{
		Alert alert= driver.switchTo().alert();
		alert.accept();

	}

	/*
	 * Get the Text from Selected Dropdown
	 * @ parameter drpValue webElement
	 */
	public static String selectValueWithGettext(WebElement drpValue)
	{
		Select select = new Select(drpValue);
		// get selected option with getFirstSelectedOption() with its text
		WebElement o = select.getFirstSelectedOption();
		String selectedoption = o.getText();
		return selectedoption;
	}


	/**
	 * Select all text from textbox using keys
	 * @param text
	 */
	public static void selectAllAndCopyTextUsingKey(WebElement txtbox)
	{
		txtbox.sendKeys(Keys.CONTROL,"A");
		txtbox.sendKeys(Keys.CONTROL,"C");
	}

	/**
	 * Select all text from textbox using keys
	 * @param text
	 */
	public static void selectAllAndDeleteTextUsingKey(WebElement txtbox)
	{
		txtbox.sendKeys(Keys.CONTROL,"A");
		txtbox.sendKeys(Keys.DELETE);
	}


	/**set COntent From ClipBoard In Variable
	 *
	 */
	public static String setContentFromClipBoardInVariable()
	{
		Transferable contents = null;
		try
		{
		Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = defaultToolkit.getSystemClipboard();
		contents = clipboard.getContents(null);
		}catch (IllegalStateException e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = defaultToolkit.getSystemClipboard();
				contents = clipboard.getContents(null);
			}
		}
		String content="";
		try {
			content = (String) contents.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}



	/**Load and read data from properties file
	 * @param suite
	 * @param key
	 * @return
	 */
	public static String getPropertyValueByKey(String suite,String key) {
		//1. load data from properties file
		Properties prop = new Properties();
		String propFilePath = System.getProperty("user.dir")+"/src/main/resources/usecasedata/"+suite+".properties";
		FileInputStream fis;
		try {
			fis = new FileInputStream(propFilePath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//2. read data
		String value = prop.get(key).toString();

		if(StringUtils.isEmpty(value)) {
			try {		
				throw new Exception("Value is not specified for key: "+key + " in properties file.");
			}catch(Exception e) {}
		}

		return value;
	}

	/*Move to Element
	 * Mouse hover on Field
	 */
	public void Mousehover(WebElement element,String fieldName) throws InterruptedException
	{
		try
		{
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
			Thread.sleep(1000);
		}catch(Exception e)
		{
			System.out.println(e);
			AllureListener.saveTextLog("Not able to move on field"+fieldName);
			Assert.fail("Not able to move on field "+fieldName);
		}
	}

	/*
	 * Get the Text from Selected Dropdown
	 * @ parameter drpValue webElement
	 */
	public static String getTextFromSelectedDropDown(WebElement drpValue)
	{
		String selectedoption="";
		try
		{
			Select select = new Select(drpValue);
			// get selected option with getFirstSelectedOption() with its text
			WebElement o = select.getFirstSelectedOption();
			selectedoption = o.getAttribute("value");
		}catch(Exception e)
		{
			System.out.println(e);
			AllureListener.saveTextLog("Not able to get Text from dropdown");
			Assert.fail("Not able to get Text from dropdown");
		}
		return selectedoption;
	}
	
	/**Get Next Date
	 * @param dateFormat
	 * @return nextDate
	 * 			as String
	 */
	public static String getTodaysDate(String dateFormat)
	{
		Calendar calendar = Calendar.getInstance();
		Date date=calendar.getTime();
		SimpleDateFormat formatter =new SimpleDateFormat(dateFormat);
		String todaysDate = formatter.format(date);
		System.out.println("Todays Date : " + todaysDate);
		return todaysDate;
		
	}

	/**Get Next Date
	 * @param dateFormat
	 * @return nextDate
	 * 			as String
	 */
	public static String getNextDate(String dateFormat)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		Date date=calendar.getTime();
		SimpleDateFormat formatter =new SimpleDateFormat(dateFormat);
		String nextDate = formatter.format(date);
		return nextDate;
		
	}
	
	/**Get Previous Date
	 * @param dateFormat
	 * @return tradingDate
	 * 			as String
	 */
	public static String getPreviousDate(String dateFormat)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date date=calendar.getTime();
		SimpleDateFormat formatter =new SimpleDateFormat(dateFormat);
		String tradingDate = formatter.format(date);
		return tradingDate;
		
	}
	
	/**Get Date After Add Number Of Year
	 * @param dateFormat as DD/MMM/YYYY
	 * @return Date After Add number of year
	 * 			as String
	 */
	public static String getDateAfterAddNoOfYear(String dateFormat,int noOfYear)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, noOfYear);
		Date date=calendar.getTime();
		SimpleDateFormat formatter =new SimpleDateFormat(dateFormat);
		String dateWithYear = formatter.format(date);
		System.out.println("dateWithYear==>"+dateWithYear);
		return dateWithYear;
		
	}
}

