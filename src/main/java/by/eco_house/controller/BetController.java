package by.eco_house.controller;

import by.eco_house.entity.Bet;
import by.eco_house.service.AuctionService;
import by.eco_house.service.BetService;
import by.eco_house.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.Locale;

/**
 * Rest controller. Implement bet api to manage bets.
 * Map all /bets requests
 */
@RestController
@RequestMapping(value = "/api/bets")
public class BetController {

    @Autowired
    private BetService betService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(BetController.class);

    /**
     * Map /bets GET requests
     * Find all bets
     * @return - JSON with found bets
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity findAll() {
        logger.debug(messageSource.getMessage("controller.bet.get", null, Locale.getDefault()));
        return ResponseEntity.ok(betService.findAll());
    }

    /**
     * Map /bets?id= GET requests
     * Find bet by id
     * If bet not found - response with NotFound status
     * @param id
     * @return - JSON with found bet
     */
    @RequestMapping(params = "id", method = RequestMethod.GET)
    ResponseEntity findById(@RequestParam("id") Long id) {
        logger.debug(messageSource.getMessage("controller.bet.get.by.id", new Object[]{id}, Locale.getDefault()));
        if (betService.findById(id).isPresent()) {
            logger.debug(messageSource.getMessage("controller.bet.get.by.id.ok", new Object[]{id}, Locale.getDefault()));
            return ResponseEntity.ok(betService.findById(id).get());
        }
        logger.debug(messageSource.getMessage("controller.bet.error.bet.not.found", new Object[]{id}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Map /bets?auctionId= GET requests
     * Find bets by eco_house id
     * If eco_house not found - response with NotFound status
     * @param auctionId
     * @return - JSON with found bets
     */
    @RequestMapping(params = "auctionId", method = RequestMethod.GET)
    ResponseEntity findByAuctionId(@RequestParam("auctionId") Long auctionId) {
        logger.debug(messageSource.getMessage("controller.bet.get.by.auction.id", new Object[]{auctionId}, Locale.getDefault()));
        if (auctionService.findById(auctionId).isPresent()) {
            logger.debug(messageSource.getMessage("controller.bet.get.by.auction.id.ok", new Object[]{auctionId}, Locale.getDefault()));
            return ResponseEntity.ok(betService.findByAuctionId(auctionId));
        }
        logger.debug(messageSource.getMessage("controller.bet.get.by.auction.id.error", new Object[]{auctionId}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Map /bets?username= GET requests
     * Find bets by username
     * IF user not found - response with NotFound status
     * @param username
     * @return - JSON with found bets
     */
    @RequestMapping(params = "username", method = RequestMethod.GET)
    ResponseEntity findByUserName(@RequestParam("username") String username) {
        logger.debug(messageSource.getMessage("controller.bet.get.by.username", new Object[]{username}, Locale.getDefault()));
        if (userService.findByUserName(username).isPresent()) {
            logger.debug(messageSource.getMessage("controller.bet.get.by.username.ok", new Object[]{username}, Locale.getDefault()));
            return ResponseEntity.ok(betService.findByUserName(username));
        }
        logger.debug(messageSource.getMessage("controller.bet.get.by.username.error", new Object[]{username}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Map /bets POST requests
     * Save bet
     * If eco_house or user specified in body not found - response with UnprocessableEntity status
     * @param bet
     * @return - link to saved bet with JSON in body
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity save(@RequestBody Bet bet) {
        logger.info(messageSource.getMessage("controller.bet.post.save.bet", new Object[]{bet}, Locale.getDefault()));
        if(!auctionService.findById(bet.getAuction_id()).isPresent()
                || !userService.findByUserName(bet.getUser_name()).isPresent()) {
            logger.debug(messageSource.getMessage("controller.bet.post.save.bet.error", new Object[]{bet}, Locale.getDefault()));
            return ResponseEntity.unprocessableEntity().build();
        }

        Bet result = new Bet();

        result.setAuction(auctionService.findById(bet.getAuction_id()).get());
        result.setUser(userService.findByUserName(bet.getUser_name()).get());
        result.setBetTime(new Date());
        result.setPrice(bet.getPrice());

        result = betService.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("?id={id}")
                .buildAndExpand(result.getId()).toUri();

        logger.debug(messageSource.getMessage("controller.bet.post.save.bet.ok", new Object[]{result}, Locale.getDefault()));
        return ResponseEntity.created(location).body(result);
    }

    /**
     * Map /bets?delete= DELETE requests
     * Delete bet by id
     * If bet not found - response with NotFound status
     * @param id
     * @return - status Ok
     */
    @RequestMapping(params = "delete", method = RequestMethod.DELETE)
    ResponseEntity delete(@RequestParam("delete") Long id) {
        logger.info(messageSource.getMessage("controller.bet.delete.bet", new Object[]{id}, Locale.getDefault()));
        if (betService.findById(id).isPresent()) {
            betService.deleteById(id);
            logger.debug(messageSource.getMessage("controller.bet.delete.bet.ok", new Object[]{id}, Locale.getDefault()));
            return ResponseEntity.ok().build();
        }
        logger.debug(messageSource.getMessage("controller.bet.error.bet.not.found", new Object[]{id}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

}
