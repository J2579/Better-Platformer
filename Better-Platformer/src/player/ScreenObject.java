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
		
		if(width < 10 || height < 10)
			throw new IllegalArgumentException("Dimensions too small to check collision.");
	}

	//Does 'this' collide o and its specified bounding box?
	public boolean collides(BoundingBox bb) {
		return BoundingBox.generateBoundingBox(this).collides(bb);
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
