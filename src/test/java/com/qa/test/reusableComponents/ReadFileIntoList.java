package com.qa.test.reusableComponents;

//Java program to illustrate reading data from file
//using nio.File
import java.util.*;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;

import com.qa.test.allureReports.AllureListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.*;

public class ReadFileIntoList {

	public static List<String> readFileInList()
	{
		String home = findUsingCommonsIO(System.getProperty("user.home")+"/Downloads").toString();
		AllureListener.saveTextLog("File Downloaded is "+home);
		List<String> lines = Collections.emptyList();
		try
		{
			lines = Files.readAllLines(Paths.get(home), StandardCharsets.UTF_8);
		}

		catch (IOException e)
		{

			// do something
			e.printStackTrace();
		}
		return lines;
	}
	
	public static void waitforFileToBeDownloaded(String fileType)
	{
		File[] dirFiles=ReadFileIntoList.listOfFiles(System.getProperty("user.home")+"/Downloads/");
		for(int i=0;i<dirFiles.length;i++)
		{
			if(dirFiles[i].toString().contains(".LOG"))
			{
				break;
			}else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void deleteFileTypeInDownload(String fileType)
	{
		try
		{
			File[] dirFiles=listOfFiles(System.getProperty("user.home")+"/Downloads/");
			for(int i=0;i<dirFiles.length;i++)
			{

				if(dirFiles[i].toString().contains(fileType))
				{
					Files.deleteIfExists(Paths.get(dirFiles[i].toString()));
					System.out.println(dirFiles[i]+" File is deleted");
				}
			}

		}
		catch(NoSuchFileException e)
		{
			//  System.out.println("No such file/directory exists");
		}
		catch(DirectoryNotEmptyException e)
		{
			// System.out.println("Directory is not empty.");
		}
		catch(IOException e)
		{
			//  System.out.println("Invalid permissions.");
		}

	}
	public static File findUsingCommonsIO(String sdir) {
		File dir = new File(sdir);
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles((FileFilter)FileFilterUtils.fileFileFilter());
			if (dirFiles != null && dirFiles.length > 0) {
				Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
				return dirFiles[0];
			}
		}

		return null;
	}

	public static File[] listOfFiles(String sdir) {
		File dir = new File(sdir);
		if (dir.isDirectory()) {
			File[] dirFiles = dir.listFiles((FileFilter)FileFilterUtils.fileFileFilter());
			return dirFiles;

		}

		return null;
	}
	
//	  public static void main(String[] args)
	//	  {
	//
	//	     List l = readFileInList();
	//	    
	//	    Iterator<String> itr = l.iterator();
	//	    while (itr.hasNext())
	//	      System.out.println(itr.next());
	//	    
	//	    
	//	    
	//	  }
}