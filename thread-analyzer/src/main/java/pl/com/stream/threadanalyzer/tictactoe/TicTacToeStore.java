package pl.com.stream.threadanalyzer.tictactoe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicTacToeStore {
	private List<Player> players = new ArrayList();

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public Set<Fight> createPairsForFight() {
		Set<Fight> result = new HashSet();

		for (Player playerA : players) {
			for (Player playerB : players) {
				if (playerA != playerB) {
					result.add(new Fight(playerA, playerB));
				}
			}
		}
		return result;
	}

	public static void main(String[] args) {
		TicTacToeStore ticTacToeStore = new TicTacToeStore();
		ticTacToeStore.addPlayer(new Player("Mirek", "localhost2:8080"));
		ticTacToeStore.addPlayer(new Player("Mirek 2", "localhost:8080"));

		Set<Fight> createPairsForFight = ticTacToeStore.createPairsForFight();
		for (Fight fight : createPairsForFight) {
			System.out.println(fight.getPlayerA() + " " + fight.getPlayerB());

			System.out.println("WInner " + fight.start() + " "
					+ fight.getWinner());

			List<Move> moves = fight.getMoves();
			for (Move move : moves) {
				System.out.println(move.getOwner().getName() + ": "
						+ move.getX() + " " + move.getY() + " "
						+ move.getComment());
			}
		}
	}
}
