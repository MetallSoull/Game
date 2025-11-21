package com.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;

public class LavaTile extends Tile {

	public BufferedImage[] updatingLava;
	private Random random = new Random();

	private int currentFrames = 0;
	private int tickCounter = 0;

	public LavaTile(Game game, SpriteSheet ss) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(6, 5, 16, 16));
		System.out.println("WaterTile loading image is " + (tileImage != null ? "Successfull" : "Failed"));
		this.game = game;

		collision = true;

		updatingLava = new BufferedImage[3];

		getTile(ss);
	}

	public void getTile(SpriteSheet ss) {
		updatingLava[0] = ss.grabImage(6, 5, 16, 16);
		updatingLava[1] = ss.grabImage(7, 4, 16, 16);
		updatingLava[2] = ss.grabImage(8, 4, 16, 16);
	}

	public void tick() {
		tickCounter++;
		if (tickCounter % 30 == 0) {
			currentFrames = (currentFrames + 1) % updatingLava.length;
		}
	}

	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(updatingLava[currentFrames], xt, yt, tileSize, tileSize, null);
	}
}
