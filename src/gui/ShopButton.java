package gui;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class ShopButton extends GameButton{
	
	private ItemResource itemResource;
	
	public ShopButton(ItemResource itemResource, ImageResource imageResource, int width, int height) {
		super(imageResource, width, height);
		this.itemResource = itemResource;
	}

	public ItemResource getItemResource() {
		return itemResource;
	}
}
