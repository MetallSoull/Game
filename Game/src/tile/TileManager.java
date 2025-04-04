package tile;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.Game;
import entity.Entity;

public class TileManager {
	
	private Game game;
	private Entity entity = new Entity();
	private Tile[] tile;
	
	private WaterTile waterTile;
	private StoneTile stoneTile;
	
	public int maxScreenCol;
	public int maxScreenRow;
	public int[][] mapTileNum;
	
	public TileManager(Game game) {
		this.game = game;		
		
		maxScreenCol = game.WIDTH / Tile.tileSize;
		maxScreenRow = game.HEIGHT / Tile.tileSize;
		tile = new Tile[10];
		mapTileNum = new int[maxScreenCol][maxScreenRow];
			
		loadTile();
		try {
			loadLevel("/level/test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadTile() {
		tile[0] = new GrassTile(game);
		tile[1] = new WaterTile(game);
		tile[2] = new StoneTile(game);
		tile[3] = new RoadTile(game);
	}
	
	public void loadLevel(String path) throws IOException {
	    InputStream is = getClass().getResourceAsStream(path);
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));

	    for (int row = 0; row < maxScreenRow; row++) {
	        String line = br.readLine();
	        String[] numbers = line.split(" ");
	        for (int col = 0; col < maxScreenCol; col++) {
	            int num = Integer.parseInt(numbers[col]);
	            mapTileNum[col][row] = num;

	            if(num == 0 && tile[0] instanceof GrassTile) {
	            	((GrassTile) tile[0]).deco(col, row);
	            }
	            if(num == 1 && tile[1] instanceof WaterTile) {
	            	((WaterTile) tile[1]).tick();
	            }
	        }
	    }
	    br.close();
	}
	
	public void tick() {
		for(Tile t : tile) {
			if(t != null) {
				t.tick();
			}
		}
	}
	
	public void render(Graphics g) {
		for(int col = 0; col < maxScreenCol; col++) {
			for(int row = 0; row < maxScreenRow; row++) {
				int tileNum = mapTileNum[col][row];
				if(tileNum >= 0 && tile[tileNum] != null) {
					tile[tileNum].render(g, col * Tile.tileSize, row * Tile.tileSize); 
				}
			}
		}
	}
}