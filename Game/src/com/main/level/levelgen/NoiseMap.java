package com.main.level.levelgen;

import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class NoiseMap {
	private int w, h;
	public static double[] values;

	public NoiseMap(int w, int h) {
		this.w = w;
		this.h = h;

		values = new double[w * h];

		Random random = new Random();

		int stepSize = w;
		double scale = 3.0 / w;
		do {
			int halfStep = stepSize / 2;
			for (int x = 0; x < w; x += stepSize) {
				for (int y = 0; y < h; y += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + stepSize, y + stepSize);

					double e = (a + b + c + d) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y + halfStep, e);
				}
			}

			for (int x = 0; x < w; x += stepSize) {
				for (int y = 0; y < h; y += stepSize) {
					double a = sample(x, y);
					double b = sample(x + stepSize, y);
					double c = sample(x, y + stepSize);
					double d = sample(x + halfStep, y + halfStep);
					double e = sample(x + halfStep, y - halfStep);
					double f = sample(x - halfStep, y + halfStep);

					double H = (a + b + d + e) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					double g = (a + c + d + f) / 4.0 + (random.nextFloat() * 2 - 1) * stepSize * scale;
					setSample(x + halfStep, y, H);
					setSample(x, y + halfStep, g);
				}
			}
			stepSize /= 2;
			scale *= 1.2;
		} while (stepSize > 1);
	}

	private double sample(int x, int y) {
		return values[(x & (w - 1)) + (y & (h - 1)) * w];
	}

	private void setSample(int x, int y, double value) {
		values[(x & (w - 1)) + (y & (h - 1)) * w] = value;
	}

	public int[][] toTileMap() {
	    int[][] tiles = new int[w][h];
	    
	    double min = Double.MAX_VALUE;
	    double max = Double.MIN_VALUE;

	    for (double v : values) {
	        if (v < min) min = v;
	        if (v > max) max = v;
	    }
	    double range = max - min;

	    for (int x = 0; x < w; x++) {
	        for (int y = 0; y < h; y++) {

	            int i = x + y * w;
	            double val = (values[i] - min) / range;

	            if (val < 0.20)       tiles[x][y] = 2; // WaterTile
	            else if (val < 0.25) tiles[x][y] = 2; // WaterTile
	            else if (val < 0.65) tiles[x][y] = 0; // GrassTile
	            else if (val < 0.70) tiles[x][y] = 0; // GrassTile
	            else if (val < 0.75) tiles[x][y] = 5; // SandTile
	            else if (val < 0.80) tiles[x][y] = 1; // StoneTile
	        }
	    }

	    return tiles;
	}

}