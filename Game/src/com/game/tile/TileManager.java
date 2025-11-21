package com.game.tile;

import java.awt.Graphics;

import com.game.entity.Player;
import com.game.gfx.SpriteSheet;
import com.game.main.Game;
import com.main.level.Level;

public class TileManager {

    public Tile[] tile;
    public Game game;

    public TileManager(Game game, Player player) {
    	this.game = game;
        loadTile();
    }
    
    public void loadTile() {
        tile = new Tile[10]; 
        SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());

        tile[0] = new GrassTile(game, ss);
        tile[1] = new StoneTile(game);
        tile[2] = new WaterTile(game, ss);
        tile[3] = new RoadTile(game);
        tile[4] = new TreeTile(game);
        tile[5] = new SandTile(game);
        tile[6] = new CactusTile(game, ss);
        tile[7] = new SmallCactusTile(game, ss);
    }

    
	public void tick() {
		for (Tile t : tile) {
	        if (t != null) {
	            t.tick();
	        }
	    }
	}

	public void render(Graphics g, Level level, Player player) {

	    // Calculate visible tile range
	    int startX = Math.max(0, player.cameraX / Tile.tileSize);
	    int startY = Math.max(0, player.cameraY / Tile.tileSize);
	    int endX = Math.min(level.w, (player.cameraX + Game.WIDTH) / Tile.tileSize + 1);
	    int endY = Math.min(level.h, (player.cameraY + Game.HEIGHT) / Tile.tileSize + 1);

	    for (int x = startX; x < endX; x++) {
	        for (int y = startY; y < endY; y++) {
	            int tileId = level.getTile(x, y);
	            Tile t = tile[tileId];

	            int screenX = x * Tile.tileSize - player.cameraX;
	            int screenY = y * Tile.tileSize - player.cameraY;

	            if (t != null) {
	                t.render(g, screenX, screenY);
	            }
	        }
	    }
	}

}
