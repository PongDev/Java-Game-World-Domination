package logic;

import javafx.scene.Scene;
import javafx.stage.Stage;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class GameState {

	public enum GameMode {
		NORMAL(0, "Normal", ImageResource.INFO_NORMALMODE), ENDLESS(1, "Endless", ImageResource.INFO_ENDLESSMODE);

		private final int gameModeCode;
		private final String gameModeName;
		private final ImageResource infoImage;
		private static final GameMode[] gameModeArray = { GameMode.NORMAL, GameMode.ENDLESS };

		private GameMode(int gameModeCode, String gameModeName, ImageResource infoPicture) {
			this.gameModeCode = gameModeCode;
			this.gameModeName = gameModeName;
			this.infoImage = infoPicture;
		}

		public GameMode getNextGameMode() {
			return (gameModeCode == gameModeArray.length - 1) ? gameModeArray[0] : gameModeArray[gameModeCode + 1];
		}

		public GameMode getPreviousGameMode() {
			return (gameModeCode == 0) ? gameModeArray[gameModeArray.length - 1] : gameModeArray[gameModeCode - 1];
		}

		public String getGameModeName() {
			return gameModeName;
		}
		
		public ImageResource getGameModeInfoImageResource() {
			return infoImage;
		}
	}

	private static Stage gameStage;
	private static SceneResource sceneResource = SceneResource.TITLE;
	private static boolean isSceneChange = false;
	private static boolean isRequestSceneUpdate = false;

	private static GameMode gameMode = GameMode.NORMAL;

	public static void setGameStage(Stage gameStage) {
		GameState.gameStage = gameStage;
	}

	public static void closeGameStage() {
		GameState.gameStage.close();
	}

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

	public static GameMode getGameMode() {
		return gameMode;
	}

	public static void setGameMode(GameMode gameMode) {
		if (GameState.gameMode != gameMode) {
			GameState.gameMode = gameMode;
			Logger.log("Game Mode Change To " + GameState.gameMode.name());
		}
	}
	
	public static boolean isRequestSceneUpdate() {
		return isRequestSceneUpdate;
	}

	public static void setRequestSceneUpdate(boolean isRequestSceneUpdate) {
		GameState.isRequestSceneUpdate = isRequestSceneUpdate;
	}

}
