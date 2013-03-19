package dk.itu.restconnection;

import java.util.ArrayList;

public class BuildingPlan 
{
	private ArrayList<Room> rooms;

	public BuildingPlan()
	{
		setRooms(new ArrayList<Room>());
	}

	public ArrayList<Room> getRooms() {
		return rooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
}
