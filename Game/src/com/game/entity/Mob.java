package com.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;
import com.game.tile.Tile;

public class Mob extends Entity {

	private Random random;
	private BufferedImage image;
	private Game game;
	private int moveTimer = 0;
	private int moveInterval = 50;

	private Rectangle nextRect;

	public Mob(Game game) {
		super(game);
		this.game = game;
		random = new Random();

		collision = true;

		solidArea = new Rectangle();
		solidArea.x = 16;
		solidArea.y = 20;
		solidArea.width = 15;
		solidArea.height = 20;

		getSprites();
	}

	private void getSprites() {
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

		playerSprite[0] = ss.grabImage(1, 1, 16, 16);
		playerSprite[1] = ss.grabImage(2, 1, 16, 16);
		playerSprite[2] = ss.grabImage(3, 1, 16, 16);
		playerSprite[3] = ss.grabImage(4, 1, 16, 16);
		playerSprite[4] = ss.grabImage(5, 1, 16, 16);
		playerSprite[5] = ss.grabImage(6, 1, 16, 16);
		playerSprite[6] = ss.grabImage(7, 1, 16, 16);
		playerSprite[7] = ss.grabImage(8, 1, 16, 16);
	}

	public void setDefaultValues() {
		playerSize = 48;
		playerSpeed = 1;
		health = 10;

		int randomX, randomY, tileNum;
		boolean validSpawn = false;

		detectedEntityOX = game.player.levelX + (levelX - (playerSize / 2));
		detectedEntityOY = game.player.levelY + (levelY - (playerSize / 2));

		while (!validSpawn) {
			randomX = random.nextInt(game.level.w) * Tile.tileSize;
			randomY = random.nextInt(game.level.h) * Tile.tileSize;

			int mobLeftCol = randomX / Tile.tileSize;
			int mobTopRow = randomY / Tile.tileSize;

			if (mobLeftCol >= 0 && mobLeftCol < game.level.w && mobTopRow >= 0 && mobTopRow < game.tileM.game.level.h) {
				mobLeftCol = Math.max(0, Math.min(mobLeftCol, game.level.w - 1));
				mobTopRow = Math.max(0, Math.min(mobTopRow, game.level.h - 1));

				tileNum = game.level.tiles[mobLeftCol][mobTopRow];

				if (tileNum >= 0 && tileNum < game.tileM.tile.length) {
					if (!game.tileM.tile[tileNum].collision) {
						Rectangle spawnRect = new Rectangle(randomX + solidArea.x, randomY + solidArea.y,
								solidArea.width, solidArea.height);

						boolean overlap = false;

						for (Entity entity : game.entities) {
							if (entity == this)
								continue;

							Rectangle entityRect = new Rectangle(entity.levelX + entity.solidArea.x,
									entity.levelY + entity.solidArea.y, entity.solidArea.width,
									entity.solidArea.height);

							if (spawnRect.intersects(entityRect)) {
								overlap = true;
								break;
							}
						}

						if (!overlap) {
							levelX = randomX;
							levelY = randomY;
							validSpawn = true;
						}
					}
				}
			}
		}

		dir = random.nextInt(4) + 1;
	}

	public void hurt(int damage) {
		health -= damage;
		if (health <= 0) {
			die();
		}
	}

	public void die() {
		dead = true;
	}

	public Rectangle getSolidArea() {
		return new Rectangle(levelX + solidArea.x, levelY + solidArea.y, solidArea.width, solidArea.height);
	}

	public void tick() {
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

		moveTimer++;

		if (moveTimer >= moveInterval) {
			moveTimer = 0;
			int chance = random.nextInt(100);
			if (chance < 20) {
				int action = random.nextInt(5);
				if (action == 0)
					dir = 1;
				if (action == 1)
					dir = 2;
				if (action == 2)
					dir = 4;
				if (action == 3)
					dir = 3;
				if (action == 4)
					dir = 5;
			}
		}

		if (dir != 0) {

			if (dir == 1)
				ya--;
			if (dir == 2)
				ya++;
			if (dir == 3)
				xa++;
			if (dir == 4)
				xa--;

			int moveSpeed = playerSpeed;

			nextRect = new Rectangle(levelX + solidArea.x + xa * moveSpeed, levelY + solidArea.y + ya * moveSpeed,
					solidArea.width, solidArea.height);

			collisionOn = false;
			game.cChecker.checkTile(this);
			game.cChecker.checkEntity(this, game.entities, nextRect);

			if (!collisionOn) {
				levelX += xa * moveSpeed;
				levelY += ya * moveSpeed;
			} else {
				moveTimer++;
				if (moveTimer >= moveInterval) {
					moveTimer = 0;
					int action = random.nextInt(5);
					if (action == 0)
						dir = 1;
					if (action == 1)
						dir = 2;
					if (action == 2)
						dir = 4;
					if (action == 3)
						dir = 3;
					if (action == 4)
						dir = 5;
				}
			}

			if (levelX < -(playerSize / 2)) {
				levelX = -(playerSize / 2);
			}
			if (levelY < -(playerSize / 2)) {
				levelY = -(playerSize / 2);
			}

			spriteCounter++;
			if (spriteCounter % 20 == 0) {
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
		}
	}

	public void render(Graphics g) {
		int screenX = levelX - game.player.cameraX;
		int screenY = levelY - game.player.cameraY;
		if (screenX + playerSize > -Tile.tileSize && screenX < game.WIDTH + Tile.tileSize
				&& screenY + playerSize > -Tile.tileSize && screenY < game.HEIGHT + Tile.tileSize) {

			g.drawImage(image, screenX, screenY, playerSize, playerSize, null);

			if (dir == 2) {
				if (spriteNum == 1)
					image = playerSprite[0];
				if (spriteNum == 2)
					image = playerSprite[1];
			}
			if (dir == 1) {
				if (spriteNum == 1)
					image = playerSprite[2];
				if (spriteNum == 2)
					image = playerSprite[3];
			}
			if (dir == 3) {
				if (spriteNum == 1)
					image = playerSprite[4];
				if (spriteNum == 2)
					image = playerSprite[5];
			}
			if (dir == 4) {
				if (spriteNum == 1)
					image = playerSprite[6];
				if (spriteNum == 2)
					image = playerSprite[7];
			}
		}
	}
}