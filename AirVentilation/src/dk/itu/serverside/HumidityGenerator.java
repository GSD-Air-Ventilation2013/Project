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
}
