package dk.itu.restconnection;

public class RestConnection {
	public static void main(String[] args) {

		RestClient rc = new RestClient();
		System.out.println(rc.getRestData("http://gsd.itu.dk/api/user/building/entry/description/3/?format=json"));
		System.out.println("Check");
	}

}
