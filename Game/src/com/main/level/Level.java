package com.main.level;

import java.util.Random;

import com.game.entity.Player;
import com.game.main.Game;
import com.game.tile.GrassTile;
import com.game.tile.Tile;
import com.game.tile.TileManager;
import com.main.level.levelgen.NoiseMap;

public class Level {

	public int w, h;
	public int[][] tiles;
	public int[][] decorations;

	public static final int GRASS = 0;
	public static final int STONE = 1;
	public static final int WATER = 2;
	public static final int SAND = 5;
	public static final int LAVA_SHALLOW = 8;
	public static final int LAVA_DEEP = 9;
	public static final int TREE = 10;
	public static final int CACTUS_SMALL = 11;
	public static final int CACTUS_LARGE = 12;

	private Game game;
	private TileManager tileM;

	public Level(Game game, int w, int h) {
		this.game = game;
		this.w = w;
		this.h = h;
		
		new NoiseMap(w, h);
		
		tiles = new int[w][h];
		decorations = new int[w][h];

		generateWorld();
	}

	private void generateWorld() {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = x + y * w;
				
				double val = NoiseMap.values[i];
				int tileId = 0;

				if (val < 0.24)
					tileId = WATER;
				else if (val < 0.45)
					tileId = SAND;
				else if (val < 0.97)
					tileId = GRASS;
				else
					tileId = STONE;

				tiles[x][y] = tileId;
			}
		}

		applyDecorations();
	}

	private void applyDecorations() {
		Random random = new Random();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {

				if (tiles[x][y] == GRASS) {
					int chance = random.nextInt(100);

					if (chance < 50) {
						decorations[x][y] = random.nextInt(5) + 1;
					} else {
						decorations[x][y] = 0;
					}
				} else {
					decorations[x][y] = 0;
				}
			}
		}
	}

	public int getTile(int x, int y) {
		return tiles[x][y];
	}
}
