package item;

import utility.ResourceManager.ImageResource;

public interface Buyable {

	public String getName();

	public String getDescription();

	public ImageResource getImage();

	public int getCost();

	public boolean isAllowBuy();
}
