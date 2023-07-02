package microservicesesofii.productservice.service;

import jakarta.persistence.EntityNotFoundException;
import microservicesesofii.productservice.dto.ProductDTO;
import microservicesesofii.productservice.model.Product;
import microservicesesofii.productservice.repository.ProductRepository;
import microservicesesofii.productservice.service.exceptions.ResourceNotFoundException;
import microservicesesofii.productservice.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private Product product;
    private ProductDTO productDTO;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 2L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        page = new PageImpl<>(List.of(product));

        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        when(productRepository.getReferenceById(existingId)).thenReturn(product);
        when(productRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        doNothing().when(productRepository).deleteById(existingId);

        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
    }

    @Test
    public void getAllProductsPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0, 6);

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertNotNull(result);
        verify(productRepository).findAll(pageable);
    }

    @Test
    public void getProductByIdShouldReturnAProductDTOWhenIdExists() {

        ProductDTO result = productService.getProductById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        verify(productRepository).findById(existingId);
    }

    @Test
    public void getProductByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(nonExistingId);
        });
        verify(productRepository).findById(nonExistingId);
    }

    @Test
    public void insertProductShouldReturnAProductDTO() {

        ProductDTO result = productService.insertProduct(productDTO);

        Assertions.assertNotNull(result);
    }

    @Test
    public void updateProductShouldReturnAProductDTOWhenIdExists() {

        ProductDTO result = productService.updateProduct(existingId, productDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingId, result.getId());
        verify(productRepository).save(product);
    }

    @Test
    public void updateProductShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(nonExistingId, productDTO);
        });
    }

    @Test
    public void deleteProductShouldDoNothingWhenIdExists() {

        Assertions.assertDoesNotThrow(() -> {
            productService.deleteProduct(existingId);
        });

        verify(productRepository).deleteById(existingId);
    }

    @Test
    public void deleteProductShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(nonExistingId);
        });

        verify(productRepository).deleteById(nonExistingId);
    }
}
