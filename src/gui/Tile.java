package gui;

import utility.ResourceManager.ImageResource;

public class Tile {

	private ImageResource imageResource;
	private String tileCode;
	private boolean isWalkable, isPlacable, isPenetrable, isHighlight;

	public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable,
			boolean isPenetrable) {
		this.imageResource = imageResource;
		this.tileCode = tileCode;
		this.isWalkable = isWalkable;
		this.isPlacable = isPlacable;
		this.isPenetrable = isPenetrable;
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public boolean isWalkable() {
		return isWalkable;
	}

	public boolean isPlacable() {
		return isPlacable;
	}

	public boolean isPenetrable() {
		return isPenetrable;
	}

	public boolean isHighlight() {
		return isHighlight;
	}

	public void setHighlight(boolean isHighlight) {
		this.isHighlight = isHighlight;
	}

}
