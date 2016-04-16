package pl.com.stream.threadanalyzer.tictactoe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import pl.com.stream.threadanalyzer.tictactoe.model.Fight;
import pl.com.stream.threadanalyzer.tictactoe.model.Player;

@Component
public class TicTacToeStore {
	private List<Player> players = new ArrayList();

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

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

	@PostConstruct
	public void init() {
		addPlayer(new Player("Mirek", "localhost:8080"));
		addPlayer(new Player("Maja", "localhost:8080"));
		addPlayer(new Player("Kacper", "localhost:8080"));
		addPlayer(new Player("Dominika", "localhost:8080"));

	}
}
