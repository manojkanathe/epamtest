package com.epamtest.sensor.model;

public class Measurement {

	@Override
	public String toString() {
		return "Measurement [sensorId=" + sensorId + ", humidity=" + humidity + "]";
	}

	private String sensorId;
	private Integer humidity;

	public Measurement(String sensorId, Integer humidity) {
		this.sensorId = sensorId;
		this.humidity = humidity;
	}
	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	@Override
    public boolean equals(Object obj) {

        try {
            Measurement measurement  = (Measurement) obj;
            return sensorId.equals(measurement.getSensorId());
        }
        catch (Exception e)
        {
            return false;
        }

    }
}
