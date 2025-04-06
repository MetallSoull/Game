package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public int playerSize;
	public int playerSpeed;
	
	public int spriteNum = 1;
	public int spriteCounter = 0;
	
	public boolean collisionOn;
	public Rectangle solidArea;
	
	public String direction;
	
	public BufferedImage slime_up, slime_up1, slime_down, slime_down1, slime, slime1, slime_left, slime_left1, slime_blink, slime_left_blink, slime_down_blink;
	
}