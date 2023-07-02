package microservicesesofii.productservice.tests;

import microservicesesofii.productservice.dto.ProductDTO;
import microservicesesofii.productservice.model.Product;

public class Factory {

    public static Product createProduct() {
        return new Product(1L, "TV", "Ótima TV", 1000.0,
                "https://img.com/img.png");
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product);
    }

}
