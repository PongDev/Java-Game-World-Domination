package utility;

import java.util.*;

import config.Config;
import gui.GameButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import utility.ResourceManager.ImageResource;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME
	}

	public enum SceneResource {
		MENU
	}

	private static Map<SceneResource, Scene> sceneResource = new HashMap<SceneResource, Scene>();
	private static Map<ImageResource, ImageView> imageResource = new HashMap<ImageResource, ImageView>();

	static {
		Logger.log("Initializing ResourceManager");
		loadImage();
		loadScene();
		Logger.log("ResourceManager Initialized");
	}

	private static String getResourceString(String filePath) {
		return ClassLoader.getSystemResource(filePath).toString();
	}

	private static ImageView getImage(String filePath) {
		return new ImageView(new Image(getResourceString(filePath)));
	}

	private static ImageView getImage(String filePath, int width, int height) {
		ImageView imageView = getImage(filePath);

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

	private static void loadImage() {
		Logger.log("Start Loading Image");
		imageResource.put(ImageResource.BG_TITLE, getImage("bg/title.png", Config.SCREEN_W, Config.SCREEN_H));
		imageResource.put(ImageResource.BTN_NEWGAME,
				getImage("btn/new_game.png", Config.TITLE_BTN_W, Config.TITLE_BTN_H));
		imageResource.put(ImageResource.BTN_LOADGAME,
				getImage("btn/load_game.png", Config.TITLE_BTN_W, Config.TITLE_BTN_H));
		imageResource.put(ImageResource.BTN_EXITGAME,
				getImage("btn/exit_game.png", Config.TITLE_BTN_W, Config.TITLE_BTN_H));
		Logger.log("Complete Loading Image");
	}

	private static void loadScene() {
		Logger.log("Start Loading Scene");
		{
			GameButton btnNewGame = new GameButton(ImageResource.BTN_NEWGAME);
			GameButton btnLoadGame = new GameButton(ImageResource.BTN_LOADGAME);
			GameButton btnExitGame = new GameButton(ImageResource.BTN_EXITGAME);

			VBox menuBar = new VBox();
			menuBar.setSpacing(10);
			menuBar.getChildren().addAll(btnNewGame, btnLoadGame, btnExitGame);
			menuBar.setAlignment(Pos.CENTER);

			StackPane root = new StackPane();
			root.getChildren().addAll(ResourceManager.getImage(ImageResource.BG_TITLE), menuBar);

			Scene scene = new Scene(root, Config.SCREEN_W, Config.SCREEN_H);
			sceneResource.put(SceneResource.MENU, scene);
		}
		Logger.log("Complete Loading Scene");
	}

	public static ImageView getImage(ImageResource image) {
		return imageResource.get(image);
	}

	public static Scene getScene(SceneResource scene) {
		return sceneResource.get(scene);
	}

}
