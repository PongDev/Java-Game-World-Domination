package utility;

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceManager {

	static Map<IMG, ImageView> resource = new HashMap<IMG, ImageView>();

	static {
		loadResource();
	}

	public enum IMG {
		BG_TITLE
	}

	private static String getResourceString(String filePath) {
		return ClassLoader.getSystemResource(filePath).toString();
	}

	private static ImageView getImage(String filePath) {
		return new ImageView(new Image(getResourceString(filePath)));
	}

	public static void loadResource() {
		Logger.log("Start Loading Resource");
		resource.put(IMG.BG_TITLE, getImage("bg/title.png"));
		Logger.log("Complete Loading Resource");
	}

	public static ImageView getResource(IMG image) {
		return resource.get(image);
	}

}
