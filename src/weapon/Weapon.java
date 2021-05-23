package weapon;

import java.util.ArrayList;

import item.Buyable;
import javafx.util.Pair;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

/**
 * Weapon Base Class
 */
public abstract class Weapon implements Buyable {

	/**
	 * ImageResourse Of The Weapon
	 */
	private ImageResource imageResourse;
	/**
	 * Weapon Attack Damage
	 */
	protected int attackDamage;
	/**
	 * Weapon Attack Speed
	 */
	protected double attackSpeed;
	/**
	 * Weapon Last time Attack
	 */
	protected long lastAttack = 0;
	/**
	 * ArrayList Contains Pair Of ItemResource And Item Amount On Buy
	 */
	protected ArrayList<Pair<ItemResource, Integer>> itemOnBuy = new ArrayList<Pair<ItemResource, Integer>>();

	/**
	 * Weapon Constructor
	 * 
	 * @param imageResource
	 * @param attackDamage
	 * @param attackSpeed
	 */
	public Weapon(ImageResource imageResource, int attackDamage, double attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	/**
	 * Getter Of ImageResourse
	 * 
	 * @return ImageResourse
	 */
	public ImageResource getImageResourse() {
		return imageResourse;
	}

	/**
	 * Getter Of LastAttack
	 * 
	 * @return LastAttack
	 */
	public long getLastAttack() {
		return lastAttack;
	}

	/**
	 * Setter Of LastAttack
	 * 
	 * @param lastAttack
	 */
	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	/**
	 * Getter Of ItemOnBuy
	 */
	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy() {
		return itemOnBuy;
	}

	/**
	 * Abstract Method For Weapon Attack
	 * 
	 * @param centerPos
	 * @param degree
	 * @return True If Weapon Attack Successfully, False If Not
	 */
	public abstract boolean attack(Position centerPos, double degree);

}