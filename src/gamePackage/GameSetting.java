package gamePackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import playerPackage.Player;

public class GameSetting {
	
	public static int playerImage = 1;
	public static boolean[] playerUnlock = {true, true, true};
	// 									 HP speed level max level max
	public static int[] player1Upgrade = {0, 0, 0, 0, 0, 0}; // max 3
	public static int[] player2Upgrade = {0, 0, 0, 0, 0, 0}; // max 3
	public static int[] player3Upgrade = {0, 0, 0, 0, 0, 0}; // max 3
	public static int[] player4Upgrade = {0, 0, 0, 0, 0, 0}; // max 3
	public static int coinNumber = 0;
	public static boolean showTrail = true;
	public static boolean showPlayerHpNumber = true;
	public static boolean showPlayerCollisionBox = false;
	public static boolean showEnemyCollisionBox = false;
	public static boolean soundOn = true;
	public static boolean musicOn = true;
	
	public void reset() {
		playerImage = 1;
		for(int i = 0; i < 3; i++) {
			playerUnlock[i] = true;
		}
		for(int i = 0; i < 6; i++) {
			player1Upgrade[i] = 0;
			player2Upgrade[i] = 0;
			player3Upgrade[i] = 0;
			player4Upgrade[i] = 0;
		}
		coinNumber = 0;
		showPlayerHpNumber = true;
		showPlayerCollisionBox = false;
		showEnemyCollisionBox = false;
		showTrail = true;
		soundOn = true;
		musicOn = true;
	}

	private String path = ".//setting_txt//setting.txt";
	
	public void set() {
		try {
			Scanner filein = new Scanner(new FileInputStream(path));
			while(filein.hasNextLine()) {
				String input = filein.nextLine();
				String[] kv = input.split(",");
				switch(kv[0]) {
				case "playerImage": playerImage = Integer.parseInt(kv[1]); break;
				case "playerUnlock": for(int i = 0; i < 3; i++) {
					playerUnlock[i] = Boolean.parseBoolean(kv[i+1]);} break;
				case "player1": for(int i = 0; i < 6; i++) {
					player1Upgrade[i] = Integer.parseInt(kv[i+1]);
				}
				case "player2": for(int i = 0; i < 6; i++) {
					player2Upgrade[i] = Integer.parseInt(kv[i+1]);
				}
				case "player3": for(int i = 0; i < 6; i++) {
					player3Upgrade[i] = Integer.parseInt(kv[i+1]);
				}
				case "player4": for(int i = 0; i < 6; i++) {
					player4Upgrade[i] = Integer.parseInt(kv[i+1]);
				}
				case "coinNumber": coinNumber = Integer.parseInt(kv[1]); break;
				case "showTrail": showTrail = Boolean.parseBoolean(kv[1]); break;
				case "showPlayerHpNumber": showPlayerHpNumber = Boolean.parseBoolean(kv[1]); break;
				case "showPlayerCollisionBox": showPlayerCollisionBox = Boolean.parseBoolean(kv[1]); break;
				case "showEnemyCollisionBox": showEnemyCollisionBox = Boolean.parseBoolean(kv[1]); break;
				case "soundOn": soundOn = Boolean.parseBoolean(kv[1]); break;
				case "musicOn": musicOn = Boolean.parseBoolean(kv[1]); break;
				}
			}
			filein.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream(path));
			writer.println("playerImage" + "," + playerImage);
			writer.println("playerUnlock" + "," + Boolean.toString(playerUnlock[0]) + "," + Boolean.toString(playerUnlock[1])+ "," + Boolean.toString(playerUnlock[2]));
			writer.println("player1" + "," + player1Upgrade[0] + "," + player1Upgrade[1] + "," + player1Upgrade[2] + "," + player1Upgrade[3] + "," + player1Upgrade[4] + "," + player1Upgrade[5]);
			writer.println("player2" + "," + player2Upgrade[0] + "," + player2Upgrade[1] + "," + player2Upgrade[2] + "," + player2Upgrade[3] + "," + player2Upgrade[4] + "," + player2Upgrade[5]);
			writer.println("player3" + "," + player3Upgrade[0] + "," + player3Upgrade[1] + "," + player3Upgrade[2] + "," + player3Upgrade[3] + "," + player3Upgrade[4] + "," + player3Upgrade[5]);
			writer.println("player4" + "," + player4Upgrade[0] + "," + player4Upgrade[1] + "," + player4Upgrade[2] + "," + player4Upgrade[3] + "," + player4Upgrade[4] + "," + player4Upgrade[5]);
			writer.println("coinNumber" + "," + coinNumber);
			writer.println("showTrail" + "," + Boolean.toString(showTrail));
			writer.println("showPlayerHpNumber" + "," + Boolean.toString(showPlayerHpNumber));
			writer.println("showPlayerCollisionBox" + "," + Boolean.toString(showPlayerCollisionBox));
			writer.println("showEnemyCollisionBox" + "," + Boolean.toString(showEnemyCollisionBox));
			writer.println("soundOn" + "," + Boolean.toString(soundOn));
			writer.println("musicOn" + "," + Boolean.toString(musicOn));
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
