package item;

import utility.ResourceManager.ImageResource;

public class Potion implements Buyable {
	private String name, description;
	private ImageResource image;
	private int cost;
	private boolean isAllowbuy = true;

	public Potion(String name, String description, ImageResource image, int cost) {
		this.name = name;
		this.description = description;
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
		return this.isAllowbuy;
	}

	public Buyable buy() {
		return new Potion(name, description, image, cost);
	}
}
