package by.eco_house.config;

import by.eco_house.service.AuctionService;
import by.eco_house.service.CategoryService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestControllerContext {

    @Bean
    public AuctionService auctionService() {
        return Mockito.mock(AuctionService.class);
    }

    @Bean
    public CategoryService categoryService() {
        return Mockito.mock(CategoryService.class);
    }

}
