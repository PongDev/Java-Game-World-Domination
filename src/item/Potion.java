package item;

import character.Character;
import utility.ResourceManager.ImageResource;

/**
 * Subclass Of Item: Potion
 */
public class Potion extends Item {

	/**
	 * Heal Amount On Use
	 */
	private int healAmount;

	/**
	 * Potion Main Constructor
	 */
	public Potion(String name, ImageResource image, int cost, int healAmount) {
		super(name, image, cost);
		this.description = String.format("Heal %d health", healAmount);
		this.healAmount = healAmount;
	}

	/**
	 * Potion Use Action
	 * 
	 * @param character Potion User
	 */
	public void use(Character character) {
		character.setHealth(character.getHealth() + healAmount);
	}

	/**
	 * Is Potion Allow Buy
	 */
	public boolean isAllowBuy() {
		return this.isAllowbuy;
	}
}
