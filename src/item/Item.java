package item;

import java.util.ArrayList;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Buyable Item Base Class
 */
public class Item implements Buyable {

	/**
	 * Item Name
	 */
	protected String name;

	/**
	 * Item Description
	 */
	protected String description;

	/**
	 * Item ImageResource
	 */
	protected ImageResource image;

	/**
	 * Item Cost
	 */
	protected int cost;

	/**
	 * Is Item Allow Buy
	 */
	protected boolean isAllowbuy = true;

	/**
	 * ArrayList Contain Received Item ItemResource And Received Amount On Buy
	 */
	protected ArrayList<Pair<ItemResource, Integer>> itemOnBuy = new ArrayList<Pair<ItemResource, Integer>>();

	/**
	 * Item Main Constructor
	 * 
	 * @param name  Item Name
	 * @param image Item ImageResource Of Icon
	 * @param cost  Item Cost
	 */
	public Item(String name, ImageResource image, int cost) {
		this.name = name;
		this.image = image;
		this.cost = cost;
	}

	/**
	 * Get Item Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get Item Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get Item ImageResource Of Icon
	 */
	public ImageResource getIconImageResource() {
		return image;
	}

	/**
	 * Get Item Cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Is Item Allow Buy
	 */
	public boolean isAllowBuy() {
		return isAllowbuy;
	}

	/**
	 * Get Item Received On Buy
	 * 
	 * @return ArrayList Contain Received Item ItemResource And Received Amount On
	 *         Buy
	 */
	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy() {
		return itemOnBuy;
	}

}
