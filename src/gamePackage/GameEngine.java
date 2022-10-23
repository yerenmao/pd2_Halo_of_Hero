package gamePackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import imagePackage.ImageLoader;
import playerPackage.Player;
import themePackage.Lost;
import themePackage.Game;
import themePackage.Help;
import themePackage.Menu;
import themePackage.Start;

public class GameEngine implements Runnable{
	
	private static int fps;
	
	private static boolean running = false;
	private static final double amountOfTicks = 60.0;
	
	private GameMain gameMain;
	private Thread thread;
	
	private Start start;
	private Menu menu;
	private Help help;
	private Game game;
	private Lost dead;
	
	public GameEngine(GameMain gameMain) {
		this.gameMain = gameMain;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				gameMain.tick();
				delta--;
			}
			gameMain.render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				frames = 0;
			}
		}
		stop();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getFps() {
		return fps;
	}

}
