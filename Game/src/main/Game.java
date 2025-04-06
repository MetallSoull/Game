package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

import entity.Player;
import gfx.ImageLoader;
import gfx.SpriteSheet;
import tile.TileManager;

public class Game extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 480;
	public static final int WIDTH = 816;
	private static final String NAME = "Game";
	private BufferedImage spriteSheet = null;
	
	private Thread gameThread;
	private boolean running;
	
	public KeyHandler keyH = new KeyHandler(this);
	public TileManager tileM;
	public Player player;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void init() throws IOException{
		ImageLoader loader = new ImageLoader();
		spriteSheet = loader.loadImage("/sheet/icons.png");
		player = new Player(this, keyH);
		tileM = new TileManager(this, player);
	}
	
	public void start() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void stop() {
		running = false;
		try {
		    gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		double nsPerTick = 1000000000.0 / 144.0;
		
		long lastTimer = System.currentTimeMillis();
		
		while(running) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / nsPerTick;
			lastTime = currentTime;
			
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(frames + " frames, " + ticks + " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void tick() {
		player.tick();
		tileM.tick();
	}
	
	private void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		tileM.render(g);
		player.render(g);
		
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
		CenterPanel centerPanel = new CenterPanel();	
		centerPanel.add(game);
		frame.setLayout(new BorderLayout());
		frame.add(centerPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
}