package item;

import character.Character;
import utility.ResourceManager.ImageResource;

public class Potion implements Buyable {
	private String name, description;
	private ImageResource image;
	private int cost;
	private int healAmount;
	private boolean isAllowbuy = true;

	public Potion(String name, ImageResource image, int cost, int healAmount) {
		this.name = name;
		this.description = String.format("Heal %d health", healAmount);
		this.image = image;
		this.cost = cost;
		this.healAmount = healAmount;
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

	public void use(Character character) {
		character.setHealth(character.getHealth() + healAmount);
	}

	public boolean isAllowBuy() {
		return this.isAllowbuy;
	}
}
