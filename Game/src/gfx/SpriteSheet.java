package gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int w, int h) {
		BufferedImage img = image.getSubimage((col * 16) - 16, (row * 16) - 16, w, h);
		return img;
	}
}
