package com.qa.hubspot.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

@Epic("Epic - 101 : create login page features")
@Feature("User Story - 501 : Create test for login page on HubSpot")
public class LoginPageTest {
	WebDriver driver;
	BasePage basePage;
	Properties prop;
	LoginPage loginPage;
	Credentials userCred;
	
	@BeforeMethod(alwaysRun=true)
	public void setUp() {
		basePage = new BasePage();
		prop = basePage.init_properties();
		String browserName = prop.getProperty("browser");
		driver = basePage.init_driver(browserName);
		driver.get(prop.getProperty("url"));
		
		loginPage = new LoginPage(driver);
		userCred = new Credentials(prop.getProperty("username"), prop.getProperty("password"));
	}
		
	@Test(priority=1, description = "verify login page title")
	@Description("verify LoginPage Title Test....")
	@Severity(SeverityLevel.NORMAL)
	public void verfiyLoginPageTitleTest() throws InterruptedException {
		String title = loginPage.getPageTitle();
		System.out.println("login page title is : " + title);
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Test(priority=2, groups = "sanity")
	@Description("verify Sign Up Link Test....")
	@Severity(SeverityLevel.CRITICAL)
	public void verfiySignUpLinkTest() {
		Assert.assertTrue(loginPage.checkSignUpLink());
	}
	
	@Test(priority=3)
	@Description("verify Login Test....")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		HomePage homePage = loginPage.doLogin(userCred);
		String accountName = homePage.getLogggedInUserAccountName();
		System.out.println("account name is : " + accountName);
		Assert.assertEquals(accountName, prop.getProperty("accountname"));
	}
	
	@DataProvider
	public Object[][] getLoginInvalidData() {
		Object data[][] = { {"test111@gmail.com", "test123"}, 
							{"test222@gmail.com", "test1234"}, 
							{"   ", "test12345"},
							{"test", "test"},
							{"   ", "   "}
							};
		return data;
	}
	
	@Test(priority=4, dataProvider = "getLoginInvalidData", enabled=false)
	public void login_InvalidTestCases(String username, String pwd) {
		userCred.setAppUsername(username);
		userCred.setAppPassword(pwd);
		loginPage.doLogin(userCred);
		Assert.assertTrue(loginPage.checkLoginErrorMessage());
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.quit();
	}
		
	}

