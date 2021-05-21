package utility;

import java.util.LinkedList;
import java.util.Queue;

import config.Config;
import javafx.util.Pair;
import logic.GameState;
import object.GameObject;

public class Utility {
	public static boolean isObjectCollide(GameObject gameObject, double posX, double posY) {
		return posX >= gameObject.getPos().X && posY >= gameObject.getPos().Y
				&& posX <= (gameObject.getPos().X + gameObject.getWidth() - 1)
				&& posY <= (gameObject.getPos().Y + gameObject.getHeight() - 1);
	}

	public static boolean isObjectCollide(GameObject target, GameObject bullet) {
		return isObjectCollide(bullet, target.getPos().X, target.getPos().Y)
				|| isObjectCollide(bullet, target.getPos().X + target.getWidth(), target.getPos().Y)
				|| isObjectCollide(bullet, target.getPos().X, target.getPos().Y + target.getHeight())
				|| isObjectCollide(bullet, target.getPos().X + target.getWidth(),
						target.getPos().Y + target.getHeight());
	}

	public static double euclideanDistance(Position pos1, Position pos2) {
		return Math.sqrt(Math.pow(pos1.X - pos2.X, 2) + Math.pow(pos1.Y - pos2.Y, 2));
	}

	public static double euclideanDistance(GameObject obj1, GameObject obj2) {
		return euclideanDistance(obj1.getCenterPos(), obj2.getCenterPos());
	}

	public static boolean isZeroSumVector(Position vector1, Position vector2) {
		return (vector1.X + vector2.X == 0 && vector1.Y + vector2.Y == 0);
	}

	public static void calculateDistanceFromGameObject(GameObject obj, int distanceFromObject[][]) {
		int posRow = (int) (obj.getCenterPos().Y / Config.TILE_H);
		int posCol = (int) (obj.getCenterPos().X / Config.TILE_W);

		for (int rowPos = 0; rowPos < GameState.getMapHeight(); rowPos++) {
			for (int colPos = 0; colPos < GameState.getMapWidth(); colPos++) {
				distanceFromObject[rowPos][colPos] = -1;
			}
		}

		Queue<Pair<Integer, Position>> queue = new LinkedList<>();

		queue.add(new Pair<>(0, new Position(posRow, posCol)));
		while (!queue.isEmpty()) {
			Position pos = queue.peek().getValue();
			int distance = queue.remove().getKey();
			if ((int) pos.X >= 0 && (int) pos.Y >= 0 && (int) pos.X < distanceFromObject.length
					&& (int) pos.Y < distanceFromObject[0].length
					&& GameState.getGameMap().isWalkable(posCol * Config.TILE_W, posRow * Config.TILE_H,
							obj.getTeam() == Config.MAIN_CHARACTER_TEAM ? Config.ENEMY_TEAM
									: Config.MAIN_CHARACTER_TEAM)
					&& distanceFromObject[(int) pos.X][(int) pos.Y] == -1) {
				distanceFromObject[(int) pos.X][(int) pos.Y] = distance;
				int[] shiftRowIndex = { (int) pos.X, (int) pos.X - 1, (int) pos.X + 1 };
				int[] shiftColIndex = { (int) pos.Y, (int) pos.Y - 1, (int) pos.Y + 1 };

				for (int rowPos = 0; rowPos < shiftRowIndex.length; rowPos++) {
					for (int colPos = 0; colPos < shiftColIndex.length; colPos++) {
						if (shiftRowIndex[rowPos] >= 0 && shiftColIndex[colPos] >= 0
								&& shiftRowIndex[rowPos] < distanceFromObject.length
								&& shiftColIndex[colPos] < distanceFromObject[0].length
								&& GameState.getGameMap().isWalkable(shiftColIndex[colPos] * Config.TILE_W,
										shiftRowIndex[rowPos] * Config.TILE_H,
										obj.getTeam() == Config.MAIN_CHARACTER_TEAM ? Config.ENEMY_TEAM
												: Config.MAIN_CHARACTER_TEAM)
								&& distanceFromObject[shiftRowIndex[rowPos]][shiftColIndex[colPos]] == -1) {
							int deltaRow = (int) (shiftRowIndex[rowPos] - pos.X);
							int deltaCol = (int) (shiftColIndex[colPos] - pos.Y);
							if (Math.abs(deltaRow) == 1 && Math.abs(deltaCol) == 1) {
								if (GameState.getGameMap().isWalkable(
										(shiftColIndex[colPos] - deltaCol) * Config.TILE_W,
										shiftRowIndex[rowPos] * Config.TILE_H,
										obj.getTeam() == Config.MAIN_CHARACTER_TEAM ? Config.ENEMY_TEAM
												: Config.MAIN_CHARACTER_TEAM)
										&& GameState.getGameMap().isWalkable(shiftColIndex[colPos] * Config.TILE_W,
												(shiftRowIndex[rowPos] - deltaRow) * Config.TILE_H,
												obj.getTeam() == Config.MAIN_CHARACTER_TEAM ? Config.ENEMY_TEAM
														: Config.MAIN_CHARACTER_TEAM)) {
									queue.add(new Pair<>(distance + 1,
											new Position(shiftRowIndex[rowPos], shiftColIndex[colPos])));
								}
							} else {
								queue.add(new Pair<>(distance + 1,
										new Position(shiftRowIndex[rowPos], shiftColIndex[colPos])));
							}
						}
					}
				}
			}
		}
	}
}
