package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.page.HomePage;
import com.qa.hubspot.page.LoginPage;
import com.qa.hubspot.util.AppConstants;
import com.qa.hubspot.util.Credentials;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Epic - 102 : create home page features")
@Feature("User Story - 502 : Create test for home page on HubSpot")

public class HomePageTest {

	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	HomePage homePage;
	Credentials userCred;
	
	@BeforeTest(alwaysRun=true)
	public void setUp() throws InterruptedException {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browserName = prop.getProperty("browser");
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
		homePage = loginPage.doLogin(userCred);
		
	}
	
	@Test(priority=1, groups = "sanity")
	@Description("verify HomePage Title Test....")
	@Severity(SeverityLevel.NORMAL)
	public void verfiyHomePageTitleTest() {
		String title = homePage.getHomePageTitle();
		System.out.println("home page title is : " + title);
		Assert.assertEquals(title, AppConstants.HOME_PAGE_TITLE);
	}
	
	@Test(priority=2)
	@Description("verify HomePage Header Test....")
	@Severity(SeverityLevel.NORMAL)
	public void verfiyHomePageHeaderTest() {
		String header = homePage.getHomePageHeader();
		System.out.println("home page header is : " + header);
		Assert.assertEquals(header, AppConstants.HOME_PAGE_HEADER);
	}
	
	@Test(priority=3)
	@Description("verify Logged In User Test....")
	@Severity(SeverityLevel.CRITICAL)
	public void verifyLoggedInUserAccountName() {
		String accountName = homePage.getLogggedInUserAccountName();
		System.out.println("logged in account name : " + accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	@AfterTest(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}
}
