package pl.com.stream.threadanalyzer.tictactoe.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import pl.com.stream.threadanalyzer.tictactoe.MoveReader;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Player {
	private String name;
	private String host;
	@JsonIgnore
	private MoveReader moveReader;
	private Integer wins = 0;
	private Integer loses = 0;
	private Integer draws = 0;

	public Player() {
		this.moveReader = new MoveReader(this);
	}

	public Player(String name, String host) {
		this();
		this.name = name;
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public Move getMove(Board board) {
		try {
			return moveReader.getNextMove(board);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean equals(Object obj) {
		Player player = (Player) obj;
		return this.name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLoses() {
		return loses;
	}

	public void setLoses(Integer loses) {
		this.loses = loses;
	}

	public Integer getDraws() {
		return draws;
	}

	public void setDraws(Integer draws) {
		this.draws = draws;
	}
}
