package render;

/**
 * Renderable Interface
 */
public interface Renderable {

	/**
	 * Function To Render
	 */
	public void render();

	/**
	 * Is Allow Render
	 */
	public boolean isAllowRender();

	/**
	 * Is Destroyed
	 */
	public boolean isDestroyed();

	/**
	 * Get ZIndex
	 */
	public int getZ();

}
