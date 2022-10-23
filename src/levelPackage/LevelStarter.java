package levelPackage;

import gamePackage.GameCamera;
import gamePackage.GameMain;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import imagePackage.ImageLoader;
import playerPackage.Player;
import themePackage.Game;
import themePackage.Game.GameLevel;

public class LevelStarter {
	
	private GameMain gameMain;
	private GameObjectController objectController;
	private Player player;
	private GameCamera camera;
	private ImageLoader imageLoader;
	
	public LevelStarter(GameMain gameMain, GameObjectController objectController, Player player, GameCamera camera, ImageLoader imageLoader) {
		this.gameMain = gameMain;
		this.objectController = objectController;
		this.player = player;
		this.camera = camera;
		this.imageLoader = imageLoader;
	}
	
	public void startLevel1() {
		gameMain.setCamera(new GameCamera(0, 0));
		player.reset();
		objectController.addPlayer(player);
		Game.gameLevel = GameLevel.Level1;
		LevelLoader.loadLevel(imageLoader, objectController, player);
	}
	
	public void startNextLevel() {
		switch(Game.gameLevel) {
			case Level1: startLevel2(); break;
			case Level2: startLevel3(); break;
			case Level3: startLevel4(); break;
			case Level4: startLevelShop(); break;
			case LevelShop: startLevel5(); break;
		default:
			break;
		}
	}
	
	private void startLevel2() {
		Game.gameLevel = GameLevel.Level2;
		LevelLoader.loadLevel(imageLoader, objectController, player);
	}
	
	private void startLevel3() {
		Game.gameLevel = GameLevel.Level3;
		LevelLoader.loadLevel(imageLoader, objectController, player);
	}
	
	private void startLevel4() {
		Game.gameLevel = GameLevel.Level4;
		LevelLoader.loadLevel(imageLoader, objectController, player);
	}
	
	private void startLevelShop() {
		LevelLoader.loadLevelShop(imageLoader, objectController, player);
		Game.gameLevel = GameLevel.LevelShop;
	}
	
	private void startLevel5() {
		Game.gameLevel = GameLevel.Level5;
		LevelLoader.loadLevel(imageLoader, objectController, player);
	}
	
	public void startLevelPlatformer() {
		LevelLoader.loadLevelPlatformer(imageLoader, objectController, player, camera);
	}
}
