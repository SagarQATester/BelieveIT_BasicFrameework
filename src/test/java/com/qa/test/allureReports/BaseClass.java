package com.qa.test.allureReports;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

public class BaseClass {

	WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	static DesiredCapabilities capabilities = new DesiredCapabilities();

	@AfterMethod
	@Step("Closing Browser")
	public void tearDown() {
		BaseClass.getDriver().close();
	}

	
	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}
	
	@Step("Chrome Browser Opened ")
	public void setUpChrome()
	{
		String driverPath=System.getProperty("user.dir")+"\\src\\main\\resources\\misc";
		WebDriverManager.chromedriver().setup();
		System.setProperty("webdriver.chrome.driver",driverPath+"\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("start-maximized");
		options.merge(capabilities);
	}
	
	@Step("FireFox Browser Opened ")
	public void setUpFireFox()
	{
		String driverPath=System.getProperty("user.dir")+"\\src\\main\\resources\\misc";
		System.setProperty("webdriver.chrome.driver",driverPath+"\\geckodriver.exe");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--test-type");
		options.addArguments("start-maximized");
		options.merge(capabilities);
	}
	
	@Step("IE Browser Opened ")
	public void setUpIE()
	{
		String driverPath=System.getProperty("user.dir")+"\\src\\main\\resources\\misc";
		System.setProperty("webdriver.ie.driver",driverPath+"\\IEDriverServer.exe");
		capabilities.setBrowserName("internet explorer");
		capabilities.setVersion("11");
	}
	
	public ThreadLocal<WebDriver> initialize_driver() throws Exception
	{

		String browser=PropertiesOperations.getPropertyValueByKey("browser");
		String url=PropertiesOperations.getPropertyValueByKey("url");
		capabilities.setBrowserName(browser);
		capabilities.setPlatform(Platform.ANY);

		if(browser.equalsIgnoreCase("chrome"))
		{
			setUpChrome();

		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			setUpFireFox();
		}

		else if(browser.equalsIgnoreCase("ie"))
		{
			
			setUpIE();
			
		}
		tdriver=openurl(url);
		return tdriver;
		
	}
	
	@Step("Navigate to url :[0]")
	public ThreadLocal<WebDriver> openurl(String url) throws MalformedURLException
	{
		String host=PropertiesOperations.getPropertyValueByKey("hosturl");
		driver = new RemoteWebDriver(new URL(host), capabilities);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(url);
		tdriver.set(driver);
		tdriver.get();
		return tdriver;
	}
}
