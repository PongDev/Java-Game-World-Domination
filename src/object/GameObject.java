package object;

import render.Renderable;
import utility.Position;
import utility.ResourceManager.ImageResource;

/**
 * Game Object Base Class
 */
public abstract class GameObject implements Renderable {

	/**
	 * Game Object ImageResource
	 */
	protected ImageResource imageResource;

	/**
	 * Game Object Top-Left Position
	 */
	protected Position pos;

	/**
	 * Game Object Width
	 */
	protected int width;

	/**
	 * Game Object Height
	 */
	protected int height;

	/**
	 * Team Game Object Belong To
	 */
	protected int team;

	/**
	 * Game Object Alternative Constructor
	 */
	public GameObject(ImageResource imageResource, int width, int height, int centerX, int centerY) {
		this(imageResource, width, height, new Position(centerX, centerY));
	}

	/**
	 * Game Object Main Constructor
	 * 
	 * @param imageResource Game Object ImageResource
	 * @param width         Game Object Width
	 * @param height        Game Object Height
	 * @param centerPos     Game Object Center Position
	 */
	public GameObject(ImageResource imageResource, int width, int height, Position centerPos) {
		this.imageResource = imageResource;
		this.width = width;
		this.height = height;
		this.pos = new Position(centerPos.X - (width / 2), centerPos.Y - (height / 2));
	}

	/**
	 * Get Game Object Center Position
	 */
	public Position getCenterPos() {
		return new Position(pos.X + (width / 2), pos.Y + (height / 2));
	}

	/**
	 * Get Game Object Image Resource
	 */
	public ImageResource getImageResource() {
		return imageResource;
	}

	/**
	 * Abstract Function For Renderable Interface
	 */
	public abstract void render();

	/**
	 * Abstract Function For Renderable Interface
	 */
	public abstract boolean isAllowRender();

	/**
	 * Abstract Function For Renderable Interface
	 */
	public abstract boolean isDestroyed();

	/**
	 * Abstract Function Action On Destroyed
	 */
	public abstract void onDestroyed();

	/**
	 * Abstract Function For Renderable Interface
	 */
	public abstract int getZ();

	/**
	 * Abstract Function Deal Damage To This Game Object
	 * 
	 * @param damage Damage Deal To This Game Object
	 */
	public abstract void dealDamage(int damage);

	/**
	 * Get Game Object Position
	 */
	public Position getPos() {
		return pos;
	}

	/**
	 * Get Game Object Width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Get Game Object Height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Get Game Object Team
	 */
	public int getTeam() {
		return team;
	}

}
