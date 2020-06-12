package by.eco_house.controller;

import by.eco_house.entity.Category;
import by.eco_house.entity.Product;
import by.eco_house.service.CategoryService;
import by.eco_house.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;

/**
 * Rest controller. Implement product api to manage products.
 * Map all /products requests
 */
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * Map /products GET requests
     * Find all products
     * @return - JSON with found products
     */
    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity findAll() {
        logger.debug(messageSource.getMessage("controller.product.get", null, Locale.getDefault()));
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Map /products/id GET requests
     * Find product by id
     * If product not found - response with NotFound status
     * @param productId
     * @return - JSOn with found product
     */
    @RequestMapping(value = "/{productId:[\\d]+}", method = RequestMethod.GET)
    ResponseEntity findById(@PathVariable Long productId) {
        logger.debug(messageSource.getMessage("controller.product.get.by.id", new Object[]{productId}, Locale.getDefault()));
        if ((productService.findById(productId)).isPresent()) {
            logger.debug(messageSource.getMessage("controller.product.get.by.id.ok", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.ok(productService.findById(productId).get());
        }
        logger.debug(messageSource.getMessage("controller.product.error.product.not.found", new Object[]{productId}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

    /**
     * Map /products POST requests
     * Save product
     * If category specified in body not found - response with UnprocessableEntity status
     * @param product
     * @return - slink to saved product with JSON in body
     */
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity save(@RequestBody Product product) {
        logger.info(messageSource.getMessage("controller.product.save.product", new Object[]{product}, Locale.getDefault()));
        if (!categoryService.findByName(product.getCategory_name()).isPresent()) {
            logger.debug(messageSource.getMessage("controller.product.save.product.error", new Object[]{product}, Locale.getDefault()));
            return ResponseEntity.unprocessableEntity().build();
        }

        Product result = new Product();

        result.setName(product.getName());
        result.setCategory(new Category(product.getCategory_name()));
        result.setPrice(product.getPrice());
        result.setDescription(product.getDescription());

        result = productService.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{productId}")
                .buildAndExpand(result.getId()).toUri();

        logger.debug(messageSource.getMessage("controller.product.save.product.ok", new Object[]{result}, Locale.getDefault()));
        return ResponseEntity.created(location).body(result);
    }

    /**
     * Map /products/id DELETE requests
     * Delete product by id
     * If product not found - response with NotFound status
     * @param productId
     * @return - status Ok
     */
    @RequestMapping(value = "/{productId:[\\d]+}", method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable Long productId) {
        logger.info(messageSource.getMessage("controller.product.delete.product", new Object[]{productId}, Locale.getDefault()));
        if (productService.findById(productId).isPresent()) {
            productService.deleteById(productId);
            logger.debug(messageSource.getMessage("controller.product.delete.product.ok", new Object[]{productId}, Locale.getDefault()));
            return ResponseEntity.ok().build();
        }
        logger.debug(messageSource.getMessage("controller.product.error.bet.not.found", new Object[]{productId}, Locale.getDefault()));
        return ResponseEntity.notFound().build();
    }

}
