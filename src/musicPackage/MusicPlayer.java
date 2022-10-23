package musicPackage;

import gamePackage.GameMain;
import gamePackage.GameSetting;
import gamePackage.GameState;

public class MusicPlayer {
	
	public static void pressSoundBtn() {
		if(GameSetting.soundOn) GameSetting.soundOn = false;
		else GameSetting.soundOn = true;
	}
	
	public static void pressMusicBtn() {
		if(GameSetting.musicOn) {GameSetting.musicOn = false; stopBGMSound(); } 
		else {GameSetting.musicOn = true; playMenuBGMSound();}
	}
	
	public static void playClickSound() {
		playSound("/musicPackage/click_sound.wav");
	}
	
	public static void playShootSound() {
		playSound("/musicPackage/shoot_sound.wav");
	}
	
	public static void playBounceShootSound() {
		playSound("/musicPackage/bounce.wav");
	}
	
	public static void playCurveShootSound() {
		playSound("/musicPackage/curve.wav");
	}
	
	public static void playSatelliteShootSound() {
		playSound("/musicPackage/satellite.wav");
	}
	
	public static void playTrapShootSound() {
		playSound("/musicPackage/trap.wav");
	}
	
	public static void playCoinSound() {
		playSound("/musicPackage/coin_sound.wav");
	}
	
	public static void playLostSound() {
		playBGM("/musicPackage/lost_sound.wav", 0);
	}
	
	public static void playMarioDieSound() {
		playBGM("/musicPackage/marioDie_sound.wav", 0);
	}
	
	public static void playMarioWonSound() {
		playBGM("/musicPackage/marioWon_sound.wav", 0);
	}
		
	public static void playMenuBGMSound() {
		playBGM("/musicPackage/menuBGM_sound.wav");
	}
	
	public static void playGameBGMSound() {
		playBGM("/musicPackage/gameBGM_sound.wav");
	}
	
	public static void playBossBGMSound() {
		playBGM("/musicPackage/bossBGM_sound.wav");
	}
	
	
	//---------- play ----------//
	
	private static void playSound(String s) {
		if(GameSetting.soundOn) {
			SoundPlayer.effect = new SoundPlayer(s);
			SoundPlayer.effect.play();
		}
	}
	
	private static void playBGM(String s) {
		if(GameSetting.musicOn) {
			stopBGMSound();
			BgmPlayer.background = new BgmPlayer(s);
			BgmPlayer.background.play();
		}
	}
	
	private static void playBGM(String s, int n) {
		if(GameSetting.musicOn) {
			stopBGMSound();
			BgmPlayer.background = new BgmPlayer(s, n);
			BgmPlayer.background.play();
		}
	}

	//---------- stop ----------//
	
	public static void stopSound() {
		if(SoundPlayer.effect != null) {
			SoundPlayer.effect.stop();
		}
	}
	
	public static void stopBGMSound() {
		if(BgmPlayer.background != null) {
			BgmPlayer.background.stop();
		}
	}
}
