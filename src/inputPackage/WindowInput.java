package inputPackage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gamePackage.GameSetting;

public class WindowInput extends WindowAdapter {
	
	private GameSetting gameSetting;
	
	public WindowInput(GameSetting gameSetting) {
		this.gameSetting = gameSetting;
	}
	
	public void windowClosing(WindowEvent e) {
		gameSetting.update();
		System.exit(0);
	}
}
