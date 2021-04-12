package utility;

import java.util.*;

import config.Config;
import gui.GameButton;
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
		BG_TITLE, BTN_NEWGAME, BTN_LOADGAME, BTN_EXITGAME, BTN_PLAY
	}

	public enum SceneResource {
		MENU, SETTING, MODE_SELECTING, PLAYING
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
		imageResource.put(ImageResource.BTN_NEWGAME, getImage("btn/new_game.png"));
		imageResource.put(ImageResource.BTN_LOADGAME, getImage("btn/load_game.png"));
		imageResource.put(ImageResource.BTN_EXITGAME, getImage("btn/exit_game.png"));
		imageResource.put(ImageResource.BTN_PLAY, getImage("btn/play.png"));
		Logger.log("Complete Loading Image");
	}

	private static void loadScene() {
		Logger.log("Start Loading Scene");
		// Title Scene
		{
			GameButton btnNewGame = new GameButton(ImageResource.BTN_NEWGAME, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
			btnNewGame.setOnMouseClicked((e) -> {
				Logger.log("Button New Game Click");
				GameState.setSceneResource(SceneResource.MODE_SELECTING);
			});
			GameButton btnLoadGame = new GameButton(ImageResource.BTN_LOADGAME, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
			btnLoadGame.setOnMouseClicked((e) -> {
				Logger.log("Button Load Game Click");
			});
			GameButton btnExitGame = new GameButton(ImageResource.BTN_EXITGAME, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
			btnExitGame.setOnMouseClicked((e) -> {
				Logger.log("Button Exit Game Click");
			});

			VBox menuBar = new VBox();
			menuBar.setSpacing(Config.TITLE_BTN_SPACING);
			menuBar.getChildren().addAll(btnNewGame, btnLoadGame, btnExitGame);
			menuBar.setAlignment(Pos.CENTER);
			menuBar.setTranslateY(Config.SCREEN_H / 4);

			StackPane root = new StackPane();
			root.getChildren().addAll(
					ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), menuBar);

			Scene scene = new Scene(root, Config.SCREEN_W, Config.SCREEN_H);
			sceneResource.put(SceneResource.MENU, scene);
		}

		// Mode Select Scene
		{
			GameButton btnPlay = new GameButton(ImageResource.BTN_PLAY, Config.TITLE_BTN_W, Config.TITLE_BTN_H);
			btnPlay.setOnMouseClicked((e) -> {
				Logger.log("Button Play Game Click");
				GameState.setSceneResource(SceneResource.PLAYING);
			});

			VBox playBar = new VBox();
			playBar.setSpacing(Config.TITLE_BTN_SPACING);
			playBar.getChildren().addAll(btnPlay);
			playBar.setAlignment(Pos.CENTER);
			playBar.setTranslateY(Config.SCREEN_H / 4);

			StackPane root = new StackPane();
			root.getChildren().addAll(
					ResourceManager.getImageView(ImageResource.BG_TITLE, Config.SCREEN_W, Config.SCREEN_H), playBar);

			Scene scene = new Scene(root, Config.SCREEN_W, Config.SCREEN_H);
			sceneResource.put(SceneResource.MODE_SELECTING, scene);
		}
		Logger.log("Complete Loading Scene");
	}

	public static ImageView getImageView(ImageResource image, int width, int height) {
		ImageView imageView = new ImageView(imageResource.get(image));

		imageView.setFitWidth(width);
		imageView.setFitHeight(height);
		return imageView;
	}

	public static Scene getScene(SceneResource scene) {
		return sceneResource.get(scene);
	}

}
