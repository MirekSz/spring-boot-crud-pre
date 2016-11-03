package hello;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

public class Auction {
	private Long id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private BigDecimal minPrice;
	private BigDecimal currentPrice;
	private String currentWiner;
	private Date expireDate;

	public Auction(Long id, String name, String description, BigDecimal minPrice) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.minPrice = minPrice;
	}

	public Auction() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getCurrentWiner() {
		return currentWiner;
	}

	public void setCurrentWiner(String currentWiner) {
		this.currentWiner = currentWiner;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

}
