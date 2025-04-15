package com.game.entity;

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
		setDefaultValues();
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

		int randomX, randomY, tileNum;

		levelX = Tile.tileSize;
		levelY = Tile.tileSize;
		
		randomX = random.nextInt(game.tileM.maxLevelCol) * Tile.tileSize;
		randomY = random.nextInt(game.tileM.maxLevelRow) * Tile.tileSize;
		
		int mobLeftCol = randomX / Tile.tileSize;
		int mobTopRow = randomY / Tile.tileSize;

		if (mobLeftCol >= 0 && mobLeftCol < game.tileM.maxLevelCol && mobTopRow >= 0
				&& mobTopRow < game.tileM.maxLevelRow) {
			tileNum = game.tileM.mapTileNum[mobLeftCol][mobTopRow];
			if (tileNum != 2 && tileNum != 4 && tileNum != 6 && tileNum != 7 && game.tileM.maxLevelCol != 0 && game.tileM.maxLevelRow != 0) {
				levelX = randomX;
				levelY = randomY;
			} 
		}

		direction = "down";
	}

	public Rectangle getSolidArea() {
		return new Rectangle(levelX + solidArea.x, levelY + solidArea.y, solidArea.width, solidArea.height);
	}

	public void tick() {
		int xa = 0;
		int ya = 0;

		if (xa > 0)
			direction = "right";
		else if (xa < 0)
			direction = "left";
		else if (ya < 0)
			direction = "up";
		else if (ya > 0)
			direction = "down";

		moveTimer++;

		if (moveTimer >= moveInterval) {
			moveTimer = 0;
			int chance = random.nextInt(100);
			if (chance < 50) {
				int action = random.nextInt(4);
				if (action == 0)
					direction = "up";
				if (action == 1)
					direction = "down";
				if (action == 2)
					direction = "left";
				if (action == 3)
					direction = "right";
			}
		}

		if (direction != null) {

			if (direction.equals("up"))
				ya--;
			if (direction.equals("down"))
				ya++;
			if (direction.equals("right"))
				xa++;
			if (direction.equals("left"))
				xa--;

			int moveSpeed = playerSpeed;

			Rectangle nextRect = new Rectangle(levelX + solidArea.x + xa * moveSpeed,
					levelY + solidArea.y + ya * moveSpeed, solidArea.width, solidArea.height);

			collisionOn = false;
			game.cChecker.checkTile(this);
			game.cChecker.overloadedCheckTile(this, nextRect);
			game.cChecker.checkEntity(this, game.entities, nextRect);

			if (!collisionOn) {
				levelX += xa * moveSpeed;
				levelY += ya * moveSpeed;
			} else {
				moveTimer++;
				if (moveTimer >= moveInterval) {
					moveTimer = 0;
					int action = random.nextInt(4);
					if (action == 0)
						direction = "up";
					if (action == 1)
						direction = "down";
					if (action == 2)
						direction = "left";
					if (action == 3)
						direction = "right";
				}
			}

			spriteCounter++;
			if (spriteCounter % 20 == 0) {
				spriteNum = (spriteNum == 1) ? 2 : 1;
			}
		}
	}

	public void render(Graphics g) {
		int screenX = levelX - game.player.levelX + Player.screenX;
		int screenY = levelY - game.player.levelY + Player.screenY;

		if (screenX + playerSize > -Tile.tileSize && screenX < game.WIDTH + Tile.tileSize
				&& screenY + playerSize > -Tile.tileSize && screenY < game.HEIGHT + Tile.tileSize) {

			g.drawImage(image, screenX, screenY, playerSize, playerSize, null);

			if (direction.equals("down")) {
				if (spriteNum == 1)
					image = playerSprite[0];
				if (spriteNum == 2)
					image = playerSprite[1];
			}
			if (direction.equals("up")) {
				if (spriteNum == 1)
					image = playerSprite[2];
				if (spriteNum == 2)
					image = playerSprite[3];
			}
			if (direction.equals("right")) {
				if (spriteNum == 1)
					image = playerSprite[4];
				if (spriteNum == 2)
					image = playerSprite[5];
			}
			if (direction.equals("left")) {
				if (spriteNum == 1)
					image = playerSprite[6];
				if (spriteNum == 2)
					image = playerSprite[7];
			}
		}
	}
}