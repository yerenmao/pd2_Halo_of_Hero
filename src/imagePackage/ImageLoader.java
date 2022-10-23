package imagePackage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private String path = ".//picture//";
	
	private BufferedImage image;
	
	private BufferedImage playerSpriteSheet;
	private BufferedImage[] playerImages1 = new BufferedImage[4];
	private BufferedImage[] playerImages2 = new BufferedImage[4];
	private BufferedImage[] playerImages3 = new BufferedImage[4];
	private BufferedImage[] playerImages4 = new BufferedImage[4];
	private BufferedImage[] playerImagesOp1 = new BufferedImage[4];
	private BufferedImage[] playerImagesOp2 = new BufferedImage[4];
	private BufferedImage[] playerImagesOp3 = new BufferedImage[4];
	private BufferedImage[] playerImagesOp4 = new BufferedImage[4];
	private BufferedImage enemySpriteSheet;
	private BufferedImage[] traceEnemyImages = new BufferedImage[4];
	private BufferedImage[] basicEnemyImages = new BufferedImage[6];
	private BufferedImage[] multiShootingEnemyImages = new BufferedImage[12];
	private BufferedImage[] multiShootingEnemySpinImages = new BufferedImage[12];
	private BufferedImage[] multiShootingEnemyRevolveImages = new BufferedImage[12];
	private BufferedImage bossSpriteSheet;
	private BufferedImage[] bossImages = new BufferedImage[4];
	
	private BufferedImage startImage;
	private BufferedImage menuImage;
	private BufferedImage wonImage;
	private BufferedImage lostImage;
	
	private BufferedImage buttonImage;
	
	private BufferedImage buttonAboutImage;
	private BufferedImage buttonPlayImage;
	private BufferedImage buttonPrewImage;
	private BufferedImage buttonQuitImage;
	private BufferedImage buttonSettingImage;
	private BufferedImage buttonMenuImage;
	private BufferedImage buttonUpgradeImage;
	private BufferedImage buttonRestartImage;
	private BufferedImage buttonCommandImage;
	private BufferedImage buttonCloseImage;
	private BufferedImage buttonSoundONImage;
	private BufferedImage buttonSoundOFFImage;
	private BufferedImage buttonMusicONImage;
	private BufferedImage buttonMusicOFFImage;
	
	private BufferedImage normalBulletImage;
	private BufferedImage strongBulletImage;
	private BufferedImage bounceBulletImage;
	private BufferedImage satelliteBulletImage;
	private BufferedImage curvedBulletImage;
	private BufferedImage trapBulletImage;
	
	private BufferedImage wallImage;
	private BufferedImage doorImage;
	private BufferedImage floorImage;
	private BufferedImage bossGroundImage;
	private BufferedImage blockImage;
	private BufferedImage movingWallImage;
	private BufferedImage shopImage;
	private BufferedImage shopBoardImage;
	private BufferedImage upgradeBoardImage;
	private BufferedImage upgradeTableImage;
	
	private BufferedImage dimondImage;
	private BufferedImage blueDimondImage;
	private BufferedImage coinImage;
	
	private BufferedImage enemyNormalBulletImage;
	private BufferedImage enemyBounceBulletImage;
	private BufferedImage enemyTraceBulletImage;
	private BufferedImage enemyChargeBulletImage;
	
	private BufferedImage potionRedImage;
	private BufferedImage potionYellowImage;
	private BufferedImage potionGreenImage;
	private BufferedImage potionBlueImage;
	private BufferedImage potionPurpleImage;
	
	private BufferedImage loadBar1Image;
	private BufferedImage loadBar2Image;
	private BufferedImage loadBar3Image;
	private BufferedImage loadBarBGImage;
	private BufferedImage loadBarTextImage;
	
	private BufferedImage questionCloudImage;
	private BufferedImage lockImage;
	
	private BufferedImage upgradeHpImage;
	private BufferedImage upgradeSpeedImage;
	
	private BufferedImage lightningImage;
	
	private BufferedImage pausedHeaderImage;
	private BufferedImage pausedTextImage;
	
	private BufferedImage angryImage;
	private BufferedImage spawnAreaImage;
	
	public void loadImage() {
		
		playerSpriteSheet = loadImage("player1_sheet.png");
		playerImages1[0] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerImages1[1] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImages1[2] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImages1[3] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerSpriteSheet = loadImage("player1_sheet_op.png");
		playerImagesOp1[0] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerImagesOp1[1] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImagesOp1[2] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImagesOp1[3] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerSpriteSheet = loadImage("player2_sheet.png");
		playerImages2[0] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerImages2[1] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImages2[2] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImages2[3] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerSpriteSheet = loadImage("player2_sheet_op.png");
		playerImagesOp2[0] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerImagesOp2[1] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImagesOp2[2] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImagesOp2[3] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerSpriteSheet = loadImage("player3_sheet.png");
		playerImages3[0] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerImages3[1] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImages3[2] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImages3[3] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerSpriteSheet = loadImage("player3_sheet_op.png");
		playerImagesOp3[0] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerImagesOp3[1] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImagesOp3[2] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImagesOp3[3] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerSpriteSheet = loadImage("player4_sheet.png");
		playerImages4[0] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		playerImages4[1] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImages4[2] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImages4[3] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerSpriteSheet = loadImage("player4_sheet_op.png");
		playerImagesOp4[0] = playerSpriteSheet.getSubimage(72, 0, 24, 24);
		playerImagesOp4[1] = playerSpriteSheet.getSubimage(48, 0, 24, 24);
		playerImagesOp4[2] = playerSpriteSheet.getSubimage(24, 0, 24, 24);
		playerImagesOp4[3] = playerSpriteSheet.getSubimage(0, 0, 24, 24);
		
		enemySpriteSheet = loadImage("enemy_sheet.png");
		traceEnemyImages[0] = enemySpriteSheet.getSubimage(0, 0, 16, 20);
		traceEnemyImages[1] = enemySpriteSheet.getSubimage(16, 0, 16, 20);
		traceEnemyImages[2] = enemySpriteSheet.getSubimage(32, 0, 16, 20);
		traceEnemyImages[3] = enemySpriteSheet.getSubimage(48, 0, 16, 20);
		basicEnemyImages[0] = enemySpriteSheet.getSubimage(0, 21, 16, 18);
		basicEnemyImages[1] = enemySpriteSheet.getSubimage(16, 21, 16, 18);
		basicEnemyImages[2] = enemySpriteSheet.getSubimage(32, 21, 16, 18);
		basicEnemyImages[3] = enemySpriteSheet.getSubimage(48, 21, 16, 18);
		basicEnemyImages[4] = enemySpriteSheet.getSubimage(64, 21, 16, 18);
		basicEnemyImages[5] = enemySpriteSheet.getSubimage(80, 21, 16, 18);
		bossSpriteSheet = loadImage("boss_sheet.png");
		bossImages[0] = bossSpriteSheet.getSubimage(24, 22, 52, 52);
		bossImages[1] = bossSpriteSheet.getSubimage(24, 122, 52, 52);
		bossImages[2] = bossSpriteSheet.getSubimage(24, 222, 52, 52);
		bossImages[3] = bossSpriteSheet.getSubimage(24, 322, 52, 52);
		
		for(int i = 0; i < 12; i++) {
			multiShootingEnemyImages[i] = loadImage("multiShootingEnemy_image" + i + ".png");
			multiShootingEnemySpinImages[i] = loadImage("multiShootingEnemySpin_image" + i + ".png");
			multiShootingEnemyRevolveImages[i] = loadImage("multiShootingEnemyRevolve_image" + i + ".png");
		}
		
		startImage = loadImage("start_image.png");
		menuImage = loadImage("menu_image.png");
		wonImage = loadImage("won_image.jpg");
		lostImage = loadImage("lost_image.jpg");
		buttonImage = loadImage("button_image.png");
		
		buttonAboutImage = loadImage("button_about.png");
		buttonPlayImage = loadImage("button_play.png");
		buttonPrewImage = loadImage("button_prew.png");
		buttonQuitImage = loadImage("button_quit.png");
		buttonSettingImage = loadImage("button_setting.png");
		buttonMenuImage = loadImage("button_menu.png");
		buttonUpgradeImage = loadImage("button_upgrade.png");
		buttonRestartImage = loadImage("button_restart.png");
		buttonCommandImage = loadImage("button_command.png");
		buttonCloseImage = loadImage("button_close.png");
		buttonSoundONImage = loadImage("button_soundON.png");
		buttonSoundOFFImage = loadImage("button_soundOFF.png");
		buttonMusicONImage = loadImage("button_musicON.png");
		buttonMusicOFFImage = loadImage("button_musicOFF.png");
		
		BufferedImage bulletSet = loadImage("bullet_set.png");
		normalBulletImage = bulletSet.getSubimage(38, 35, 10, 10);
		strongBulletImage = bulletSet.getSubimage(4, 35, 10, 10);
		bounceBulletImage = loadImage("playerBounceBullet_image.png");
		satelliteBulletImage = loadImage("satelliteBullet_image.png");
		curvedBulletImage = loadImage("curvedBullet_image.png");
		trapBulletImage = loadImage("trapBullet_image.png");
		
		wallImage = loadImage("wall_image.png");
		floorImage = loadImage("floor_image.png");
		bossGroundImage = menuImage.getSubimage(46, 25, 48, 25);
		doorImage = loadImage("door_image.png");
		blockImage = loadImage("block_image.png");
		movingWallImage = loadImage("movingWall_image.png");
		shopImage = loadImage("shop_image.png");
		shopBoardImage = loadImage("shopBoard_image.png");
		upgradeBoardImage = loadImage("upgradeBoard_image.png");
		upgradeTableImage = loadImage("upgradeTable_image.png");
		
		dimondImage = loadImage("dimond_image.png");
		blueDimondImage = loadImage("blueDimond_image.png");
		coinImage = loadImage("coin_image.png");
		
		enemyNormalBulletImage = loadImage("enemyNormalBullet_image.png");
		enemyBounceBulletImage = loadImage("enemyBounceBullet_image.png");
		enemyTraceBulletImage = loadImage("enemyTraceBullet_image.png");
		enemyChargeBulletImage = loadImage("enemyChargeBullet_image.png");
		
		potionRedImage = loadImage("potionRed_image.png").getSubimage(0, 0, 16, 16);
		potionYellowImage = loadImage("potionYellow_image.png").getSubimage(0, 0, 16, 16);
		potionGreenImage = loadImage("potionGreen_image.png").getSubimage(0, 0, 16, 16);
		potionBlueImage = loadImage("potionBlue_image.png").getSubimage(0, 0, 16, 16);
		potionPurpleImage = loadImage("potionPurple_image.png").getSubimage(0, 0, 16, 16);
		
		loadBar1Image = loadImage("loadBar1_image.png");
		loadBar2Image = loadImage("loadBar2_image.png");
		loadBar3Image = loadImage("loadBar3_image.png");
		loadBarBGImage = loadImage("loadBarbg_image.png");
		loadBarTextImage = loadImage("loadBartext_image.png");
		
		questionCloudImage = loadImage("questionCloud_image.png");
		lockImage = loadImage("lock_image.png");
		
		upgradeHpImage = loadImage("upgradeHP_image.png");
		upgradeSpeedImage = loadImage("upgradeSpeed_image.png");
		
		lightningImage = loadImage("lightning.png");
		
		pausedHeaderImage = loadImage("pausedHeader_image.png");
		pausedTextImage = loadImage("pausedText_image.png");
		
		angryImage = loadImage("angry_image.png");
		spawnAreaImage = loadImage("spawnArea_image.png");
	}
	
	private BufferedImage loadImage(String path) {
		try {image = ImageIO.read(getClass().getResource(path));}
		catch(IOException e) {e.printStackTrace();}	
		return image;
	}
	
	public BufferedImage[] getPlayerImages1() {
		return playerImages1;
	}
	
	public BufferedImage[] getPlayerImagesOp1() {
		return playerImagesOp1;
	}
	
	public BufferedImage[] getPlayerImages2() {
		return playerImages2;
	}
	
	public BufferedImage[] getPlayerImagesOp2() {
		return playerImagesOp2;
	}
	
	public BufferedImage[] getPlayerImages3() {
		return playerImages3;
	}
	
	public BufferedImage[] getPlayerImagesOp3() {
		return playerImagesOp3;
	}
	
	public BufferedImage[] getPlayerImages4() {
		return playerImages4;
	}
	
	public BufferedImage[] getPlayerImagesOp4() {
		return playerImagesOp4;
	}
	
	public BufferedImage[] getTraceEnemyImages() {
		return traceEnemyImages;
	}
	
	public BufferedImage[] getBasicEnemyImages() {
		return basicEnemyImages;
	}
	
	public BufferedImage[] getMultiShootingEnemyImages() {
		return multiShootingEnemyImages;
	}
	
	public BufferedImage[] getMultiShootingEnemySpinImages() {
		return multiShootingEnemySpinImages;
	}
	
	public BufferedImage[] getMultiShootingEnemyRevolveImages() {
		return multiShootingEnemyRevolveImages;
	}
	
	public BufferedImage[] getBossImages() {
		return bossImages;
	}
	
	public BufferedImage getStartImage() {
		return startImage;
	}
	
	public BufferedImage getMenuImage() {
		return menuImage;
	}
	
	public BufferedImage getWonImage() {
		return wonImage;
	}
	
	public BufferedImage getLostImage() {
		return lostImage;
	}
	
	public BufferedImage getButtonImage() {
		return buttonImage;
	}
	
	public BufferedImage getButtonAboutImage() {
		return buttonAboutImage;
	}
	
	public BufferedImage getButtonPlayImage() {
		return buttonPlayImage;
	}
	
	public BufferedImage getButtonPrewImage() {
		return buttonPrewImage;
	}
	
	public BufferedImage getButtonQuitImage() {
		return buttonQuitImage;
	}
	
	public BufferedImage getButtonSettingImage() {
		return buttonSettingImage;
	}
	
	public BufferedImage getButtonMenuImage() {
		return buttonMenuImage;
	}
	
	public BufferedImage getButtonUpgradeImage() {
		return buttonUpgradeImage;
	}
	
	public BufferedImage getButtonRestartImage() {
		return buttonRestartImage;
	}
	
	public BufferedImage getButtonCommandImage() {
		return buttonCommandImage;
	}
	
	public BufferedImage getButtonSoundONImage() {
		return buttonSoundONImage;
	}
	
	public BufferedImage getButtonSoundOFFImage() {
		return buttonSoundOFFImage;
	}
	
	public BufferedImage getButtonMusicONImage() {
		return buttonMusicONImage;
	}
	
	public BufferedImage getButtonMusicOFFImage() {
		return buttonMusicOFFImage;
	}
	
	public BufferedImage getButtonCloseImage() {
		return buttonCloseImage;
	}
	
	public BufferedImage getNormalBulletImage() {
		return normalBulletImage;
	}
	
	public BufferedImage getStrongBulletImage() {
		return strongBulletImage;
	}
	
	public BufferedImage getBounceBulletImage() {
		return bounceBulletImage;
	}
	
	public BufferedImage getSatelliteBulletImage() {
		return satelliteBulletImage;
	}
	
	public BufferedImage getCurvedBulletImage() {
		return curvedBulletImage;
	}
	
	public BufferedImage getTrapBulletImage() {
		return trapBulletImage;
	}
	
	public BufferedImage getWallImage() {
		return wallImage;
	}
	
	public BufferedImage getFloorImage() {
		return floorImage;
	}
	
	public BufferedImage getBossGroundImage() {
		return bossGroundImage;
	}
	
	public BufferedImage getDoorImage() {
		return doorImage;
	}
	
	public BufferedImage getBlockImage() {
		return blockImage;
	}
	
	public BufferedImage getMovingWallImage() {
		return movingWallImage;
	}
	
	public BufferedImage getShopImage() {
		return shopImage;
	}
	
	public BufferedImage getShopBoardImage() {
		return shopBoardImage;
	}
	
	public BufferedImage getUpgradeBoardImage() {
		return upgradeBoardImage;
	}
	
	public BufferedImage getUpgradeTableImage() {
		return upgradeTableImage;
	}
	
	public BufferedImage getDimondImage() {
		return dimondImage;
	}
	
	public BufferedImage getBlueDimondImage() {
		return blueDimondImage;
	}
	
	public BufferedImage getCoinImage() {
		return coinImage;
	}
	
	public BufferedImage getEnemyNormalBulletImage() {
		return enemyNormalBulletImage;
	}
	
	public BufferedImage getEnemyBounceBulletImage() {
		return enemyBounceBulletImage;
	}
	
	public BufferedImage getEnemyTraceBulletImage() {
		return enemyTraceBulletImage;
	}
	
	public BufferedImage getEnemyChargeBulletImage() {
		return enemyChargeBulletImage;
	}
	
	public BufferedImage getPotionRedImage() {
		return potionRedImage;
	}
	
	public BufferedImage getPotionYellowImage() {
		return potionYellowImage;
	}
	
	public BufferedImage getPotionGreenImage() {
		return potionGreenImage;
	}
	
	public BufferedImage getPotionBlueImage() {
		return potionBlueImage;
	}
	
	public BufferedImage getPotionPurpleImage() {
		return potionPurpleImage;
	}
	
	public BufferedImage getLoadBar1Image() {
		return loadBar1Image;
	}
	
	public BufferedImage getLoadBar2Image() {
		return loadBar2Image;
	}
	
	public BufferedImage getLoadBar3Image() {
		return loadBar3Image;
	}
	
	public BufferedImage getLoadBarBGImage() {
		return loadBarBGImage;
	}
	
	public BufferedImage getLoadBarTextImage() {
		return loadBarTextImage;
	}
	
	public BufferedImage getQuestionCloudImage() {
		return questionCloudImage;
	}
	
	public BufferedImage getLockImage() {
		return lockImage;
	}
	
	public BufferedImage getUpgradeHpImage() {
		return upgradeHpImage;
	}
	
	public BufferedImage getUpgradeSpeedImage() {
		return upgradeSpeedImage;
	}
	
	public BufferedImage getLightningImage() {
		return lightningImage;
	}
	
	public BufferedImage getPausedHeaderImage() {
		return pausedHeaderImage;
	}
	
	public BufferedImage getPausedTextImage() {
		return pausedTextImage;
	}
	
	public BufferedImage getAngryImage() {
		return angryImage;
	}
	
	public BufferedImage getSpawnAreaImage() {
		return spawnAreaImage;
	}
}
