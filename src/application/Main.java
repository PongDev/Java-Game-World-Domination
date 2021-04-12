package application;

import config.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.IMG;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		StackPane root = new StackPane();

		ImageView bgTitle = ResourceManager.getResource(IMG.BG_TITLE);
		root.getChildren().addAll(bgTitle);

		Scene scene = new Scene(root, Config.SCREEN_W, Config.SCREEN_H);
		stage.setTitle(Config.GAME_TITLE);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		Logger.log("Application Launch");
		launch(args);
	}
}
