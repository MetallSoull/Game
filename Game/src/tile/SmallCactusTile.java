package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import gfx.SpriteSheet;
import main.Game;

public class SmallCactusTile extends Tile {

	private Random random;

	public SmallCactusTile(Game game, SpriteSheet ss) {
		super(ss.grabImage(7, 6, 16, 16));
		this.game = game;

		random = new Random();
	}

	public int randomGen(int col, int row) {
		int chance = random.nextInt(100);
		if (chance < 20) {
			return 7;
		}
		return 5;
	}

	public void render(Graphics g, int xt, int yt) {
		g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
}