package render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RenderManager {

	private static ArrayList<Renderable> renderableObject = new ArrayList<Renderable>();
	private static Comparator<Renderable> renderableComparator = (Renderable obj1, Renderable obj2) -> {
		return (obj1.getZ() > obj2.getZ()) ? 1 : -1;
	};
	private static boolean isSort = true;

	public static void render() {
		updateRenderableObject();
		if (!isSort) {
			Collections.sort(renderableObject, renderableComparator);
			isSort = true;
		}
		for (Renderable object : renderableObject) {
			if (object.isAllowRender()) {
				object.render();
			}
		}
	}

	public static void add(Renderable obj) {
		isSort = false;
		renderableObject.add(obj);
	}

	public static void updateRenderableObject() {
		for (int i = renderableObject.size() - 1; i >= 0; i--) {
			if (renderableObject.get(i).isDestroyed()) {
				renderableObject.remove(i);
			}
		}
	}

}
