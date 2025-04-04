package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


import gfx.SpriteSheet;
import main.Game;
import main.KeyHandler;

public class Player extends Entity {

	private Game game;
	private KeyHandler keyH;
	
	public BufferedImage image = null;

	public Player(Game game, KeyHandler keyH) {
		this.game = game;
		this.keyH = keyH;
		
		solidArea = new Rectangle(0, 0, playerSize, playerSize);
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = solidArea.y * 2;
		solidArea.height = solidArea.y * 2;
		
		getSprites();
		setDefaultValues();
	}
	
	public void getSprites() {
		SpriteSheet spriteS = new SpriteSheet(game.getSpriteSheet());
		slime = spriteS.grabImage(1, 1, 16, 16);
		slime_left = spriteS.grabImage(5, 1, 16, 16);
		slime_up = spriteS.grabImage(3, 1, 16, 16);
		slime_down = spriteS.grabImage(7, 1, 16, 16);
		
		slime_up1 = spriteS.grabImage(4, 1, 16, 16);
		slime_down1 = spriteS.grabImage(8, 1, 16, 16);
		slime_left1 = spriteS.grabImage(6, 1, 16, 16);
		slime1 = spriteS.grabImage(2, 1, 16, 16);
	}
	
	public void setDefaultValues() {
		playerSize = 48;
		
		playerX = (game.WIDTH / 2) - (playerSize / 2);
		playerY = (game.HEIGHT / 2) - (playerSize / 2);
		
		direction = "down";
	}
	
	public void tick() {
		if(keyH.up) { 
			direction = "up";
			playerY-=playerSpeed;
			if(keyH.right) {
				playerX -= 0.2;
				playerY += 0.2;
			}
			if(keyH.left) {
				playerX += 0.2;
				playerY += 0.2;
			}
			if(playerY < -playerSize) {
				playerY = game.HEIGHT;
			}
			System.out.println(playerY);
		}
		if(keyH.down) { 
			direction = "down";
			playerY+=playerSpeed;
			if(keyH.right) {
				playerX -= 0.2;
				playerY -= 0.2;
			}
			if(keyH.left) {
				playerX += 0.2;
				playerY -= 0.2;
			}
			if(playerY > game.HEIGHT) {
				playerY = -playerSize;
			}
			System.out.println(playerY);
		}
		if(keyH.right) { 
			direction = "right";
			playerX+=playerSpeed;
			if(playerX > game.WIDTH) {
				playerX = -playerSize;
			}
			System.out.println(playerX);
		}
		if(keyH.left) { 
			direction = "left";
			playerX-=playerSpeed;
			if(playerX < -playerSize) {
				playerX = game.WIDTH;
			}
			System.out.println(playerX);
		}
		
		spriteCounter++;
		if(spriteCounter > 40) {
			if(spriteNum == 1) spriteNum = 2;
			else if(spriteNum == 2) spriteNum = 1;
			spriteCounter = 0;
		}
	}
	
	public void render(Graphics g) {
		
		if(direction.equals("up")) {
			if(spriteNum == 1) image = slime_up;
			if(spriteNum == 2) image = slime_up1;
		}
		if(direction.equals("down")) {
			if(spriteNum == 1) image = slime_down;
			if(spriteNum == 2) image = slime_down1;
		}
		if(direction.equals("left")) {
			if(spriteNum == 1) image = slime_left;
			if(spriteNum == 2) image = slime_left1;
		}
		if(direction.equals("right")) {
			if(spriteNum == 1) image = slime;
			if(spriteNum == 2) image = slime1;
		}
		
		g.drawImage(image, (int)playerX, (int)playerY, playerSize, playerSize, null);
	}
}
