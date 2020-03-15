package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import gfx.DrawData;
import player.Player;
import player.ScreenObject;

public class Game {

	private static final int INITIAL_X = 250;
	private static final int INITIAL_Y = 100;
	
	public static final int ROOM_WIDTH = 500;
	public static final int ROOM_HEIGHT = ROOM_WIDTH; //square rooms mf
	
	private boolean leftHeld = false;
	private boolean rightHeld = false;
	private int mostRecentXDir = 0;
	
	
	private static Game instance = null;
	private Room[][] rooms = new Room[2][2];
	private int currentRoomRow = 1;
	private int currentRoomCol = 0;
	private Room currentRoom;
	private Player player;
	
	private Game() {
		player = new Player(INITIAL_X, INITIAL_Y);
		
		rooms[1][0] = new Room();
		rooms[1][0].addObject(new ScreenObject(50, 50, 100, 100));
		rooms[1][0].addObject(new ScreenObject(250, 220, 150, 25));
		rooms[1][0].addObject(new ScreenObject(450,350,100,300));
		rooms[1][0].addObject(new ScreenObject(300,300,150,100));
		rooms[1][0].addObject(new ScreenObject(50,450,100,100));
		rooms[1][0].addObject(new ScreenObject(325,5,450,10));
		
		rooms[1][1] = new Room();
		rooms[1][1].addObject(new ScreenObject(450,250,100,500));
		rooms[1][1].addObject(new ScreenObject(75,350,150,300));
		rooms[1][1].addObject(new ScreenObject(200,5,400,10));
		
		rooms[0][0] = new Room();
		rooms[0][0].addObject(new ScreenObject(50,200,100,400));
		rooms[0][0].addObject(new ScreenObject(450,75,100,150));
		rooms[0][0].addObject(new ScreenObject(250,250,100,30));
		
		rooms[0][1] = new Room();
		rooms[0][1].addObject(new ScreenObject(75,75,150,150));
		rooms[0][1].addObject(new ScreenObject(450,250,100,500));
		
		
		currentRoom = rooms[currentRoomRow][currentRoomCol];
	}
	
	public boolean incrementCol() {
		if(currentRoomCol == rooms[0].length - 1) 
			return false;
		++currentRoomCol;
		currentRoom = rooms[currentRoomRow][currentRoomCol];
		return true;
	}
	
	public boolean decrementCol() {
		if(currentRoomCol == 0) 
			return false;
		--currentRoomCol;
		currentRoom = rooms[currentRoomRow][currentRoomCol];
		return true;
	}
	
	public boolean incrementRow() {
		if(currentRoomRow == rooms.length - 1) 
			return false;
		++currentRoomRow;
		currentRoom = rooms[currentRoomRow][currentRoomCol];
		return true;
	}
	
	public boolean decrementRow() {
		if(currentRoomRow == 0) 
			return false;
		--currentRoomRow;
		currentRoom = rooms[currentRoomRow][currentRoomCol];
		return true;
	}
	
	public void updateEventTick() {
		currentRoom.update();
//		currentRoom.updatePlayerPosition();
		updatePlayerXDirection(); //Reliant on KB Input
	}
	
	private void updatePlayerXDirection() {
		if(!leftHeld && !rightHeld)
			player.setDirection(0);
		else if(leftHeld && !rightHeld)
			player.setDirection(-1);
		else if(!leftHeld && rightHeld)
			player.setDirection(1);
		else if(leftHeld && rightHeld)
			player.setDirection(mostRecentXDir);
	}

	public ArrayList<DrawData> getCurrentRoomDrawData() {
		return currentRoom.getBlitList();
	}
	  
	public DrawData getPlayerDrawData() {
		return player.getDrawData();
	}
	
	public void handleKeyPress(KeyEvent e) {
		int ke = e.getKeyCode();
		
		if(ke == KeyEvent.VK_A) {
			leftHeld = true;
			mostRecentXDir = -1;
		}
		else if(ke == KeyEvent.VK_D) {
			rightHeld = true;
			mostRecentXDir = 1;
		}
		else if(ke == KeyEvent.VK_SPACE)
			player.jump();
			
	}
	
	public void handleKeyRelease(KeyEvent e) {
		int ke = e.getKeyCode();
		
		if(ke == KeyEvent.VK_A) 
			leftHeld = false;
		else if(ke == KeyEvent.VK_D) 
			rightHeld = false;
	}

	public Player getPlayerRef() {
		return player;
	}
	
	public static Game getInstance() {
		if(instance == null)
			instance = new Game();
		return instance;
	}
}
