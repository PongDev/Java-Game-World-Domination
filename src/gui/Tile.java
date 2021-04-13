package gui;

import utility.ResourceManager.ImageResource;

public class Tile {

	private ImageResource imageResource;
	private String tileCode;

	public Tile(ImageResource imageResource, String tileCode) {
		this.imageResource = imageResource;
		this.tileCode = tileCode;
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

}
