package game;

import java.util.ArrayList;

import gfx.DrawData;
import player.Player;
import player.ScreenObject;

public class Game {

	private static final int INITIAL_X = 250;
	private static final int INITIAL_Y = 250;
	
	public static final int ROOM_WIDTH = 500;
	public static final int ROOM_HEIGHT = ROOM_WIDTH; //square rooms mf
	
	private static Game instance = null;
	private Room[][] rooms = new Room[2][2];
	private int currentRoomRow = 0;
	private int currentRoomCol = 0;
	private Room currentRoom;
	private Player player;
	
	private Game() {
		player = new Player(INITIAL_X, INITIAL_Y);
		
		rooms[0][0] = new Room();
		currentRoom = rooms[currentRoomRow][currentRoomCol];
		
		currentRoom.addObject(new ScreenObject(50, 50, 100, 100));
		currentRoom.addObject(new ScreenObject(450, 50, 100, 100));
		currentRoom.addObject(new ScreenObject(50, 450, 100, 100));
		currentRoom.addObject(new ScreenObject(450, 450, 100, 100));
		
	}
	
	public ArrayList<DrawData> getCurrentRoomDrawData() {
		return currentRoom.getBlitList();
	}
	  
	public DrawData getPlayerDrawData() {
		return player.getDrawData();
	}
	
	
	
	
	public static Game getInstance() {
		if(instance == null)
			instance = new Game();
		return instance;
	}
}
