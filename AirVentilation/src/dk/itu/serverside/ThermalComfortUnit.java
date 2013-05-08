package dk.itu.serverside;

public class ThermalComfortUnit {
	private double value;
	private String timestamp;
	
	public double getValue()
	{
		return value;
	}
	
	public String getTimeStamp()
	{
		return timestamp;
	}
	
	public void setValue(double value)
	{
		this.value = value;
	}
	
	public void setTimeStamp(String timeStamp)
	{
		this.timestamp = timeStamp;
	}
}
