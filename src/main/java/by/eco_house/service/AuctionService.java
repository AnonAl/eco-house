package by.eco_house.service;

import by.eco_house.entity.Auction;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service for data management in a table "auctions"
 */
public interface AuctionService {

    /**
     * Find all auctions in DB
     * @return list of auctions
     */
    List<Auction> findAll();

    /**
     * Find eco_house in DB with ID
     * @param id
     * @return Optional of found result.
     */
    Optional<Auction> findById(Long id);

    /**
     * Find finished or not auctions
     * @param finished - boolean
     * @return list of found auctions
     */
    List<Auction> findFinished(Boolean finished);

    /**
     * Find auctions by category name
     * @param categoryName
     * @return list of found auctions
     */
    List<Auction> findByCategoryName(String categoryName);

    /**
     * Find auctions with search tag contains in product name
     * @param searchTag - case-sensitive
     * @return list of found auctions
     */
    List<Auction> findByProductNameContains(String searchTag);

    /**
     * Find auctions by owner username
     * @param userName
     * @return list of found auctions
     */
    List<Auction> findByUserName(String userName);

    /**
     * Find auctions what end before input date
     * @param date
     * @return list of found auctions
     */
    List<Auction> findByEndTimeLessThan(Date date);

    /**
     * Save eco_house to DB
     * @param auction
     * @return saved eco_house
     */
    Auction save(final Auction auction);

    /**
     * Delete eco_house by ID
     * @param id
     */
    void deleteById(Long id);

}
