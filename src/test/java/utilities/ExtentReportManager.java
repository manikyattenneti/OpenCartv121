package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter; // UI of the report
	public ExtentReports extent;  // populate common info on the report - os name, browser name etc
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext) //execute only once before the test execution starts 
	{
		/*SimpleDaeFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		  Date dt = new Date();
		  String currentDateTimeStamp = df.format(date);  
		 */
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		
		repName = "Test-Report-" + timeStamp + ".html";
	    //sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"//reports/myReport.html"); // specify location of the report
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // specify location of the report
		
	    sparkReporter.config().setDocumentTitle("OpenCart Automation Report"); // title of report
	    sparkReporter.config().setReportName("OpenCart Functional Testing"); // name of the report
	    sparkReporter.config().setTheme(Theme.DARK);
	    
	    extent = new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Application", "OpenCart");
	    extent.setSystemInfo("Module", "Admin");
	    extent.setSystemInfo("Sub Module", "Customers");
	    extent.setSystemInfo("User Name ", System.getProperty("user.name")); // current user of the system
	    extent.setSystemInfo("Environment", "QA");
	    
	    String os = testContext.getCurrentXmlTest().getParameter("os");
	    extent.setSystemInfo("Operating System", os);
	    
	    String browser = testContext.getCurrentXmlTest().getParameter("browser");
	    extent.setSystemInfo("Browser name", browser);
	    
	    List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	    if(!includedGroups.isEmpty()) {
	    	extent.setSystemInfo("Groups", includedGroups.toString());
	    }
	    
	}

	public void onTestSuccess(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); // create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); //to display groups in report
		test.log(Status.PASS, result.getName()+" got Successfully executed"); // update status p/f/s
	}
	
	public void onTestFailure(ITestResult result) 
	{
	    test = extent.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());
	    
	    test.log(Status.FAIL, result.getName()+" got Failed");
	    test.log(Status.FAIL, result.getThrowable().getMessage()); //log error message
	    
	    try {
	    	String imgPath = new BaseClass().captureScreen(result.getName());
	    	test.addScreenCaptureFromPath(imgPath);
	    } catch(Exception e1)
	    {
	    	e1.printStackTrace();
	    }
	}
	
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
	    test.log(Status.SKIP, result.getName()+"got Skipped");
	    test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext context) //execute only once after completion of all test methods execution
	{
		extent.flush(); //writes all the logs to reports
		
		String pathOfExtentReport = System.getProperty("user.dir")+ "\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * try { URL url = new
		 * URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
		 * 
		 *          //create email message
		 * ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceResolver(url));
		 * email.setHostName("smtp.googlemail.com");
		 * email.setPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password");
		 * email.setSSLOnConnect(true);
		 * email.setFrom("pavanoltraining@gmail.com"); //sender
		 * email.setSubject("Test Results");
		 * email.setMsg("Please find Attached Report...");
		 * email.addTo("pavankumar.busyqa@gmail.com"); //receiver
		 * email.attach(url, "extent report", "please check report...");
		 * email.send(); //send the email
		 * }
		 * catch(Exception e) { e.printStackTrace(); }
		 */
		
	}

}
