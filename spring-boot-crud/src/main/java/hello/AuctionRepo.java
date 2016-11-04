package hello;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepo extends JpaRepository<Auction, Long> {
	@Query("FROM Auction a WHERE a.owner = ?#{principal.getUsername()}")
	List<Auction> getAllForCurrentUser();

	@Override
	// @Yours
	Auction findOne(Long id);
}
