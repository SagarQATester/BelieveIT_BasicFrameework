package com.qa.test.reusableComponents;

import java.io.File;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {
	
	static String filePath;
	static Sheet sh;
	
	
	//get test data from test data sheet in hashmap based on row number
	@SuppressWarnings("deprecation")
	public static HashMap<String, String> getTestDataInMap(String sheetname,int rowNum) throws Exception 
	{
		filePath = System.getProperty("user.dir")+PropertiesOperations.getPropertyValueByKey("testDataLocation");

		//open file - workbook
		 File testDataFile = new File(filePath);
		 Workbook wb = null;
		 wb = WorkbookFactory.create(testDataFile);
		
		 sh = wb.getSheet(sheetname);

				//read data row by row and put in map
		HashMap<String, String> hm = new HashMap<String, String>();
		
		for (int i = 0; i < sh.getRow(0).getLastCellNum(); i++)
		{
			if(sh.getRow(rowNum).getCell(i) != null) 
			{
				sh.getRow(rowNum).getCell(i).toString();
				//setCellType(CellType.STRING);	
				hm.put(sh.getRow(0).getCell(i).toString(), sh.getRow(rowNum).getCell(i).toString());
			}
		}	
		return hm;
	}
	
//	//get row count
//	public int getRowCount() {		
//		return sh.getLastRowNum();
//	}
//	
//	//ger column count
//	public int getColCount() {
//		return sh.getRow(0).getLastCellNum();
//		
//	}
}