package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.SpriteSheet;
import main.Game;

public class StoneTile extends Tile {
	
	private static Game game;
	private BufferedImage stoneTile;
	
	public boolean collision;
	
	public StoneTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(2, 5, 16, 16));        
		System.out.println("StoneTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
		stoneTile = tileImage;
    }
	
	@Override
	public void render(Graphics g, int xt, int yt) {	
		 g.drawImage(stoneTile, xt, yt, tileSize, tileSize, null);
	}
}