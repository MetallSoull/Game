package com.game.tile;

import java.awt.Graphics;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;

public class SandTile extends Tile {
	
	public SandTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(5, 5, 16, 16));
		this.game = game;
	}
	
	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
}
