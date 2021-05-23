package update;

/**
 * Updatable Interface
 */
public interface Updatable {

	/**
	 * Function To Process Update
	 */
	public abstract void update();

	/**
	 * Is UpdateManager Should Remove Object From updatableObject
	 */
	public abstract boolean isRemoveFromUpdate();

}
