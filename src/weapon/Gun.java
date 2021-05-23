package weapon;

import java.util.Date;

import character.MainCharacter;
import config.Config;
import object.GameObject;
import object.ObjectManager;
import render.RenderManager;
import sound.SoundManager;
import update.UpdateManager;
import utility.Position;
import utility.ResourceManager;
import utility.ResourceManager.GameObjectResource;
import utility.ResourceManager.ImageResource;
import utility.ResourceManager.ItemResource;
import utility.ResourceManager.SoundResource;

/**
 * Subclass Of Weapon: Gun Base Class
 */
public class Gun extends Weapon {

	/**
	 * Properties Of Weapon Bullet
	 */
	private BulletProperties bulletProperties;
	/**
	 * Gun Name
	 */
	protected String name = "";
	/**
	 * Gun Description
	 */
	protected String description = "";
	/**
	 * Gun Cost In Shop
	 */
	protected int cost = 0;
	/**
	 * Gun ItemResource
	 */
	protected ItemResource itemResource = null;
	/**
	 * Gun Current Bullet Amount
	 */
	protected ItemResource bulletUse = null;
	/**
	 * Gun Attack Sound
	 */
	protected SoundResource attackSound = null;
	/**
	 * Gun Attack SoundVolume
	 */
	protected double attackSoundVolume = Config.SOUND_VOLUME;

	/**
	 * Gun Main Constructor
	 * 
	 * @param imageResource
	 * @param attackDamage
	 * @param attackSpeed
	 * @param bulletImageResource
	 * @param bulletSpeed
	 * @param bulletWidth
	 * @param bulleteHeight
	 * @param team                Bullet Team
	 * @param holderZIndex        Owner Z Index
	 * @param owner               Owner Of the Weapon
	 */
	public Gun(ImageResource imageResource, int attackDamage, double attackSpeed, ImageResource bulletImageResource,
			int bulletSpeed, int bulletWidth, int bulleteHeight, int team, int holderZIndex, GameObject owner) {
		super(imageResource, attackDamage, attackSpeed);
		setBulletProperties(new BulletProperties(bulletImageResource, bulletWidth, bulleteHeight, attackDamage,
				bulletSpeed, team, holderZIndex + 1, owner));
	}

	/**
	 * Gun Alternative Constructor
	 * 
	 * @param imageResource
	 * @param attackDamage
	 * @param attackSpeed
	 * @param bulletImageResource
	 * @param bulletSpeed
	 * @param bulletWidth
	 * @param bulleteHeight
	 * @param team                Bullet Team
	 * @param holderZIndex        Owner Z Index
	 */
	public Gun(ImageResource imageResource, int attackDamage, double attackSpeed, ImageResource bulletImageResource,
			int bulletSpeed, int bulletWidth, int bulleteHeight, int team, int holderZIndex) {
		this(imageResource, attackDamage, attackSpeed, bulletImageResource, bulletSpeed, bulletWidth, bulleteHeight,
				team, holderZIndex, null);
	}

	/**
	 * Getter Of BulletProperties
	 * 
	 * @return BulletProperties
	 */
	public BulletProperties getBulletProperties() {
		return bulletProperties;
	}

	/**
	 * Setter Of BulletProperties
	 * 
	 * @param bulletProperties
	 */
	public void setBulletProperties(BulletProperties bulletProperties) {
		this.bulletProperties = bulletProperties;
	}

	/**
	 * Gun Attack Function
	 */
	public boolean attack(Position centerPos, double degree) {
		if ((new Date()).getTime() - lastAttack >= 1000 / attackSpeed) {
			lastAttack = (new Date()).getTime();
			Bullet bullet = new Bullet(bulletProperties, centerPos, degree);
			UpdateManager.add(bullet);
			RenderManager.add(bullet);
			ObjectManager.addBullet(bullet);
			if (attackSound != null) {
				SoundManager.playSoundEffect(attackSound, attackSoundVolume);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Getter Of Gun Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter Of Gun Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter Of IconImageResource
	 */
	public ImageResource getIconImageResource() {
		return getImageResourse();
	}

	/**
	 * Getter Of Gun Cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Is This Gun Allow Buy
	 */
	public boolean isAllowBuy() {
		return ((MainCharacter) ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER))
				.countItemInInventory(itemResource) < 1;
	}

	/**
	 * Getter Of BulletUse
	 * 
	 * @return BulletUse
	 */
	public ItemResource getBulletUse() {
		return bulletUse;
	}

}
