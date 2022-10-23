package inputPackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import assetsPackage.Shop;
import gamePackage.GameCamera;
import gamePackage.GameCommandLine;
import gamePackage.GameMain;
import gamePackage.GameObjectController;
import gamePackage.GameSetting;
import gamePackage.GameState;
import levelPackage.LevelStarter;
import musicPackage.MusicPlayer;
import playerPackage.Player;
import themePackage.Button;
import themePackage.DressingRoom;
import themePackage.Game;
import themePackage.Help;
import themePackage.Lost;
import themePackage.Menu;
import themePackage.Setting;
import themePackage.Upgrade;
import themePackage.Won;

public class MouseInput extends MouseAdapter {
	
	private Player player;
	private GameCamera camera;
	private Game gameTheme;
	private GameSetting gameSetting;
	private GameObjectController objectController;
	
	public MouseInput(Game gameTheme, Player player, GameCamera camera, LevelStarter levelStarter, GameSetting gameSetting, GameObjectController objectController) {
		this.player = player;
		this.camera = camera;
		this.gameTheme = gameTheme;
		this.gameSetting = gameSetting;
		this.objectController = objectController;
	}
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(GameMain.state == GameState.Game) {
			gameMouseReleased(e);
		} else if(GameMain.state == GameState.Menu) {
			menuMouseReleased(mx, my);
		} else if(GameMain.state == GameState.DressingRoom) {
			dressingRoomMouseReleased(mx, my);
	    } else if(GameMain.state == GameState.Setting) {
			settingMouseReleased(mx, my);
	    } else if(GameMain.state == GameState.Help) {
			helpMouseReleased(mx, my);
		} else if(GameMain.state == GameState.Won) { 
			wonMouseReleased(mx, my);
		} else if(GameMain.state == GameState.Lost) {
			lostMouseReleased(mx, my);
		} else if(GameMain.state == GameState.Start) {
			startMouseReleased();
		}
	}
	
	private void gameMouseReleased(MouseEvent e) {
		if(GameMain.state == GameState.Game) {
			if(!Game.paused) {
				if(Game.gameLevel != Game.GameLevel.LevelShop)
					player.shootBullet(e, camera);
			} else {
				int mx = e.getX() - 101;
				int my = e.getY() - 106;
//				System.out.println(mx + "," + my);
				if(mouseInside(mx, my, Game.menuBtnX, Game.menuBtnY, Button.WIDTH, Button.HEIGHT)) {
					gameTheme.pauseGame();
					GameMain.state = GameState.Menu;
					player.reset();
					objectController.clearAll();
					MusicPlayer.playMenuBGMSound();
				} else if(mouseInside(mx, my, Game.restartBtnX, Game.restartBtnY, Button.WIDTH, Button.HEIGHT)) {
					gameTheme.pauseGame();
					player.reset();
					objectController.clearAll();
					gameTheme.start();
				}
			}
			if(Game.gameLevel == Game.GameLevel.LevelShop) {
				int mx = e.getX();
				int my = e.getY();
				shopMouseReleased(mx, my);
			}
		}
	}
	
	private void shopMouseReleased(int mx, int my) {
		if(!mouseInside(mx, my, Shop.boardX, Shop.boardY, Shop.boardW, Shop.boardH)) {
			MusicPlayer.playClickSound();
			Shop.shopMode = 0;
		}
		if(Shop.shopMode == 1) {
			if(mouseInside(mx, my, Shop.hpBoxX, Shop.hpBox1Y, Shop.hpBoxW, Shop.hpBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.hpDimond1) {
					player.spendDimond(Shop.hpDimond1);
					player.recoverHp(Shop.hp1);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.hpBoxX, Shop.hpBox2Y, Shop.hpBoxW, Shop.hpBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.hpDimond2) {
					player.spendDimond(Shop.hpDimond2);
					player.recoverHp(Shop.hp2);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.hpBoxX, Shop.hpBox3Y, Shop.hpBoxW, Shop.hpBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.hpDimond3) {
					player.spendDimond(Shop.hpDimond3);
					player.recoverHp(Shop.hp3);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.hpBoxX, Shop.hpBox4Y, Shop.hpBoxW, Shop.hpBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.hpDimond4) {
					player.spendDimond(Shop.hpDimond4);
					player.drinkRecoverPotion();
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.hpBoxX, Shop.hpBox5Y, Shop.hpBoxW, Shop.hpBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.hpDimond5) {
					player.spendDimond(Shop.hpDimond5);
					player.drinkLessDamagePotion();
				} else Shop.showErrorMessage();
			}
		} else if(Shop.shopMode == 2) {
			if(mouseInside(mx, my, Shop.bulletBoxX, Shop.bulletBox1Y, Shop.bulletBoxW, Shop.bulletBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.bulletDimond1) {
					player.spendDimond(Shop.bulletDimond1);
					player.addLeftKeyBullet(Shop.bullet1);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.bulletBoxX, Shop.bulletBox2Y, Shop.bulletBoxW, Shop.bulletBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.bulletDimond2) {
					player.spendDimond(Shop.bulletDimond2);
					player.addLeftKeyBullet(Shop.bullet2);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.bulletBoxX, Shop.bulletBox3Y, Shop.bulletBoxW, Shop.bulletBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.bulletDimond3) {
					player.spendDimond(Shop.bulletDimond3);
					player.addRightKeyBullet(Shop.bullet3);
				} else Shop.showErrorMessage();
			} else if(mouseInside(mx, my, Shop.bulletBoxX, Shop.bulletBox4Y, Shop.bulletBoxW, Shop.bulletBoxH)) {
				MusicPlayer.playClickSound();
				if(player.getDimondAmount() >= Shop.bulletDimond4) {
					player.spendDimond(Shop.bulletDimond4);
					player.addRightKeyBullet(Shop.bullet4);
				} else Shop.showErrorMessage();
			}
		}
	}
	
	private void menuMouseReleased(int mx, int my) {
		if(Menu.resetPressed) resetMouseReleased(mx, my);
		else {
			if(mouseInside(mx, my, Menu.playBtnX, Menu.playBtnY, Button.playButtonWIDTH, Button.playButtonHEIGHT)) {
				MusicPlayer.playClickSound();
				gameTheme.start();
			} else if(mouseInside(mx, my, Menu.soundBtnX, Menu.soundBtnY, Button.WIDTH, Button.HEIGHT)) {
				MusicPlayer.pressSoundBtn();
				MusicPlayer.playClickSound();
			} else if(mouseInside(mx, my, Menu.musicBtnX, Menu.musicBtnY, Button.WIDTH, Button.HEIGHT)) {
				MusicPlayer.playClickSound();
				MusicPlayer.pressMusicBtn();
			} else if(mouseInside(mx, my, Menu.helpBtnX, Menu.helpBtnY, Button.WIDTH, Button.HEIGHT)) {
				MusicPlayer.playClickSound();
				GameMain.state = GameState.Help;
			} else if(mouseInside(mx, my, Menu.settingBtnX, Menu.settingBtnY, Button.WIDTH, Button.HEIGHT)) {
				MusicPlayer.playClickSound();
				GameMain.state = GameState.Setting;
			} else if(mouseInside(mx, my, Menu.resetBtnX, Menu.resetBtnY, Button.WIDTH, Button.HEIGHT)) {		// reset
				MusicPlayer.playClickSound();
				Menu.resetPressed = true;
			} else if(mouseInside(mx, my, Menu.playerImgX, Menu.playerImgY, Menu.playerImgW, Menu.playerImgH)) {
				MusicPlayer.playClickSound();
				player.setX(GameMain.WIDTH/2-25); player.setY(GameMain.HEIGHT/2+60);
				GameMain.state = GameState.DressingRoom;
			} else if(mouseInside(mx, my, Menu.commandBtnX, Menu.commandBtnY, Button.WIDTH, Button.HEIGHT)) {
				MusicPlayer.playClickSound();
				new GameCommandLine(player, objectController);
			} 
		}
	}
	
	private void resetMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, Menu.resetYesX, Menu.resetYesY, Menu.resetBtnW, Menu.resetBtnH)) {
			MusicPlayer.playClickSound();
			gameSetting.reset();
			gameSetting.update();
			System.exit(0);
		} else if(mouseInside(mx, my, Menu.resetNoX, Menu.resetNoY, Menu.resetBtnW, Menu.resetBtnH)) {
			MusicPlayer.playClickSound();
			Menu.resetPressed = false;
		}
	}
	
	private void dressingRoomMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, DressingRoom.buttonBackX, DressingRoom.buttonBackY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			GameMain.state = GameState.Menu;
			DressingRoom.resetUpgradeBtn();
			DressingRoom.setPlayerLock(0);
			DressingRoom.setUpgradePressed(0);
			DressingRoom.resetPressBullet();
			DressingRoom.resetError();
			player.setAttribute();
		} else if(mouseInside(mx, my, DressingRoom.buttonUpgradeX, DressingRoom.buttonUpgradeY, Button.WIDTH, Button.HEIGHT)) { 
			MusicPlayer.playClickSound();
			DressingRoom.setPlayerLock(0);
			DressingRoom.pressUpgradeBtn();
			DressingRoom.setUpgradePressed(0);
			player.setAttribute();
		} else if(mouseInside(mx, my, DressingRoom.player1X, DressingRoom.player1Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
			MusicPlayer.playClickSound();
			player.changePlayerType(1); GameSetting.playerImage = 1;
			DressingRoom.setUpgradePressed(0);
		} else if(mouseInside(mx, my, DressingRoom.player2X, DressingRoom.player2Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
			DressingRoom.setUpgradePressed(0);
			if(!GameSetting.playerUnlock[0]) {MusicPlayer.playClickSound(); player.changePlayerType(2); GameSetting.playerImage = 2;}
			else {
				if(DressingRoom.getPlayerLockPressed(1)) {
					if(mouseInside(mx, my, DressingRoom.buyPlayerBtnBuy1X, DressingRoom.buyPlayerBtnBuy1Y, DressingRoom.buyPlayerBtnW, DressingRoom.buyPlayerBtnH)) {
						MusicPlayer.playClickSound();
						if(GameSetting.coinNumber >= DressingRoom.buyPlayerCoin[0]) {
							MusicPlayer.playCoinSound();
							GameSetting.coinNumber -= DressingRoom.buyPlayerCoin[0];
							GameSetting.playerUnlock[0] = false;
							player.changePlayerType(2); GameSetting.playerImage = 2;
						} else DressingRoom.showError();
					} else if(mouseInside(mx, my, DressingRoom.buyPlayerBtnNah1X, DressingRoom.buyPlayerBtnNah1Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
						MusicPlayer.playClickSound();
						DressingRoom.setPlayerLock(0);
					}
				} else {
					MusicPlayer.playClickSound();
					DressingRoom.setPlayerLock(1);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.player3X, DressingRoom.player3Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
			DressingRoom.setUpgradePressed(0);
			if(!GameSetting.playerUnlock[1]) {MusicPlayer.playClickSound(); player.changePlayerType(3); GameSetting.playerImage = 3;}
			else {
				if(DressingRoom.getPlayerLockPressed(2)) {
					if(mouseInside(mx, my, DressingRoom.buyPlayerBtnBuy2X, DressingRoom.buyPlayerBtnBuy2Y, DressingRoom.buyPlayerBtnW, DressingRoom.buyPlayerBtnH)) {
						MusicPlayer.playClickSound();
						if(GameSetting.coinNumber >= DressingRoom.buyPlayerCoin[1]) {
							MusicPlayer.playCoinSound();
							GameSetting.coinNumber -= DressingRoom.buyPlayerCoin[1];
							GameSetting.playerUnlock[1] = false;
							player.changePlayerType(3); GameSetting.playerImage = 3;
						} else DressingRoom.showError();
					} else if(mouseInside(mx, my, DressingRoom.buyPlayerBtnNah2X, DressingRoom.buyPlayerBtnNah2Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
						MusicPlayer.playClickSound();
						DressingRoom.setPlayerLock(0);
					}
				} else {
					MusicPlayer.playClickSound();
					DressingRoom.setPlayerLock(2);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.player4X, DressingRoom.player4Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
			DressingRoom.setUpgradePressed(0);
			if(!GameSetting.playerUnlock[2]) {MusicPlayer.playClickSound(); player.changePlayerType(4); GameSetting.playerImage = 4;}
			else {
				if(DressingRoom.getPlayerLockPressed(3)) {
					if(mouseInside(mx, my, DressingRoom.buyPlayerBtnBuy3X, DressingRoom.buyPlayerBtnBuy3Y, DressingRoom.buyPlayerBtnW, DressingRoom.buyPlayerBtnH)) {
						MusicPlayer.playClickSound();
						if(GameSetting.coinNumber >= DressingRoom.buyPlayerCoin[2]) {
							MusicPlayer.playCoinSound();
							GameSetting.coinNumber -= DressingRoom.buyPlayerCoin[2];
							GameSetting.playerUnlock[2] = false;
							player.changePlayerType(4); GameSetting.playerImage = 4;
						} else DressingRoom.showError();
					} else if(mouseInside(mx, my, DressingRoom.buyPlayerBtnNah3X, DressingRoom.buyPlayerBtnNah3Y, DressingRoom.playerPicW, DressingRoom.playerPicH)) {
						MusicPlayer.playClickSound();
						DressingRoom.setPlayerLock(0);
					}
				} else {
					MusicPlayer.playClickSound();
					DressingRoom.setPlayerLock(3);
				}
			}
		} else {
			DressingRoom.setPlayerLock(0);
		}
		if(DressingRoom.getUpgradeMode()) {
			dressingRoomUpgradeModeMouseReleased(mx, my);
		}
	}	
	
	private void dressingRoomUpgradeModeMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, DressingRoom.upgradeBtnX, DressingRoom.upgradeBtn1Y, DressingRoom.upgradeBtnW, DressingRoom.upgradeBtnH) && player.getUpgradeCost(1) >= 0) {
			DressingRoom.setPlayerLock(0);
			if(!DressingRoom.getUpgradePressed(1)) {
				MusicPlayer.playClickSound(); DressingRoom.setUpgradePressed(1);				
			} else {
				if(mouseInside(mx, my, DressingRoom.upgradeYesBtnX, DressingRoom.upgradeYesBtn1Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					if(GameSetting.coinNumber >= player.getUpgradeCost(1)) {
						MusicPlayer.playCoinSound();
						GameSetting.coinNumber -= player.getUpgradeCost(1);
						player.upgrade(1);
					} else DressingRoom.showError();
					DressingRoom.setUpgradePressed(0);
				} else if(mouseInside(mx, my, DressingRoom.upgradeNahBtnX, DressingRoom.upgradeNahBtn1Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					DressingRoom.setUpgradePressed(0);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.upgradeBtnX, DressingRoom.upgradeBtn2Y, DressingRoom.upgradeBtnW, DressingRoom.upgradeBtnH)  && player.getUpgradeCost(2) >= 0) {
			DressingRoom.setPlayerLock(0);
			if(!DressingRoom.getUpgradePressed(2)) {
				MusicPlayer.playClickSound(); DressingRoom.setUpgradePressed(2);				
			} else {
				if(mouseInside(mx, my, DressingRoom.upgradeYesBtnX, DressingRoom.upgradeYesBtn2Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound(); 
					if(GameSetting.coinNumber >= player.getUpgradeCost(2)) {
						MusicPlayer.playCoinSound();
						GameSetting.coinNumber -= player.getUpgradeCost(2);
						player.upgrade(2);
					} else DressingRoom.showError();
					DressingRoom.setUpgradePressed(0);
				} else if(mouseInside(mx, my, DressingRoom.upgradeNahBtnX, DressingRoom.upgradeNahBtn2Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					DressingRoom.setUpgradePressed(0);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.upgradeBtnX, DressingRoom.upgradeBtn3Y, DressingRoom.upgradeBtnW, DressingRoom.upgradeBtnH)  && player.getUpgradeCost(3) >= 0) {
			DressingRoom.setPlayerLock(0);
			if(!DressingRoom.getUpgradePressed(3)) {
				MusicPlayer.playClickSound(); DressingRoom.setUpgradePressed(3);				
			} else {
				if(mouseInside(mx, my, DressingRoom.upgradeYesBtnX, DressingRoom.upgradeYesBtn3Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					if(GameSetting.coinNumber >= player.getUpgradeCost(3)) {
						MusicPlayer.playCoinSound();
						if(!DressingRoom.getBulletPressed(1)) {
							GameSetting.coinNumber -= player.getUpgradeCost(3); player.upgrade(3);
						} else {
							GameSetting.coinNumber -= player.getUpgradeCost(3); player.upgrade(4);
						}
					} else DressingRoom.showError();
					DressingRoom.setUpgradePressed(0);
				} else if(mouseInside(mx, my, DressingRoom.upgradeNahBtnX, DressingRoom.upgradeNahBtn3Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					DressingRoom.setUpgradePressed(0);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.upgradeBtnX, DressingRoom.upgradeBtn4Y, DressingRoom.upgradeBtnW, DressingRoom.upgradeBtnH)  && player.getUpgradeCost(4) >= 0) {
			DressingRoom.setPlayerLock(0);
			if(!DressingRoom.getUpgradePressed(4)) {
				MusicPlayer.playClickSound(); DressingRoom.setUpgradePressed(4);				
			} else {
				if(mouseInside(mx, my, DressingRoom.upgradeYesBtnX, DressingRoom.upgradeYesBtn4Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					if(GameSetting.coinNumber >= player.getUpgradeCost(4)) {
						MusicPlayer.playCoinSound();
						if(!DressingRoom.getBulletPressed(2)) {
							GameSetting.coinNumber -= player.getUpgradeCost(4); player.upgrade(5);
						} else {
							GameSetting.coinNumber -= player.getUpgradeCost(4); player.upgrade(6);
						}
					} else DressingRoom.showError();
					DressingRoom.setUpgradePressed(0);
				} else if(mouseInside(mx, my, DressingRoom.upgradeNahBtnX, DressingRoom.upgradeNahBtn4Y, DressingRoom.upgradeYesNahBtnW, DressingRoom.upgradeYesNahBtnH)) {
					MusicPlayer.playClickSound();
					DressingRoom.setUpgradePressed(0);
				}
			}
		} else if(mouseInside(mx, my, DressingRoom.bulletBtnX, DressingRoom.bulletBtn1Y, DressingRoom.bulletBtnW, DressingRoom.bulletBtnH)) {
			MusicPlayer.playClickSound();
			DressingRoom.setPlayerLock(0);
			DressingRoom.pressBullet(1);
			DressingRoom.setUpgradePressed(0);
		} else if(mouseInside(mx, my, DressingRoom.bulletBtnX, DressingRoom.bulletBtn2Y, DressingRoom.bulletBtnW, DressingRoom.bulletBtnH)) {
			MusicPlayer.playClickSound();
			DressingRoom.setPlayerLock(0);
			DressingRoom.pressBullet(2);
			DressingRoom.setUpgradePressed(0);
		} else {
			DressingRoom.setUpgradePressed(0);
		}
	}
	
	private void settingMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, Setting.backBtnX, Setting.backBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			GameMain.state = GameState.Menu;
		} else if(mouseInside(mx, my, Setting.PlayerTrailOnBtnX, Setting.PlayerTrailOnBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showTrail = true;
		} else if(mouseInside(mx, my, Setting.PlayerTrailOffBtnX, Setting.PlayerTrailOffBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showTrail = false;
		} else if(mouseInside(mx, my, Setting.PlayerHpNumberOnBtnX, Setting.PlayerHpNumberOnBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showPlayerHpNumber = true;
		} else if(mouseInside(mx, my, Setting.PlayerHpNumberOffBtnX, Setting.PlayerHpNumberOffBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showPlayerHpNumber = false;
		} else if(mouseInside(mx, my, Setting.PlayerCollisionBoxOnBtnX, Setting.PlayerCollisionBoxOnBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showPlayerCollisionBox = true;
		} else if(mouseInside(mx, my, Setting.PlayerCollisionBoxOffBtnX, Setting.PlayerCollisionBoxOffBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showPlayerCollisionBox = false;
		} else if(mouseInside(mx, my, Setting.EnemyCollisionBoxOnBtnX, Setting.EnemyCollisionBoxOnBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showEnemyCollisionBox = true;
		} else if(mouseInside(mx, my, Setting.EnemyCollisionBoxOffBtnX, Setting.EnemyCollisionBoxOffBtnY, Setting.onoffBtnW, Setting.onoffBtnH) ) {
			MusicPlayer.playClickSound();
			GameSetting.showEnemyCollisionBox = false;
		}
	}
	
	private void helpMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, Help.backBtnX, Help.backBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			GameMain.state = GameState.Menu;
		}
	}
	
	private void wonMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, Won.menuBtnX, Won.menuBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			GameMain.state = GameState.Menu;
			MusicPlayer.playMenuBGMSound();
		} else if(mouseInside(mx, my, Won.restartBtnX, Won.restartBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			gameTheme.start();
		}
	}
	
	private void lostMouseReleased(int mx, int my) {
		if(mouseInside(mx, my, Lost.menuBtnX, Lost.menuBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			GameMain.state = GameState.Menu;
			MusicPlayer.playMenuBGMSound();
		} else if(mouseInside(mx, my, Lost.restartBtnX, Lost.restartBtnY, Button.WIDTH, Button.HEIGHT)) {
			MusicPlayer.playClickSound();
			gameTheme.start();
		}
	}
	
	private void startMouseReleased() {
		MusicPlayer.playClickSound();
		GameMain.state = GameState.Menu;
	}
	
	private boolean mouseInside(int mx, int my, int x, int y, int width, int height) {
		if(x < mx && mx < x + width) {
			if(my > y && my < y + height) return true;
			else return false;
		} else return false;
	}
}
