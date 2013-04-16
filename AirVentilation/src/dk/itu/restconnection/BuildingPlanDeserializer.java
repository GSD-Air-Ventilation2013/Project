package dk.itu.restconnection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import com.google.gson.*;

public class BuildingPlanDeserializer implements JsonDeserializer<BuildingPlan> {
	@Override
	public BuildingPlan deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		BuildingPlan buildingPlan = new BuildingPlan();
		ArrayList<Room> roomList = new ArrayList<Room>();
		buildingPlan.setRooms(roomList);

		JsonObject rooms = json.getAsJsonObject().getAsJsonObject("value").getAsJsonObject("rooms");

		for (Map.Entry<String, JsonElement> entry : rooms.entrySet())
			roomList.add(buildRoom(entry.getValue().getAsJsonObject()));

		return buildingPlan;
	}

	private Room buildRoom(JsonObject json) {
		Room room = new Room();

		room.setRoomId(json.getAsJsonPrimitive("ID").getAsString());
		room.setNumAC(json.getAsJsonPrimitive("numAC").getAsInt());
		room.setNumHeaters(json.getAsJsonPrimitive("numHeaters").getAsInt());

		return room;

	}
}
