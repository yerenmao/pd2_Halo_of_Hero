package bulletPackage;

import gamePackage.GameCamera;
import gamePackage.GameObject;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;

public abstract class Bullet extends GameObject {
	
	public static final int SIZE = 24;
	public static final float SPEED = 7f;
	
	protected float x, y;
	protected float mx, my;
	protected float velx, vely;
	
	protected ImageLoader imageLoader;
	protected GameObjectController objectController;
	
	public Bullet(float x, float y, float mx, float my, ImageLoader imageLoader, GameObjectController objectController) {
		this.x = x;
		this.y = y;
		this.mx = mx;
		this.my = my;
		this.imageLoader = imageLoader;
		this.objectController = objectController;
	}
	
	protected abstract void wallCollision();
}
