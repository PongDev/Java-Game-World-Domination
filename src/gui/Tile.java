package gui;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import utility.ResourceManager.ImageResource;

public class Tile {

	private ImageResource imageResource;
	private String tileCode;
	private boolean isWalkable, isPlacable, isPenetrable, isHighlight; 
	private Map<Integer, Boolean> isWhitelist = new HashMap<Integer, Boolean>();

	public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable,
			boolean isPenetrable) {
		this.imageResource = imageResource;
		this.tileCode = tileCode;
		this.isWalkable = isWalkable;
		this.isPlacable = isPlacable;
		this.isPenetrable = isPenetrable;
	}

	public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable,
			boolean isPenetrable, Map<Integer, Boolean> isWhitelist) {
		this(imageResource, tileCode, isWalkable, isPlacable, isPenetrable);
		this.isWhitelist = isWhitelist;
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

	public boolean IsWhitelist(int team) {
		return isWhitelist.get(team);
	}

	public void setHighlight(boolean isHighlight) {
		this.isHighlight = isHighlight;
	}

}
