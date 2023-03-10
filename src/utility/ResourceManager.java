package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import character.MainCharacter;
import config.Config;
import gui.BadEndingScenePane;
import gui.BeginningLorePane;
import gui.EndCreditScenePane;
import gui.GameMap;
import gui.GoodEndingScenePane;
import gui.ModeSelectScenePane;
import gui.PlayScenePane;
import gui.Shop;
import gui.TitleScenePane;
import input.InputManager;
import item.Buyable;
import item.HealthPotion;
import item.PistolAmmo;
import item.RifleAmmo;
import item.ShotgunAmmo;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import logic.GameState;
import object.GameObject;
import render.RenderManager;
import tower.BarricadeTower;
import tower.MachineGunTower;
import tower.SniperTower;
import update.Updatable;
import update.UpdateManager;
import weapon.AK47;
import weapon.DesertEagle;
import weapon.DoubleBarrel;
import weapon.MachineGun;
import weapon.Shotgun;
import weapon.Sniper;

/**
 * Resource Manager: Management All Game Resource
 */
public class ResourceManager {

	/**
	 * In-Game Image Resource Enumerator
	 */
	public enum ImageResource {
		BG_TITLE, INFO_NORMALMODE, INFO_ENDLESSMODE, BTN, BTN_HOVER, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY,
		BTN_BACK, BTN_NEXT, BTN_PREVIOUS, TILE_FLOOR, TILE_FLOOR_1, TILE_FLOOR_2, TILE_WALL, TILE_UNWALKABLE_FLOOR,
		TILE_UNPLACABLE_FLOOR, TILE_GATE_CLOSE, TILE_SHOP, CHARACTER_MAIN, BULLET, ENEMY_BULLET, GUN_PISTOL,
		GUN_DESERTEAGLE, GUN_AK47, GUN_DOUBLEBARREL, GUN_SHOTGUN, GUN_SNIPER, GUN_MACHINEGUN, SPRITE_KNIGHT,
		SPRITE_ELITE_KNIGHT, CROSS_HAIR, AMMO_PANE, HEALTH_POTION_PANE, STATUS_PANE, HEALTH_POTION, ITEM_BUTTON,
		ITEM_BUTTON_TRANSPARENT, ITEM_DESCRIPTION, BARRIER_TOWER, MACHINE_GUN_TOWER, MACHINE_GUN_TOWER_BASE,
		MACHINE_GUN_TOWER_HEAD, SNIPER_TOWER, SNIPER_TOWER_BASE, SNIPER_TOWER_HEAD, AMMO_PISTOL, AMMO_RIFLE,
		AMMO_SHOTGUN, COIN, BEGINNING_LORE_1, BEGINNING_LORE_2, BEGINNING_LORE_3, BEGINNING_LORE_4, BEGINNING_LORE_5,
		ENDING_BAD, ENDING_GOOD_1, GAME_CONTROL, BEGINNING_GAME_CONTROL, END_CREDIT
	}

	/**
	 * In-Game Sound Resource Enumerator
	 */
	public enum SoundResource {
		TITLE, PLAYING, CAR_CRASH, ENDING_BAD, ENDING_GOOD, GUN_PISTOL, GUN_DESERTEAGLE, GUN_AK47, GUN_DOUBLEBARREL,
		GUN_SHOTGUN, GUN_SNIPER, GUN_MACHINEGUN, HEALTH_POTON, DEPLOY_TOWER, GUN_OUT_OF_AMMO, TOWER_DESTROYED,
		ENEMY_DEATH
	}

	/**
	 * In-Game Scene Resource Enumerator
	 */
	public enum SceneResource {
		TITLE, SETTING, MODE_SELECTING, PLAYING, BEGINNING_LORE, ENDING_BAD, ENDING_GOOD, END_CREDIT
	}

	/**
	 * In-Game Item Resource Enumerator
	 */
	public enum ItemResource {
		BARRIER_TOWER, MACHINE_GUN_TOWER, SNIPER_TOWER, HEALTH_POTION, GUN_DESERTEAGLE, GUN_AK47, GUN_DOUBLEBARREL,
		GUN_SHOTGUN, GUN_SNIPER, GUN_MACHINEGUN, AMMO_PISTOL, AMMO_RIFLE, AMMO_SHOTGUN
	}

	/**
	 * In-Game UI Resource Enumerator
	 */
	public enum UIResource {
		SHOP
	}

	/**
	 * In-Game Game Object Resource Enumerator
	 */
	public enum GameObjectResource {
		MAIN_CHARACTER
	}

	/**
	 * Hash Map Mapping From ImageResource To Image
	 */
	private static Map<ImageResource, Image> imageResource = new HashMap<ImageResource, Image>();

	/**
	 * Hash Map Mapping From SoundResource To AudioClip
	 */
	private static Map<SoundResource, AudioClip> soundResource = new HashMap<SoundResource, AudioClip>();

	/**
	 * Map Resource String
	 */
	private static String[][] mapResource;

	/**
	 * Hash Map Mapping From SceneResource To Scene
	 */
	private static Map<SceneResource, Scene> sceneResource = new HashMap<SceneResource, Scene>();

	/**
	 * Hash Map Mapping From ItemResource To Buyable Object
	 */
	private static Map<ItemResource, Buyable> itemResource = new HashMap<ItemResource, Buyable>();

	/**
	 * Hash Map Mapping From UIResource To Pane
	 */
	private static Map<UIResource, Pane> uiResource = new HashMap<UIResource, Pane>();

	/**
	 * Hash Map Mapping From GameObjectResource To GameObject
	 */
	private static Map<GameObjectResource, GameObject> gameObjectResource = new HashMap<GameObjectResource, GameObject>();

	/**
	 * Initialize Resource
	 */
	static {
		Logger.log("Initializing ResourceManager");
		loadImage();
		loadSound();
		loadMap();
		loadItem();
		loadUI();
		loadScene();
		loadGameObject();
		Logger.log("ResourceManager Initialized");
	}

	/**
	 * Get Resource String From File Path
	 * 
	 * @param filePath Resource File Path
	 * @return Resource String From File Path
	 */
	private static String getResourceString(String filePath) {
		return ClassLoader.getSystemResource(filePath).toString();
	}

	/**
	 * Get Image From File Path
	 * 
	 * @param filePath Image File Path
	 * @return Image From File Path
	 */
	private static Image getImage(String filePath) {
		return new Image(getResourceString(filePath));
	}

	/**
	 * Load Image Resource
	 */
	private static void loadImage() {
		Logger.log("Start Loading Image");
		imageResource.put(ImageResource.BG_TITLE, getImage("bg/title.png"));
		imageResource.put(ImageResource.INFO_NORMALMODE, getImage("info/normal_mode.png"));
		imageResource.put(ImageResource.INFO_ENDLESSMODE, getImage("info/endless_mode.png"));
		imageResource.put(ImageResource.AMMO_PANE, getImage("info/ammo_pane.png"));
		imageResource.put(ImageResource.HEALTH_POTION_PANE, getImage("info/healthPotion_pane.png"));
		imageResource.put(ImageResource.STATUS_PANE, getImage("info/status_pane.png"));
		imageResource.put(ImageResource.ITEM_DESCRIPTION, getImage("info/item_description.png"));
		imageResource.put(ImageResource.BTN, getImage("btn/button.png"));
		imageResource.put(ImageResource.BTN_HOVER, getImage("btn/button_hover.png"));
		imageResource.put(ImageResource.BTN_NEWGAME, getImage("btn/new_game.png"));
		imageResource.put(ImageResource.BTN_LOADGAME, getImage("btn/load_game.png"));
		imageResource.put(ImageResource.BTN_EXITGAME, getImage("btn/exit_game.png"));
		imageResource.put(ImageResource.ITEM_BUTTON, getImage("btn/item_button.png"));
		imageResource.put(ImageResource.ITEM_BUTTON_TRANSPARENT, getImage("btn/item_button_bg.png"));
		imageResource.put(ImageResource.BTN_PLAY, getImage("btn/play.png"));
		imageResource.put(ImageResource.BTN_BACK, getImage("btn/back.png"));
		imageResource.put(ImageResource.BTN_NEXT, getImage("btn/next.png"));
		imageResource.put(ImageResource.BTN_PREVIOUS, getImage("btn/previous.png"));
		imageResource.put(ImageResource.TILE_FLOOR, getImage("tile/tile_floor.png"));
		imageResource.put(ImageResource.TILE_FLOOR_1, getImage("tile/tile_floor_1.png"));
		imageResource.put(ImageResource.TILE_FLOOR_2, getImage("tile/tile_floor_2.png"));
		imageResource.put(ImageResource.TILE_WALL, getImage("tile/tile_wall.png"));
		imageResource.put(ImageResource.TILE_UNWALKABLE_FLOOR, getImage("tile/tile_unwalkable_floor.png"));
		imageResource.put(ImageResource.TILE_UNPLACABLE_FLOOR, getImage("tile/tile_unplaceable_floor.png"));
		imageResource.put(ImageResource.TILE_SHOP, getImage("tile/tile_shop.png"));
		imageResource.put(ImageResource.CHARACTER_MAIN, getImage("sprite/main_character.png"));
		imageResource.put(ImageResource.BULLET, getImage("sprite/bullet.png"));
		imageResource.put(ImageResource.ENEMY_BULLET, getImage("sprite/enemy_bullet.png"));
		imageResource.put(ImageResource.GUN_PISTOL, getImage("weapon/gun_pistol.png"));
		imageResource.put(ImageResource.GUN_DESERTEAGLE, getImage("weapon/gun_deserteagle.png"));
		imageResource.put(ImageResource.GUN_AK47, getImage("weapon/gun_ak47.png"));
		imageResource.put(ImageResource.GUN_DOUBLEBARREL, getImage("weapon/gun_doublebarrel.png"));
		imageResource.put(ImageResource.GUN_SHOTGUN, getImage("weapon/gun_shotgun.png"));
		imageResource.put(ImageResource.GUN_SNIPER, getImage("weapon/gun_sniper.png"));
		imageResource.put(ImageResource.GUN_MACHINEGUN, getImage("weapon/gun_machinegun.png"));
		imageResource.put(ImageResource.AMMO_PISTOL, getImage("weapon/ammo_pistol.png"));
		imageResource.put(ImageResource.AMMO_RIFLE, getImage("weapon/ammo_rifle.png"));
		imageResource.put(ImageResource.AMMO_SHOTGUN, getImage("weapon/ammo_shotgun.png"));
		imageResource.put(ImageResource.SPRITE_KNIGHT, getImage("sprite/sprite_knight.png"));
		imageResource.put(ImageResource.SPRITE_ELITE_KNIGHT, getImage("sprite/sprite_elite_knight.png"));
		imageResource.put(ImageResource.CROSS_HAIR, getImage("sprite/crosshair.png"));
		imageResource.put(ImageResource.MACHINE_GUN_TOWER, getImage("sprite/tower_machine_gun.png"));
		imageResource.put(ImageResource.MACHINE_GUN_TOWER_BASE, getImage("sprite/tower_base_machine_gun.png"));
		imageResource.put(ImageResource.MACHINE_GUN_TOWER_HEAD, getImage("sprite/tower_gun_machine_gun.png"));
		imageResource.put(ImageResource.SNIPER_TOWER, getImage("sprite/tower_sniper.png"));
		imageResource.put(ImageResource.SNIPER_TOWER_BASE, getImage("sprite/tower_base_sniper.png"));
		imageResource.put(ImageResource.SNIPER_TOWER_HEAD, getImage("sprite/tower_gun_sniper.png"));
		imageResource.put(ImageResource.BARRIER_TOWER, getImage("sprite/tower_barrier.png"));
		imageResource.put(ImageResource.HEALTH_POTION, getImage("sprite/healing_potion.png"));
		imageResource.put(ImageResource.COIN, getImage("sprite/coin.png"));
		imageResource.put(ImageResource.BEGINNING_LORE_1, getImage("lore/beginning_lore_1.png"));
		imageResource.put(ImageResource.BEGINNING_LORE_2, getImage("lore/beginning_lore_2.png"));
		imageResource.put(ImageResource.BEGINNING_LORE_3, getImage("lore/beginning_lore_3.png"));
		imageResource.put(ImageResource.BEGINNING_LORE_4, getImage("lore/beginning_lore_4.png"));
		imageResource.put(ImageResource.BEGINNING_LORE_5, getImage("lore/beginning_lore_5.png"));
		imageResource.put(ImageResource.ENDING_BAD, getImage("lore/ending_bad.jpg"));
		imageResource.put(ImageResource.ENDING_GOOD_1, getImage("lore/ending_good_1.png"));
		imageResource.put(ImageResource.GAME_CONTROL, getImage("lore/game_control.png"));
		imageResource.put(ImageResource.END_CREDIT, getImage("lore/end_credit.png"));
		imageResource.put(ImageResource.BEGINNING_GAME_CONTROL, getImage("lore/beginning_game_control.png"));
		Logger.log("Complete Loading Image");
	}

	/**
	 * Get Sound From File Path
	 * 
	 * @param filePath Sound File Path
	 * @return AudioClip Generate From Sound File Path
	 */
	private static AudioClip getSound(String filePath) {
		return new AudioClip(getResourceString(filePath));
	}

	/**
	 * Load Sound Resource
	 */
	private static void loadSound() {
		soundResource.put(SoundResource.TITLE, getSound("sound/TheFatRat - Nemesis.mp3"));
		soundResource.put(SoundResource.PLAYING, getSound("sound/Glorious_morning.mp3"));
		soundResource.put(SoundResource.CAR_CRASH, getSound("sound/car_crash.mp3"));
		soundResource.put(SoundResource.ENDING_BAD, getSound("sound/ending_bad.mp3"));
		soundResource.put(SoundResource.ENDING_GOOD, getSound("sound/ending_good.mp3"));
		soundResource.put(SoundResource.GUN_PISTOL, getSound("sound/gun_pistol.mp3"));
		soundResource.put(SoundResource.GUN_DESERTEAGLE, getSound("sound/gun_deserteagle.mp3"));
		soundResource.put(SoundResource.GUN_AK47, getSound("sound/gun_ak47.mp3"));
		soundResource.put(SoundResource.GUN_DOUBLEBARREL, getSound("sound/gun_doublebarrel.mp3"));
		soundResource.put(SoundResource.GUN_SHOTGUN, getSound("sound/gun_shotgun.mp3"));
		soundResource.put(SoundResource.GUN_SNIPER, getSound("sound/gun_sniper.mp3"));
		soundResource.put(SoundResource.GUN_MACHINEGUN, getSound("sound/gun_machinegun.mp3"));
		soundResource.put(SoundResource.HEALTH_POTON, getSound("sound/health_potion.mp3"));
		soundResource.put(SoundResource.DEPLOY_TOWER, getSound("sound/deploy_tower.mp3"));
		soundResource.put(SoundResource.GUN_OUT_OF_AMMO, getSound("sound/gun_out_of_ammo.mp3"));
		soundResource.put(SoundResource.TOWER_DESTROYED, getSound("sound/tower_destroyed.mp3"));
		soundResource.put(SoundResource.ENEMY_DEATH, getSound("sound/enemy_death.mp3"));
	}

	/**
	 * Load Map Resource
	 */
	private static void loadMap() {
		try {
			Logger.log("Start Loading Map");
			InputStream inputStream = ClassLoader.getSystemResourceAsStream(Config.MAP_PATH);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			ArrayList<String[]> data = new ArrayList<String[]>();
			String lineInput;

			while ((lineInput = bufferedReader.readLine()) != null) {
				data.add(lineInput.split(","));
			}

			mapResource = data.toArray(new String[data.size()][]);
			GameState.setMapSize(mapResource[0].length, mapResource.length);
			GameState.setGameMap(new GameMap());
			Logger.log(String.format("Map Size W x H = %d x %d", mapResource[0].length, mapResource.length));
		} catch (IOException e) {
			Logger.error("Load Map Failed");
			e.printStackTrace();
		}
		RenderManager.add(GameState.getGameMap());
		Logger.log("Complete Loading Map");
	}

	/**
	 * Load Item Resource
	 */
	private static void loadItem() {
		itemResource.put(ItemResource.MACHINE_GUN_TOWER, new MachineGunTower(0, 0, 0));
		itemResource.put(ItemResource.SNIPER_TOWER, new SniperTower(0, 0, 0));
		itemResource.put(ItemResource.BARRIER_TOWER, new BarricadeTower(0, 0, 0));
		itemResource.put(ItemResource.HEALTH_POTION, new HealthPotion());
		itemResource.put(ItemResource.GUN_DESERTEAGLE,
				new DesertEagle(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_AK47, new AK47(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_DOUBLEBARREL,
				new DoubleBarrel(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_SHOTGUN,
				new Shotgun(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_SNIPER, new Sniper(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_MACHINEGUN,
				new MachineGun(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.AMMO_PISTOL, new PistolAmmo());
		itemResource.put(ItemResource.AMMO_RIFLE, new RifleAmmo());
		itemResource.put(ItemResource.AMMO_SHOTGUN, new ShotgunAmmo());
	}

	/**
	 * Load UI Resource
	 */
	private static void loadUI() {
		Logger.log("Start Loading UI");

		uiResource.put(UIResource.SHOP, new Shop());

		Logger.log("Complete Loading UI");
	}

	/**
	 * Load Scene Resource
	 */
	private static void loadScene() {
		Logger.log("Start Loading Scene");

		// Title Scene
		sceneResource.put(SceneResource.TITLE, new Scene(new TitleScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		// Mode Select Scene
		sceneResource.put(SceneResource.MODE_SELECTING,
				new Scene(new ModeSelectScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		// Playing Scene
		sceneResource.put(SceneResource.PLAYING, new Scene(new PlayScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		// Beginning Lore Scene
		sceneResource.put(SceneResource.BEGINNING_LORE,
				new Scene(new BeginningLorePane(), Config.SCREEN_W, Config.SCREEN_H));

		// Bad Ending Scene
		sceneResource.put(SceneResource.ENDING_BAD,
				new Scene(new BadEndingScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		// Good Ending Scene
		sceneResource.put(SceneResource.ENDING_GOOD,
				new Scene(new GoodEndingScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		// End Credit
		sceneResource.put(SceneResource.END_CREDIT,
				new Scene(new EndCreditScenePane(), Config.SCREEN_W, Config.SCREEN_H));

		UpdateManager.add((Updatable) getScene(SceneResource.TITLE).getRoot());
		InputManager.addEventListener(sceneResource.get(SceneResource.PLAYING));
		InputManager.addEventListener(sceneResource.get(SceneResource.BEGINNING_LORE));
		InputManager.addEventListener(sceneResource.get(SceneResource.ENDING_BAD));
		InputManager.addEventListener(sceneResource.get(SceneResource.ENDING_GOOD));
		InputManager.addEventListener(sceneResource.get(SceneResource.END_CREDIT));

		Logger.log("Complete Loading Scene");
	}

	/**
	 * Load Game Object Resource
	 */
	private static void loadGameObject() {
		gameObjectResource.put(GameObjectResource.MAIN_CHARACTER,
				new MainCharacter(ImageResource.CHARACTER_MAIN, Config.CHARACTER_W, Config.CHARACTER_H, "",
						Config.MAIN_CHARACTER_INITIAL_MAX_HEALTH, Config.MAIN_CHARACTER_INITIAL_DEFENSE,
						Config.MAIN_CHARACTER_INITIAL_SPD, Config.MAIN_CHARACTER_INITIAL_WEAPON,
						Config.MAIN_CHARACTER_TEAM, new Position(Config.SPAWN_CENTER)));
	}

	/**
	 * Get Image From ImageResource
	 */
	public static Image getImage(ImageResource image) {
		return imageResource.get(image);
	}

	/**
	 * Get ImageView From ImageResource
	 * 
	 * @param image  ImageResource
	 * @param width  ImageView Width
	 * @param height ImageView Height
	 * @return ImageView
	 */
	public static ImageView getImageView(ImageResource image, int width, int height) {
		ImageView imageView = new ImageView(getImage(image));

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

	/**
	 * Get Sound From SoundResource
	 * 
	 * @param sound SoundResource
	 * @return AudioClip From SoundResource
	 */
	public static AudioClip getSound(SoundResource sound) {
		return soundResource.get(sound);
	}

	/**
	 * Get Item From ItemResource
	 * 
	 * @param item ItemResource
	 * @return Buyable Item
	 */
	public static Buyable getItem(ItemResource item) {
		return itemResource.get(item);
	}

	/**
	 * Get UI From UIResource
	 * 
	 * @param ui UIResource
	 * @return UI Pane
	 */
	public static Pane getUI(UIResource ui) {
		return uiResource.get(ui);
	}

	/**
	 * Get Scene From SceneResource
	 */
	public static Scene getScene(SceneResource scene) {
		return sceneResource.get(scene);
	}

	/**
	 * Get Font Resource Stream From Configure Font Path
	 * 
	 * @return Font InputStream
	 */
	public static InputStream getFontResourceStream() {
		return ClassLoader.getSystemResourceAsStream(Config.FONT_PATH);
	}

	/**
	 * Get Map Resource
	 * 
	 * @return Map Resource String
	 */
	public static String[][] getMapResource() {
		return mapResource;
	}

	/**
	 * Get Game Object From GameObjectResource
	 */
	public static GameObject getGameObject(GameObjectResource gameObject) {
		return gameObjectResource.get(gameObject);
	}

}
