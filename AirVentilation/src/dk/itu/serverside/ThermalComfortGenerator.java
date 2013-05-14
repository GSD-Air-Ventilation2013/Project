package dk.itu.serverside;

// creates relative humidity based on our own metric
public class ThermalComfortGenerator 
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
	public double calculateThermalComfort(double dewPoint, double temperature)
	{
		double relativeHumidity = (calculateActualVaporPressure(dewPoint) / calculateSaturatedVaporPressure(temperature)) * 100;
		double pmv = calculatePMV(temperature, relativeHumidity);
		return pmv;
	}
	
	
	// taken from http://www.lth.se/fileadmin/eat/Termisk_miljoe/PMV-PPD.html
	private double calculatePMV(double temp, double relativeHumidity)
	{
		//M (W/m2), Metabolic energy production (58 to 232 W/m2)
		int M = 70;
		
		// W (W/m2), Rate of mechanical work, (normally 0)
		int W = 0;
		
		//v (m/s), Relative air velocity (0.1 to 1 m/s)
		double v = 0.1;
		
		//Ta (C), Ambient air temperature (10-30)
		double Ta = temp;
		
		// operative temperature (static)
		double To = 24.5;
		
		//Tr (C), Mean radiant temperature (often close to ambient air temperature)
		double Tr = 2*To - temp;
		
		//rh (%), Relative humidity
		double rh = relativeHumidity;
		
		//Icl (clo), basic clothing insulation (1 clo = 0.155 W/m2K)
		double Icl = 1.0;
		
		Icl=Icl*0.155;
		double Ia=0.092*Math.exp(-0.15*v-0.22*W)-0.0045;
		double Tsk = 35.7-0.0285*M;
		
		// Calculation of Pa (Pa) 
		double Pa = (rh/100)*0.1333*Math.exp(18.6686-4030.183/(Ta+235));
		
		// *** Calculation of Dlimneutral and Dlimminimal *** 
		// Calculation of S (W/m2),fcl (n.d.), hr W/m2C with stepwise iteration 
		// Initial values !
		double Tcl = Ta; 
		double hr = 3; 
		double S = 0.0; 
		double ArAdu = 0.77; 
		int factor = 500; 
		double Iclr = Icl; 
		double Balance = 0.0;
		double E = 0.0;
		double Ediff;
		double Hres;
		double R = 0.0;
		double C = 0.0;
		double fcl = 0.0;
		double hc = 0.0;
		
		while (Math.abs(Balance) > 0.01);
		{
		fcl=1.05+0.65*Icl;
		E=0.42*((M-W)-58);
		Ediff=3.05*(0.255*Tsk-3.36-Pa);
		Hres=1.73E-2*M*(5.867-Pa)+1.4E-3*M*(34-Ta);
		Tcl=Tsk-Icl*(M-W-E-Ediff-Hres-S);      
		hr=5.67E-8*0.95*ArAdu*(Math.exp(4*Math.log(273+Tcl))-
		Math.exp(4*Math.log(273+Tr)))/(Tcl-Tr);
		hc=12.1*Math.pow(v,0.5);
		R = fcl*hr*(Tcl-Tr);
		C = fcl*hc*(Tcl-Ta);
		Balance = M-W-E-Ediff-Hres-R-C-S;  
		if (Balance>0)  {
			S=S+factor;
			factor=factor/2;
		}
		else {
			S=S-factor;
		}     
		}
		
		S = M-W-E-Ediff-Hres-R-C;
		
		double PMV =(0.303*Math.exp(-0.036*M)+0.028)*S;
		return Math.round(PMV*100)/100;
	}
	
	public double calculatePPD(double PMV)
	{
		double PPD = 100-95*Math.exp(-0.03353*Math.pow(PMV,4)-0.2179*Math.pow(PMV,2));
		return Math.round((PPD)*10)/10;
	}
}

