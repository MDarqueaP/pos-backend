package ec.com.edimca.posbackend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.edimca.posbackend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAll(Sort sort);

}
