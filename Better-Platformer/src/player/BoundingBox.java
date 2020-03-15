package player;

import java.awt.Point;

public class BoundingBox {
	
	public Point topL, botR;

	public BoundingBox(int x, int y, int width, int height) {
		topL = new Point(x,y+height);
		botR = new Point(x+width,y);
	}

	public static BoundingBox generateBoundingBox(ScreenObject o) {
		int x = (int)(o.getPosition().getX()) - (o.getWidth() / 2); //???
		int y = (int)(o.getPosition().getY()) - (o.getHeight() / 2);
		int height = o.getHeight();
		int width = o.getWidth();
		
		BoundingBox bb = new BoundingBox(x,y,width,height);
		return bb;
	}
	
	public static BoundingBox generateTopBoundingBox(ScreenObject o) {
		BoundingBox bb = generateBoundingBox(o);
		bb.botR.y = (int)bb.topL.getY() - (int)((bb.topL.getY()-bb.botR.getY())/4);
		int bbwidth = (int)(bb.botR.getX()-bb.topL.getX());
		if(bbwidth/8 > 15) //For large-width'd objects
			bbwidth = 15*8;
		bb.topL.x += (bbwidth/8);
		bb.botR.x -= (bbwidth/8);
		return bb;
	}
	
	public static BoundingBox generateBottomBoundingBox(ScreenObject o) {
		BoundingBox bb = generateBoundingBox(o);
		bb.topL.y = (int)bb.botR.getY() + (int)((bb.topL.getY()-bb.botR.getY())/4);
		int bbwidth = (int)(bb.botR.getX()-bb.topL.getX());
		if(bbwidth/8 > 15) //For large-width'd objects
			bbwidth = 15*8;
		bb.topL.x += (bbwidth/8);
		bb.botR.x -= (bbwidth/8);
		return bb;
	}
	
	public static BoundingBox generateRightBoundingBox(ScreenObject o) {
		BoundingBox bb = generateBoundingBox(o);
		bb.topL.x = (int)bb.botR.getX() - (int)((bb.botR.getX()-bb.topL.getX())/4);
		int bbheight = (int)((bb.topL.getY()-bb.botR.getY()));
		if(bbheight/8 > 15) //For large-width'd objects
			bbheight = 15*8;
		bb.topL.y -= (bbheight/8);
		bb.botR.y += (bbheight/8);
		return bb;
	}
	
	public static BoundingBox generateLeftBoundingBox(ScreenObject o) {
		BoundingBox bb = generateBoundingBox(o);
		bb.botR.x = (int)bb.topL.getX() + (int)((bb.botR.getX()-bb.topL.getX())/4);
		int bbheight = (int)((bb.topL.getY()-bb.botR.getY()));
		if(bbheight/8 > 15) //For large-width'd objects
			bbheight = 15*8;
		bb.topL.y -= (bbheight/8);
		bb.botR.y += (bbheight/8);
		return bb;
	}
	
	
	public boolean collides(BoundingBox other) {
		if(botR.x < other.topL.x || other.botR.x < topL.x) //left
			return false;
		else if(topL.y < other.botR.y || other.topL.y < botR.y) //down
			return false;
		return true;
	}
}