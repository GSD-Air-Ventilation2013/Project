package dk.itu.restconnection;

public class Measurement {
	private String uuid;
	private double value;
	private String timestamp;
	private double thermalComfort;
	private double predictedPercentageDissatisfied;
	
	public double getPPD()
	{
		return this.predictedPercentageDissatisfied;
	}
	
	public void setPDD(double pdd)
	{
		this.predictedPercentageDissatisfied = pdd;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setThermalComfort(double hc)
	{
		this.thermalComfort = hc;
	}
	
	public double getThermalComfort() {
		return thermalComfort;
	}
}
