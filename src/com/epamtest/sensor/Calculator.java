package com.epamtest.sensor;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.epamtest.sensor.model.Measurement;
import com.epamtest.sensor.model.Report;
import com.epamtest.sensor.processor.CsvProcessor;

public class Calculator {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String dirPath = getDirectoryPath(args);
		
		if (dirPath == null || dirPath.trim().length() == 0) {
			System.out.println("Please provide valid path to dir.");
			System.exit(0);
		}
		
		List<Measurement> measurements = CsvProcessor.processFiles(dirPath );
		Report report = CsvProcessor.populateReports(measurements);
		CsvProcessor.printReport(report);
	}
	
	private static String getDirectoryPath(String[] args) {
		String dirPath = null;
		if (args.length == 0) {
			System.out.println("Please provide valid path to dir:");
			Scanner sc = new Scanner(System.in);
			dirPath = sc.nextLine();
			if (!isValidPath(dirPath)) {
				System.out.println("Invalid path. Reading from default dir resource." + dirPath);
				System.out.println();
				dirPath = "resource";
			}
			sc.close();
			
		} else {
			dirPath = args[0];
		}
		return dirPath;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isValidPath(String path) {
		try {
			Paths.get(path);
			File file = new File(path);
			if (file.isDirectory())
				return true;
			else
				return false;
		} catch (InvalidPathException | NullPointerException ex) {
			return false;
		}
	}

}
