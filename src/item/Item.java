package item;

import java.util.ArrayList;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public class Item implements Buyable {

	protected String name, description;
	protected ImageResource image;
	protected int cost;
	protected boolean isAllowbuy = true;
	protected ArrayList<Pair<ItemResource, Integer>> itemOnBuy = new ArrayList<Pair<ItemResource, Integer>>();

	public Item(String name, ImageResource image, int cost) {
		this.name = name;
		this.image = image;
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ImageResource getImage() {
		return image;
	}

	public int getCost() {
		return cost;
	}

	public boolean isAllowBuy() {
		return isAllowbuy;
	}

	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy() {
		return itemOnBuy;
	}

}
