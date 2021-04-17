package gui;

import utility.ResourceManager.ImageResource;

public class Tile {

	private ImageResource imageResource;
	private String tileCode;
	private boolean isWalkable, isPlacable;

	public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable) {
		this.imageResource = imageResource;
		this.tileCode = tileCode;
		this.isWalkable = isWalkable;
		this.isPlacable = isPlacable;
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

}
