package dk.itu.serverside;

// creates relative humidity based on our own metric
public class HumidityGenerator 
{
	// variables we need:
	// dewpoint
	// temperature
	// rain
	// relative humidity: less than 40% feels dry, more than 80% feels moist.
	// high dewpoint + hign RH = bad human comfort.
	
	
	// air from the outside, keeps its dewpoint even if cooled by aircon.
	// the closer the temp is to the dewpoint the more relative humidity
	// -----
	// when it's cold outsite, heating of the air through aircon can bring down the RH
	
	//CONCLUSION: Calculate the dewpoint of the outside enviroment and convert it using the inside's variables.
	
	// more clouds = dewpoint and temperature are closer
	
	//calculate saturated vapor pressure (T is temperature): Saturation Vapor Pressure = 6.11 * 10.0^(7.5*T / (237.7+T))
	//http://www.ehow.com/how_5619620_calculate-humidity-temperature-dew-point.html
	private double calculateSaturatedVaporPressure(double temperature)
	{
		return 6.11 * Math.pow(10.0, (7.5*temperature / (237.7+temperature)));
	}
	
	//calculate the actual vapor pressure(D is the dew point): Actual vapor pressure = 6.11 * 10.0^(7.5*D / (237.7+D))
	//http://www.ehow.com/how_5619620_calculate-humidity-temperature-dew-point.html
	private double calculateActualVaporPressure(double dewPoint)
	{
		return 6.11 * Math.pow(10.0, (7.5*dewPoint / (237.7+dewPoint)));
	}
	
	//calculate the relative humidity: actual vapor pressure / saturated vapor pressure
	public double calculateRelativeHumidity(double dewPoint, double temperature)
	{
		return (calculateActualVaporPressure(dewPoint) / calculateSaturatedVaporPressure(temperature)) * 100;
	}
}
