package object;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import render.Renderable;

public abstract class GameObject implements Renderable {

	protected boolean isDestroy;
	protected boolean isVisible;
	protected Image sprite;
	protected Point2D position = new Point2D(0.0D, 0.0D);

	public GameObject() {
		this.isDestroy = false;
		this.isVisible = true;
	}
	
	public double getWidth() {
		 return getSprite().getWidth();
	}
	
	public double getHeight() {
		 return getSprite().getHeight();
	}

	public boolean isDestroy() {
		return isDestroy;
	}

	public void setDestroy(boolean isDestroy) {
		this.isDestroy = isDestroy;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

}
