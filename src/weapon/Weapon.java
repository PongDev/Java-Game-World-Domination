package weapon;

import java.util.ArrayList;

import item.Buyable;
import javafx.util.Pair;
import utility.Position;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;

public abstract class Weapon implements Buyable {

	private ImageResource imageResourse;
	protected int attackDamage;
	protected double attackSpeed;
	protected long lastAttack = 0;
	protected ArrayList<Pair<ItemResource, Integer>> itemOnBuy = new ArrayList<Pair<ItemResource, Integer>>();

	public Weapon(ImageResource imageResource, int attackDamage, double attackSpeed) {
		this.imageResourse = imageResource;
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
	}

	public ImageResource getImageResourse() {
		return imageResourse;
	}

	public long getLastAttack() {
		return lastAttack;
	}

	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	public ArrayList<Pair<ItemResource, Integer>> itemOnBuy() {
		return itemOnBuy;
	}

	public abstract boolean attack(Position centerPos, double degree);

}