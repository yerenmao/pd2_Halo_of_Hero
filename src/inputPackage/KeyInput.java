package inputPackage;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gamePackage.GameMain;
import gamePackage.GameObjectController;
import gamePackage.GameState;
import playerPackage.Player;
import themePackage.Game;
import themePackage.Game.GameMode;

public class KeyInput extends KeyAdapter {
	
	private Game gameTheme;
	private Player player;
	private GameObjectController objectController;
	
	public KeyInput(Game gameTheme, Player player, GameObjectController objectController) {
		this.gameTheme = gameTheme;
		this.player = player;
		this.objectController = objectController;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(GameMain.state == GameState.Game) {
			gameKeyPressed(key);
		} else if(GameMain.state == GameState.DressingRoom) {
			dressingRoomKeyPressed(key);
		} else if(GameMain.state == GameState.Start) {
			if(key == KeyEvent.VK_SPACE) GameMain.state = GameState.Menu;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(GameMain.state == GameState.Game) {
			gameKeyReleased(key);
		} else if(GameMain.state == GameState.DressingRoom) {
			dressingRoomKeyReleased(key);
		}
	}
	
	private void gameKeyPressed(int key) {
		WASDPressed(key);
		if(key == KeyEvent.VK_SPACE) {
			if(Game.gameMode == Game.GameMode.normalMode) {
				gameTheme.pauseGame();
			} else if(Game.gameMode == Game.GameMode.platformerMode) {
				player.setJump(true);
			}
		}
	}
	
	private void gameKeyReleased(int key) {
		WASDReleased(key);
		if(key == KeyEvent.VK_SPACE) player.setJump(false);
	}
	
	private void dressingRoomKeyPressed(int key) {
		WASDPressed(key);
	}
	
	private void dressingRoomKeyReleased(int key) {
		WASDReleased(key);
	}
	
	private void WASDPressed(int key) {
		if(key == KeyEvent.VK_W) player.setUp(true);
		if(key == KeyEvent.VK_A) player.setLeft(true);
		if(key == KeyEvent.VK_S) player.setDown(true);
		if(key == KeyEvent.VK_D) player.setRight(true);
	}
	
	private void WASDReleased(int key) {
		if(key == KeyEvent.VK_W) player.setUp(false);
		if(key == KeyEvent.VK_A) player.setLeft(false);
		if(key == KeyEvent.VK_S) player.setDown(false);
		if(key == KeyEvent.VK_D) player.setRight(false);
	}
}
