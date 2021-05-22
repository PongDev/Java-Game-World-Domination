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
import weapon.Shotgun;
import weapon.Sniper;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE, INFO_NORMALMODE, INFO_ENDLESSMODE, BTN, BTN_HOVER, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY,
		BTN_BACK, BTN_NEXT, BTN_PREVIOUS, TILE_FLOOR, TILE_FLOOR_1, TILE_FLOOR_2, TILE_WALL, TILE_UNWALKABLE_FLOOR,
		TILE_UNPLACABLE_FLOOR, TILE_GATE_CLOSE, TILE_SHOP, CHARACTER_MAIN, BULLET, ENEMY_BULLET, GUN_PISTOL, GUN_AK47,
		GUN_SHOTGUN, GUN_SNIPER, SPRITE_KNIGHT, SPRITE_ELITE_KNIGHT, CROSS_HAIR, AMMO_PANE, HEALTH_POTION_PANE,
		STATUS_PANE, HEALTH_POTION, ITEM_BUTTON, ITEM_BUTTON_TRANSPARENT, ITEM_DESCRIPTION, BARRIER_TOWER,
		MACHINE_GUN_TOWER, MACHINE_GUN_TOWER_BASE, MACHINE_GUN_TOWER_HEAD, SNIPER_TOWER, SNIPER_TOWER_BASE,
		SNIPER_TOWER_HEAD, AMMO_PISTOL, AMMO_RIFLE, AMMO_SHOTGUN, COIN, BEGINNING_LORE_1, BEGINNING_LORE_2,
		BEGINNING_LORE_3, BEGINNING_LORE_4, BEGINNING_LORE_5, ENDING_BAD, ENDING_GOOD_1, GAME_CONTROL,
		BEGINNING_GAME_CONTROL, END_CREDIT
	}

	public enum SoundResource {
		TITLE, PLAYING, CAR_CRASH, ENDING_BAD, ENDING_GOOD, GUN_SHOT
	}

	public enum SceneResource {
		TITLE, SETTING, MODE_SELECTING, PLAYING, BEGINNING_LORE, ENDING_BAD, ENDING_GOOD
	}

	public enum ItemResource {
		BARRIER_TOWER, MACHINE_GUN_TOWER, SNIPER_TOWER, HEALTH_POTION, GUN_AK47, GUN_SHOTGUN, GUN_SNIPER, AMMO_PISTOL,
		AMMO_RIFLE, AMMO_SHOTGUN
	}

	public enum UIResource {
		SHOP
	}

	public enum GameObjectResource {
		MAIN_CHARACTER
	}

	private static Map<ImageResource, Image> imageResource = new HashMap<ImageResource, Image>();
	private static Map<SoundResource, AudioClip> soundResource = new HashMap<SoundResource, AudioClip>();
	private static String[][] mapResource;
	private static Map<SceneResource, Scene> sceneResource = new HashMap<SceneResource, Scene>();
	private static Map<ItemResource, Buyable> itemResource = new HashMap<ItemResource, Buyable>();
	private static Map<UIResource, Pane> uiResource = new HashMap<UIResource, Pane>();
	private static Map<GameObjectResource, GameObject> gameObjectResource = new HashMap<GameObjectResource, GameObject>();

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

	private static String getResourceString(String filePath) {
		return ClassLoader.getSystemResource(filePath).toString();
	}

	private static Image getImage(String filePath) {
		return new Image(getResourceString(filePath));
	}

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
		imageResource.put(ImageResource.GUN_AK47, getImage("weapon/gun_ak47.png"));
		imageResource.put(ImageResource.GUN_SHOTGUN, getImage("weapon/gun_shotgun.png"));
		imageResource.put(ImageResource.GUN_SNIPER, getImage("weapon/gun_sniper.png"));
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
		soundResource.put(SoundResource.GUN_SHOT, getSound("sound/gun_shot.mp3"));
		Logger.log("Complete Loading Image");
	}

	private static AudioClip getSound(String filePath) {
		return new AudioClip(getResourceString(filePath));
	}

	private static void loadSound() {
		soundResource.put(SoundResource.TITLE, getSound("sound/TheFatRat - Nemesis.mp3"));
		soundResource.put(SoundResource.PLAYING, getSound("sound/Glorious_morning.mp3"));
		soundResource.put(SoundResource.CAR_CRASH, getSound("sound/car_crash.mp3"));
		soundResource.put(SoundResource.ENDING_BAD, getSound("sound/ending_bad.mp3"));
		soundResource.put(SoundResource.ENDING_GOOD, getSound("sound/ending_good.mp3"));
	}

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

	private static void loadItem() {
		itemResource.put(ItemResource.MACHINE_GUN_TOWER, new MachineGunTower(0, 0, 0));
		itemResource.put(ItemResource.SNIPER_TOWER, new SniperTower(0, 0, 0));
		itemResource.put(ItemResource.BARRIER_TOWER, new BarricadeTower(0, 0, 0));
		itemResource.put(ItemResource.HEALTH_POTION, new HealthPotion());
		itemResource.put(ItemResource.GUN_AK47, new AK47(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_SHOTGUN,
				new Shotgun(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.GUN_SNIPER, new Sniper(Config.MAIN_CHARACTER_TEAM, Config.ZINDEX_MAIN_CHARACTER));
		itemResource.put(ItemResource.AMMO_PISTOL, new PistolAmmo());
		itemResource.put(ItemResource.AMMO_RIFLE, new RifleAmmo());
		itemResource.put(ItemResource.AMMO_SHOTGUN, new ShotgunAmmo());
	}

	private static void loadUI() {
		Logger.log("Start Loading UI");

		uiResource.put(UIResource.SHOP, new Shop());

		Logger.log("Complete Loading UI");
	}

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

		UpdateManager.add((Updatable) getScene(SceneResource.TITLE).getRoot());
		InputManager.addEventListener(sceneResource.get(SceneResource.PLAYING));
		InputManager.addEventListener(sceneResource.get(SceneResource.BEGINNING_LORE));
		InputManager.addEventListener(sceneResource.get(SceneResource.ENDING_BAD));
		InputManager.addEventListener(sceneResource.get(SceneResource.ENDING_GOOD));

		Logger.log("Complete Loading Scene");
	}

	private static void loadGameObject() {
		gameObjectResource.put(GameObjectResource.MAIN_CHARACTER,
				new MainCharacter(ImageResource.CHARACTER_MAIN, Config.CHARACTER_W, Config.CHARACTER_H, "",
						Config.MAIN_CHARACTER_INITIAL_MAX_HEALTH, Config.MAIN_CHARACTER_INITIAL_DEFENSE,
						Config.MAIN_CHARACTER_INITIAL_SPD, Config.MAIN_CHARACTER_INITIAL_WEAPON,
						Config.MAIN_CHARACTER_TEAM, new Position(Config.SPAWN_CENTER)));
	}

	public static Image getImage(ImageResource image) {
		return imageResource.get(image);
	}

	public static ImageView getImageView(ImageResource image, int width, int height) {
		ImageView imageView = new ImageView(getImage(image));

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

	public static AudioClip getSound(SoundResource sound) {
		return soundResource.get(sound);
	}

	public static Buyable getItem(ItemResource item) {
		return itemResource.get(item);
	}

	public static Pane getUI(UIResource ui) {
		return uiResource.get(ui);
	}

	public static Scene getScene(SceneResource scene) {
		return sceneResource.get(scene);
	}

	public static InputStream getFontResourceStream() {
		return ClassLoader.getSystemResourceAsStream(Config.FONT_PATH);
	}

	public static String[][] getMapResource() {
		return mapResource;
	}

	public static GameObject getGameObject(GameObjectResource gameObject) {
		return gameObjectResource.get(gameObject);
	}

}
