package logic;

import javafx.scene.Scene;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.SceneResource;

public class GameState {

	private static SceneResource sceneResource = SceneResource.MENU;
	private static boolean isSceneChange = false;

	public static void setSceneResource(SceneResource sceneResource) {
		if (GameState.sceneResource != sceneResource) {
			Logger.log("Change Scene To " + sceneResource.name());
			GameState.sceneResource = sceneResource;
			GameState.isSceneChange = true;
		}
	}

	public static boolean isSceneChange() {
		return isSceneChange;
	}

	public static Scene getScene() {
		isSceneChange = false;
		return ResourceManager.getScene(sceneResource);
	}

}
