package com.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;

public class CactusTile extends Tile {

	private BufferedImage cactusTile, smallCactusTile;

	private Random random;

	public CactusTile(Game game, SpriteSheet ss) {
		super(ss.grabImage(6, 6, 16, 16));
		this.game = game;

		random = new Random();
		
		collision = true;
	}

	public int randomGen(int col, int row) {
		int chance = random.nextInt(100);
		if (chance < 5) {
			return 6;
		}
		return 5;
	}

	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(cactusTile, xt, yt, tileSize, tileSize, null);
		g.drawImage(smallCactusTile, xt, yt, tileSize, tileSize, null);
	}

	public void generation(Graphics g, int xt, int yt, int obsType) {
		if (obsType == 1)
			g.drawImage(cactusTile, xt, yt, tileSize, tileSize, null);
		if (obsType == 2)
			g.drawImage(smallCactusTile, xt, yt, tileSize, tileSize, null);
	}
}