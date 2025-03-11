package com.jocata.oms.service;

import com.jocata.oms.datamodel.um.entity.ProductEntity;
import com.jocata.oms.datamodel.um.form.ProductRequest;
import com.jocata.oms.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity addProduct(ProductRequest productRequest) {
        ProductEntity product = ProductEntity.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(BigDecimal.valueOf(Long.parseLong(productRequest.getPrice())))
                .build();
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Integer id, ProductRequest productRequest) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            ProductEntity product = productOpt.get();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(BigDecimal.valueOf(Long.parseLong(productRequest.getPrice())));
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}