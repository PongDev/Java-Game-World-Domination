package gui;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class ShopButton extends GameButton{
	/**
	 * Item That Associate With Button
	 */
	private ItemResource itemResource;
	/**
	 * ShopButton Constructor
	 * @param Item That Associate With Button
	 * @param Button Image
	 * @param Button Width
	 * @param Button Height
	 */
	public ShopButton(ItemResource itemResource, ImageResource imageResource, int width, int height) {
		super(imageResource, width, height);
		this.itemResource = itemResource;
	}

	/**
	* Getter Of ItemResource
	* @return getItemResource
	*/
	public ItemResource getItemResource() {
		return itemResource;
	}
}
