package com.game.tile;

import java.awt.Graphics;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;

public class StoneTile extends Tile {
		
	public StoneTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(2, 5, 16, 16));        
		System.out.println("StoneTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
		this.game = game;
		
		collision = true;
	 }
	
	@Override
	public void render(Graphics g, int xt, int yt) {	
		 g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
}