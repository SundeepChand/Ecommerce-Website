package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.respository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();

        List<ProductDto> productsResponse = new ArrayList<>();
        for (Product product: products) {
            productsResponse.add(getProductDtoFromProduct(product));
        }
        return productsResponse;
    }

    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription((productDto.getDescription()));
        product.setImageURL(productDto.getImageURL());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);

        productRepo.save(product);
    }

    public void updateProduct(ProductDto productDto, Category updatedCategory) throws Exception {
        if (productDto.getId().isEmpty()) {
            throw new Exception("id of the product to be updated not provided");
        }
        Optional<Product> oldProductOptional = productRepo.findById(productDto.getId().get());
        if (oldProductOptional.isEmpty()) {
            throw new Exception("product with given id does not exist");
        }
        Product oldProduct = oldProductOptional.get();
        if (productDto.getName() != null) {
            oldProduct.setName(productDto.getName());
        }
        if (productDto.getDescription() != null) {
            oldProduct.setDescription(productDto.getDescription());
        }
        if (productDto.getImageURL() != null) {
            oldProduct.setImageURL(productDto.getImageURL());
        }
        if (productDto.getPrice() != 0.0d) {
            oldProduct.setPrice(productDto.getPrice());
        }
        if (updatedCategory != null) {
            oldProduct.setCategory(updatedCategory);
        }
        productRepo.save(oldProduct);
    }

    private ProductDto getProductDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryId(product.getCategory().getId());
        return productDto;
    }
}
