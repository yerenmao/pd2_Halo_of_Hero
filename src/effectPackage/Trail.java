package effectPackage;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamePackage.GameObject;
import gamePackage.GameObjectController;

public class Trail {
	
	private int dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2;
	private float alpha = 1;
	private float life;
	private GameObjectController objectController;
	private BufferedImage image;
	
	public Trail(float life, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, GameObjectController objectController, BufferedImage image) {
		this.dx1 = dx1;
		this.dy1 = dy1;
		this.dx2 = dx2;
		this.dy2 = dy2;
		this.sx1 = sx1;
		this.sy1 = sy1;
		this.sx2 = sx2;
		this.sy2 = sy2;
		this.life = life;
		this.objectController = objectController;
		this.image = image;
	}

	public void tick() {
		if(alpha > life) {
			alpha -= life;
		} else {
			objectController.removeTrail(this);
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		g2d.setComposite(makeTransparent(1));
	}
	
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}
}
