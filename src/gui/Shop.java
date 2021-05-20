package gui;

import java.util.ArrayList;

import character.MainCharacter;
import config.Config;
import item.Buyable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import logic.GameState;
import render.Renderable;
import utility.Logger;
import utility.ResourceManager;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Shop extends StackPane {

	private GameText shopText, descriptionText, costText;
	private GridPane itemPane;
	private ImageView descriptionImage;
	private ArrayList<Buyable> itemList = new ArrayList<Buyable>();
	private ArrayList<ShopButton> itemButtonList = new ArrayList<ShopButton>();
	private ArrayList<StackPane> itemStackPaneList = new ArrayList<StackPane>();
	private GameButton buyButton;
	private int currentPage;
	private ItemResource selectedItem;

	public Shop() {

		// TODO ownText, nextPageBTN, previousPageBTN;

		this.currentPage = 0;
		this.setVisible(false);
		this.setMinHeight(Config.SCREEN_H / 1.1);
		this.setMaxHeight(Config.SCREEN_H / 1.1);
		this.setMinWidth(Config.SCREEN_W / 1.1);
		this.setMaxWidth(Config.SCREEN_W / 1.1);
		this.setBackground(
				new Background(new BackgroundFill(Color.rgb(160, 160, 160, 0.8), CornerRadii.EMPTY, new Insets(5))));

		shopText = new GameText("Shop", Config.SCREEN_H / 10);
		shopText.setAlignment(Pos.TOP_CENTER);
		shopText.setTranslateY(-Config.SCREEN_H / 2.70);

		descriptionImage = ResourceManager.getImageView(ImageResource.ITEM_DESCRIPTION, (int) (Config.SCREEN_W / 2),
				(int) (Config.SCREEN_H / 8));
		descriptionImage.setTranslateX(-Config.SCREEN_H / 6);
		descriptionImage.setTranslateY(Config.SCREEN_W / 5);
		descriptionImage.setVisible(false);

		costText = new GameText("", Config.SCREEN_H / 25);
		costText.setAlignment(Pos.CENTER);
		costText.setTranslateX(Config.SCREEN_W / 4);
		costText.setTranslateY(Config.SCREEN_H / 4);
		costText.setVisible(false);

		descriptionText = new GameText("", Config.SCREEN_H / 25);
		descriptionText.setAlignment(Pos.TOP_LEFT);
		descriptionText.setPrefWidth(Config.SCREEN_W / 2);
		descriptionText.setTranslateX(-Config.SCREEN_H / 8);
		descriptionText.setTranslateY(Config.SCREEN_W / 5);

		for (ItemResource itemResource : ItemResource.values()) {
			Buyable item = ResourceManager.getItem(itemResource);

			VBox itemVBox = new VBox();
			itemVBox.setAlignment(Pos.CENTER);
			GameText itemName = new GameText(item.getName(), Config.SCREEN_H / 28);
			itemName.setTextAlignment(TextAlignment.CENTER);
			itemName.setPrefWidth(Config.SCREEN_W / 7);
			itemName.setWrapText(true);
			ImageView itemImage = ResourceManager.getImageView(item.getImage(), (int) (Config.SCREEN_W / 11),
					(int) (Config.SCREEN_H / 7));
			itemVBox.getChildren().addAll(itemImage, itemName);

			ShopButton itemButton = new ShopButton(itemResource, ImageResource.ITEM_BUTTON_TRANSPARENT,
					Config.SCREEN_W / 6, (int) (Config.SCREEN_H / 4.25));
			ImageView itemButtonBG = ResourceManager.getImageView(ImageResource.ITEM_BUTTON,
					(int) (Config.SCREEN_W / 6), (int) (Config.SCREEN_H / 4.25));
			itemButtonList.add(itemButton);

			// Selected item to buy
			itemButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					descriptionText.setText(item.getName() + "\n" + item.getDescription());
					descriptionImage.setVisible(true);
					costText.setText("Cost " + Integer.toString(item.getCost()));
					costText.setVisible(true);
					selectedItem = itemResource;
				}
			});

			StackPane itemStackPane = new StackPane();
			itemStackPane.getChildren().addAll(itemButtonBG, itemVBox, itemButton);
			itemStackPaneList.add(itemStackPane);
			itemList.add(item);
		}
		itemPane = new GridPane();
		itemPane.setAlignment(Pos.CENTER);
		itemPane.setTranslateY(-Config.SCREEN_H / 17);
		itemPane.setVgap(10);
		itemPane.setHgap(10);
		itemPane.setPadding(new Insets(5));

		// Auto generate gridPane at current page
		for (int i = 0 * currentPage; i < Math.min(i + 8, itemStackPaneList.size()); i++) {
			itemPane.add(itemStackPaneList.get(i), i % 4, i / 4, 1, 1);
		}

		// Buy item
		buyButton = new GameButton("Buy", ImageResource.BTN, Config.SCREEN_W / 6, Config.SCREEN_H / 10);
		buyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Buyable item = ResourceManager.getItem(selectedItem);
				MainCharacter mainCharacter = ((MainCharacter) ResourceManager
						.getGameObject(GameObjectResource.MAIN_CHARACTER));

				if (item.isAllowBuy()) {
					if (mainCharacter.getMoney() >= item.getCost()) {
						mainCharacter.setMoney(mainCharacter.getMoney() - item.getCost());
						mainCharacter.addItemToInventory(selectedItem);
						Logger.log("Bought " + ResourceManager.getItem(selectedItem).getName());
					} else {
						Logger.log("Money Not Enough To Buy " + ResourceManager.getItem(selectedItem).getName());
					}
				} else {
					Logger.log(ResourceManager.getItem(selectedItem).getName() + " Is Not Allow To Buy");
				}
			}
		});
		buyButton.setTextFill(Color.WHITE);
		buyButton.setTranslateX(Config.SCREEN_W / 3.75);
		buyButton.setTranslateY(Config.SCREEN_H / 3);
		buyButton.setOnHoverBackground(ImageResource.BTN_HOVER);

		this.getChildren().addAll(itemPane, shopText, descriptionImage, descriptionText, buyButton, costText);
	}

	public void toggleVisible() {
		this.setVisible(!this.isVisible());
		GameState.setPause(this.isVisible());
	}

}
