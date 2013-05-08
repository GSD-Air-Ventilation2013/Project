package dk.itu.restconnection;

public class RestConnection {	
	
	public static void main(String[] args) {

		RestClient rc = new RestClient();
		//System.out.println(rc.getBuildingPlan().getRooms().get(0).getNumAC());
		Measurement[] m = rc.getMeasurements("floor-0-room-1.temp", "2");
		
		System.out.println(m[0].getValue());
		System.out.println("Check");
	}
}
