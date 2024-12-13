package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//Data Provider 1
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path = ".\\testData\\LoginData_OpenCart.xlsx"; // taking xl file from testData - System.getProperty("user.dir")+"\\testData\\OpenCart_LoginData.xlsx";
		
		ExcelUtility xlUtil = new ExcelUtility(path);
		
		/*int totalrows = xlUtil.getRowCount("Sheet1");
		int totalcols = xlUtil.getCellCount("Sheet1", 1);*/
		
		int totalrows = 5;
		int totalcols = 3;
		
		String loginData[][] = new String[totalrows][totalcols]; // created for 2 dimensional array which can store key value pair data
		
		for(int i =1; i <=totalrows; i++) // 1 // read the data from xl storing in 2 dimensional array
		{
			for(int j=0; j < totalcols; j++) // 0 i is rows, j is cols
			{
				loginData[i-1][j] = xlUtil.getCellData("Sheet1", i, j);
			}
		}
		return loginData; // returning 2 dimensional array
	
	}
	
	//Data Provider 2
	
	//Data Provider 3
	
	//Data Provider 4
}
