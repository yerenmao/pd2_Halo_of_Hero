package effectPackage;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] image;
	private int num;
	private int picNow = 0;
	private static final int n = 6;
	
	public Animation(BufferedImage[] image, int num) {
		this.num = num;
		this.image = image;
	}
	
	public void tick() {
		if(picNow == num*n-1)
			picNow = 0;
		picNow++;
	}
	
	public BufferedImage getImage() {
		return image[picNow/n];
	}
	
	public BufferedImage getImageNum0() {
		return image[0];
	}
	
}
