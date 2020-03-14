package game;

import java.util.ArrayList;

import gfx.DrawData;
import player.ScreenObject;

public class Room {

	//A room has objects inside of it
	private ArrayList<ScreenObject> objects;
	
	public Room() {
		objects = new ArrayList<ScreenObject>();
	}
	
	public void addObject(ScreenObject o) {
		objects.add(o);
	}
	
	public ArrayList<DrawData> getBlitList() {
		ArrayList<DrawData> blit = new ArrayList<DrawData>();
		for(ScreenObject o: objects) {
			blit.add(o.getDrawData());
		}
		return blit;
	}
	
}
