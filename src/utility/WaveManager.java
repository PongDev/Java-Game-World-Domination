package utility;

import java.util.ArrayList;
import java.util.Date;

import character.Enemy;
import config.Config;
import logic.GameState;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Gun;

public class WaveManager {

	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private static int wave = 0;
	private static String displayWaveText = "";
	private static long displayWaveTextTimestamp = 0;
	private static long waveEndTimestamp = 0;
	private static boolean isWaveEnd = false;
	private static boolean isPauseBetweenWaveEnd = false;

	public static void initialize() {
		wave = 0;
		displayWaveText = "";
		displayWaveTextTimestamp = 0;
		startNewWave();
	}

	private static void startNewWave() {
		isWaveEnd = false;
		wave += 1;
		displayWaveText = "Wave " + Integer.toString(WaveManager.getWave());
		displayWaveTextTimestamp = (new Date()).getTime();
		for (int i = 1; i <= wave; i++) {
			testSpawnEnemy(i);
		}
		Logger.log("Start Wave " + wave);
	}

	private static void testSpawnEnemy(int i) {
		// TODO random enemy spawn point

		enemyList.add(new Enemy(ImageResource.SPRITE_KNIGHT_SWORD, Config.CHARACTER_W, Config.CHARACTER_H,
				"Knight Sword", 3, 0, 1,
				new Gun(ImageResource.GUN_AK47, 1, 2, ImageResource.ENEMY_BULLET, 10, 10, 10, Config.ENEMY_TEAM,
						Config.ZINDEX_ENEMY),
				Config.ENEMY_TEAM, 1000,
				new Position((int) (Config.TILE_W * 2 * i), (GameState.getMapHeight() * Config.TILE_H) / 2)));
	}

	public static int getWave() {
		return wave;
	}

	public static String getDisplayWaveText() {
		return displayWaveText;
	}

	public static void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				if (isPauseBetweenWaveEnd) {
					waveEndTimestamp += GameState.getLastPauseDulation();
					isPauseBetweenWaveEnd = false;
				}
				for (int i = enemyList.size() - 1; i >= 0; i--) {
					if (enemyList.get(i).isDestroyed()) {
						enemyList.remove(i);
					}
				}
				if (!isWaveEnd && enemyList.isEmpty()) {
					Logger.log(String.format("Wave %d End", wave));
					isWaveEnd = true;
					waveEndTimestamp = (new Date()).getTime();
				}
				if (isWaveEnd) {
					displayWaveText = String.format("Wave %d Begin In %d Second(s)", wave + 1,
							(Config.DELAY_BETWEEN_WAVE - ((new Date()).getTime() - waveEndTimestamp)) / 1000);
					if ((new Date()).getTime() - waveEndTimestamp >= Config.DELAY_BETWEEN_WAVE) {
						startNewWave();
					}
				}
				if (!isWaveEnd
						&& (new Date()).getTime() - displayWaveTextTimestamp >= Config.DISPLAY_WAVE_TEXT_DURATION) {
					displayWaveText = "";
				}
			} else if (isWaveEnd == true) {
				isPauseBetweenWaveEnd = true;
			}
		}
	}
}
