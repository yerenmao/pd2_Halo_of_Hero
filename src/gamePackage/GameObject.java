package gamePackage;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
}
