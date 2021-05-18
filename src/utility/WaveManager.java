package utility;

import java.util.Date;

import character.Enemy;
import config.Config;
import gui.PlayScenePane;
import javafx.scene.control.Label;
import logic.GameState;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Gun;

public class WaveManager {
	private static int wave = 0;
	public static int enemyPerWave = 0;
	public static int enemyDied = 0;
	public static long time = 0;
	public static boolean isDisplayWaveText = false;

	public static void startNewWave() {
		wave += 1;
		enemyPerWave = wave; // placeholder
		enemyDied = 0;
		for (int i = 1; i <= enemyPerWave; i++) {
			testSpawnEnemy(i);
		}
		Logger.log("start wave " + wave);
		isDisplayWaveText = true;
		time = (new Date()).getTime();
	}

	public static void testSpawnEnemy(int i) {
		// TODO random enemy spawn point

		Enemy test = new Enemy(ImageResource.SPRITE_KNIGHT_SWORD, Config.CHARACTER_W, Config.CHARACTER_H,
				"Knight Sword", 3, 0, 1,
				new Gun(ImageResource.GUN_AK47, 1, 2, ImageResource.ENEMY_BULLET, 10, 10, 10, Config.ENEMY_TEAM,
						Config.ZINDEX_ENEMY),
				Config.ENEMY_TEAM,
				new Position((int) (Config.TILE_W * 2 * i), (GameState.getMapHeight() * Config.TILE_H) / 2));
	}

	public static int getWave() {
		return wave;
	}
}
