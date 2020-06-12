package by.eco_house.service.implementation;

import by.eco_house.entity.Bet;
import by.eco_house.repository.BetRepository;
import by.eco_house.service.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Implementing the BetService interface
 */
@Service("betService")
@Transactional
public class BetServiceImpl implements BetService {

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(BetServiceImpl.class);

    @Override
    public List<Bet> findAll() {
        logger.debug(messageSource.getMessage("service.bet.find.all", null, Locale.getDefault()));
        return betRepository.findAll();
    }

    @Override
    public Optional<Bet> findById(Long id) {
        logger.debug(messageSource.getMessage("service.bet.find.by.id", new Object[]{id}, Locale.getDefault()));
        return betRepository.findById(id);
    }

    @Override
    public List<Bet> findByAuctionId(Long id) {
        logger.debug(messageSource.getMessage("service.bet.find.by.auction.id", new Object[]{id}, Locale.getDefault()));
        return betRepository.findByAuctionId(id);
    }

    @Override
    public List<Bet> findByUserName(String userName) {
        logger.debug(messageSource.getMessage("service.bet.find.by.user.name", new Object[]{userName}, Locale.getDefault()));
        return betRepository.findByUserUserName(userName);
    }

    @Override
    public Bet save(Bet bet) {
        logger.debug(messageSource.getMessage("service.bet.save", new Object[]{bet}, Locale.getDefault()));
        return betRepository.save(bet);
    }

    @Override
    public void deleteById(Long id) {
        logger.debug(messageSource.getMessage("service.bet.delete.by.id", new Object[]{id}, Locale.getDefault()));
        betRepository.deleteById(id);
    }
}
