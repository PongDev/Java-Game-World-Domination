package utility;

import java.util.ArrayList;
import java.util.Date;

import character.Enemy;
import config.Config;
import gui.GameMap;
import logic.GameState;
import sound.SoundManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import weapon.Gun;

public class WaveManager {

	/**
	 * ArrayList Contains All Enemy Spawned In Current Wave
	 */
	private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	/**
	 * ArrayList Contains Number Of Enemy Per Wave For Normal Mode
	 */
	private static int[] enemyPerWaveList = { 1, 2, 2, 3, 3, 4, 4, 5, 5, 5, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 13,
			13, 14, 14, 15, 15, 16, 16, 17, 17, 0 };
	/**
	 * Wave Amount
	 */
	private static int wave = 0;
	/**
	 * Text To Display When Start Wave
	 */
	private static String displayWaveText = "";
	/**
	 * Display Wave Text Timestamp
	 */
	private static long displayWaveTextTimestamp = 0;
	/**
	 * Wave End Timestamp
	 */
	private static long waveEndTimestamp = 0;
	/**
	 * Enemy Spawn Time Delay
	 */
	private static long enemySpawnTimeDelay = 0;
	/**
	 * Last Enemy Spawn Timestamp
	 */
	private static long lastEnemySpawnTime = 0;
	/**
	 * Is Wave End
	 */
	private static boolean isWaveEnd = false;
	/**
	 * Is Pause Between Wave End
	 */
	private static boolean isPauseBetweenWaveEnd = false;
	/**
	 * Is Spawning Enemy
	 */
	private static boolean isSpawningEnemy = false;
	/**
	 * Has Elite Spawn In Current Wave
	 */
	private static boolean hasEliteSpawn = false;
	/**
	 * Total Enemy Need To Spawn Per Wave
	 */
	private static int enemyPerWave;
	/**
	 * Amount Of Enemy That Has Spawned In Current Wave
	 */
	private static int enemySpawnedInCurrentWave;

	/**
	 * Initialize Value
	 */
	public static void initialize() {
		wave = 0;
		displayWaveText = "";
		displayWaveTextTimestamp = 0;
		startNewWave();
	}

	/**
	 * Start New Wave, Set IsWaveEnd To False
	 */
	private static void startNewWave() {
		isWaveEnd = false;
		wave += 1;
		hasEliteSpawn = false;
		// End game
		if (GameState.getGameMode().getGameModeName() == "Normal" && wave == 31) {
			SoundManager.playSoundEffect(SoundResource.ENDING_GOOD, 0.5);
			GameState.setSceneResource(SceneResource.ENDING_GOOD);
		}

		displayWaveText = "Wave " + Integer.toString(WaveManager.getWave());
		displayWaveTextTimestamp = (new Date()).getTime();

		if (GameState.getGameMode().getGameModeName() == "Normal") {
			enemyPerWave = enemyPerWaveList[wave - 1];
		} else if (GameState.getGameMode().getGameModeName() == "Endless") {
			enemyPerWave = (wave % 10 == 0) ? wave + 3 : wave;
		}
		enemySpawnedInCurrentWave = 0;
		lastEnemySpawnTime = (new Date()).getTime();
		enemySpawnTimeDelay = (long) (Config.MINIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY + (Math.random()
				* (Config.MAXIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY - Config.MINIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY)));
		isSpawningEnemy = true;

		Logger.log("Start Wave " + wave);
	}

	/**
	 * Spawn An Enemy According To Value From randomEnemyType 
	 */
	private static void spawnEnemy() {
		int randomSpawnTile = (int) (Math.random() * GameMap.getEnemySpawnableTile().size());
		Position spawnLocation = new Position(
				(int) (GameMap.getEnemySpawnableTile().get(randomSpawnTile).X * Config.TILE_W + (Config.TILE_W / 2)),
				(int) (GameMap.getEnemySpawnableTile().get(randomSpawnTile).Y * Config.TILE_H) + (Config.TILE_H / 2));

		ImageResource enemyImageResource, gunImageResource;
		String name;
		int health, armor, speed, attackDamage, bulletSpeed, moneyDrop;
		double attackSpeed;

		int enemyType = randomEnemyType();

		switch (enemyType) {
		case 0:
			enemyImageResource = ImageResource.SPRITE_KNIGHT;
			gunImageResource = ImageResource.GUN_AK47;
			name = "Knight Rifle";
			health = calculateStat(60);
			armor = 0;
			speed = 1;
			attackDamage = calculateStat(10);
			attackSpeed = 0.75;
			bulletSpeed = 3;
			moneyDrop = 3;
			break;
		case 1:
			enemyImageResource = ImageResource.SPRITE_KNIGHT;
			gunImageResource = ImageResource.GUN_PISTOL;
			name = "Knight Pistol";
			health = calculateStat(40);
			armor = 0;
			speed = 2;
			attackDamage = calculateStat(10);
			attackSpeed = 0.5;
			bulletSpeed = 3;
			moneyDrop = 2;
			break;
		case 2:
			enemyImageResource = ImageResource.SPRITE_ELITE_KNIGHT;
			gunImageResource = ImageResource.GUN_AK47;
			name = "Elite Knight";
			health = calculateStat(120);
			armor = 0;
			speed = 1;
			attackDamage = calculateStat(14);
			attackSpeed = 1;
			bulletSpeed = 5;
			moneyDrop = calculateStat(6);
			break;
		case 3:
			enemyImageResource = ImageResource.SPRITE_ELITE_KNIGHT;
			gunImageResource = ImageResource.GUN_MACHINEGUN;
			name = "Super Elite Knight";
			health = calculateStat(120) * 4;
			armor = 0;
			speed = 1;
			attackDamage = calculateStat(14);
			attackSpeed = 1.5;
			bulletSpeed = 5;
			moneyDrop = calculateStat(30);
			break;
		default:
			enemyImageResource = ImageResource.SPRITE_KNIGHT;
			gunImageResource = ImageResource.GUN_AK47;
			name = "Knight Rifle";
			health = calculateStat(60);
			armor = 0;
			speed = 1;
			attackDamage = calculateStat(10);
			attackSpeed = 1;
			bulletSpeed = 3;
			moneyDrop = 3;
			break;
		}
		enemyList
				.add(new Enemy(enemyImageResource, Config.CHARACTER_W, Config.CHARACTER_H, name, health, armor, speed,
						new Gun(gunImageResource, attackDamage, attackSpeed, ImageResource.ENEMY_BULLET, bulletSpeed,
								10, 10, Config.ENEMY_TEAM, Config.ZINDEX_ENEMY),
						Config.ENEMY_TEAM, moneyDrop, spawnLocation));
	}

	/**
	 * Calculate Enemy Stat By Wave Number
	 * @param normalStat Enemy Stat In First Wave
	 * @return Enemy Stat Of Current Wave
	 */
	public static int calculateStat(int normalStat) {
		return normalStat + ((normalStat / 2) * (wave / 10));
	}

	/**
	 * Random Enemy Type Algorithm
	 * @return Integer That Represent Enemy Type
	 */
	public static int randomEnemyType() {
		int randomEnemyType = (Math.random() > 0.7 ? 1 : 0);
		if (!hasEliteSpawn) {
			if (wave % 10 == 0) {
				randomEnemyType = 3;
				hasEliteSpawn = true;
			} else if ((wave == 5 || wave == 15)) {
				randomEnemyType = 2;
				hasEliteSpawn = true;
			}
		} else if (wave > 20 && randomEnemyType == 1) {
			randomEnemyType = 1 + (Math.random() > 0.7 ? 1 : 0);
		}
		return randomEnemyType;
	}

	/**
	 * Getter Of Wave
	 * @return Wave
	 */
	public static int getWave() {
		return wave;
	}

	/**
	 * Getter Of DisplayWaveText
	 * @return DisplayWaveText
	 */
	public static String getDisplayWaveText() {
		return displayWaveText;
	}

	/**
	 * Spawn Enemy When Start Wave, Check If Wave Has End
	 */
	public static void update() {
		if (GameState.getSceneResource() == SceneResource.PLAYING) {
			if (!GameState.isPause()) {
				if (isSpawningEnemy == true && (new Date()).getTime() - lastEnemySpawnTime > enemySpawnTimeDelay) {
					spawnEnemy();
					enemySpawnedInCurrentWave += 1;
					lastEnemySpawnTime = (new Date()).getTime();
					enemySpawnTimeDelay = (long) (Config.MINIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY + (Math.random()
							* (Config.MAXIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY - Config.MINIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY)));
					if (enemySpawnedInCurrentWave == enemyPerWave) {
						isSpawningEnemy = false;
					}
				}

				if (isPauseBetweenWaveEnd) {
					waveEndTimestamp += GameState.getLastPauseDulation();
					isPauseBetweenWaveEnd = false;
				}
				for (int i = enemyList.size() - 1; i >= 0; i--) {
					if (enemyList.get(i).isDestroyed()) {
						enemyList.remove(i);
					}
				}
				if (!isWaveEnd && enemyList.isEmpty() && enemySpawnedInCurrentWave == enemyPerWave) {
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

	/**
	 * Getter Of IsWaveEnd
	 * @return IsWaveEnd
	 */
	public static boolean isWaveEnd() {
		return isWaveEnd;
	}

	/**
	 * Force Start NewWave
	 * @return Time Left Before New Wave Start Normally
	 */
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
