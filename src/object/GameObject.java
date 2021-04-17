package object;

import render.Renderable;
import utility.Position;
import utility.ResourceManager.ImageResource;

public abstract class GameObject implements Renderable {

	protected ImageResource imageResource;
	protected Position pos;
	protected int width, height;

	public GameObject(ImageResource imageResource, int width, int height, int centerX, int centerY) {
		this(imageResource, width, height, new Position(centerX, centerY));
	}

	public GameObject(ImageResource imageResource, int width, int height, Position centerPos) {
		this.imageResource = imageResource;
		this.width = width;
		this.height = height;
		this.pos = new Position(centerPos.X - (width / 2), centerPos.Y - (height / 2));
	}

	public Position getCenterPos() {
		return new Position(pos.X + (width / 2), pos.Y + (height / 2));
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public abstract boolean isDestroyed();

	public Position getPos() {
		return pos;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
