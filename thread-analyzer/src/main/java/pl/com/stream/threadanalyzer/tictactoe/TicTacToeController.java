package pl.com.stream.threadanalyzer.tictactoe;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.com.stream.threadanalyzer.tictactoe.model.Fight;
import pl.com.stream.threadanalyzer.tictactoe.model.Player;
import pl.com.stream.threadanalyzer.tictactoe.model.Round;

@RestController
@CrossOrigin(origins = "*")
public class TicTacToeController {
	@Autowired
	private Round round;
	@Autowired
	TicTacToeStore store;

	@RequestMapping("/register")
	public void register(@RequestBody Player register) {
		System.out.println(register);
	}

	@RequestMapping("/getPlayers")
	public List<Player> getPlayers() {
		return store.getPlayers();
	}

	@RequestMapping("/getCurrentRound")
	public Integer getCurrentRound() {
		return round.getRound();
	}

	@RequestMapping("/getCurrentFight")
	public Fight getCurrentFight() {
		return round.getCurrentFight();
	}
}
