package pl.com.stream.threadanalyzer.tictactoe;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.com.stream.threadanalyzer.tictactoe.Board.BoardResult;

@Component
public class TicTacToeRoundScheduler {
	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	TicTacToeStore store;

	@Scheduled(cron = "0 * * * * *")
	public synchronized void execute() throws Exception {
		template.convertAndSend("/topic/players", store.getPlayers());
		Set<Fight> createPairsForFight = store.createPairsForFight();
		for (Fight fight : createPairsForFight) {
			template.convertAndSend("/topic/fight", fight);
			Thread.sleep(1000);
			System.out.println(fight.getPlayerA() + " " + fight.getPlayerB());

			BoardResult start = fight.start(template);
			if (start == BoardResult.WINNER) {
				fight.getWinner().setWins(fight.getWinner().getWins() + 1);
			} else if (start == BoardResult.DRAW) {
				fight.getPlayerA().setWins(fight.getPlayerA().getWins() + 1);
				fight.getPlayerB().setWins(fight.getPlayerB().getWins() + 1);
			}
			System.out.println("WInner " + start + " " + fight.getWinner());

			// List<Move> moves = fight.getMoves();
			// for (Move move : moves) {
			// System.out.println(move.getOwner().getName() + ": "
			// + move.getX() + " " + move.getY() + " "
			// + move.getComment());
			// }
		}
		template.convertAndSend("/topic/players", store.getPlayers());
	}
}
