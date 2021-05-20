package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import character.MainCharacter;
import config.Config;
import gui.GameMap;
import gui.ModeSelectScenePane;
import gui.PlayScenePane;
import gui.Shop;
import gui.TitleScenePane;
import input.InputManager;
import item.Buyable;
import item.Potion;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import logic.GameState;
import object.GameObject;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import utility.ResourceManager.ImageResource;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE, INFO_NORMALMODE, INFO_ENDLESSMODE, BTN, BTN_HOVER, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY,
		BTN_BACK, BTN_NEXT, BTN_PREVIOUS, TILE_FLOOR, TILE_FLOOR_1, TILE_FLOOR_2, TILE_WALL, TILE_UNWALKABLE_FLOOR,
		TILE_UNPLACABLE_FLOOR, TILE_GATE_CLOSE, CHARACTER_MAIN, BULLET, ENEMY_BULLET, GUN_AK47, GUN_SHOTGUN, GUN_SNIPER,
		SPRITE_KNIGHT_SWORD, CROSS_HAIR, AMMO_PANE, HEALTH_POTION_PANE, STATUS_PANE, HEALTH_POTION, ITEM_BUTTON,
		ITEM_BUTTON_TRANSPARENT, ITEM_DESCRIPTION, MACHINE_GUN_TOWER, SNIPER_TOWER, BARRIER_TOWER
	}

	public enum SoundResource {
		TITLE, PLAYING
	}

	public enum SceneResource {
		TITLE, SETTING, MODE_SELECTING, PLAYING
	}

	public enum ItemResource {
		MACHINE_GUN_TOWER, SNIPER_TOWER, BARRIER_TOWER, HEALTH_POTION, GUN_AK47, GUN_SHOTGUN, GUN_SNIPER
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
		imageResource.put(ImageResource.TILE_GATE_CLOSE, getImage("tile/tile_gate_close.png"));
		imageResource.put(ImageResource.CHARACTER_MAIN, getImage("sprite/main_character.png"));
		imageResource.put(ImageResource.BULLET, getImage("sprite/bullet.png"));
		imageResource.put(ImageResource.ENEMY_BULLET, getImage("sprite/enemy_bullet.png"));
		imageResource.put(ImageResource.GUN_AK47, getImage("weapon/gun_ak47.png"));
		imageResource.put(ImageResource.GUN_SHOTGUN, getImage("weapon/gun_shotgun.png"));
		imageResource.put(ImageResource.GUN_SNIPER, getImage("weapon/gun_sniper.png"));
		imageResource.put(ImageResource.SPRITE_KNIGHT_SWORD, getImage("sprite/sprite_knight_sword.png"));
		imageResource.put(ImageResource.CROSS_HAIR, getImage("sprite/crosshair.png"));
		imageResource.put(ImageResource.MACHINE_GUN_TOWER, getImage("sprite/tower_machine_gun.png"));
		imageResource.put(ImageResource.SNIPER_TOWER, getImage("sprite/tower_sniper.png"));
		imageResource.put(ImageResource.BARRIER_TOWER, getImage("sprite/tower_barrier.png"));
		imageResource.put(ImageResource.HEALTH_POTION, getImage("sprite/healing_potion.png"));
		Logger.log("Complete Loading Image");
	}

	private static AudioClip getSound(String filePath) {
		return new AudioClip(getResourceString(filePath));
	}

	private static void loadSound() {
		soundResource.put(SoundResource.TITLE, getSound("sound/TheFatRat - Nemesis.mp3"));
		soundResource.put(SoundResource.PLAYING, getSound("sound/Glorious_morning.mp3"));
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
		itemResource.put(ItemResource.MACHINE_GUN_TOWER, new Potion("Machine gun tower",
				"fast attack speed, medium damage", ImageResource.MACHINE_GUN_TOWER, 40));
		itemResource.put(ItemResource.SNIPER_TOWER,
				new Potion("Sniper tower", "low attack speed, very high damage", ImageResource.SNIPER_TOWER, 40));
		itemResource.put(ItemResource.BARRIER_TOWER,
				new Potion("Barrier tower", "can block enemies bullet", ImageResource.BARRIER_TOWER, 40));
		itemResource.put(ItemResource.HEALTH_POTION,
				new Potion("Health Potion", "Heal 40 health", ImageResource.HEALTH_POTION, 20));
		itemResource.put(ItemResource.GUN_AK47,
				new Potion("AK47", "3 damage, 0.5 second firerate, 120 ammo", ImageResource.GUN_AK47, 60));
		itemResource.put(ItemResource.GUN_SHOTGUN,
				new Potion("Shotgun", "3-9 damage, 1.0 second firerate, 20 ammo", ImageResource.GUN_SHOTGUN, 50));
		itemResource.put(ItemResource.GUN_SNIPER,
				new Potion("Sniper", "12 damage, 1.0 second firerate, 30 ammo", ImageResource.GUN_SNIPER, 75));
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

		UpdateManager.add((Updatable) getScene(SceneResource.TITLE).getRoot());
		InputManager.addEventListener(sceneResource.get(SceneResource.PLAYING));

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
