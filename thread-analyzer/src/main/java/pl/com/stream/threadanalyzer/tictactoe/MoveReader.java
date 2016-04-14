package pl.com.stream.threadanalyzer.tictactoe;

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
