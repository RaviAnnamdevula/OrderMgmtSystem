package com.jocata.oms.service;

import com.jocata.oms.datamodel.um.entity.InventoryEntity;
import com.jocata.oms.datamodel.um.entity.ProductEntity;
import com.jocata.oms.datamodel.um.form.ProductRequest;
import com.jocata.oms.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "products")
    public List<ProductEntity> getAllProducts() {
        List<ProductEntity> p = productRepository.findAll();
        return p;
    }

    @CachePut(value = "products", key = "#result.productId")
    public ProductEntity addProduct(ProductRequest productRequest) {
        ProductEntity product = ProductEntity.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(Integer.valueOf(productRequest.getPrice()))
                .build();

        ProductEntity productEntity = productRepository.save(product);
        String str = "http://localhost:8082/inventory/warehouse/update?productId="+productEntity.getProductId()+"&quantity="+productRequest.getQuantity();

        restTemplate.put(str,null);


        return productEntity;
    }

    @CachePut(value = "products", key = "#id")
    public ProductEntity updateProduct(Integer id, ProductRequest productRequest) {
        Optional<ProductEntity> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            ProductEntity product = productOpt.get();
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(Integer.valueOf(productRequest.getPrice()));
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductEntity getByProductId(Integer id) {
        return productRepository.findByProductId(id);
    }
}
