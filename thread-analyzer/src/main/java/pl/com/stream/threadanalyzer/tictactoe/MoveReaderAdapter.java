package pl.com.stream.threadanalyzer.tictactoe;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pl.com.stream.threadanalyzer.tictactoe.model.Board;
import pl.com.stream.threadanalyzer.tictactoe.model.Move;
import pl.com.stream.threadanalyzer.tictactoe.model.Player;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class MoveReaderAdapter extends HystrixCommand<Move> {

	private Player player;
	private Board board;
	private RestTemplate restTemplate;
	private String url;

	public MoveReaderAdapter(Board board, Player player) {
		super(
				Setter.withGroupKey(
						HystrixCommandGroupKey.Factory.asKey(player.getName()))
						.andCommandKey(
								HystrixCommandKey.Factory.asKey(player
										.getName()))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties
										.Setter()
										.withExecutionTimeoutInMilliseconds(
												5000)
										.withMetricsRollingPercentileWindowInMilliseconds(
												60 * 60 * 1000)
										.withMetricsRollingStatisticalWindowInMilliseconds(
												60 * 60 * 1000)));
		this.board = board;
		this.player = player;

		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().clear();
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.getObjectMapper().setVisibility(PropertyAccessor.ALL,
				Visibility.NONE);
		messageConverter.getObjectMapper().setVisibility(
				PropertyAccessor.FIELD, Visibility.ANY);
		restTemplate.getMessageConverters().add(messageConverter);
		url = "http://" + this.player.getHost() + "/getNextMove";
	}

	@Override
	protected Move run() {
		Move postForEntity = restTemplate.postForObject(url, this.board,
				Move.class);
		postForEntity.setOwner(player);
		return postForEntity;
	}

	@Override
	protected Move getFallback() {
		Throwable failedExecutionException = null;
		try {
			System.out.println("Events (so far) in Fallback: "
					+ getExecutionEvents());
			failedExecutionException = getFailedExecutionException();
			return new Move(
					"Backend failed " + failedExecutionException == null ? "Backend failed "
							: failedExecutionException.getMessage(),
					this.player);
		} catch (Exception e) {
			Move move = new Move("Backend failed ", this.player);
			return move;
		}
	}
}
