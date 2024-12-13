package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.server.LoaderHandler;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BaseClass {

	public static WebDriver driver;
	public Logger logger; // log4j
	public Properties prop;

	@BeforeClass(groups = { "Sanity", "Regression", "Master" })
	@Parameters({ "os", "browser" })
	public void setup(String os, String browser) throws IOException {
		// Loading config.properties
		FileReader file = new FileReader("./src//test//resources//config.properties");
		prop = new Properties();
		prop.load(file);

		logger = LogManager.getLogger(this.getClass()); // Log4j2

		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) 
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
//			capbailities.setPlatform(Platform.WIN11);
//			capbailities.setBrowserName(browser);

			// OS
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching OS");
			}

			// Browser
			switch (browser.toLowerCase()) {
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				System.out.println("No matching browser");
				return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		}

		if (prop.getProperty("execution_env").equalsIgnoreCase("local")) 
		{
			switch (browser.toLowerCase()) {
			case "chrome":
				driver = new ChromeDriver();
				break;
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid browser name..");
				return;
			}

			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			driver.get(prop.getProperty("appURL2")); // reading URL from properties file
			driver.manage().window().maximize();
		}
	}

	@AfterClass(groups = { "Sanity", "Regression", "Master" })
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;

	}

	public String randomNumber() {
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}

	public String randomAlphaNumeric() {
		String generatedPwd = RandomStringUtils.randomAlphanumeric(8);
		return (generatedPwd + "$");
	}

	public String captureScreen(String tname) {

		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File sourceFile = takeScreenshot.getScreenshotAs(OutputType.FILE);

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timestamp;
		File targetFile = new File(targetFilePath);

		sourceFile.renameTo(targetFile);

		return targetFilePath;
	}
}
