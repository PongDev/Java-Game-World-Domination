package utility;

import java.util.*;

import config.Config;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import utility.ResourceManager.ImageResource;

public class ResourceManager {

	public enum ImageResource {
		BG_TITLE
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
		Logger.log("Complete Loading Image");
	}

	private static void loadScene() {
		Logger.log("Start Loading Scene");
		{
			StackPane root = new StackPane();
			root.getChildren().addAll(ResourceManager.getImage(ImageResource.BG_TITLE));
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
