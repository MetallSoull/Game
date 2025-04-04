package tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import gfx.SpriteSheet;
import main.Game;

public class GrassTile extends Tile {

    private static Game game;
    
    public BufferedImage grassTile, tallGrass1, tallGrass2, flowers1, flowers2, rocks;
    public int[][] mapTileNum;

    public GrassTile(Game game) {
    	super(new SpriteSheet(game.getSpriteSheet()).grabImage(1, 5, 16, 16));
    	System.out.println("GrassTile loading image is " + (tileImage != null ? "successfull" : "failed "));
    	this.game = game;
        this.mapTileNum = new int[screenCol][screenRow]; 
        
        grassTile = tileImage;
        getTile();
    }

   public void getTile() {
        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
        
        tallGrass1 = ss.grabImage(1, 6, 16, 16);
        tallGrass2 = ss.grabImage(2, 6, 16, 16);
        flowers1 = ss.grabImage(3, 6, 16, 16);
        flowers2 = ss.grabImage(4, 6, 16, 16);
        rocks = ss.grabImage(5, 6, 16, 16);
    }

    public void deco(int col, int row) {
    	
    	int chance = new Random().nextInt(100);
    	if(chance < 40) {
            int randDeco = new Random().nextInt(5) + 1; 
            mapTileNum[col][row] = randDeco;
        }
    }

    @Override
    public void render(Graphics g, int xt, int yt) {
    	g.drawImage(grassTile, xt, yt, tileSize, tileSize, null);
    	
        int decoration = mapTileNum[xt / tileSize][yt / tileSize];

        if(decoration == 1) g.drawImage(tallGrass1, xt, yt, tileSize, tileSize, null);
        else if(decoration == 2) g.drawImage(tallGrass2, xt, yt, tileSize, tileSize, null);   
        else if(decoration == 3) g.drawImage(flowers1, xt, yt, tileSize, tileSize, null);   
        else if(decoration == 4) g.drawImage(flowers2, xt, yt, tileSize, tileSize, null);   
        else if(decoration == 5) g.drawImage(rocks, xt, yt, tileSize, tileSize, null);   
    }
}