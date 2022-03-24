package com.epamtest.sensor.model;

import java.util.List;

public class Report {
	/**
	 * It reports how many files it processed
• It reports how many measurements it processed
• It reports how many measurements failed
• For each sensor it calculates min/avg/max humidity
• NaN values are ignored from min/avg/max
• Sensors with only NaN measurements have min/avg/max as NaN/NaN/NaN
• Program sorts sensors by highest avg humidity (NaN values go last)

	 */
	int noOfFiles;
	int processedMeasurements;
	int failedMeasurements;
	List<Sensor> sensors;
	
	
	
	public int getNoOfFiles() {
		return noOfFiles;
	}
	public void setNoOfFiles(int noOfFiles) {
		this.noOfFiles = noOfFiles;
	}
	public int getProcessedMeasurements() {
		return processedMeasurements;
	}
	public void setProcessedMeasurements(int processedMeasurements) {
		this.processedMeasurements = processedMeasurements;
	}
	public int getFailedMeasurements() {
		return failedMeasurements;
	}
	public void setFailedMeasurements(int failedMeasurements) {
		this.failedMeasurements = failedMeasurements;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	
	
}
