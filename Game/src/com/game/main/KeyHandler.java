package com.game.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private Game game;

	public boolean up, down, left, right;
	public boolean attack;
	public boolean attackO;

	public KeyHandler(Game game) {
		this.game = game;
		game.addKeyListener(this);
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			up = true;
		if (key == KeyEvent.VK_S)
			down = true;
		if (key == KeyEvent.VK_A)
			left = true;
		if (key == KeyEvent.VK_D)
			right = true;
		if (key == KeyEvent.VK_UP)
			up = true;
		if (key == KeyEvent.VK_DOWN)
			down = true;
		if (key == KeyEvent.VK_LEFT)
			left = true;
		if (key == KeyEvent.VK_RIGHT)
			right = true;

		if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_C) && !attack) {
			attackO = true; 
			attack = true;  
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			up = false;
		if (key == KeyEvent.VK_S)
			down = false;
		if (key == KeyEvent.VK_A)
			left = false;
		if (key == KeyEvent.VK_D)
			right = false;

		if (key == KeyEvent.VK_UP)
			up = false;
		if (key == KeyEvent.VK_DOWN)
			down = false;
		if (key == KeyEvent.VK_LEFT)
			left = false;
		if (key == KeyEvent.VK_RIGHT)
			right = false;

		if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_C) {
			attackO = false;
			attack = false;
		}
	}
}