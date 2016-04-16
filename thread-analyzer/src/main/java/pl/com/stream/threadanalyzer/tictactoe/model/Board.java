package pl.com.stream.threadanalyzer.tictactoe.model;

import java.io.Serializable;
import java.lang.reflect.Field;

public class Board implements Serializable {
	public static enum BoardResult {
		WINNER, DRAW, CONTINUE
	}

	private Player x1y1, x2y1, x3y1;
	private Player x1y2, x2y2, x3y2;
	private Player x1y3, x2y3, x3y3;

	public boolean hasWinner(Player player) {
		return (x1y1 == x2y1 && x2y1 == x3y1 && x3y1 == player)
				|| (x1y2 == x2y2 && x2y2 == x3y2 && x3y2 == player)
				|| (x1y3 == x2y3 && x2y3 == x3y3 && x3y3 == player)
				|| (x1y1 == x1y2 && x1y2 == x1y3 && x1y3 == player)
				|| (x2y1 == x2y2 && x2y2 == x3y3 && x3y3 == player)
				|| (x2y1 == x2y2 && x2y2 == x3y3 && x3y3 == player)
				|| (x1y1 == x2y2 && x2y2 == x3y3 && x3y3 == player)
				|| (x1y3 == x2y2 && x2y2 == x3y1 && x3y1 == player);
	}

	public BoardResult move(Move move, Player player) {
		String fieldName = move.fieldName();
		try {
			Field declaredField = Board.class.getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			if (declaredField.get(this) == null) {
				declaredField.set(this, player);
				if (hasWinner(player)) {
					return BoardResult.WINNER;
				}
			}
		} catch (Exception e) {
		}
		if (allFieldsSet()) {
			return BoardResult.DRAW;
		}
		return BoardResult.CONTINUE;
	}

	public boolean isDuplication(Move move) {
		String fieldName = move.fieldName();
		try {
			Field declaredField = Board.class.getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			return declaredField.get(this) != null;
		} catch (Exception e) {
		}
		return false;
	}

	private boolean allFieldsSet() {
		return x1y1 != null && x2y1 != null && x3y1 != null && x1y2 != null
				&& x2y2 != null && x3y2 != null && x1y3 != null && x2y3 != null
				&& x3y3 != null;
	}
}
