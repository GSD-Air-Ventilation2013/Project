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

	static final String BUILDINGINFO_URL = "http://gsd.itu.dk/api/user/building/entry/description/3/?format=json";
	static final String MEASURE_URL = "http://gsd.itu.dk/api/user/measurement/?bid=3&limit=1&order_by=-timestamp&format=json&uuid=";

	BufferedReader br = null;
	BuildingPlan building;

	public BuildingPlan getBuildingPlan() {
		Gson gson = new GsonBuilder().registerTypeAdapter(BuildingPlan.class, new BuildingPlanDeserializer()).create();

		String json = getJsonString(BUILDINGINFO_URL);
		return gson.fromJson(json, BuildingPlan.class);
	}

	public Measurement getMeasurement(String uuid) {
		Gson gson = new GsonBuilder().registerTypeAdapter(Measurement.class, new MeasurementDeserializer()).create();

		String json = getJsonString(MEASURE_URL + uuid);
		// FOR TESTING PURPOSES TO ELIMINATE LONG RESPONSE TIMES.
		// String json =
		// "{\"meta\": {\"limit\": 1, \"next\": \"/api/user/measurement/?limit=1&uuid=room-0-ac-0-gain&format=json&order_by=-timestamp&bid=3&offset=1\", \"offset\": 0, \"previous\": null, \"total_count\": 24057}, \"objects\": [{\"bid\": 3, \"id\": \"152834984\", \"resource_uri\": \"/api/user/measurement/152834984/\", \"timestamp\": \"2013-03-19T11:19:18+00:00\", \"uuid\": \"room-0-ac-0-gain\", \"val\": 0.0}]}";
		return gson.fromJson(json, Measurement.class);
	}

	private String getJsonString(String url) {

		try {
			// connecting to restAPI
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			// If connection successful
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			InputStreamReader input = new InputStreamReader((conn.getInputStream()));

			br = new BufferedReader(input);
			String line = null;
			StringBuffer json = new StringBuffer();
			while ((line = br.readLine()) != null) {
				json.append(line + "\n");
			}
			br.close();
			conn.disconnect();

			return json.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}