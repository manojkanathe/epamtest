package com.epamtest.sensor.model;

public class Sensor {
	private String id;
	private Integer min;
	private Integer max;
	private Double avg;

	public Sensor(Integer min, Integer max, Double avg) {
		this.min = min;
		this.max = max;
		this.avg = avg;
	}

	public Sensor(String id, Integer min, Integer max, Double avg) {
		this.id = id;
		this.min = min;
		this.max = max;
		this.avg = avg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	@Override
	public String toString() {
		return "Sensor [ id= " + id + ", min=" + min + ", max=" + max + ", avg=" + avg + "]";
	}

}
