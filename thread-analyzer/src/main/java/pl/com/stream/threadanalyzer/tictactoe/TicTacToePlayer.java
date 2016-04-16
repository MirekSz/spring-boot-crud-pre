package pl.com.stream.threadanalyzer.tictactoe;

import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.com.stream.threadanalyzer.tictactoe.model.Board;
import pl.com.stream.threadanalyzer.tictactoe.model.Move;

@RestController
public class TicTacToePlayer {
	@RequestMapping(value = "/getNextMove", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Move register(@RequestBody Board register) {
		Random random = new Random();
		int x = random.nextInt(3) + 1;
		int y = random.nextInt(3) + 1;
		return new Move(x, y);
	}
}
