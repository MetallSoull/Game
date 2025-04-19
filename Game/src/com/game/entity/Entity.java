package com.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.game.main.Game;
import com.game.main.KeyHandler;
import com.game.tile.Tile;

public class Entity {

	private KeyHandler keyH;

	public int playerSize;
	public int playerSpeed;

	public int levelX;
	public int levelY;
	public int mobX;
	public int mobY;
	public int mobScreenX, mobScreenY;

	public String action = "";
	public boolean attackOn;
	public int hurt;
	public int attackTime = 0;

	public int spriteNum = 1;
	public int spriteCounter = 0;

	public boolean collision;

	public boolean collisionOn;
	public Rectangle solidArea;

	public int dir;

	public BufferedImage[] playerSprite = new BufferedImage[8];
	public BufferedImage[] attackParticle = new BufferedImage[8];
	private Game game;
	public Entity collidedEntity;

	public Entity(Game game) {
		this.game = game;
		solidArea = new Rectangle(0, 0, 48, 48);
	}

	public void tick() {

	}

	public void render(Graphics g) {

	}
}