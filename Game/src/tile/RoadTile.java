package tile;

import gfx.SpriteSheet;
import main.Game;

public class RoadTile extends Tile {
	
	private Game game;
	
	public RoadTile(Game game) {
		super(new SpriteSheet(game.getSpriteSheet()).grabImage(3, 5, 16, 16));
		System.out.println("RoadTile loading image is " + (tileImage != null ? "successfull" : "Failed"));
		this.game = game;
	}
}