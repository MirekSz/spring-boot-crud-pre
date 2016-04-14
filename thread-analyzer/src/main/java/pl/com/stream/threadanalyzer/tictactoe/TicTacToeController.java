package pl.com.stream.threadanalyzer.tictactoe;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicTacToeController {
	@RequestMapping("/register")
	public void register(@RequestBody Player register) {
		System.out.println(register);
	}
}
