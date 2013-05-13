package dk.itu.serverside;

public class PolicyHandler {

	private static final String highTcLowAcLowHeat = "[PPD index]% of people are feeling too hot. To decrease the number of people feeling too hot you can increase the AC gain. However, this will also increase the energy consumption";
	private static final String highTcLowAcHighHeat = "[PPD index]% of people are feeling too hot. To decrease the number of people feeling too hot you can decrease the heater gain. This will reduce the energy consumption. If people are still feeling too hot after a period of time, try increasing the AC gain. However, this will increase energy consumption.";
	
	private static final String highTcHighAcLowHeat = "[PPD index]% of people are feeling too hot. If possible the AC gain could be increased and the heater gain could be decreased. This will lower the temperature and thereby increase the thermal comfort in the room. Remember that energy consumption depends on your action. If not possible buy ice cream for everyone.";
	private static final String hightTcHighAcHighHeat = "[PPD index]% of people are feeling too hot. To decrease the number of people feeling too hot you can decrease the heater gain. This will also decrease the energy consumption. If people are still feeling to hot after a period of time, try increasing the AC gain if this is possible. However, this will increase energy consumption.";
	
	private static final String lowTcLowAcLowHeat = "[PPD index]% of people are feeling too cold. To decrease the number of people feeling too cold you can increase the heater gain. However, this will also increase the energy consumption.";
	private static final String lowTcLowAcHighHeat = "[PPD index]% of people are feeling too cold. If possible the heater gain could be increased and the AC gain could be decreased. This will lower the temperature and thereby increase the thermal comfort in the room. Remember that energy consumption depends on your action. If not possible buy warm jackets for everyone.";
	
	private static final String lowTcHighAcLowHeat = "[PPD index]% of people are feeling too cold. To decrease the number of people feeling too cold you can decrease the AC gain. This will increase the energy consumption. If people are still feeling too cold after a period of time, try increasing the heater gain. However, this will increase energy consumption.";
	private static final String lowTcHighAcHighHeat = "[PPD index]% of people are feeling too cold. To decrease the number of people feeling too cold you can decrease the AC gain. This will also decrease the energy consumption. If people are still feeling too cold after a period of time, try increasing the heater gain if this is possible. However, this will increase energy consumption.";
	
	private static final String appropriate = "[PPD index]% of people are feeling dissatisfied.";
	
	private static final String highTcDefault = "[PPD index]% of people are feeling too hot. To decrease the number of people feeling too hot you can increase the AC gain or decrease the heater gain. However, this will could lead to an increase in the energy consumption";
	private static final String lowTcDefault = "[PPD index]% of people are feeling too cold. To decrease the number of people feeling too cold you can decrease the AC gain. This will increase the energy consumption. If people are still feeling too cold after a period of time, try increasing the heater gain. However, this will increase energy consumption.";
	
	public String getRecommendedAction(String ac, String heat, String tc, String ppd) {
		int thermalComfort = Integer.parseInt(tc);
		double heatGain = Double.parseDouble(heat);	
		double acGain = Double.parseDouble(ac);
		
		//
		// TC  > 0.5
		//
		if(thermalComfort > 0.5)
		{
			// low ac
			if(acGain <= 20)
			{
				// low heat
				if(heatGain <= 20)
				{
					return highTcLowAcLowHeat.replace("[PPD index]", ppd);
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return highTcLowAcHighHeat.replace("[PPD index]", ppd);	
				}
				
				return highTcDefault.replace("[PPD index]", ppd);
			}
			
			// high ac
			if(acGain >= 80)
			{
				// low heat
				if(heatGain <= 20)
				{
					return highTcHighAcLowHeat.replace("[PPD index]", ppd);
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return hightTcHighAcHighHeat.replace("[PPD index]", ppd);
				}
				
				return ppd+"% of people are satisfied.";
			}
			
			return highTcDefault.replace("[PPD index]", ppd);
		}
		
		//
		// TC  < -0.5
		//
		if(thermalComfort < -0.5)
		{
			// low ac
			if(acGain <= 20)
			{
				// low heat
				if(heatGain <= 20)
				{
					return lowTcLowAcLowHeat.replace("[PPD index]", ppd);
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return lowTcLowAcHighHeat.replace("[PPD index]", ppd);
				}
				
				return lowTcDefault.replace("[PPD index]", ppd);
			}
			
			// high ac
			if(acGain >= 80)
			{
				// low heat
				if(heatGain <= 20)
				{
					return lowTcHighAcLowHeat.replace("[PPD index]", ppd);
				}
				
				// high heat
				if(heatGain >= 80)
				{
					return lowTcHighAcHighHeat.replace("[PPD index]", ppd);
				}
			}
			
			return lowTcDefault.replace("[PPD index]", ppd);
		}
		
		return appropriate.replace("[PPD index]", ppd);
	}
}
