package application;

import config.Config;
import input.InputManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GameState;
import render.RenderManager;
import update.UpdateManager;
import utility.Logger;
import utility.WaveManager;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		GameState.setGameStage(stage);
		stage.setTitle(Config.GAME_TITLE);
		stage.setResizable(false);
		stage.setScene(GameState.getScene());
		stage.show();
		AnimationTimer animation = new AnimationTimer() {

			@Override
			public void handle(long now) {
				if (GameState.isSceneChange()) {
					stage.setScene(GameState.getScene());
					stage.show();
				}
				InputManager.processInputableObject();
				UpdateManager.update();
				WaveManager.update();
				RenderManager.render();
			}
		};
		animation.start();
	}

	public static void main(String[] args) {
		Logger.log("Application Launch");
		GameState.setRunning(true);
		launch(args);
		GameState.setRunning(false);
		Logger.log("Application Close");
	}
}
