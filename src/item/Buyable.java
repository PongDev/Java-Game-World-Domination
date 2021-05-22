package item;

import java.util.ArrayList;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Buyable Interface
 */
public interface Buyable {

	/**
	 * Get Item Name
	 */
	public String getName();

	/**
	 * Get Item Description
	 */
	public String getDescription();

	/**
	 * Get Item ImageResource Icon Image
	 */
	public ImageResource getIconImageResource();

	/**
	 * Get Item Cost
	 */
	public int getCost();

	/**
	 * Is Item Allow Buy
	 */
	public boolean isAllowBuy();

	/**
	 * Received Item On Buy
	 * 
	 * @return ArrayList Of ItemResource, Amount Of Item Received
	 */
	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy();
}
