package microservicesesofii.productservice.service;

import microservicesesofii.productservice.dto.ProductDTO;
import microservicesesofii.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        countTotalProducts = 12L;
    }

    @Test
    public void getAllProductsPagedShouldReturnAPageWhenPageZeroWithSizeTen() {

        Pageable pageable = PageRequest.of(0, 6);

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(6, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void getAllProductsPagedShouldReturnAnEmptyPageWhenPageDoesNotExist() {

        Pageable pageable = PageRequest.of(50, 6);

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getAllProductsPagedShouldReturnASortedPageWhenSortByName() {

        Pageable pageable = PageRequest.of(0, 6, Sort.by("name"));

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }

    @Test
    public void deleteProductShouldDeleteResourceWhenIdExists() {

        productService.deleteProduct(existingId);

        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }
}
