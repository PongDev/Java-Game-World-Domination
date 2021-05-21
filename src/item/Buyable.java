package item;

import java.util.ArrayList;

import javafx.util.Pair;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public interface Buyable {

	public String getName();

	public String getDescription();

	public ImageResource getImage();

	public int getCost();

	public boolean isAllowBuy();

	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy();
}
