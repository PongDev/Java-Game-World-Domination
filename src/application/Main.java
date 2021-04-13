package application;

import config.Config;
import gui.Updatable;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GameState;
import utility.Logger;

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
				if (GameState.isRequestSceneUpdate() && stage.getScene().getRoot() instanceof Updatable) {
					((Updatable) stage.getScene().getRoot()).update();
				}
			}
		};
		animation.start();
	}

	public static void main(String[] args) {
		Logger.log("Application Launch");
		launch(args);
		Logger.log("Application Close");
	}
}
