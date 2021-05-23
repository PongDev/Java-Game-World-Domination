package update;

import java.util.ArrayList;

/**
 * Update Manager: Management Update Of Updatable Object
 */
public class UpdateManager {

	/**
	 * Array List Contain Updatable Object
	 */
	private static ArrayList<Updatable> updatableObject = new ArrayList<Updatable>();

	/**
	 * Array List Contain Updatable Object Waiting For Add Into updatableObject
	 */
	private static ArrayList<Updatable> updatableObjectQueue = new ArrayList<Updatable>();

	/**
	 * Update updatableObject Array List - Remove Item Should Remove From Update
	 */
	private static void updateUpdatableObject() {
		for (int i = updatableObject.size() - 1; i >= 0; i--) {
			if (updatableObject.get(i).isRemoveFromUpdate()) {
				updatableObject.remove(i);
			}
		}
	}

	/**
	 * Move Object In updatableObjectQueue To updatableObject Then Update Object In
	 * updatableObject
	 */
	public static void update() {
		for (Updatable obj : updatableObjectQueue) {
			updatableObject.add(obj);
		}
		updatableObjectQueue.clear();
		updateUpdatableObject();
		for (Updatable obj : updatableObject) {
			obj.update();
		}
	}

	/**
	 * Add Object To updatableObjectQueue
	 */
	public static void add(Updatable obj) {
		updatableObjectQueue.add(obj);
	}

}
