package gamePackage;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public GameWindow(int width, int height, String title, GameMain game) {
		
		this.setTitle(title);
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		
		this.add(game);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.requestFocus();
		
		this.setVisible(true);
	}
}
