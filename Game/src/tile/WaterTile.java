package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import gfx.SpriteSheet;
import main.Game;

public class WaterTile extends Tile{
		
	public BufferedImage[] updatingWater;
	private Random random = new Random();
	
	private int currentFrames = 0;
	private int tickCounter = 0;
	
	public WaterTile(Game game, SpriteSheet ss) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(1, 4, 16, 16));
		System.out.println("WaterTile loading image is " + (tileImage != null ? "Successfull" : "Failed"));
    	this.game = game;
    	
    	collision = true;
		
		updatingWater = new BufferedImage[3];
		
		getTile(ss);
	}
	
	public void getTile(SpriteSheet ss) {
		updatingWater[0] = ss.grabImage(1, 4, 16, 16);
		updatingWater[1] = ss.grabImage(2, 4, 16, 16);
		updatingWater[2] = ss.grabImage(3, 4, 16, 16);
	}
	
	public void tick() {
		tickCounter++;
		int chance = random.nextInt(100);
		if(chance < 50) {
		    if(tickCounter % 30 == 0) {
			    currentFrames = (currentFrames + 1) % updatingWater.length; 
		    }
		}
	}
	
	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(updatingWater[currentFrames], xt, yt, tileSize, tileSize, null);
	}
}
