package utility;

import java.util.*;

import config.Config;
import gui.GameButton;
import gui.GameText;
import gui.ModeSelectScenePane;
import gui.PlayScenePane;
import gui.TitleScenePane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameState;
import utility.ResourceManager.ImageResource;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE, BTN, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY, BTN_BACK, BTN_NEXT, BTN_PREVIOUS
	}

	public enum SceneResource {
		TITLE, SETTING, MODE_SELECTING, PLAYING
	}

	private static Map<SceneResource, Scene> sceneResource = new HashMap<SceneResource, Scene>();
	private static Map<ImageResource, Image> imageResource = new HashMap<ImageResource, Image>();

	static {
		Logger.log("Initializing ResourceManager");
		loadImage();
		loadScene();
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
		imageResource.put(ImageResource.BTN, getImage("btn/button.png"));
		imageResource.put(ImageResource.BTN_NEWGAME, getImage("btn/new_game.png"));
		imageResource.put(ImageResource.BTN_LOADGAME, getImage("btn/load_game.png"));
		imageResource.put(ImageResource.BTN_EXITGAME, getImage("btn/exit_game.png"));
		imageResource.put(ImageResource.BTN_PLAY, getImage("btn/play.png"));
		imageResource.put(ImageResource.BTN_BACK, getImage("btn/back.png"));
		imageResource.put(ImageResource.BTN_NEXT, getImage("btn/next.png"));
		imageResource.put(ImageResource.BTN_PREVIOUS, getImage("btn/previous.png"));
		Logger.log("Complete Loading Image");
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

		Logger.log("Complete Loading Scene");
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

	public static Scene getScene(SceneResource scene) {
		return sceneResource.get(scene);
	}

}
