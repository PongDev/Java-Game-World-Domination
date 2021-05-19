package config;

import logic.GameState;
import utility.Position;
import utility.ResourceManager.ImageResource;
import weapon.Gun;
import weapon.Weapon;

public class Config {
	public static final int SCREEN_W = 800;
	public static final int SCREEN_H = 500;

	public static final String TITLE_LABEL1 = "That Time I Got Reincarnated as a";
	public static final String TITLE_LABEL2 = "World Dominator";
	public static final int TITLE_BTN_W = 200;
	public static final int TITLE_BTN_H = 50;
	public static final int TITLE_BTN_SPACING = 15;

	public static final int MODE_SELECT_BTN_SIZE = 50;

	public static final String GAME_TITLE = "That Time I Got Reincarnated as a World Dominator";

	public static final String PAUSE_MESSAGE = "Game Pause";

	public static final boolean ENABLE_LOGGER = true;

	public static final String FONT_PATH = "font/Berlin_Sans_FB_Regular.ttf";

	public static final int TILE_W = 48;
	public static final int TILE_H = 48;

	public static final int CHARACTER_W = 48;
	public static final int CHARACTER_H = 48;

	public static final int MAIN_CHARACTER_INITIAL_MAX_HEALTH = 5;
	public static final int MAIN_CHARACTER_INITIAL_DEFENSE = 0;
	public static final int MAIN_CHARACTER_INITIAL_SPD = 2;
	public static final int MAIN_CHARACTER_TEAM = 0;
	public static final Weapon MAIN_CHARACTER_INITIAL_WEAPON = new Gun(ImageResource.GUN_AK47, 1, 5,
			ImageResource.BULLET, 10, 10, 10, Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER);

	public static final int ENEMY_TEAM = 1;
	public static final double ENEMY_DISPERSION = 40;
	public static final int ENEMY_ZERO_SUM_MOVING_VECTOR_TIME_ALLOW = 1000;
	public static final int ENEMY_SUSPEND_MOVING_VECTOR_UPDATE_TIME = 10000;

	public static final int SCREEN_TILE_W = 16;
	public static final int SCREEN_TILE_H = 10;

	public static final String MAP_PATH = "map/map_test_2.csv";

	public static final int ZINDEX_MAP = 0;
	public static final int ZINDEX_MAIN_CHARACTER = 10;
	public static final int ZINDEX_ENEMY = 10;

	public static final Position SPAWN_CENTER = new Position((int) (Config.TILE_W * 2.5),
			(GameState.getMapHeight() * Config.TILE_H) / 2);

	public static final double SOUND_VOLUME = 0.025;
}
