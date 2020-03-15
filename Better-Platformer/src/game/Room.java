package game;

import java.util.ArrayList;

import gfx.DrawData;
import player.BoundingBox;
import player.PhysicsScreenObject;
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
	
	public void update() {
		for(ScreenObject o: objects) { //At this point, we can choose to ignore collision with physics objects
									   //by copying 'objects' into another list, stripping it of instances of PhysicsScreenObject!
			if(o instanceof PhysicsScreenObject) {
				((PhysicsScreenObject)(o)).updatePosition(objects);
			}
		}
		//Special Player Collide
		Game.getInstance().getPlayerRef().updatePosition(objects);
	}

	public ArrayList<DrawData> getBlitList() {
		ArrayList<DrawData> blit = new ArrayList<DrawData>();
		for(ScreenObject o: objects) {
			blit.add(o.getDrawData());
		}
		return blit;
	}
}
