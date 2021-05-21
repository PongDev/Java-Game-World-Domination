package gui;

import character.MainCharacter;
import config.Config;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import logic.GameState;
import sound.SoundManager;
import update.Updatable;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SceneResource;
import utility.ResourceManager.SoundResource;
import utility.ResourceManager.UIResource;
import utility.ResourceManager;
import utility.WaveManager;
import weapon.Gun;

public class PlayScenePane extends StackPane implements Updatable {

	private AnchorPane gameUI;
	private VBox towerUI;
	private HBox statusBox;
	private StackPane statusPane, healhtPotionBox, ammoBox, barricadeTower, machineGunTower, sniperTower;
	private GameText waveText, pauseText, barricadeTowerAmount, machineGunTowerAmount, sniperTowerAmount;
	private GameText statusBarWaveText, coinText, healthPotionText, ammoText, deployTowerText;
	private ImageView coinImage, statusPaneImage, healthPotionpPaneImage, ammoPaneImage, barricadeTowerIcon,
			machineGunTowerIcon, sniperTowerIcon;
	private int wave = 0;

	public PlayScenePane() {
		Image cursorImage = ResourceManager.getImage(ImageResource.CROSS_HAIR);
		this.setCursor(new ImageCursor(cursorImage, cursorImage.getWidth() / 2, cursorImage.getHeight() / 2));

		statusBarWaveText = new GameText("", Config.SCREEN_H / 20, Color.WHITE);
		coinText = new GameText("", Config.SCREEN_H / 20, Color.WHITE);
		statusPaneImage = ResourceManager.getImageView(ImageResource.STATUS_PANE, (int) (Config.SCREEN_W / 4),
				(int) (Config.SCREEN_H / 15));
		coinImage = ResourceManager.getImageView(ImageResource.COIN, (int) (Config.SCREEN_W / 25),
				(int) (Config.SCREEN_H / 15));
		statusBox = new HBox();
		statusBox.setAlignment(Pos.CENTER);
		statusBox.setSpacing(5);
		statusBox.setPadding(new Insets(5));
		statusBox.getChildren().addAll(statusBarWaveText, coinImage, coinText);
		statusPane = new StackPane();
		statusPane.getChildren().addAll(statusPaneImage, statusBox);

		healthPotionText = new GameText("", Config.SCREEN_H / 20, Color.WHITE);
		healthPotionText.setAlignment(Pos.CENTER);
		healthPotionText.setTranslateY(Config.SCREEN_H / 25);
		healthPotionpPaneImage = ResourceManager.getImageView(ImageResource.HEALTH_POTION_PANE,
				(int) (Config.SCREEN_W / 10), (int) (Config.SCREEN_H / 6.5));
		healhtPotionBox = new StackPane();
		healhtPotionBox.getChildren().addAll(healthPotionpPaneImage, healthPotionText);

		ammoText = new GameText("", Config.SCREEN_H / 20, Color.WHITE);
		ammoText.setAlignment(Pos.CENTER);
		ammoPaneImage = ResourceManager.getImageView(ImageResource.AMMO_PANE, (int) (Config.SCREEN_W / 10),
				(int) (Config.SCREEN_H / 15));
		ammoBox = new StackPane();
		ammoBox.getChildren().addAll(ammoPaneImage, ammoText);

		barricadeTowerIcon = ResourceManager.getImageView(ImageResource.BARRIER_TOWER, (int) (Config.TILE_W / 1),
				(int) (Config.TILE_H / 1));
		barricadeTowerAmount = new GameText("", Config.SCREEN_H / 15, Color.WHITE);
		barricadeTower = new StackPane();
		barricadeTower.getChildren().addAll(barricadeTowerIcon, barricadeTowerAmount);

		machineGunTowerIcon = ResourceManager.getImageView(ImageResource.MACHINE_GUN_TOWER, (int) (Config.TILE_W / 1),
				(int) (Config.TILE_H / 1));
		machineGunTowerAmount = new GameText("", Config.SCREEN_H / 15, Color.WHITE);
		machineGunTower = new StackPane();
		machineGunTower.getChildren().addAll(machineGunTowerIcon, machineGunTowerAmount);

		sniperTowerIcon = ResourceManager.getImageView(ImageResource.SNIPER_TOWER, (int) (Config.TILE_W / 1),
				(int) (Config.TILE_H / 1));
		sniperTowerAmount = new GameText("", Config.SCREEN_H / 15, Color.WHITE);
		sniperTower = new StackPane();
		sniperTower.getChildren().addAll(sniperTowerIcon, sniperTowerAmount);

		towerUI = new VBox();
		towerUI.setSpacing(5);
		towerUI.getChildren().addAll(barricadeTower, machineGunTower, sniperTower);

		gameUI = new AnchorPane();
		AnchorPane.setTopAnchor(statusPane, 10.0);
		AnchorPane.setLeftAnchor(statusPane, 10.0);

		AnchorPane.setTopAnchor(healhtPotionBox, 10.0);
		AnchorPane.setRightAnchor(healhtPotionBox, 10.0);

		AnchorPane.setBottomAnchor(ammoBox, 10.0);
		AnchorPane.setRightAnchor(ammoBox, 10.0);

		AnchorPane.setBottomAnchor(towerUI, 10.0);
		AnchorPane.setLeftAnchor(towerUI, 10.0);
		gameUI.getChildren().addAll(ammoBox, healhtPotionBox, statusPane, towerUI);

		deployTowerText = new GameText("Select Nearby Tile To Deploy Tower", Config.SCREEN_H / 10);
		deployTowerText.setAlignment(Pos.CENTER);
		deployTowerText.setTranslateY(-Config.SCREEN_H / 5);
		deployTowerText.setVisible(false);

		waveText = new GameText("", Config.SCREEN_H / 9);
		waveText.setAlignment(Pos.CENTER);
		waveText.setTranslateY(-Config.SCREEN_H / 3);
		waveText.setVisible(false);

		pauseText = new GameText(Config.PAUSE_MESSAGE, Config.SCREEN_H / 5);
		pauseText.setAlignment(Pos.CENTER);
		pauseText.setVisible(false);
		this.getChildren().addAll(GameState.getGameMap(), gameUI, deployTowerText, waveText, pauseText,
				ResourceManager.getUI(UIResource.SHOP));
	}

	public void update() {
		MainCharacter mainCharacter = (MainCharacter) ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER);

		if (!WaveManager.getDisplayWaveText().isBlank()) {
			if (WaveManager.getDisplayWaveText() != waveText.getText()) {
				waveText.setText(WaveManager.getDisplayWaveText());
			}
			waveText.setVisible(true);
		} else {
			waveText.setVisible(false);
		}

		if (wave != WaveManager.getWave()) {
			statusBarWaveText.setText("Wave " + Integer.toString(WaveManager.getWave()));
			wave = WaveManager.getWave();
		}

		this.coinText.setText(Integer.toString(mainCharacter.getMoney()));
		barricadeTowerAmount
				.setText("1: x" + Integer.toString(mainCharacter.countItemInInventory(ItemResource.BARRIER_TOWER)));
		machineGunTowerAmount
				.setText("2: x" + Integer.toString(mainCharacter.countItemInInventory(ItemResource.MACHINE_GUN_TOWER)));
		sniperTowerAmount
				.setText("3: x" + Integer.toString(mainCharacter.countItemInInventory(ItemResource.SNIPER_TOWER)));
		this.healthPotionText.setText(Integer.toString(mainCharacter.countItemInInventory(ItemResource.HEALTH_POTION)));
		barricadeTower.setOpacity(0.6);
		machineGunTower.setOpacity(0.6);
		sniperTower.setOpacity(0.6);
		if (mainCharacter.getSelectedTower() != null) {
			switch (mainCharacter.getSelectedTower()) {
			case BARRIER_TOWER:
				barricadeTower.setOpacity(1);
				break;
			case MACHINE_GUN_TOWER:
				machineGunTower.setOpacity(1);
				break;
			case SNIPER_TOWER:
				sniperTower.setOpacity(1);
				break;
			default:
				break;
			}
		}

		if (mainCharacter.getWeapon() instanceof Gun && ((Gun) mainCharacter.getWeapon()).getBulletUse() != null) {
			ammoText.setText(Integer
					.toString(mainCharacter.countItemInInventory(((Gun) mainCharacter.getWeapon()).getBulletUse())));
		} else {
			ammoText.setText("-");
		}

		if (SoundManager.getCurrentSoundResource() != SoundResource.PLAYING) {
			SoundManager.setCurrentSound(SoundResource.PLAYING);
		}
		pauseText.setVisible(GameState.isPause());
		deployTowerText.setVisible(mainCharacter.getSelectedTower() != null);
	}

	public boolean isRemoveFromUpdate() {
		return GameState.getSceneResource() != SceneResource.PLAYING;
	}

}
