package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import character.Enemy;
import character.MainCharacter;
import config.Config;
import gui.GameMap;
import gui.ModeSelectScenePane;
import gui.PlayScenePane;
import gui.TitleScenePane;
import input.InputManager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import logic.GameState;
import object.GameObject;
import render.RenderManager;
import update.Updatable;
import update.UpdateManager;
import weapon.Gun;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE, INFO_NORMALMODE, INFO_ENDLESSMODE, BTN, BTN_HOVER, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY,
		BTN_BACK, BTN_NEXT, BTN_PREVIOUS, TILE_FLOOR, TILE_WALL, TILE_UNWALKABLE_FLOOR, TILE_UNPLACABLE_FLOOR,
		TILE_GATE_CLOSE, CHARACTER_MAIN, BULLET, GUN_AK47, SPRITE_KNIGHT_SWORD
	}

	public enum SoundResource {
		TITLE, PLAYING
	}

	public enum SceneResource {
		TITLE, SETTING, MODE_SELECTING, PLAYING
	}

	public enum GameObjectResource {
		MAIN_CHARACTER
	}

	private static Map<ImageResource, Image> imageResource = new HashMap<ImageResource, Image>();
	private static Map<SoundResource, AudioClip> soundResource = new HashMap<SoundResource, AudioClip>();
	private static String[][] mapResource;
	private static Map<SceneResource, Scene> sceneResource = new HashMap<SceneResource, Scene>();
	private static Map<GameObjectResource, GameObject> gameObjectResource = new HashMap<GameObjectResource, GameObject>();

	static {
		Logger.log("Initializing ResourceManager");
		loadImage();
		loadSound();
		loadMap();
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
		imageResource.put(ImageResource.BTN, getImage("btn/button.png"));
		imageResource.put(ImageResource.BTN_HOVER, getImage("btn/button_hover.png"));
		imageResource.put(ImageResource.BTN_NEWGAME, getImage("btn/new_game.png"));
		imageResource.put(ImageResource.BTN_LOADGAME, getImage("btn/load_game.png"));
		imageResource.put(ImageResource.BTN_EXITGAME, getImage("btn/exit_game.png"));
		imageResource.put(ImageResource.BTN_PLAY, getImage("btn/play.png"));
		imageResource.put(ImageResource.BTN_BACK, getImage("btn/back.png"));
		imageResource.put(ImageResource.BTN_NEXT, getImage("btn/next.png"));
		imageResource.put(ImageResource.BTN_PREVIOUS, getImage("btn/previous.png"));
		imageResource.put(ImageResource.TILE_FLOOR, getImage("tile/tile_floor.png"));
		imageResource.put(ImageResource.TILE_WALL, getImage("tile/tile_wall.png"));
		imageResource.put(ImageResource.TILE_UNWALKABLE_FLOOR, getImage("tile/tile_unwalkable_floor.png"));
		imageResource.put(ImageResource.TILE_UNPLACABLE_FLOOR, getImage("tile/tile_unplaceable_floor.png"));
		imageResource.put(ImageResource.TILE_GATE_CLOSE, getImage("tile/tile_gate_close.png"));
		imageResource.put(ImageResource.CHARACTER_MAIN, getImage("sprite/main_character.png"));
		imageResource.put(ImageResource.BULLET, getImage("sprite/bullet.png"));
		imageResource.put(ImageResource.GUN_AK47, getImage("weapon/gun_ak47.png"));
		imageResource.put(ImageResource.SPRITE_KNIGHT_SWORD, getImage("sprite/sprite_knight_sword.png"));
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

		for (int i = 1; i <= 1d; i++) {
			testSpawnEnemy(i);
		}
	}

	public static void testSpawnEnemy(int i) {
		Enemy test = new Enemy(ImageResource.SPRITE_KNIGHT_SWORD, Config.CHARACTER_W, Config.CHARACTER_H,
				"Knight Sword", 3, 0, 1,
				new Gun(ImageResource.GUN_AK47, 1, 5, ImageResource.BULLET, 10, 10, 10, Config.ENEMY_TEAM,
						Config.ZINDEX_ENEMY),
				Config.ENEMY_TEAM,
				new Position((int) (Config.TILE_W * 2 * i), (GameState.getMapHeight() * Config.TILE_H) / 2));
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
