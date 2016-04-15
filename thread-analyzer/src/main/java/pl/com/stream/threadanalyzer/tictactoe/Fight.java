package pl.com.stream.threadanalyzer.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import pl.com.stream.threadanalyzer.tictactoe.Board.BoardResult;

public class Fight {

	private Player playerA;
	private Player playerB;

	private Board board = new Board();

	private List<Move> moves = new ArrayList<Move>();

	private Player currenPlayer;
	private Player winner;

	public Player nextRound() {
		if (this.currenPlayer == null) {
			this.currenPlayer = roundFirstPlayer();
		} else {
			this.currenPlayer = this.currenPlayer == playerA ? playerB
					: playerA;
		}
		return this.currenPlayer;
	}

	private Player roundFirstPlayer() {
		Random random = new Random();
		int result = random.nextInt(10) % 2;
		if (result == 0) {
			return playerB;
		}
		return playerA;
	}

	public BoardResult start(SimpMessagingTemplate template) throws Exception {
		BoardResult win = BoardResult.CONTINUE;
		while (getMoves().size() < 100) {
			Player Player = nextRound();
			Move move = Player.getMove(board);
			getMoves().add(move);

			if (move.isValid(board)) {
				Thread.sleep(1000);
				win = board.move(move, Player);
				if (win == BoardResult.WINNER) {
					this.winner = Player;
					break;
				} else if (win == BoardResult.DRAW) {
					break;
				}
			} else {
				move.setComment("Invalid");
			}
			template.convertAndSend("/topic/move", move);

		}
		return win;
	}

	public Fight(Player playerA, Player playerB) {
		this.playerA = playerA;
		this.playerB = playerB;
	}

	public void setPlayerA(Player playerA) {
		this.playerA = playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Player playerB) {
		this.playerB = playerB;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Player getPlayerA() {
		return playerA;
	}

	@Override
	public int hashCode() {
		return this.playerA.hashCode() + this.playerB.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Fight fight = (Fight) obj;
		return (this.playerA.equals(fight.playerA) && this.playerB
				.equals(fight.playerB))
				|| (this.playerA.equals(fight.playerB) && this.playerA
						.equals(fight.playerB));
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

}
