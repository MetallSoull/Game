package com.game.main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.game.entity.Entity;
import com.game.entity.Mob;
import com.game.entity.Player;
import com.game.gfx.ImageLoader;
import com.game.gfx.SpriteSheet;
import com.game.tile.CollisionChecker;
import com.game.tile.Tile;
import com.game.tile.TileManager;
import com.main.level.*;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 1080;
	public static final int WIDTH = HEIGHT * 16 / 9;
	private static final String NAME = "Game";
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;

	private Thread gameThread;
	private boolean running;

	public CollisionChecker cChecker = new CollisionChecker(this);
	public KeyHandler keyH = new KeyHandler(this);
	public TileManager tileM;
	public Player player;
	public Mob mob;	
	public Entity entity;
	public Level level;
	public List<Entity> entities = new ArrayList<>();

	public int gridCellSize = Tile.tileSize * 4;
	public List<Entity>[][] entityGrid;
	
	private List<Entity> entitiesToAdd = new ArrayList<>();
	private List<Entity> entitiesToRemove = new ArrayList<>();

	public int FPS = 240;
	public int TICKS;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void init() throws IOException {
		ImageLoader loader = new ImageLoader();
		spriteSheet = loader.loadImage("/sheet/icons.png"); // MUST be non-null

		if(spriteSheet == null) {
		    throw new RuntimeException("Failed to load sprite sheet!");
		}
		
		level = new Level(this, 512, 512);
		player = new Player(this, keyH);
		tileM = new TileManager(this, player);
		
		int colCalc = (int) Math.ceil((double) level.w / 4);
		int rowCalc = (int) Math.ceil((double) level.h / 4);
		entityGrid = new ArrayList[colCalc][rowCalc];
		for (int x = 0; x < entityGrid.length; x++) {
			for (int y = 0; y < entityGrid[x].length; y++) {
				entityGrid[x][y] = new ArrayList<>();
			}
		}
		entities.add(player);
	}

	public void start() {
		running = true;
		if (running) {
			gameThread = new Thread(this);
			gameThread.start();
		}
	}

	public void stop() {
		running = false;
		if (!running) {
			try {
				gameThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {

		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int ticks = 0;
		int frames = 0;

		long currentTime;
		long lastTime = System.nanoTime();
		double delta = 0;
		double nsPerTick = 1000000000.0 / FPS;

		long lastTimer = System.currentTimeMillis();

		while (running) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / nsPerTick;
			lastTime = currentTime;

			while (delta >= 1) {
				tick();
				ticks++;
				delta--;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(frames + " frames, " + ticks + " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void updateEntityGrid() {
		for (int x = 0; x < entityGrid.length; x++) {
			for (int y = 0; y < entityGrid[x].length; y++) {
				entityGrid[x][y].clear();
			}
		}

		for (Entity entity : entities) {

			int entityGridX = (entity.levelX + entity.solidArea.x) / gridCellSize;
			int entityGridY = (entity.levelY + entity.solidArea.y) / gridCellSize;
			if (entityGridX >= 0 && entityGridX < entityGrid.length && entityGridY >= 0
					&& entityGridY < entityGrid[entityGridX].length) {
				entityGrid[entityGridX][entityGridY].add(entity);
			}
		}
	}

	private void tick() {
	    tileM.tick();
	    updateEntityGrid();

	    for (Entity entity : entities) {
	        entity.tick();
	    }

	    entities.addAll(entitiesToAdd);
	    entities.removeAll(entitiesToRemove);

	    entitiesToAdd.clear();
	    entitiesToRemove.clear();
	}

	private void render() {

		if (image == null) {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		}

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		Graphics gImage = image.getGraphics();
		gImage.setColor(Color.GRAY);
		gImage.fillRect(0, 0, WIDTH, HEIGHT);

		tileM.render(gImage, level, player);
		for (Entity entity : entities) {
			entity.render(gImage);
		}

		int xo = (getWidth() - WIDTH) / 2;
		int yo = (getHeight() - HEIGHT) / 2;
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, xo, yo, WIDTH, HEIGHT, null);

		g.dispose();
		bs.show();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public static void main(String args[]) {

		Game game = new Game();

		JFrame frame = new JFrame();
		frame.setTitle(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}
}