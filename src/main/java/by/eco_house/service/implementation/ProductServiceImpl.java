package by.eco_house.service.implementation;

import by.eco_house.entity.Product;
import by.eco_house.repository.ProductRepository;
import by.eco_house.service.ProductService;
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
 * Implementing the ProductService interface
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<Product> findAll() {
        logger.debug(messageSource.getMessage("service.product.find.all", null, Locale.getDefault()));
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        logger.debug(messageSource.getMessage("service.product.find.by.id", new Object[]{id}, Locale.getDefault()));
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        logger.debug(messageSource.getMessage("service.product.save", new Object[]{product.getName()}, Locale.getDefault()));
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        logger.debug(messageSource.getMessage("service.product.delete.by.id", new Object[]{id}, Locale.getDefault()));
        productRepository.deleteById(id);
    }
}
