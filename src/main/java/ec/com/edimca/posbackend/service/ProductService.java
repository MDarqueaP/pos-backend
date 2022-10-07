package ec.com.edimca.posbackend.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;

import ec.com.edimca.posbackend.model.Product;
import ec.com.edimca.posbackend.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {

        Iterable<Product> productList = this.productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (CollectionUtils.isEmpty((List<Product>) productList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }

        return (List<Product>) productList;
    }

    @Transactional(readOnly = true)
    public Product getProduct(int id) {

        Optional<Product> productFound = this.productRepository.findById(id);
        if (!productFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        return productFound.get();
    }

    @Transactional(readOnly = false)
    public Boolean newProduct(Product product) {

        Date date = new Date();

        product.setCreationDate(new Timestamp(date.getTime()));

        this.productRepository.save(product);

        return Boolean.TRUE;
    }

    @Transactional(readOnly = false)
    public Boolean updateProduct(int productId, Product product) {

        Date date = new Date();

        Optional<Product> productFound = this.productRepository.findById(productId);

        if (!productFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        productFound.get().setName(product.getName());
        productFound.get().setPrice(product.getPrice());
        productFound.get().setDescription(product.getDescription());
        productFound.get().setUseCases(product.getUseCases());
        productFound.get().setStock(product.getStock());
        productFound.get().setFormatAvailable(product.getFormatAvailable());
        productFound.get().setUpdateDate(new Timestamp(date.getTime()));

        this.productRepository.save(productFound.get());

        return Boolean.TRUE;
    }

    @Transactional(readOnly = false)
    public Boolean deleteProduct(int productId) {

        Optional<Product> productFound = this.productRepository.findById(productId);

        if (!productFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        this.productRepository.delete(productFound.get());

        return Boolean.TRUE;
    }

}
