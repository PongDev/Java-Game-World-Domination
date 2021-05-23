package gui;

import java.util.HashMap;
import java.util.Map;

import utility.ResourceManager.ImageResource;

/**
 * Contains Tile Properties
 */
public class Tile {
	/**
	 * ImageResource Of Tile
	 */
    private ImageResource imageResource;
    /**
     * Tile Code
     */
    private String tileCode;
    /**
     * Is GameObject Walkable
     */
    private boolean isWalkable;
    /**
     * Is Tower Placable
     */
    private boolean isPlacable;
    /**
     * Is Bullet Penetrable
     */
    private boolean isPenetrable;
    /**
     * Is Tile Highlight
     */
    private boolean isHighlight;
    /**
     * Map Contains Key Of GameObject Team And Value Of Is Team Whitelist
     */
    private Map<Integer, Boolean> isWhitelist = new HashMap<Integer, Boolean>();

    /**
     * Tile Main Constructor
     * @param imageResource
     * @param tileCode
     * @param isWalkable
     * @param isPlacable
     * @param isPenetrable
     */
    public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable,
            boolean isPenetrable) {
        this.imageResource = imageResource;
        this.tileCode = tileCode;
        this.isWalkable = isWalkable;
        this.isPlacable = isPlacable;
        this.isPenetrable = isPenetrable;
    }
    /**
     * Tile Alternative Constructor
     * @param imageResource
     * @param tileCode
     * @param isWalkable
     * @param isPlacable
     * @param isPenetrable
     * @param isWhitelist
     */
    public Tile(ImageResource imageResource, String tileCode, boolean isWalkable, boolean isPlacable,
            boolean isPenetrable, Map<Integer, Boolean> isWhitelist) {
        this(imageResource, tileCode, isWalkable, isPlacable, isPenetrable);
        this.isWhitelist = isWhitelist;
    }

    /**
     * Getter Of ImageResource
     * @return imageResource
     */
    public ImageResource getImageResource() {
        return imageResource;
    }

    /**
     * Getter Of isWalkable
     * @return isWalkable
     */
    public boolean isWalkable() {
        return isWalkable;
    }
    /**
     * Getter Of isPlacable
     * @return isPlacable
     */
    public boolean isPlacable() {
        return isPlacable;
    }
    /**
     * Getter Of isPenetrable
     * @return isPenetrable
     */
    public boolean isPenetrable() {
        return isPenetrable;
    }
    /**
     * Getter Of isHighlight
     * @return isHighlight
     */
    public boolean isHighlight() {
        return isHighlight;
    }
    /**
     * Getter Of IsWhitelist
     * @param team
     * @return IsWhitelist
     */
    public boolean IsWhitelist(int team) {
        return isWhitelist.get(team);
    }
    /**
     * Getter Of setHighlight
     * @param isHighlight
     */
    public void setHighlight(boolean isHighlight) {
        this.isHighlight = isHighlight;
    }
    /**
     * Getter Of TileCode
     * @return tileCode
     */
    public String getTileCode() {
        return tileCode;
    }
}
