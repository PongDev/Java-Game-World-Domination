package render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Render Manager: Management Render System
 */
public class RenderManager {

	/**
	 * Array List Contain Current In-Game Renderable Object
	 */
	private static ArrayList<Renderable> renderableObject = new ArrayList<Renderable>();

	/**
	 * Comparator For Sort ZIndex
	 */
	private static Comparator<Renderable> renderableComparator = (Renderable obj1, Renderable obj2) -> {
		if (obj1.getZ() == obj2.getZ()) {
			return 0;
		} else {
			return (obj1.getZ() > obj2.getZ()) ? 1 : -1;
		}
	};

	/**
	 * Is renderableObject Currently Sort
	 */
	private static boolean isSort = true;

	/**
	 * Update renderableObject Array List Remove Destroyed Object
	 */
	private static void updateRenderableObject() {
		for (int i = renderableObject.size() - 1; i >= 0; i--) {
			if (renderableObject.get(i).isDestroyed()) {
				renderableObject.remove(i);
			}
		}
	}

	/**
	 * Render Renderable Object In renderableObject Array List
	 */
	public static void render() {
		updateRenderableObject();
		if (!isSort) {
			Collections.sort(renderableObject, renderableComparator);
			isSort = true;
		}
		for (Renderable obj : renderableObject) {
			if (obj.isAllowRender()) {
				obj.render();
			}
		}
	}

	/**
	 * Add Renderable Object To renderableObject Array List
	 */
	public static void add(Renderable obj) {
		isSort = false;
		renderableObject.add(obj);
	}

}
