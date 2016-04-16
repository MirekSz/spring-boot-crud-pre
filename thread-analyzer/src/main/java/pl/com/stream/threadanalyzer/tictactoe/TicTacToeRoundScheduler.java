package pl.com.stream.threadanalyzer.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pl.com.stream.threadanalyzer.tictactoe.model.Round;

@Component
public class TicTacToeRoundScheduler {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	Round round;

	@Scheduled(cron = "*/5 * * * * *")
	public synchronized void execute() throws Exception {
		round.startNextRound();
	}
}
