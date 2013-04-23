package dk.itu.restconnection;

public class Measurement {
	private String uuid;
	private double value;
	private String timestamp;
	private double relativeHumidity;
	
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
	
	public void setRelativeHumidity(double rh)
	{
		this.relativeHumidity = rh;
	}
	
	public double RelativeHumidity() {
		return relativeHumidity;
	}
}
