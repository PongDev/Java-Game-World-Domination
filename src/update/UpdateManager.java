package update;

import java.util.ArrayList;

public class UpdateManager {

	private static ArrayList<Updatable> updatableObject = new ArrayList<Updatable>();

	private static void updateUpdatableObject() {
		for (int i = updatableObject.size() - 1; i >= 0; i--) {
			if (updatableObject.get(i).isRemoveFromUpdate()) {
				updatableObject.remove(i);
			}
		}
	}

	public static void update() {
		updateUpdatableObject();
		for (Updatable obj : updatableObject) {
			obj.update();
		}
	}

	public static void add(Updatable obj) {
		updatableObject.add(obj);
	}

}
