package player;

import java.util.ArrayList;

import game.Game;

public class PhysicsScreenObject extends ScreenObject {

	@SuppressWarnings("unused")
	private boolean gravity;
	private double g0, v0; // dx/dt = g0(t)+v0
	private int xdir = 0; // d = {-1,0,1}
	private int xSpeed;
	private int timeInAir = 0;
	private boolean isJumping = false;
	private boolean isOnFloor = false;
	private int jumpsRemaining;
	
	private static final int JMP_TOT = 2;
	private static final double STD_GRAV = -0.66 * Math.sqrt(2);
	private static final double STD_YV = 15;
	private static final int STD_XV = 5;
	
	public PhysicsScreenObject(int startX, int startY, int width, int height, boolean gravity) {
		super(startX, startY, width, height);
		this.gravity = gravity; //Individually setable
		this.xSpeed = STD_XV;
		this.g0 = STD_GRAV;
		jumpsRemaining = JMP_TOT;
	}

	public void setDirection(int dir) {
		if(dir == 0)
			this.xdir = dir;
		else
			this.xdir = dir / Math.abs(dir);
	}
	
	public void jump() {
		if(!isJumping || jumpsRemaining > 0) {
			v0 = STD_YV;
			g0 = STD_GRAV;
			timeInAir = 0;
			isJumping = true;
			isOnFloor = false;
			--jumpsRemaining;
		}
	}
	
	public void updatePosition(ArrayList<ScreenObject> collideList) {
		
		super.getPosition().translate(xSpeed * xdir, 0); //trans x
		
		//Bind at left and right screen edge
		if(super.getPosition().getX() + (super.getWidth()/2) > Game.ROOM_WIDTH) { //RIGHT EDGE
			if(Game.getInstance().incrementCol()) {
				super.getPosition().x = 25 + (super.getWidth()/2); //right -> left
				return;
			}
			else //Fail
				super.getPosition().x = Game.ROOM_WIDTH - (super.getWidth()/2); //right -> right
		}
		else if(super.getPosition().getX() - (super.getWidth()/2) < 0) { //LEFT EDGE
			if(Game.getInstance().decrementCol()) {
				super.getPosition().x = Game.ROOM_WIDTH - (super.getWidth()/2) - 25; //left -> right
				return;
			}
			else
				super.getPosition().x = super.getWidth()/2;
		}
		
		//Check if we have collided with objects on the left or right
		for(ScreenObject o: collideList) {
			if(this.collides(BoundingBox.generateRightBoundingBox(o))) {
				super.getPosition().x = (int)o.getPosition().getX() + (o.getWidth()/2) + (super.getWidth()/2);
				break;
			}
		}
		
		//Check if we have collided with objects on the left or right
		for(ScreenObject o: collideList) {
			if(this.collides(BoundingBox.generateLeftBoundingBox(o))) {
				super.getPosition().x = (int)o.getPosition().getX() - (o.getWidth()/2) - (super.getWidth()/2);
				break;
			}
		}
		
		
		if(isJumping) {
			++timeInAir;

			//Evaluate the derivative of the position function, dx/dt. Assume that (1/60)s ~ 0; apply Euler's thm.
			double delta = (g0 * timeInAir) + v0;
			if(delta < -18)
				delta = -18;
			super.getPosition().y += (int)delta;
			
			//Check collide on ground
			if(super.getPosition().getY() - (super.getHeight() / 2) < 0) {
				if(Game.getInstance().incrementRow()) {
					super.getPosition().y = Game.ROOM_HEIGHT - (super.getHeight() / 2) - 10;
					return;
				}
				else {
					super.getPosition().y = 10 + (super.getHeight() / 2);
					isJumping = false;
					isOnFloor = true;
					timeInAir = 0;
					jumpsRemaining = JMP_TOT;
				}
			}
			
			//Check went up through ceil
			if(super.getPosition().getY() + (super.getHeight() / 2) > Game.ROOM_HEIGHT) {
				if(Game.getInstance().decrementRow()) {
					super.getPosition().y = (super.getHeight() / 2) + 10;
					return;
				}
				else {
					super.getPosition().y = Game.ROOM_HEIGHT - (super.getHeight() / 2);
					v0 = -0.1; 
					timeInAir = 0;
				}
			}
			
			//Check collide fall on actors (should we stop jumping yet?)
			for(ScreenObject o: collideList) {
				if(this.collides(BoundingBox.generateTopBoundingBox(o))) {
					super.getPosition().y = (int)o.getPosition().getY() + (o.getHeight()/2) + (super.getHeight()/2);
					isJumping = false;
					timeInAir = 0;
					jumpsRemaining = JMP_TOT;
					break;
				}
			}
			
			//Did we hit our head?
			for(ScreenObject o: collideList) {
				if(this.collides(BoundingBox.generateBottomBoundingBox(o))) {
					super.getPosition().y = (int)o.getPosition().getY() - (o.getHeight()/2) - (super.getHeight()/2); //Liable to break
					v0 = -0.1; //Set to '0' to magnetize! #FeatureNotABug
					timeInAir = 0;
					break;
				}
			}
		}
		else { //isJumping = false;
			if(!isOnFloor) {
				boolean shouldFall = true;
				for(ScreenObject o: collideList) {
					if(this.collides(BoundingBox.generateTopBoundingBox(o))) {
						shouldFall = false;
						break;
					}
				}
				if(shouldFall) {
					isJumping = true;
					timeInAir = 0;
					v0 = 0;
				}
			}
		}
	}
}