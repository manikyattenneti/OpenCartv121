package pageObjects;

import java.time.Duration;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstName;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephone;

	@FindBy(xpath = "//input[@id='input-passowrd']")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkPolicy;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account  Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String firstname) {
		txtFirstName.sendKeys(firstname);
	}

	public void setLastName(String lastname) {
		txtLastName.sendKeys(lastname);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setTelephone(String telephone) {
		txtTelephone.sendKeys(telephone);
	}

	public void setPassword(String password) {
		txtPassword.sendKeys(password);
	}

	public void setConfirmPassword(String confpwd) {
		txtConfirmPassword.sendKeys(confpwd);
	}

	public void setPrivacyPolicy() {
		chkPolicy.click();
	}

	public void conitnue() {
		btnContinue.click();

		/*
		 * //Sol 2 btnContinue.submit();
		 */

		/*
		 * //Sol 3 Actions act = new Actions(driver);
		 * act.moveToElement(btnContinue).click().perform();
		 */

		/*
		 * //Sol 4 JavascriptExecutor js = (JavascriptExecutor)driver;
		 * js.executeScript("arguments[0].click();", btnContinue);
		 */

		/*
		 * //Sol 5 btnContinue.sendKeys(Keys.RETURN);
		 */

		/*
		 * //Sol 6 WebDriverWait wait = new WebDriverWait(driver,
		 * Duration.ofSeconds(10));
		 * wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
		 */

	}

	public String  getConfirmationMessage() {
		try{
			return (msgConfirmation.getText());
		} catch(Exception e) {
			return (e.getMessage());
		}
	}

}
