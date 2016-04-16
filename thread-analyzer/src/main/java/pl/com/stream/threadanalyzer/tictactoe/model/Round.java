package pl.com.stream.threadanalyzer.tictactoe.model;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import pl.com.stream.threadanalyzer.tictactoe.TicTacToeStore;
import pl.com.stream.threadanalyzer.tictactoe.model.Board.BoardResult;

@Service
public class Round {

	@Autowired
	TicTacToeStore store;
	@Autowired
	private SimpMessagingTemplate template;

	private Integer round = 1;
	private Fight currentFight;

	public void startNextRound() throws Exception {
		template.convertAndSend("/topic/round", round);
		Set<Fight> createPairsForFight = store.createPairsForFight();
		for (Fight fight : createPairsForFight) {
			currentFight = fight;
			template.convertAndSend("/topic/fight", fight);

			BoardResult start = fight.start(template);
			if (start == BoardResult.WINNER) {
				fight.getWinner().setWins(fight.getWinner().getWins() + 1);
				fight.getLoser().setLoses(fight.getLoser().getLoses() + 1);
			} else if (start == BoardResult.DRAW) {
				fight.getPlayerA().setDraws(fight.getPlayerA().getDraws() + 1);
				fight.getPlayerB().setDraws(fight.getPlayerB().getDraws() + 1);
			}
			System.out.println("WInner " + start + " " + fight.getWinner());
		}
		template.convertAndSend("/topic/players", store.getPlayers());
		round++;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Fight getCurrentFight() {
		return currentFight;
	}

	public void setCurrentFight(Fight currentFight) {
		this.currentFight = currentFight;
	}
}
