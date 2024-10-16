package com.qa.test.reusableComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelSheetData {
	
	
	public static Map<String,  Map<String, String>> setMapData(String sheetName,String tcID) throws IOException {

		  String path = "src/main/resources/usecasedata/TestData.xlsx";
		  int rowNo=getTestCaseID(path,sheetName,tcID);
		  List<String> columnList=getColumnData(path,sheetName);
		   
		  FileInputStream fis = new FileInputStream(path);
	
		  Workbook workbook = new XSSFWorkbook(fis);
		
		  Sheet sheet = workbook.getSheet(sheetName);
		  		  
		  Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String,String>>();
		  
		  Map<String, String> dataMap = new HashMap<String, String>();
		  
		  //Looping over entire row
			
		  for(int i=1; i<=columnList.size(); i++){
			  
			  Row row = sheet.getRow(rowNo);
			  Cell valueCell = row.getCell(i);
			 
			  String key = columnList.get(i-1);  
			  String value="";
			  try
			  {
			  value = valueCell.getStringCellValue().trim();
			  }catch(NullPointerException e)
			  {
				 // System.out.println("value is not set for Key "+key);
			  }catch(IllegalStateException e1)
			  {
				  value = String.valueOf(valueCell.getNumericCellValue());
			  }
			 
			  //Putting key & value in dataMap
			  dataMap.put(key, value);
				  
			  //Putting dataMap to excelFileMap
			  excelFileMap.put(tcID, dataMap);
		  }
		  System.out.println(excelFileMap);
		 //Returning excelFileMap
		return excelFileMap;

	}
	
	public static int getTestCaseID(String path,String sheetName,String tcID) throws IOException
	{
		  
		  FileInputStream fis = new FileInputStream(path);
	
		  Workbook workbook = new XSSFWorkbook(fis);
		
		  Sheet sheet = workbook.getSheet(sheetName);
		  
		  int lastRow = sheet.getLastRowNum();
		  
		  
		  int excelFileMapKey=0;
		  try
		  {
		  //Looping over entire row
		  for(int i=0; i<=lastRow; i++){
			  
			  Row row = sheet.getRow(i);
		
			  //0th Cell as Key
			  Cell keyCell = row.getCell(0);
				  
			  String key = keyCell.getStringCellValue().trim();
			 

			  if(key.equalsIgnoreCase(tcID))
			  {
				  excelFileMapKey=i;
			  }
		
		  }
		  System.out.println("TC ID is "+tcID);
		  System.out.println("Found at Row "+excelFileMapKey);
		  }catch(NullPointerException e)
		  {
			 
		  }
		  
		  
		  if(excelFileMapKey==0)
		  {
			 System.out.println("Test Case not found with ID"+tcID); 
		  }
		  return excelFileMapKey;
		
	}
	
	public static List<String> getColumnData(String path,String sheetName) throws IOException
	{
		 FileInputStream fis = new FileInputStream(path);
			
		  Workbook workbook = new XSSFWorkbook(fis);
		
		  Sheet sheet = workbook.getSheet(sheetName);
		  
	      int lastcell=sheet.getRow(0).getLastCellNum();
	      List<String> keyList=new ArrayList<String>();  
		  //Looping over entire row
		  for(int i=1;i<=lastcell-1;i++)
		  {
			  Row row = sheet.getRow(0);
			  Cell keyCell = row.getCell(i);
			  keyList.add(keyCell.getStringCellValue().trim());		  
		  }
		//  System.out.println(keyList);
		  return keyList;
	}
	
	public static void main(String[] args) throws IOException {
	
		ReadExcelSheetData r = new ReadExcelSheetData();
		
		Map<String, Map<String, String>> excelFileMap=r.setMapData("BankBranches","TS_004-TC_001");
		Map<String, String> dataMap=excelFileMap.get("TS_004-TC_001");
		System.out.println(dataMap);
		System.out.println(dataMap.get("branchName2").replace(".0", ""));
		Map<String, Map<String, String>> excelFileMap1=r.setMapData("BankBranches","TS_004-TC_002");
		Map<String, String> dataMap1=excelFileMap1.get("TS_004-TC_002");
			System.out.println(dataMap1);
			System.out.println(dataMap1.get("branchName").replace(".0", ""));
		
		//System.out.println("Value of the keyword (search) is- "+val);

	}
	

}
