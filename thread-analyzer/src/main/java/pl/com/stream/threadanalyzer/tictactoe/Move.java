package pl.com.stream.threadanalyzer.tictactoe;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Move {
	private Player owner;
	private Integer x;
	private Integer y;
	private boolean failed = false;
	private String comment;

	public Move() {

	}

	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Move(String comment) {
		this.comment = comment;
		this.failed = true;
	}

	public Move(String comment, Player owner) {
		this(comment);
		this.owner = owner;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String fieldName() {
		return "x" + x + "y" + y;
	}

	public boolean isValid() {
		if (failed) {
			return false;
		}
		boolean valid = (x > 0 && x <= 3) && (y > 0 && y <= 3);
		return valid;
	}

	public boolean isValid(Board board) {
		if (failed) {
			return false;
		}
		boolean valid = (x > 0 && x <= 3) && (y > 0 && y <= 3);
		return valid && !board.isDuplication(this);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		if (this.comment == null)
			this.comment = comment;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
}
