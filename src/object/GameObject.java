package object;

import render.Renderable;
import utility.Position;
import utility.ResourceManager.ImageResource;

public abstract class GameObject implements Renderable {

	protected ImageResource imageResource;
	protected Position pos, centerPos;
	protected int width, height;

	public GameObject(ImageResource imageResource, int width, int height, int centerX, int centerY) {
		this(imageResource, width, height, new Position(centerX, centerY));
	}

	public GameObject(ImageResource imageResource, int width, int height, Position centerPos) {
		this.imageResource = imageResource;
		this.width = width;
		this.height = height;
		this.centerPos = centerPos;
		this.pos = new Position();
		calculatePos();
	}

	public void calculatePos() {
		this.pos.X = centerPos.X - (width / 2);
		this.pos.Y = centerPos.Y - (height / 2);
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public abstract boolean isDestroyed();

}
