package gamePackage;

import effectPackage.Clamper;
import playerPackage.Player;
import themePackage.Game;
import themePackage.Game.GameLevel;
import themePackage.Game.GameMode;

public class GameCamera {
	
	private float x, y;
	
	public GameCamera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(Player player) {
		x += ((player.getX()+24 - x) - GameMain.WIDTH/2) * 0.1f;
		y += ((player.getY()+24 - y) - GameMain.HEIGHT/2) * 0.1f;
		
		if(Game.gameMode == GameMode.normalMode) {
			if(Game.gameLevel == GameLevel.Level1 || Game.gameLevel == GameLevel.Level2 || Game.gameLevel == GameLevel.Level3 || Game.gameLevel == GameLevel.Level4 || Game.gameLevel == GameLevel.Level5) {
				x = Clamper.clamp(x, 0, 500);
				y = Clamper.clamp(y, 0, 427);
			} else if(Game.gameLevel == GameLevel.LevelTest) {
				x = Clamper.clamp(x, 210, 290);
				y = Clamper.clamp(y, 0, 427);
			} else if(Game.gameLevel == GameLevel.LevelShop) {
				x = Clamper.clamp(x, 0, 20);
				y = Clamper.clamp(y, 0, 8);
			}
		} else if(Game.gameMode == GameMode.platformerMode) {
			x = Clamper.clamp(x, 0, 500);
			y = Clamper.clamp(y, 0, 400);
		}
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
