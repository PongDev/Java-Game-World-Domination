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

/**
 * Game State: Contain And Manage Game State
 */
public class GameState {

	/**
	 * Game Mode Enumerator
	 */
	public enum GameMode {
		NORMAL(0, "Normal", ImageResource.INFO_NORMALMODE,
				"Survive 30 waves to win\n" + "Elite enemies every 10 waves"),
		ENDLESS(1, "Endless", ImageResource.INFO_ENDLESSMODE,
				"Survive as long as you can!\n" + "Elite enemies every 10 waves");

		/**
		 * Game Mode Code
		 */
		private final int gameModeCode;

		/**
		 * Game Mode Name
		 */
		private final String gameModeName;

		/**
		 * Game Mode Info Text
		 */
		private final String gameModeInfoText;

		/**
		 * Game Mode Info Image ImageResource
		 */
		private final ImageResource gameModeinfoImage;

		/**
		 * Array Of Available Game Mode
		 */
		private static final GameMode[] gameModeArray = { GameMode.NORMAL, GameMode.ENDLESS };

		/**
		 * Game Mode Main Constructor
		 */
		private GameMode(int gameModeCode, String gameModeName, ImageResource gameModeinfoPicture,
				String gameModeInfoText) {
			this.gameModeCode = gameModeCode;
			this.gameModeName = gameModeName;
			this.gameModeinfoImage = gameModeinfoPicture;
			this.gameModeInfoText = gameModeInfoText;
		}

		/**
		 * Get Next Game Mode
		 */
		public GameMode getNextGameMode() {
			return (gameModeCode == gameModeArray.length - 1) ? gameModeArray[0] : gameModeArray[gameModeCode + 1];
		}

		/**
		 * Get Previous Game Mode
		 */
		public GameMode getPreviousGameMode() {
			return (gameModeCode == 0) ? gameModeArray[gameModeArray.length - 1] : gameModeArray[gameModeCode - 1];
		}

		/**
		 * Get Game Mode Name
		 */
		public String getGameModeName() {
			return gameModeName;
		}

		/**
		 * Get Game Mode Info ImageResource
		 */
		public ImageResource getGameModeInfoImageResource() {
			return gameModeinfoImage;
		}

		/**
		 * Get Game Mode Info Text
		 */
		public String getGameModeInfoText() {
			return gameModeInfoText;
		}
	}

	/**
	 * Is Game Running
	 */
	private static boolean isRunning = false;

	/**
	 * Game Stage
	 */
	private static Stage gameStage;

	/**
	 * Current Game SceneResource
	 */
	private static SceneResource sceneResource = SceneResource.TITLE;

	/**
	 * Is Scene Change
	 */
	private static boolean isSceneChange = false;

	/**
	 * Current Game Mode
	 */
	private static GameMode gameMode = GameMode.NORMAL;

	/**
	 * Map Width In Tile
	 */
	private static int mapWidth;

	/**
	 * Map Height In Tile
	 */
	private static int mapHeight;

	/**
	 * Current Game Map
	 */
	private static GameMap gameMap;

	/**
	 * Is Game Pause
	 */
	private static boolean isPause = false;

	/**
	 * Last Game Pause Time Stamp
	 */
	private static long lastPauseTimestamp = 0;

	/**
	 * Last Game Pause Duration
	 */
	private static long lastPauseDulation = 0;

	/**
	 * Set Map Size
	 * 
	 * @param width  Map Width In Tile
	 * @param height Map Height In Tile
	 */
	public static void setMapSize(int width, int height) {
		mapWidth = width;
		mapHeight = height;
	}

	/**
	 * Get Map Width In Tile
	 */
	public static int getMapWidth() {
		return mapWidth;
	}

	/**
	 * Get Map Height In Tile
	 */
	public static int getMapHeight() {
		return mapHeight;
	}

	/**
	 * Get Current Game Map
	 */
	public static GameMap getGameMap() {
		return gameMap;
	}

	/**
	 * Set Current Game Map
	 */
	public static void setGameMap(GameMap gameMap) {
		GameState.gameMap = gameMap;
	}

	/**
	 * Set Game Stage
	 */
	public static void setGameStage(Stage gameStage) {
		GameState.gameStage = gameStage;
	}

	/**
	 * Close Game State
	 */
	public static void closeGameStage() {
		GameState.gameStage.close();
		isRunning = false;
	}

	/**
	 * Set Current SceneResource
	 */
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

	/**
	 * Get Current SceneResource
	 */
	public static SceneResource getSceneResource() {
		return sceneResource;
	}

	/**
	 * Is Scene Change
	 */
	public static boolean isSceneChange() {
		return isSceneChange;
	}

	/**
	 * Get Current Scene
	 */
	public static Scene getScene() {
		isSceneChange = false;
		return ResourceManager.getScene(sceneResource);
	}

	/**
	 * Get Current Game Mode
	 */
	public static GameMode getGameMode() {
		return gameMode;
	}

	/**
	 * Set Current Game Mode
	 */
	public static void setGameMode(GameMode gameMode) {
		if (GameState.gameMode != gameMode) {
			GameState.gameMode = gameMode;
			Logger.log("Game Mode Change To " + GameState.gameMode.name());
		}
	}

	/**
	 * Is Game Running
	 */
	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * Set Game Running State
	 */
	public static void setRunning(boolean isRunning) {
		GameState.isRunning = isRunning;
	}

	/**
	 * Is Game Pause
	 */
	public static boolean isPause() {
		return isPause;
	}

	/**
	 * Set Game Pause
	 */
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

	/**
	 * Get Last Game Pause Duration
	 */
	public static long getLastPauseDulation() {
		return lastPauseDulation;
	}

}
