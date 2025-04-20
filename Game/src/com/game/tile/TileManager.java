package com.game.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.game.main.Game;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.gfx.SpriteSheet;

public class TileManager {

	private Game game;
	private Player player;
	private Mob mob;
	public Tile[] tile;

	public int maxLevelCol;
	public int maxLevelRow;

	public int[][] mapTileNum;
	public int[][] grassDecoMap;

	public int xScroll, yScroll;

	public TileManager(Game game, Player player) {
		this.game = game;
		this.player = player;

		tile = new Tile[10];
		loadTile();
		try {
			loadLevel("/level/world01.txt");
			applyDecorations();
			generateStructures();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadTile() {
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

	public void loadLevel(String path) throws IOException {
		InputStream is = getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		List<String> lines = new ArrayList<>();
		String line;
		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		br.close();

		maxLevelRow = lines.size();
		if (maxLevelRow > 0) {
			maxLevelCol = lines.get(0).split(" ").length;
			mapTileNum = new int[maxLevelCol][maxLevelRow];
			grassDecoMap = new int[maxLevelCol][maxLevelRow];
			for (int row = 0; row < maxLevelRow; row++) {
				String[] numbers = lines.get(row).split(" ");
				for (int col = 0; col < maxLevelCol; col++) {
					if (col < numbers.length) {
						mapTileNum[col][row] = Integer.parseInt(numbers[col]);
					}
				}
			}
		}
		System.out.println("World map loaded. Rows: " + maxLevelRow + ", Columns: " + maxLevelCol);
	}

	public void applyDecorations() {
		if (tile[0] instanceof GrassTile) {
			GrassTile grassTile = (GrassTile) tile[0];
			for (int row = 0; row < maxLevelRow; row++) {
				for (int col = 0; col < maxLevelCol; col++) {
					if (mapTileNum[col][row] == 0) {
						grassDecoMap[col][row] = grassTile.deco(col, row);
					}
				}
			}
		}
	}

	public void generateStructures() {
		if (tile[0] instanceof GrassTile) {
			TreeTile treeTile = (TreeTile) tile[4];
			for (int row = 0; row < maxLevelRow; row++) {
				for (int col = 0; col < maxLevelCol; col++) {
					int worldX = Tile.tileSize * col;
					int worldY = Tile.tileSize * row;
					int radius = Tile.tileSize * 1;
					if (mapTileNum[col][row] == 0) {
						if (grassDecoMap[col][row] == 0) {
							if (!(worldX >= player.levelX - radius && worldX < player.levelX + +radius
									&& worldY >= player.levelY - radius && worldY < player.levelY + +radius))
								mapTileNum[col][row] = treeTile.randomGenerate(col, row);
						}
					}
				}
			}
		}
		if (tile[5] instanceof SandTile) {
			CactusTile cactusTile = (CactusTile) tile[6];
			SmallCactusTile smallCactusTile = (SmallCactusTile) tile[7];
			for (int row = 0; row < maxLevelRow; row++) {
				for (int col = 0; col < maxLevelCol; col++) {
					if (mapTileNum[col][row] == 5) {
						Random random = new Random();
						int chance = random.nextInt(2) + 1;
						if (chance == 1) {
							mapTileNum[col][row] = cactusTile.randomGen(col, row);
						} else if (chance == 2) {
							mapTileNum[col][row] = smallCactusTile.randomGen(col, row);
						}
					}
				}
			}
		}
	}

	public void tick() {
		for (Tile t : tile) {
			if (t != null) {
				t.tick();
			}
		}
	}

	public void render(Graphics g) {
		for (int col = 0; col < maxLevelCol; col++) {
			for (int row = 0; row < maxLevelRow; row++) {
				int tileNum = mapTileNum[col][row];

				int levelX = col * Tile.tileSize;
				int levelY = row * Tile.tileSize;
				int screenX = levelX - player.cameraX;
				int screenY = levelY - player.cameraY;

				if (screenX + Tile.tileSize > -Tile.tileSize && screenX < game.WIDTH + Tile.tileSize
						&& screenY + Tile.tileSize > -Tile.tileSize && screenY < game.HEIGHT + Tile.tileSize) {

					if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null) {
						g.drawImage(tile[tileNum].tileImage, screenX, screenY, Tile.tileSize, Tile.tileSize, null);
						if (tileNum == 0 && tile[0] instanceof GrassTile) {
							int decoType = grassDecoMap[col][row];
							((GrassTile) tile[0]).renderDeco(g, screenX, screenY, decoType);
						} else if (tileNum == 2 && tile[2] instanceof WaterTile) {
							((WaterTile) tile[2]).render(g, screenX, screenY);
						} else if (tileNum == 5 && tile[5] instanceof SandTile) {
							int obsType = mapTileNum[col][row];
							((CactusTile) tile[6]).generation(g, screenX, screenY, obsType);
						}
					}
				}
			}
		}
	}
}