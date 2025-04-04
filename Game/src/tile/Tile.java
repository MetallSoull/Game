package tile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import main.Game;

public class Tile {
	
	private Game game;
	
	public static BufferedImage tileImage;
	
	public boolean collision;
	
	public static int tileSize = 48;
	
	public int screenCol = game.WIDTH / tileSize;
	public int screenRow = game.HEIGHT / tileSize;
	
	public Tile(BufferedImage tileImage) {
		this.tileImage = tileImage;
	}
	
	public void render(Graphics g, int xt, int yt) {
		if(tileImage != null) {
			g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
		}
	}
	
	public void tick() {
		
		
	}
}