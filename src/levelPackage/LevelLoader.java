package levelPackage;

import java.util.Random;

import assetsPackage.Block;
import assetsPackage.Door;
import assetsPackage.Floor;
import assetsPackage.MovingWall;
import assetsPackage.Shop;
import assetsPackage.Wall;
import enemyPackage.BasicEnemy;
import enemyPackage.BossEnemy;
import enemyPackage.MultiShootingEnemy;
import enemyPackage.MultiShootingEnemyRevolve;
import enemyPackage.MultiShootingEnemySpin;
import enemyPackage.TraceEnemy;
import gamePackage.GameCamera;
import gamePackage.GameObjectController;
import imagePackage.ImageLoader;
import pickUpPackage.BulletPU;
import pickUpPackage.DimondPU;
import pickUpPackage.HealthPU;
import playerPackage.Player;
import themePackage.Game;
import themePackage.Game.GameLevel;

public class LevelLoader {
	
	private static void load(int[][] level, int x, int y, int n, ImageLoader imageLoader, GameObjectController controller, Player player) {
		int xx = x*Wall.WIDTH;
		int yy = y*Wall.HEIGHT;
		if(level[y][x] == 1) {
			controller.addWall(new Wall(xx, yy, imageLoader));
		} else if(level[y][x] == 2) {
			controller.addEnemy(new BasicEnemy(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 3) {
			controller.addEnemy(new TraceEnemy(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 4) {
			controller.addEnemy(new MultiShootingEnemy(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 5) {
			controller.addEnemy(new MultiShootingEnemySpin(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 6) {
			controller.addEnemy(new MultiShootingEnemyRevolve(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 7) {
			controller.addFloor(new Floor(xx, yy, imageLoader, 1));
		} else if(level[y][x] == 8) {
			controller.addEnemy(new BossEnemy(xx, yy, imageLoader, controller, player));
		} else if(level[y][x] == 9) {
			controller.addDoor(new Door(xx, yy, imageLoader, "Level"+n));
		}
	}
	
	private static void load(int[][] level, int n, ImageLoader imageLoader, GameObjectController controller, Player player) {
		for(int y = 0; y < 40; y++) { 
			for(int x = 0; x < 50; x++) {
				load(level, x, y, n, imageLoader, controller, player);
			}
		}
	}
	
	public static void loadLevel(ImageLoader imageLoader, GameObjectController controller, Player player) {
		
		
		if(Game.gameLevel == GameLevel.Level1) {
			load(Level1.level, 1, imageLoader, controller, player);
		} else if(Game.gameLevel == GameLevel.Level2) {
			load(Level2.level, 2, imageLoader, controller, player);
		} else if(Game.gameLevel == GameLevel.Level3) {
			load(Level3.level, 3, imageLoader, controller, player);
		} else if(Game.gameLevel == GameLevel.Level4) {
			load(Level4.level, 4, imageLoader, controller, player);
		} else if(Game.gameLevel == GameLevel.Level5) {
			load(Level5.level, 5, imageLoader, controller, player);
			
			
		} else if(Game.gameLevel == GameLevel.LevelTest) {
			for(int y = 0; y < 40; y++) { 
				for(int x = 0; x < 50; x++) {
					int xx = x*Wall.WIDTH;
					int yy = y*Wall.HEIGHT;
					if(LevelTest.leveTest[y][x] == 1) {
						controller.addWall(new Wall(xx, yy, imageLoader));
					} else if(LevelTest.leveTest[y][x] == 2) {
						controller.addEnemy(new BasicEnemy(xx, yy, imageLoader, controller, player));
					} else if(LevelTest.leveTest[y][x] == 3) {
						controller.addEnemy(new TraceEnemy(xx, yy, imageLoader, controller, player));
					} else if(LevelTest.leveTest[y][x] == 4) {
						controller.addEnemy(new MultiShootingEnemy(xx, yy, imageLoader, controller, player));
					} else if(LevelTest.leveTest[y][x] == 5) {
						controller.addDoor(new Door(xx, yy, imageLoader, "Level1"));
					} else if(LevelTest.leveTest[y][x] == 6) {
						controller.addBlock(new Block(xx, yy, imageLoader, controller, player));
					} else if(LevelTest.leveTest[y][x] == 7) {
						controller.addMovingWall(new MovingWall(xx, yy, imageLoader, controller, player));
					} 
				}
			}
		}
	}
	
	public static void loadLevelPlatformer(ImageLoader imageLoader, GameObjectController controller, Player player, GameCamera camera) {
		for(int y = 0; y < 34; y++) { 
			for(int x = 0; x < 50; x++) {
				int xx = x*Wall.WIDTH;
				int yy = y*Wall.HEIGHT;
				if(LevelPlatformer.levelPlatformer[y][x] == 1) {
					controller.addWall(new Wall(xx, yy, imageLoader));
				} else if(LevelPlatformer.levelPlatformer[y][x] == 5) {
					controller.addDoor(new Door(xx, yy, imageLoader, "LevelPlatformer"));
				} else if(LevelPlatformer.levelPlatformer[y][x] == 9) {
					Random r = new Random();
					switch(r.nextInt(4)) {
					case 0: controller.addPickUp(new DimondPU(xx, yy, imageLoader, controller)); break;
					case 1: controller.addPickUp(new BulletPU(xx, yy, imageLoader, controller, player, BulletPU.Key.Left)); break;
					case 2: controller.addPickUp(new BulletPU(xx, yy, imageLoader, controller, player, BulletPU.Key.Right)); break;
					case 3: controller.addPickUp(new HealthPU(xx, yy, imageLoader, controller)); break;
					default: break;
					}
				}
			}
		}
	}
	
	public static void loadLevelShop(ImageLoader imageLoader, GameObjectController controller, Player player) {
		for(int y = 0; y < 26; y++) { 
			for(int x = 0; x < 34; x++) {
				int xx = x*Wall.WIDTH;
				int yy = y*Wall.HEIGHT;
				if(LevelShop.level[y][x] == 1) {
					controller.addWall(new Wall(xx, yy, imageLoader));
				} else if(LevelShop.level[y][x] == 6) {
					controller.addFloor(new Floor(xx, yy, imageLoader));
				} else if(LevelShop.level[y][x] == 7) {
					controller.addShop(new Shop(xx, yy, imageLoader, player, Shop.Type.hpShop));
				} else if(LevelShop.level[y][x] == 8) {
					controller.addShop(new Shop(xx, yy, imageLoader, player, Shop.Type.bulletShop));
				} else if(LevelShop.level[y][x] == 9) {
					controller.addDoor(new Door(xx, yy, imageLoader, "LevelShop"));
				}
			}
		}
	}
}
