package application;

import config.Config;
import javafx.application.Application;
import javafx.stage.Stage;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.SceneResource;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle(Config.GAME_TITLE);
		stage.setScene(ResourceManager.getScene(SceneResource.MENU));
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		Logger.log("Application Launch");
		launch(args);
	}
}
