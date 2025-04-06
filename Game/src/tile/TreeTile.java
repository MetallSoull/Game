package tile;

import java.awt.Graphics;
import java.util.Random;
import gfx.SpriteSheet;
import main.Game;

public class TreeTile extends Tile{
	
	private Random random = new Random();

	public TreeTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(4, 5, 16, 16));
		System.out.println("TreeTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
		this.game = game;

		collision = true;
	}
	
	@Override
	public void render(Graphics g, int xt, int yt) {
		g.drawImage(tileImage, xt, yt, tileSize, tileSize, null);
	}
	
	public int randomGenerate(int col, int row) {
		int chance = random.nextInt(100);
		if(chance < 25) {
			 return 4;
		}
		return 0;
	}
}
