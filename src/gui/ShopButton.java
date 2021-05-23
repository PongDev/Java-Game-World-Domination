package gui;

import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Subclass Of GameButton: ShopButton Class
 */
public class ShopButton extends GameButton{
	/**
	 * Item That Associate With Button
	 */
	private ItemResource itemResource;
	/**
	 * ShopButton Constructor
	 * @param itemResource Item That Associate With Button
	 * @param imageResource Button Image
	 * @param width Button Width
	 * @param height Button Height
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
