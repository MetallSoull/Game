package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.SpriteSheet;
import main.Game;

public class RoadTile extends Tile {
	
	private Game game;

	public RoadTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(3, 5, 16, 16));
		System.out.println("RoadTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
		this.game = game;
	}

	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
}