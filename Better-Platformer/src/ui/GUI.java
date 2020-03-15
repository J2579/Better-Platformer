package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import game.Game;
import gfx.DrawData;

/**
 * GUI Controller / Server for the A.R.G.U.S system. This could probably be split up into
 * two classes, with the server itself being another model as part of the MVC.
 * 
 * @author J2579
 */
@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener, KeyListener {

	private Game game;
	private GraphicsWindow gfx;
	private Timer tick; //Event tick
	
	private static final int WIDTH = 500;  //Window width
	private static final int HEIGHT = 500; //Window height

	public static void main(String[] args) {
		GUI n = new GUI();
		n.setup();
	}


	public void setup() {
		
		setTitle("2D Platform Game"); //Frame Properties
		setSize(WIDTH+15,HEIGHT+40);
		setLocation(0,0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Shutdown behavior
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				//Save? and close the game
				super.windowClosing(e);
			}
				
		});

		gfx = new GraphicsWindow(WIDTH, HEIGHT);
		add(gfx);
		setVisible(true); 
		
		gfx.createAndSetBuffer();

		addKeyListener(this);
		requestFocus();
		
		//Game fuckery
		game = Game.getInstance();
		
		tick = new Timer(17, this); //Event tick17
		tick.setRepeats(true);
		tick.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.handleKeyPress(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.handleKeyRelease(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * Updates the graphics, model, and connection on tick.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(tick)) {
			gfx.update();
			game.updateEventTick();
		}
	}

	private class GraphicsWindow extends DoubleBufferedCanvas {

		public GraphicsWindow(int width, int height) {
			super(width, height);
		}

		@Override
		public void draw(Graphics g) {
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight()); //White out screen
			g.setColor(Color.CYAN);
			
			//Game stuff
			for(DrawData dd: game.getCurrentRoomDrawData()) {
				g.drawRect(dd.x, dd.y, dd.width, dd.height);
			}
			
			g.setColor(Color.WHITE);
			DrawData playerData = game.getPlayerDrawData();
			g.fillRect(playerData.x, playerData.y, playerData.width, playerData.height);
		}
	}
}