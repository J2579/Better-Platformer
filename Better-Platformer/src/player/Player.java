package player;

public class Player extends PhysicsScreenObject {
		
	private static final int PLAYER_WIDTH = 50;
	private static final int PLAYER_HEIGHT = 110;
	
	public Player(int startX, int startY) {
		super(startX, startY, PLAYER_WIDTH, PLAYER_HEIGHT, true);
	}
}
