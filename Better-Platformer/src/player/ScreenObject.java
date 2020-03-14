package player;

import java.awt.Point;

import gfx.DrawData;

public class ScreenObject {

	private Point position; //Center of object
	private int width;
	private int height;
	
	public ScreenObject(int startX, int startY, int width, int height) {
		position = new Point(startX, startY);
		this.width = width;
		this.height = height;
	}

	
	public DrawData getDrawData() {
		return DrawData.defineDrawCoordanitesFromScreenObject(this);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Point getPosition() {
		return position;
	}
}
