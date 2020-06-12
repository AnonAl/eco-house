package by.eco_house.config;

import by.eco_house.repository.AuctionRepository;
import by.eco_house.service.AuctionService;
import by.eco_house.service.implementation.AuctionServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class TestServiceContext {

    @Bean
    public AuctionService auctionServiceImpl() {
        return new AuctionServiceImpl();
    }

    @Bean
    public AuctionRepository auctionRepository() {
        return Mockito.mock(AuctionRepository.class);
    }

}
