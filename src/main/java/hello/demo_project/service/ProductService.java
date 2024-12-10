package hello.demo_project.service;

import hello.demo_project.domain.product.Product;
import hello.demo_project.domain.product.ProductDto;
import hello.demo_project.domain.product.ProductRepository;
import hello.demo_project.domain.product.ProductReq;
import hello.demo_project.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProduct(long id) throws DataNotFoundException {
        Product product =productRepository.getProductsByProductId(id)
                .orElseThrow(() -> new DataNotFoundException("product not found"));

        log.info("product : {}", product);

        return new ProductDto(product.getProductId(), product.getName(), product.getPrice(), product.getScope_Avg(),
                product.getImage_path(), product.getProduct_TypeId(), product.getStock());
    }

    public List<ProductDto> getProductList() {
        List<Product> products = productRepository.findAll();
        log.info("product : {}", products);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(new ProductDto(product.getProductId(), product.getName(),product.getPrice(), product.getScope_Avg(),
                    product.getImage_path(), product.getProduct_TypeId(), product.getStock()));
        }

        return productDtos;
    }


    public void addProduct(ProductReq req) {
        Product product = new Product(req.getName(), req.getPrice(), req.getScope_Avg(), req.getImage_path(), req.getProduct_TypeId(), req.getStock());
    }
}
