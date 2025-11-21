package com.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.game.gfx.SpriteSheet;
import com.game.main.Game;
import com.game.main.KeyHandler;
import com.game.tile.Tile;
import com.main.level.Level;

public class Player extends Entity {

	private Game game;
	private KeyHandler keyH;
	public BufferedImage image = null;

	public int screenX;
	public int screenY;
	public int cameraX;
	public int cameraY;

	private Random random = new Random();

	public Player(Game game, KeyHandler keyH) {
		super(game);
		this.game = game;
		this.keyH = keyH;
		screenX = (game.WIDTH / 2) - (Tile.tileSize / 2);
		screenY = (game.HEIGHT / 2) - (Tile.tileSize / 2);

		collision = true;

		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 20;
		solidArea.width = 15;
		solidArea.height = 20;

		getSprites();
		setDefaultValues();
	}

	public void getSprites() {
		SpriteSheet spriteS = new SpriteSheet(game.getSpriteSheet());

		playerSprite[0] = spriteS.grabImage(3, 1, 16, 16);
		playerSprite[1] = spriteS.grabImage(1, 1, 16, 16);
		playerSprite[2] = spriteS.grabImage(7, 1, 16, 16);
		playerSprite[3] = spriteS.grabImage(5, 1, 16, 16);
		playerSprite[4] = spriteS.grabImage(4, 1, 16, 16);
		playerSprite[5] = spriteS.grabImage(2, 1, 16, 16);
		playerSprite[6] = spriteS.grabImage(8, 1, 16, 16);
		playerSprite[7] = spriteS.grabImage(6, 1, 16, 16);
		attackParticle[0] = spriteS.grabImage(1, 2, 16, 16);
		attackParticle[1] = spriteS.grabImage(2, 2, 16, 16);
		attackParticle[2] = spriteS.grabImage(3, 2, 16, 16);
		attackParticle[3] = spriteS.grabImage(4, 2, 16, 16);
	}

	public void setDefaultValues() {
	    playerSpeed = 1;
	    playerSize = 48;
	    health = 10;

	    levelX = (game.level.w / 2) * Tile.tileSize - (playerSize / 2);
	    levelY = (game.level.h / 2) * Tile.tileSize - (playerSize / 2);

	    dir = new Random().nextInt(4) + 1;
	}


	public Rectangle getSolidArea() {
		return new Rectangle(levelX + solidArea.x, levelY + solidArea.y, solidArea.width, solidArea.height);
	}

	public void tick() {
		if (cameraX == 0 && cameraY == 0) {
			cameraX = levelX - screenX;
			cameraY = levelY - screenY;
		}

		boolean movingUp = keyH.up;
		boolean movingDown = keyH.down;
		boolean movingLeft = keyH.left;
		boolean movingRight = keyH.right;
		boolean attackO = keyH.attackO;
		boolean moving = movingUp || movingDown || movingLeft || movingRight;

		xa = 0;
		ya = 0;

		if (xa > 0)
			dir = 3;
		else if (xa < 0)
			dir = 4;
		else if (ya < 0)
			dir = 1;
		else if (ya > 0)
			dir = 2;

		if (movingLeft) {
			xa--;
			moving = true;
			dir = 4;
		} else if (movingRight) {
			xa++;
			moving = true;
			dir = 3;
		}

		if (movingUp) {
			ya--;
			moving = true;
			dir = 1;
		} else if (movingDown) {
			ya++;
			moving = true;
			dir = 2;
		}

		if (attackCooldown > 0) {
			attackCooldown--;
		}

		if (attackO && attackCooldown == 0) {
			attack();
			attackCooldown = 50;
			attackO = false;
		}
		/*
		 * System.out.println("AttackO: " + attackO + " AttackCooldown: " +
		 * attackCooldown);
		 */
		if (moving) {
			int moveSpeed = playerSpeed;

			int nextX = levelX + xa * moveSpeed;
			Rectangle nextRectX = new Rectangle(nextX + solidArea.x, levelY + solidArea.y, solidArea.width,
					solidArea.height);
			collisionOn = false;
			game.cChecker.checkEntity(this, game.entities, nextRectX);
			if (!collisionOn) {
				levelX = nextX;
			}

			int nextY = levelY + ya * moveSpeed;
			Rectangle nextRectY = new Rectangle(levelX + solidArea.x, nextY + solidArea.y, solidArea.width,
					solidArea.height);
			collisionOn = false;
			game.cChecker.checkEntity(this, game.entities, nextRectY);
			if (!collisionOn) {
				levelY = nextY;
			}

			if (levelX < -(playerSize / 2)) {
				levelX = -(playerSize / 2);
			}

			if (levelY < -(playerSize / 2)) {
				levelY = -(playerSize / 2);
			}

			spriteCounter++;
			if (spriteCounter % 25 == 0) {
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
		}

		cameraX = levelX - screenX;
		cameraY = levelY - screenY;

		if (cameraX < 0)
			cameraX = 0;
		if (cameraY < 0)
			cameraY = 0;

		int maxCamX = game.level.w * Tile.tileSize - game.WIDTH;
		int maxCamY = game.level.h * Tile.tileSize - game.HEIGHT;

		if (cameraX > maxCamX)
			cameraX = maxCamX;
		if (cameraY > maxCamY)
			cameraY = maxCamY;
	}

	public void attack() {

		Rectangle attackArea = new Rectangle(levelX, levelY, Tile.tileSize, Tile.tileSize);

		switch (dir) {
		case 1:
			attackArea.y -= Tile.tileSize;
			break; // up
		case 2:
			attackArea.y += Tile.tileSize;
			break; // down
		case 3:
			attackArea.x += Tile.tileSize;
			break; // right
		case 4:
			attackArea.x -= Tile.tileSize;
			break; // left
		}

		List<Entity> toRemove = new ArrayList<>();

		for (Entity entity : game.entities) {
			if (entity != this && attackArea.intersects(entity.getSolidArea())) {
				entity.hurt(random.nextInt(4) + 1);
				return;
			}
		}

		for (Entity entity : game.entities) {
			if (entity.dead)
				toRemove.add(entity);
		}
		game.entities.removeAll(toRemove);

		int offsetX = solidArea != null ? solidArea.x : 0;
		int offsetY = solidArea != null ? solidArea.y : 0;

		int tileCol = (attackArea.x + offsetX) / Tile.tileSize;
		int tileRow = (attackArea.y + offsetY) / Tile.tileSize;

		if (tileCol >= 0 && tileCol < game.level.w && tileRow >= 0 && tileRow < game.level.h) {
			int tileNum = game.level.tiles[tileCol][tileRow];

			if (tileNum == Level.TREE) {
				game.level.tiles[tileCol][tileRow] = Level.GRASS;
			} else if (tileNum == Level.CACTUS_SMALL || tileNum == Level.CACTUS_LARGE) {
				game.level.tiles[tileCol][tileRow] = Level.SAND;
			}
		}
	}

	public void render(Graphics g) {
		int drawX = levelX - cameraX;
		int drawY = levelY - cameraY;

		g.drawImage(image, drawX, drawY, playerSize, playerSize, null);
		if (dir == 1) {
			if (spriteNum == 1)
				image = playerSprite[0];
			if (spriteNum == 2)
				image = playerSprite[4];
		}

		if (dir == 2) {
			if (spriteNum == 1)
				image = playerSprite[1];
			if (spriteNum == 2)
				image = playerSprite[5];
		}

		if (dir == 4) {
			if (spriteNum == 1)
				image = playerSprite[2];
			if (spriteNum == 2)
				image = playerSprite[6];
		}

		if (dir == 3) {
			if (spriteNum == 1)
				image = playerSprite[3];
			if (spriteNum == 2)
				image = playerSprite[7];
		}
	}
}