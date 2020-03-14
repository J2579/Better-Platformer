package gfx;

import game.Game;
import player.ScreenObject;

public class DrawData {

	public int x,y,width,height;

	public static DrawData defineDrawCoordanitesFromScreenObject(ScreenObject o) {
		DrawData d = new DrawData();
		
		d.x = o.getPosition().x - (o.getWidth()/2);
		d.y = Game.ROOM_HEIGHT - (o.getPosition().y + (o.getHeight()/2));
		d.width = o.getWidth();
		d.height = o.getHeight();
				
		return d;
	}
	
	@Override
	public String toString() {
		return "x: " + x + ", y: " + y + ", width: " + width + ", height: " + height;
	}
}
