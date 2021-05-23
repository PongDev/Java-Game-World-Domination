package config;

import logic.GameState;
import utility.Position;
import weapon.Pistol;
import weapon.Weapon;

/**
 * Contains Game Config
 */
public class Config {
	/**
	 * Screen Width
	 */
	public static final int SCREEN_W = 800;
	/**
	 * Screen Height
	 */
	public static final int SCREEN_H = 500;
	/**
	 * Game Name First Half
	 */
	public static final String TITLE_LABEL1 = "That Time I Got Reincarnated as a";
	/**
	 * Game Name Second Half
	 */
	public static final String TITLE_LABEL2 = "World Dominator";
	/**
	 * Title Scene Pane Button Width
	 */
	public static final int TITLE_BTN_W = 200;
	/**
	 * Title Scene Pane Button Height
	 */
	public static final int TITLE_BTN_H = 50;
	/**
	 * Title Scene Pane Button Spacing
	 */
	public static final int TITLE_BTN_SPACING = 15;
	/**
	 * Mode Select Scene Pane Button Size
	 */
	public static final int MODE_SELECT_BTN_SIZE = 50;
	/**
	 * Full Game Name
	 */
	public static final String GAME_TITLE = "That Time I Got Reincarnated as a World Dominator";
	/**
	 * Text To Display When Pause
	 */
	public static final String PAUSE_MESSAGE = "Game Pause";
	/**
	 * Is Logger Enable
	 */
	public static final boolean ENABLE_LOGGER = true;
	/**
	 * Path Of Game Font
	 */
	public static final String FONT_PATH = "font/Berlin_Sans_FB_Regular.ttf";
	/**
	 * Tile Width
	 */
	public static final int TILE_W = 48;
	/**
	 * Tile Height
	 */
	public static final int TILE_H = 48;

	/**
	 * Character Width
	 */
	public static final int CHARACTER_W = 48;
	/**
	 * Character Height
	 */
	public static final int CHARACTER_H = 48;
	/**
	 * Main Character Initial Max Health
	 */
	public static final int MAIN_CHARACTER_INITIAL_MAX_HEALTH = 100;
	/**
	 * Main Character Initial Defense
	 */
	public static final int MAIN_CHARACTER_INITIAL_DEFENSE = 0;
	/**
	 * Main Character Initial Speed
	 */
	public static final int MAIN_CHARACTER_INITIAL_SPD = 2;
	/**
	 * Main Character Team
	 */
	public static final int MAIN_CHARACTER_TEAM = 0;
	/**
	 * Main Character Initial Weapon
	 */
	public static final Weapon MAIN_CHARACTER_INITIAL_WEAPON = new Pistol(Config.MAIN_CHARACTER_TEAM,
			Config.ZINDEX_MAIN_CHARACTER);
	/**
	 * Main Character Tower Deploy Range
	 */
	public static final int MAIN_CHARACTER_DEPLOY_RANGE = Math.max(TILE_W, TILE_H) * 3;
	/**
	 * Main Character Open Shop Range
	 */
	public static final int MAIN_CHARACTER_OPEN_SHOP_RANGE = Math.max(TILE_W, TILE_H) * 3;
	/**
	 * Tower Shooting Dispersion
	 */
	public static final double TOWER_DISPERSION = 40;

	/**
	 * Enemy Team
	 */
	public static final int ENEMY_TEAM = 1;
	/**
	 * Enemy Shooting Dispersion
	 */
	public static final double ENEMY_DISPERSION = 40;
	/**
	 * Enemy Zero Sum Moving Vector Time Allow
	 */
	public static final int ENEMY_ZERO_SUM_MOVING_VECTOR_TIME_ALLOW = 1000;
	/**
	 * Enemy Suspend Moving Vector Update Time
	 */
	public static final int ENEMY_SUSPEND_MOVING_VECTOR_UPDATE_TIME = 10000;

	/**
	 * Display Wave Text Time Duration
	 */
	public static final int DISPLAY_WAVE_TEXT_DURATION = 2000;
	/**
	 * Time Delay Between Wave
	 */
	public static final int DELAY_BETWEEN_WAVE = 10000;
	/**
	 * Ratio To Convert Time Left Into Money When Player Force Start New Wave
	 */
	public static final int DELAY_BETWEEN_WAVE_TO_MONEY = 1;
	/**
	 * Minimum Enemy Spawn Time Delay
	 */
	public static final int MINIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY = 400;
	/**
	 * Maximum Enemy Spawn Time Delay
	 */
	public static final int MAXIMUM_WAVE_ENEMY_SPAWN_TIME_DELAY = 1000;

	/**
	 * MaxiMum Row Of Tile Player Can See On Screen
	 */
	public static final int SCREEN_TILE_W = 16;
	/**
	 * MaxiMum Column Of Tile Player Can See On Screen
	 */
	public static final int SCREEN_TILE_H = 10;
	/**
	 * Map Path
	 */
	public static final String MAP_PATH = "map/map_test_3.csv";

	/**
	 * Map Z Index
	 */
	public static final int ZINDEX_MAP = 0;
	/**
	 * Tower Z Index
	 */
	public static final int ZINDEX_TOWER = 9;
	/**
	 * Main Character Z Index
	 */
	public static final int ZINDEX_MAIN_CHARACTER = 10;
	/**
	 * Enemy Z Index
	 */
	public static final int ZINDEX_ENEMY = 9;

	/**
	 * Text Inform Player To Press [SPACE] To Change Cutscene
	 */
	public static final String SKIP_TEXT = "Press [SPACE] to continue";

	/**
	 * Main Character Spawn Point
	 */
	public static final Position SPAWN_CENTER = new Position((int) (Config.TILE_W * 2.5),
			(GameState.getMapHeight() * Config.TILE_H) / 2);

	/**
	 * Item DIsplay Per Page in Shop
	 */
	public static final int SHOP_ITEM_PER_PAGE = 8;

	/**
	 * Background Music Sound Volume
	 */
	public static final double SOUND_VOLUME = 0.05;
}
