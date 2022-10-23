package gamePackage;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import imagePackage.ImageLoader;
import inputPackage.KeyInput;
import inputPackage.MouseInput;
import inputPackage.WindowInput;
import levelPackage.LevelStarter;
import musicPackage.MusicPlayer;
import playerPackage.Player;
import themePackage.DressingRoom;
import themePackage.Game;
import themePackage.Help;
import themePackage.Lost;
import themePackage.Menu;
import themePackage.Setting;
import themePackage.Start;
import themePackage.Upgrade;
import themePackage.Won;

public class GameMain extends Canvas {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1000, HEIGHT = 800;
	public static GameState state = GameState.Start;
	
	private GameWindow gameWindow;
	private GameSetting gameSetting;
	private GameObjectController objectController;
	private GameEngine gameEngine;
	private LevelStarter levelStarter;
	private ImageLoader imageLoader;
	private Player player;
	
	private Game gameTheme;
	private Menu menuTheme;
	private Setting settingTheme;
	private Help helpTheme;
	private Won wonTheme;
	private Lost deadTheme;
	private Start startTheme;
	private DressingRoom dressingRoomTheme;
	
	private GameCamera camera;
	
	private GameMain() {
		
		// new Setting
		gameSetting = new GameSetting();
		
		// set
		gameSetting.set();
		
		// load game pictures
		imageLoader = new ImageLoader();
		imageLoader.loadImage();
		
		// new object controller
		objectController = new GameObjectController();
		
		// new camera;
		camera = new GameCamera(0, 0);
		
		// new game engine
		gameEngine = new GameEngine(this);
		
		// new player
		player = new Player(WIDTH/2, HEIGHT/2, imageLoader, objectController);
		
		// new level starter
		levelStarter = new LevelStarter(this, objectController, player, camera, imageLoader);

		// load theme
		loadTheme();
		
		// get game control
		player.getGameControl(gameTheme);
		
		
		this.addKeyListener(new KeyInput(gameTheme, player, objectController));
		this.addMouseListener(new MouseInput(gameTheme, player, camera, levelStarter, gameSetting, objectController));
		
		gameWindow = new GameWindow(WIDTH, HEIGHT, "HALO OF HERO", this);
		gameWindow.addWindowListener(new WindowInput(gameSetting));
		
		gameEngine.start();
		
		MusicPlayer.playMenuBGMSound();
		
//		while(true) {
//			SoundLoader.playBGM();
//		}
	}
	
	public void setCamera(GameCamera camera) {
		this.camera = camera;
	}
	
	public void tick() {
		if(GameMain.state == GameState.Game) {
			gameTheme.tick();
		} else if(GameMain.state == GameState.Menu) {
			menuTheme.tick();
		} else if(GameMain.state == GameState.DressingRoom) { 
			dressingRoomTheme.tick();
	    } else if(GameMain.state == GameState.Setting){ 
	    	
	    } else if(GameMain.state == GameState.Help) {
			
		} else if(GameMain.state == GameState.Won) { 
		
		} else if(GameMain.state == GameState.Lost) {
			
		} else if(GameMain.state == GameState.Start) {
			startTheme.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		if(GameMain.state == GameState.Game) {
			gameTheme.render(g);
		} else if(GameMain.state == GameState.Menu) {
			menuTheme.render(g);
		} else if(GameMain.state == GameState.DressingRoom) {
			dressingRoomTheme.render(g);
		} else if(GameMain.state == GameState.Setting) {
			settingTheme.render(g);
		} else if(GameMain.state == GameState.Help) {
			helpTheme.render(g);
		} else if(GameMain.state == GameState.Won) { 
			wonTheme.render(g);
		} else if(GameMain.state == GameState.Lost) { 
			deadTheme.render(g);
		} else if(GameMain.state == GameState.Start) {
			startTheme.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	private void loadTheme() {
		gameTheme = new Game(imageLoader, objectController, player, camera, levelStarter);
		menuTheme = new Menu(player, imageLoader);
		settingTheme = new Setting(imageLoader);
		helpTheme = new Help(imageLoader);
		wonTheme = new Won(imageLoader);
		deadTheme = new Lost(imageLoader);
		startTheme = new Start(imageLoader);
		dressingRoomTheme = new DressingRoom(player, imageLoader);
	}
	
	public static void main(String[] args) {
		new GameMain();
	}
}
