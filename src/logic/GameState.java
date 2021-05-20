package logic;

import java.util.Date;

import gui.GameMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import update.Updatable;
import update.UpdateManager;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.SceneResource;

public class GameState {

	public enum GameMode {
		NORMAL(0, "Normal", ImageResource.INFO_NORMALMODE,
				"Survive 30 waves to win\n" + "Elite enemies every 10 waves"),
		ENDLESS(1, "Endless", ImageResource.INFO_ENDLESSMODE,
				"Survive as long as you can!\n" + "Elite enemies every 15 waves");

		private final int gameModeCode;
		private final String gameModeName;
		private final String gameModeInfoText;
		private final ImageResource gameModeinfoImage;
		private static final GameMode[] gameModeArray = { GameMode.NORMAL, GameMode.ENDLESS };

		private GameMode(int gameModeCode, String gameModeName, ImageResource gameModeinfoPicture,
				String gameModeInfoText) {
			this.gameModeCode = gameModeCode;
			this.gameModeName = gameModeName;
			this.gameModeinfoImage = gameModeinfoPicture;
			this.gameModeInfoText = gameModeInfoText;
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
			return gameModeinfoImage;
		}

		public String getGameModeInfoText() {
			return gameModeInfoText;
		}
	}

	private static boolean isRunning = false;
	private static Stage gameStage;
	private static SceneResource sceneResource = SceneResource.TITLE;
	private static boolean isSceneChange = false;
	private static GameMode gameMode = GameMode.NORMAL;
	private static int mapWidth;
	private static int mapHeight;
	private static GameMap gameMap;
	private static boolean isPause = false;
	private static long lastPauseTimestamp = 0;
	private static long lastPauseDulation = 0;

	public static void setMapSize(int width, int height) {
		mapWidth = width;
		mapHeight = height;
	}

	public static int getMapWidth() {
		return mapWidth;
	}

	public static int getMapHeight() {
		return mapHeight;
	}

	public static GameMap getGameMap() {
		return gameMap;
	}

	public static void setGameMap(GameMap gameMap) {
		GameState.gameMap = gameMap;
	}

	public static void setGameStage(Stage gameStage) {
		GameState.gameStage = gameStage;
	}

	public static void closeGameStage() {
		GameState.gameStage.close();
		isRunning = false;
	}

	public static void setSceneResource(SceneResource sceneResource) {
		if (GameState.sceneResource != sceneResource) {
			Logger.log("Change Scene To " + sceneResource.name());
			if (ResourceManager.getScene(sceneResource).getRoot() instanceof Updatable) {
				UpdateManager.add((Updatable) ResourceManager.getScene(sceneResource).getRoot());
			}
			GameState.sceneResource = sceneResource;
			GameState.isSceneChange = true;
		}
	}

	public static SceneResource getSceneResource() {
		return sceneResource;
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

	public static boolean isRunning() {
		return isRunning;
	}

	public static void setRunning(boolean isRunning) {
		GameState.isRunning = isRunning;
	}

	public static boolean isPause() {
		return isPause;
	}

	public static void setPause(boolean isPause) {
		if (GameState.isPause != isPause) {
			GameState.isPause = isPause;
			if (GameState.isPause) {
				lastPauseTimestamp = (new Date()).getTime();
			} else {
				lastPauseDulation = (new Date()).getTime() - lastPauseTimestamp;
				lastPauseTimestamp = 0;
			}
		}
	}

	public static long getLastPauseDulation() {
		return lastPauseDulation;
	}

}
