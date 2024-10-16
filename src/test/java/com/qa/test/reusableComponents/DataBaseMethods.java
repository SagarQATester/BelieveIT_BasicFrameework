package com.qa.test.reusableComponents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBaseMethods {

	String defaultNCMSDBHost=PropertiesOperations.getPropertyValueByKey("defaultNCMSDBHost");
	String defaultNCMSDBPort=PropertiesOperations.getPropertyValueByKey("defaultNCMSDBPort");
	String defaultNCMSDBServiceName=PropertiesOperations.getPropertyValueByKey("defaultNCMSDBServiceName");
	String defaultNCMSDBUsername=PropertiesOperations.getPropertyValueByKey("defaultNCMSDBUsername");
	String defaultNCMSDBPassword=PropertiesOperations.getPropertyValueByKey("defaultNCMSDBPassword");
	String DBResult = "";
	ResultSet rs;
	Connection conn;
	
	
	/**
	 * Use this function to Verify value with the
	 * Database This function connects to the database and execute the specified
	 * query and returns Single Value
	 * 
	 * @param DBQuery - Sql query
	 * @param defaultDBHost - Host name of the databse
	 * @param defaultDBPort - Port of the Database to be connected
	 * @param defaultDBServiceName - ServiceName of the Database to be connected
	 * @param defaultDBUsername - Username of the Database to be connected
	 * @param defaultDBPassword - Password of the Database to be connected
	 * @return
	 * 		List of Values  after executing the query
	 * @throws SQLException
	 */
	public List<String> getValueFromDB(String DBQuery, String columName)
			throws SQLException {
		List<String> dataList = new ArrayList<String>();
		// TODO Auto-generated method stub
		String connectionString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + defaultNCMSDBHost + ")(PORT=" + defaultNCMSDBPort + "))(ADDRESS=(PROTOCOL=TCP)(HOST=)(PORT=" + defaultNCMSDBPort + ")))(FAILOVER=on)(LOAD_BALANCE=off)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=" + defaultNCMSDBServiceName + ")))";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(connectionString, defaultNCMSDBUsername, defaultNCMSDBPassword);
			String sql = DBQuery;
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				dataList.add(rs.getString(columName));
			}
		} catch (SQLException sqlExp) {
			System.out.println("##### SQLException In getConnection  #### " + sqlExp.getMessage() + " #### Caused By ####" + sqlExp.getCause());
		} catch (Exception exp) {
			System.out.println("##### Exception In getConnection  #### " + exp.getMessage() + " #### Caused By ####" + exp.getCause());
			
		} finally {
			try {
				if (rs != null && conn != null) {
					rs.close();
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}
	
	/**
	 * Use this function to Verify value with the Database
	 * This function connects to the database and execute the specified query
	 * and returns Single Value
	 * 
	 * @param DBQuery - Sql query
	 * @param defaultDBHost - Host name of the databse
	 * @param defaultDBPort - Port of the Database to be connected
	 * @param defaultDBServiceName - ServiceName of the Database to be connected
	 * @param defaultDBUsername - Username of the Database to be connected
	 * @param defaultDBPassword - Password of the Database to be connected
	 * * @return
	 * 		value after executing the query
	 * @throws SQLException
	 * */
	public String getSingleValueFromDB(String DBQuery,String columnName) throws SQLException {
		// TODO Auto-generated method stub
		String connectionString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + defaultNCMSDBHost + ")(PORT=" + defaultNCMSDBPort + "))(ADDRESS=(PROTOCOL=TCP)(HOST=)(PORT=" + defaultNCMSDBPort + ")))(FAILOVER=on)(LOAD_BALANCE=off)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=" + defaultNCMSDBServiceName + ")))";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(connectionString, defaultNCMSDBUsername, defaultNCMSDBPassword);

			DBResult = null;
			String sql = DBQuery;
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				DBResult = rs.getString(columnName);
			}
			System.out.println(DBResult);
		} catch (SQLException sqlExp) {
			System.out.println("##### SQLException In getConnection  #### "
					+ sqlExp.getMessage() + " #### Caused By ####"
					+ sqlExp.getCause());
		} catch (Exception exp) {
			System.out.println("##### Exception In getConnection  #### "
					+ exp.getMessage() + " #### Caused By ####"
					+ exp.getCause());
		} finally {
			try {
				if (rs != null && conn != null) {
					rs.close();
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DBResult;
	}

	/**
	 * Get the HasMap of Bank cities based on cityName
	 * 
	 * @param cityName - cityName of Bank
	 * @return
	 * 		HashMap after executing the query
	 * @throws SQLException
	 */
	public HashMap<String, String> getBankCitiesDetails(String cityName) throws SQLException {
		String connectionString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=" + defaultNCMSDBHost + ")(PORT=" + defaultNCMSDBPort + "))(ADDRESS=(PROTOCOL=TCP)(HOST=)(PORT=" + defaultNCMSDBPort + ")))(FAILOVER=on)(LOAD_BALANCE=off)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=" + defaultNCMSDBServiceName + ")))";
		HashMap<String, String> bank_details = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(connectionString, defaultNCMSDBUsername, defaultNCMSDBPassword);
			String query = "SELECT BCT_CD,"
					+ "BCT_NAME,"
					+ "BCT_CREAT_BY,"
					+ "BCT_CREAT_DT,"
					+ "BCT_LST_UPD_BY,"
					+ "BCT_LST_UPD_DT from BNKCITIES"
					+ " where BCT_NAME='"
					+ cityName+"'";
			ResultSet rs = conn.createStatement().executeQuery(query);
			while (rs.next()) {
				bank_details = new HashMap<String, String>();
				bank_details.put("ID", rs.getString("BCT_CD"));
				bank_details.put("BANKNAME", rs.getString("BCT_NAME"));
				bank_details.put("CREATEDBY", rs.getString("BCT_CREAT_BY"));
				bank_details.put("CREATEDDATE", rs.getString("BCT_CREAT_DT"));
				bank_details.put("LASTUPDATEDBY", rs.getString("BCT_LST_UPD_BY"));
				bank_details.put("LASTUPDATEDDATE", rs.getString("BCT_LST_UPD_DT"));
			}
			
		} catch (SQLException ex) {
			System.out.println("Exception: " + ex.getMessage());
			System.out.println("caused by: " + ex.getCause());
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
		} finally {
			if (rs != null && conn != null) {
				rs.close();
				conn.close();
			}
		}
		return bank_details;
	}
	
	
	public static void main(String args[]) throws SQLException
	{
		
			DataBaseMethods dbConn=new DataBaseMethods();
			
			String abc =dbConn.getSingleValueFromDB("SELECT APPROVED_BANKS.APP_CD FROM APPROVED_BANKS WHERE APPROVED_BANKS.APP_CD=(SELECT max(APPROVED_BANKS.APP_CD) FROM APPROVED_BANKS where APPROVED_BANKS.APP_STATUS='A')","APP_CD");
			System.out.println(abc);
			
			HashMap<String, String> bank_details=dbConn.getBankCitiesDetails("MUMBAI");
			System.out.println(bank_details);
	}
	
	

}
