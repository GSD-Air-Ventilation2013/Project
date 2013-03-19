package dk.itu.restconnection;

public class RestConnection {	
	
	public static void main(String[] args) {

		RestClient rc = new RestClient();
		//System.out.println(rc.getBuildingPlan().getRooms().get(0).getNumAC());
		Measurement m = rc.getMeasurement("room-0-ac-0-gain");
		System.out.println(m.getValue());
		System.out.println("Check");
	}
}
