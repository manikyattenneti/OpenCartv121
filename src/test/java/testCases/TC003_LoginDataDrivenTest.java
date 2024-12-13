package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 * Data is valid - login success - test pass  - logout
 * Data is valid - login failed - test failed
 * 
 * Data is invalid - login success - test fail - logout
 * Data is invalid - login failed - test pass
 */

public class TC003_LoginDataDrivenTest extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "datadriven") // getting data provider from different class
	public void verify_LoginDDT(String email, String pwd, String expRes) 
	{
		logger.info("***** Starting TC003_LoginDataDrivenTest *****");
		
		try 
		{
			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();

			// Login
			LoginPage lp = new LoginPage(driver);
			lp.setEmailAddress(email);
			lp.setPassword(pwd);
			lp.clickLogin();

			// MyAccount
			MyAccountPage macp = new MyAccountPage(driver);
			boolean targetPage = macp.isMyAccountPageExists();

			if (expRes.equalsIgnoreCase("Valid")) 
			{
				if (targetPage == true) 
				{
					macp.clickLogout();
					Assert.assertTrue(true);
				} 
				else 
				{
					Assert.assertTrue(false);
				}
			}

			if (expRes.equalsIgnoreCase("Invalid")) 
			{
				if (targetPage == true) 
				{
					macp.clickLogout();
					Assert.assertTrue(false);
				} 
				else 
				{
					Assert.assertTrue(true);
				}
			}
		} 
		catch (Exception e) 
		{
			Assert.fail();
		}
		logger.info("***** Ending TC003_LoginDataDrivenTest *****");
	}
}
