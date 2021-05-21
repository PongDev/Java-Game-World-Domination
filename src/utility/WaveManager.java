package utility;

import java.util.ArrayList;
import java.util.Date;

import character.Enemy;
import config.Config;
import gui.GameMap;
import logic.GameState;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import weapon.Gun;
import weapon.Weapon;

public class WaveManager {

	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private static int[] enemyPerWaveList = { 3, 3, 4, 4, 5, 5, 5, 6, 6, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 16, 17,
			17, 18, 18, 19, 19, 20, 20, 21, 25 };
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

		if (GameState.getGameMode().getGameModeName() == "Normal") {
			for (int i = 1; i <= enemyPerWaveList[wave - 1]; i++) {
				spawnEnemy(i);
			}
		} else if (GameState.getGameMode().getGameModeName() == "Endless"){
			int enemyPerWave = wave;
			if (wave % 10 == 0)
				enemyPerWave += 3;
			for (int i = 1; i <= enemyPerWave; i++) {
				spawnEnemy(i);
			}
		}
		Logger.log("Start Wave " + wave);
	}

	private static void spawnEnemy(int i) {
		int randomSpawnTile = (int) (Math.random() * GameMap.getEnemySpawnableTile().size());
		Position spawnLocation = new Position(
				(int) (GameMap.getEnemySpawnableTile().get(randomSpawnTile).X * Config.TILE_W + (Config.TILE_W / 2)),
				(int) (GameMap.getEnemySpawnableTile().get(randomSpawnTile).Y * Config.TILE_H) + (Config.TILE_H / 2));

		if(i == 1 && wave % 5 == 0) {
			enemyList.add(new Enemy(ImageResource.SPRITE_ELITE_KNIGHT, Config.CHARACTER_W, Config.CHARACTER_H,
					"Elite Knight", 10, 0, 1, new Gun(ImageResource.GUN_AK47, 1, 5, ImageResource.ENEMY_BULLET, 15, 10, 10,
					Config.ENEMY_TEAM, Config.ZINDEX_ENEMY),Config.ENEMY_TEAM, 1000, spawnLocation));
		}
		else {
			int randomEnemyType = (Math.random() > 0.7 ? 1 : 0);
			if(randomEnemyType == 0) {
				enemyList.add(new Enemy(ImageResource.SPRITE_KNIGHT_SMG, Config.CHARACTER_W, Config.CHARACTER_H,
						"Knight Rifle", 3, 0, 1, new Gun(ImageResource.GUN_AK47, 1, 2, ImageResource.ENEMY_BULLET, 10, 10, 10,
								Config.ENEMY_TEAM, Config.ZINDEX_ENEMY),Config.ENEMY_TEAM, 1000, spawnLocation));
			}
			else if(randomEnemyType == 1) {
				enemyList.add(new Enemy(ImageResource.SPRITE_KNIGHT_SWORD, Config.CHARACTER_W, Config.CHARACTER_H,
						"Knight Sword", 2, 0, 2, new Gun(ImageResource.GUN_SHOTGUN, 1, 1, ImageResource.ENEMY_BULLET, 5, 10, 10,
								Config.ENEMY_TEAM, Config.ZINDEX_ENEMY),Config.ENEMY_TEAM, 1000, spawnLocation));
			}
		}
		Logger.log("Spawn enemy at " + GameMap.getEnemySpawnableTile().get(randomSpawnTile).X + " "
				+ GameMap.getEnemySpawnableTile().get(randomSpawnTile).Y);

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

	public static boolean isWaveEnd() {
		return isWaveEnd;
	}

	public static int forceStartNewWave() {
		if (isWaveEnd) {
			int timeLeft = (int) (Config.DELAY_BETWEEN_WAVE - ((new Date()).getTime() - waveEndTimestamp)) / 1000;

			waveEndTimestamp = (new Date()).getTime() - Config.DELAY_BETWEEN_WAVE;
			return timeLeft;
		} else {
			return 0;
		}
	}
}
