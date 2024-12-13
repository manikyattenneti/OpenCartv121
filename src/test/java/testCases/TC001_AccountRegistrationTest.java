package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups = {"Regression", "Master"})
	public void verify_account_registration() {
		logger.info("***Starting TC001_AccountRegistrationTest ***");
		try {
			HomePage hp = new HomePage(driver);

			hp.clickMyAccount();
			logger.info("Clicked on MyAccount link");

			hp.clickRegister();
			logger.info("Clicked on Register link");

			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);

			logger.info("Providing customer details");
			regpage.setFirstName(randomString().toUpperCase());
			regpage.setLastName(randomString().toUpperCase());
			regpage.setEmail(randomString() + "@gmail.com"); // randomly generate the email
			regpage.setTelephone(randomNumber());

			String password = randomAlphaNumeric();

			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.setPrivacyPolicy();
			regpage.conitnue();

			logger.info("Validating expected message");
			String confmsg = regpage.getConfirmationMessage();
			Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		} 
		catch (Exception e) {
			logger.error("Test Failed!!");
			logger.debug("Debug logs..");
			Assert.fail();
		}
		
		logger.info("***Completed TC001_AccountRegistrationTest ***");

	}

}
