package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import gfx.SpriteSheet;
import main.Game;

public class WaterTile extends Tile{
	
	private static Game game;
	
	public BufferedImage[] updatingWater;
	private Random random = new Random();
	
	private int currentFrames = 0;
	private int tickCounter = 0;

	public boolean collision;
	
	public WaterTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(1, 4, 16, 16));
		System.out.println("WaterTile loading image is " + (tileImage != null ? "Successfull" : "Failed"));
    	this.game = game;
		
		updatingWater = new BufferedImage[3];
		
		getTile();
	}
	
	public void getTile() {
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		updatingWater[0] = ss.grabImage(1, 4, 16, 16);
		updatingWater[1] = ss.grabImage(2, 4, 16, 16);
		updatingWater[2] = ss.grabImage(3, 4, 16, 16);
	}
	
	public void tick() {
		tickCounter++;
		if(tickCounter % 30 == 0) {
			currentFrames = (currentFrames + 1) % updatingWater.length; 
		}
	}
	
	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(updatingWater[currentFrames], xt, yt, tileSize, tileSize, null);
	}
}
