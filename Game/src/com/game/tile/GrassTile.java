package com.game.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.gfx.SpriteSheet;
import com.game.main.Game;

public class GrassTile extends Tile {

    private static Game game;
    
    public BufferedImage tallGrass1, tallGrass2, flowers1, flowers2, rocks;
    
    public GrassTile(Game game, SpriteSheet ss) {
    	super(ss.grabImage(1, 5, 16, 16));
    	System.out.println("GrassTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
    	this.game = game;

        getTile(ss);
    }

   public void getTile(SpriteSheet ss) {
        tallGrass1 = ss.grabImage(1, 6, 16, 16);
        tallGrass2 = ss.grabImage(2, 6, 16, 16);
        flowers1 = ss.grabImage(3, 6, 16, 16);
        flowers2 = ss.grabImage(4, 6, 16, 16);
        rocks = ss.grabImage(5, 6, 16, 16);
    }

    public int deco(int col, int row) {
    	int chance = new Random().nextInt(100);
    	if(chance < 50) {
            return new Random().nextInt(5) + 1; 
        }
    	return 0; 
    }

    @Override
    public void render(Graphics g, int xt, int yt) {
    	g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
    }
    
    public void renderDeco(Graphics g, int xt, int yt, int decoType) {
    	if(decoType == 1) g.drawImage(tallGrass1, xt, yt, tileSize, tileSize, null);
    	if(decoType == 2) g.drawImage(tallGrass2, xt, yt, tileSize, tileSize, null);
    	if(decoType == 3) g.drawImage(flowers1, xt, yt, tileSize, tileSize, null);
    	if(decoType == 4) g.drawImage(flowers2, xt, yt, tileSize, tileSize, null);
    	if(decoType == 5) g.drawImage(rocks, xt, yt, tileSize, tileSize, null);
    }
}