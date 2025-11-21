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
	public int health;

	public int xa, ya;
	public int levelX;
	public int levelY;
	public int mobX;
	public int mobY;
	public int mobScreenX, mobScreenY;

	public long attackTime = 0;
	public long attackCooldown = 0;
	public boolean dead = false;

	public int spriteNum = 1;
	public int spriteCounter = 0;

	public boolean collision;

	public boolean collisionOn;
	public Rectangle solidArea;

	public int dir;
	
	public int action;
	public int detectedEntityOX;
	public int detectedEntityOY;

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
	
	public Rectangle getSolidArea() {
	    Rectangle area = new Rectangle(levelX + solidArea.x, levelY + solidArea.y, solidArea.width, solidArea.height);
	    return area;
	}
	
	public void attack() {
		
	}
	
	public void hurt(int damage) {
		health -= damage;
		if(health <= 0) {
			die();
		}
	}
	
	public void die() {
		
	}
}