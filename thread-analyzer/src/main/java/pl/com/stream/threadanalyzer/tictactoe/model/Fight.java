package pl.com.stream.threadanalyzer.tictactoe.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import pl.com.stream.threadanalyzer.tictactoe.model.Board.BoardResult;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Fight {

	private Player playerA;
	private Player playerB;

	@JsonIgnore
	private Board board = new Board();
	@JsonIgnore
	private List<Move> moves = new ArrayList<Move>();
	@JsonIgnore
	private Player currenPlayer;
	@JsonIgnore
	private Player winner;
	@JsonIgnore
	private Player loser;

	public Player nextPlayer() {
		if (this.currenPlayer == null) {
			this.currenPlayer = roundFirstPlayer();
		} else {
			this.currenPlayer = this.currenPlayer == playerA ? playerB : playerA;
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
			Player player = nextPlayer();
			Move move = player.getMove(board);
			getMoves().add(move);

			if (move.isValid(board)) {
				template.convertAndSend("/topic/move", move);
				win = board.move(move, player);
				if (win == BoardResult.WINNER) {
					this.winner = player;
					this.loser = nextPlayer();
					break;
				} else if (win == BoardResult.DRAW) {
					break;
				}
			} else {
				move.setComment("Invalid");
			}

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
		return (this.playerA.equals(fight.playerA) && this.playerB.equals(fight.playerB))
				|| (this.playerA.equals(fight.playerB) && this.playerA.equals(fight.playerB));
	}

	public List<Move> getMoves() {
		return moves;
	}

	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	public Player getLoser() {
		return loser;
	}

	public void setLoser(Player loser) {
		this.loser = loser;
	}

}
