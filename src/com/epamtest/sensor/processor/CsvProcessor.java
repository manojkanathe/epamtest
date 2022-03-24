package com.epamtest.sensor.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epamtest.sensor.model.Measurement;
import com.epamtest.sensor.model.Report;
import com.epamtest.sensor.model.Sensor;

/**
 * 
 * @author mkanathe
 *
 */
public class CsvProcessor {
	static int fileCount = 0;
	static int processedMeasurements = 0;
	static int failedMeasurements = 0;

	public static Report populateReports(List<Measurement> measurements) {
		Report report = new Report();
		report.setNoOfFiles(fileCount);
		report.setProcessedMeasurements(processedMeasurements);
		report.setFailedMeasurements(failedMeasurements);
		
		List<Measurement> failedMeasurementsList = measurements.stream().filter(m -> m.getHumidity() == null)
				.collect(Collectors.toList());

		List<Measurement> passedMeasurementsList = measurements.stream().filter(m -> m.getHumidity() != null)
				.collect(Collectors.toList());

		failedMeasurementsList.removeAll(passedMeasurementsList); // remove a sensor related data if it is available in
																	// passed list
		//Calculate avg. min and max for passed Measurements
		Map<String, Sensor> sensorStatisticsMap = calculateStatisticsPerSensor(passedMeasurementsList);

		//Create final sensors data with failed measurments for output
		List<Sensor> sensors = createSensorsWithStatistics(sensorStatisticsMap, failedMeasurementsList);

		report.setSensors(sensors);
		return report;
	}

	/**
	 * 
	 * @param sensorStatisticsMap
	 * @param failedMeasurementsList
	 * @return
	 */
	private static List<Sensor> createSensorsWithStatistics(Map<String, Sensor> sensorStatisticsMap,
			List<Measurement> failedMeasurementsList) {
		List<Sensor> outputSensors = new ArrayList<>();
		
		sensorStatisticsMap.entrySet().stream().forEach(e -> outputSensors.add(new Sensor(e.getKey(),
				((Sensor) e.getValue()).getMin(), ((Sensor) e.getValue()).getMax(), ((Sensor) e.getValue()).getAvg())));
		
		// sorts sensors by highest avg humidity
		List<Sensor> sortedList = outputSensors.stream().sorted((s1, s2) -> (int) (s2.getAvg() - s1.getAvg()))
				.collect(Collectors.toList());
		
		//NaN values go last
		failedMeasurementsList.stream().forEach(e -> sortedList.add(new Sensor(e.getSensorId(), null, null, null)));

		return sortedList;
	}

	/**
	 * Calculate avg, min max humidity for each sensor
	 * @param passedMeasurementsList
	 * @return Map<String, Sensor> 
	 */
	private static Map<String, Sensor> calculateStatisticsPerSensor(List<Measurement> passedMeasurementsList) {
		
		Map<String, Sensor> calculatedSensorsMap = passedMeasurementsList.stream().collect(Collectors
				.groupingBy(Measurement::getSensorId, Collectors.collectingAndThen(Collectors.toList(), list -> {
					int max = list.stream().map(Measurement::getHumidity).filter(Objects::nonNull)
							.collect(Collectors.summarizingInt(Integer::intValue)).getMax();
					Double avg = list.stream().map(Measurement::getHumidity).filter(Objects::nonNull)
							.collect(Collectors.summarizingInt(Integer::intValue)).getAverage();
					int min = list.stream().map(Measurement::getHumidity).filter(Objects::nonNull)
							.collect(Collectors.summarizingInt(Integer::intValue)).getMin();
					return new Sensor(min, max, avg);
				})));
		return calculatedSensorsMap;
	}

	/**
	 * Process and parse files and generate Measurements
	 * @param dirPath
	 * @return List<Measurement>
	 */
	public static List<Measurement> processFiles(String dirPath) {
		List<Measurement> measurements = new ArrayList<>();

		File filesDir = new File(dirPath);
		for (final File csvFile : filesDir.listFiles()) {
			if (csvFile.isDirectory() || !csvFile.getName().endsWith(".csv")) {
				// listFilesForFolder(fileEntry);
			} else {
				try {
					List<String> lines = (new BufferedReader(new FileReader(csvFile)).lines().skip(1)
							.collect(Collectors.toList()));
					for (String line : lines) {
						String[] split = line.split(",");
						String id = split[0];
						Integer huminity = null;
						try {
							huminity = Integer.parseInt(split[1]);
						} catch (NumberFormatException e) {
							huminity = null;
							failedMeasurements++;
						}
						processedMeasurements++;
						measurements.add(new Measurement(id, huminity));
					}
					fileCount++;
				} catch (IOException ioe) {

				}
			}
		}
		return measurements;
	}

	/**
	 * Print final statistics report for each sensor.
	 * @param report
	 */
	public static void printReport(Report report) {

		System.out.println("Num of processed files: " + report.getNoOfFiles());
		System.out.println("Num of processed measurements: " + report.getProcessedMeasurements());
		System.out.println("Num of failed measurements: " + report.getFailedMeasurements());
		System.out.println();
		System.out.println("Sensors with highest avg humidity:");
		System.out.println("sensor-id, min, avg, max");
		for (Sensor sensor : report.getSensors()) {
			if (sensor.getMax() == null) {
				System.out.println(sensor.getId() + ",\"NaN\", \"NaN\", \"NaN\"");
			} else {
				System.out.println(
						sensor.getId() + ", " + sensor.getMin() + ", " + sensor.getAvg() + ", " + sensor.getMax());
			}
		}
	}
}
