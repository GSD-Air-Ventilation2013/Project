package dk.itu.restconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestClient {
	BufferedReader br = null;
	BuildingPlan building;

	public BuildingPlan getRestData(String urlAddress) {

		try {
			Gson gson = new GsonBuilder()
							.registerTypeAdapter(BuildingPlan.class, new BuildingPlanDeserializer())
							.create();

			// connecting to restAPI
			URL url = new URL(urlAddress);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			// If connection successful
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			InputStreamReader input = new InputStreamReader((conn.getInputStream()));
			br = new BufferedReader(input);
			
			building = gson.fromJson(br, BuildingPlan.class);

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return building;
	}

}