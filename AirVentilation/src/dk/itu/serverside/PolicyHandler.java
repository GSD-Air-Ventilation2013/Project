package dk.itu.serverside;

public class PolicyHandler {

	private static final String highTcLowAcLowHeat = "Increase your AC";
	private static final String highTcLowAcHighHeat = "Turn down your heating system";
	
	private static final String highTcHighAcLowHeat = "Pray!";
	private static final String hightTcHighAcHighHeat = "Turn down your heating system";
	
	private static final String lowTcLowAcLowHeat = "Turn up your heating";
	private static final String lowTcLowAcHighHeat = "Pray!";
	
	private static final String lowTcHighAcLowHeat = "Turn down your ac";
	private static final String lowTcHighAcHighHeat = "Turn down your ac";
	
	private static final String appropriate = "Sit back, relax";
	
	public String getRecommendedAction(String ac, String heat, String tc) {
		int thermalComfort = Integer.parseInt(tc);
		double heatGain = Double.parseDouble(heat);	
		double acGain = Double.parseDouble(ac);
		
		//
		// TC  > 3
		//
		if(thermalComfort > 3)
		{
			// low ac
			if(acGain <= 20)
			{
				// low heat
				if(heatGain <= 20)
				{
					return highTcLowAcLowHeat;
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return highTcLowAcHighHeat;	
				}
				
				return "low ac medium heating";
			}
			
			// high ac
			if(acGain >= 80)
			{
				// low heat
				if(heatGain <= 20)
				{
					return highTcHighAcLowHeat;
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return hightTcHighAcHighHeat;
				}
				
				return "high ac medium heating";
			}
		}
		
		//
		// TC  < -3
		//
		if(thermalComfort < -3)
		{
			// low ac
			if(acGain <= 20)
			{
				// low heat
				if(heatGain <= 20)
				{
					return lowTcLowAcLowHeat;
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return lowTcLowAcHighHeat;
				}
				
				return "low ac medium heating";
			}
			
			// high ac
			if(acGain >= 80)
			{
				// low heat
				if(heatGain <= 20)
				{
					return lowTcHighAcLowHeat;
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return lowTcHighAcHighHeat;
				}
				
				return "high ac medium heating";
			}
		}
		
		return appropriate;
	}
}
