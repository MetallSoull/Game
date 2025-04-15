package com.game.tile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import com.game.main.Game;

public class Tile {
	
	protected Game game;
	
	public BufferedImage tileImage;
	
	public boolean collision;
	
	public static int tileSize = 48;
	
	public int screenCol = game.WIDTH / tileSize;
	public int screenRow = game.HEIGHT / tileSize;
	
	public Tile(BufferedImage tileImage) {
		this.tileImage = tileImage;
	}
	
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
	
	public void tick() {
		
	}
}