package render;

public interface Renderable {

	public void render();

	public boolean isAllowRender();

	public boolean isDestroyed();

	public int getZ();

}
