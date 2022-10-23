package effectPackage;

import java.awt.AlphaComposite;

public class Transparent {
	
	public static AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}
}
