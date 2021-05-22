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

public class Gun extends Weapon {

	private BulletProperties bulletProperties;
	protected String name = "";
	protected String description = "";
	protected int cost = 0;
	protected ItemResource itemResource = null;
	protected ItemResource bulletUse = null;
	protected SoundResource attackSound = null;
	protected double attackSoundVolume = Config.SOUND_VOLUME;

	public Gun(ImageResource imageResource, int attackDamage, double attackSpeed, ImageResource bulletImageResource,
			int bulletSpeed, int bulletWidth, int bulleteHeight, int team, int holderZIndex, GameObject owner) {
		super(imageResource, attackDamage, attackSpeed);
		setBulletProperties(new BulletProperties(bulletImageResource, bulletWidth, bulleteHeight, attackDamage,
				bulletSpeed, team, holderZIndex + 1, owner));
	}

	public Gun(ImageResource imageResource, int attackDamage, double attackSpeed, ImageResource bulletImageResource,
			int bulletSpeed, int bulletWidth, int bulleteHeight, int team, int holderZIndex) {
		this(imageResource, attackDamage, attackSpeed, bulletImageResource, bulletSpeed, bulletWidth, bulleteHeight,
				team, holderZIndex, null);
	}

	public BulletProperties getBulletProperties() {
		return bulletProperties;
	}

	public void setBulletProperties(BulletProperties bulletProperties) {
		this.bulletProperties = bulletProperties;
	}

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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ImageResource getImage() {
		return getImageResourse();
	}

	public int getCost() {
		return cost;
	}

	public boolean isAllowBuy() {
		return ((MainCharacter) ResourceManager.getGameObject(GameObjectResource.MAIN_CHARACTER))
				.countItemInInventory(itemResource) < 1;
	}

	public ItemResource getBulletUse() {
		return bulletUse;
	}

}
