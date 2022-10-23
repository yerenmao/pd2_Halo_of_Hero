package pickUpPackage;

import java.awt.Graphics;
import java.awt.Rectangle;

import gamePackage.GameObject;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public abstract class PickUp extends GameObject {
	
	protected int x, y;
	public static final int WIDTH = 48, HEIGHT = 48;
	protected ImageLoader imageLoader;
	protected GameObjectController objectController;
	
	public PickUp(int x, int y, ImageLoader imageLoader, GameObjectController objectController) {
		this.x = x;
		this.y = y;
		this.imageLoader = imageLoader;
		this.objectController = objectController;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
}
