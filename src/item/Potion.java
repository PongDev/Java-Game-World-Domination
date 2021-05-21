package item;

import character.Character;
import utility.ResourceManager.ImageResource;

public class Potion extends Item {

	private int healAmount;

	public Potion(String name, ImageResource image, int cost, int healAmount) {
		super(name, image, cost);
		this.description = String.format("Heal %d health", healAmount);
		this.healAmount = healAmount;
	}

	public void use(Character character) {
		character.setHealth(character.getHealth() + healAmount);
	}

	public boolean isAllowBuy() {
		return this.isAllowbuy;
	}
}
