package pl.com.stream.threadanalyzer.tictactoe;

import pl.com.stream.threadanalyzer.tictactoe.model.Board;
import pl.com.stream.threadanalyzer.tictactoe.model.Move;
import pl.com.stream.threadanalyzer.tictactoe.model.Player;

public class MoveReader {

	private Player player;

	public MoveReader(Player player) {
		this.player = player;
	}

	public Move getNextMove(Board board) {
		MoveReaderAdapter moveReaderAdapter = new MoveReaderAdapter(board,
				player);
		return moveReaderAdapter.execute();
	}

}
