package dk.itu.restconnection;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class MeasurementDeserializer implements JsonDeserializer<Measurement> {
	@Override
	public Measurement deserialize(JsonElement json, Type type, JsonDeserializationContext ctx) throws JsonParseException {
		Measurement measurement = new Measurement();

		JsonObject obj = json.getAsJsonObject().getAsJsonArray("objects").get(0).getAsJsonObject();

		measurement.setUuid(obj.getAsJsonPrimitive("uuid").getAsString());
		measurement.setValue(obj.getAsJsonPrimitive("val").getAsDouble());

		return measurement;
	}

}
