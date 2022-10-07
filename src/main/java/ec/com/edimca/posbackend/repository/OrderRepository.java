package ec.com.edimca.posbackend.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.edimca.posbackend.model.OrderHeader;

public interface OrderRepository extends JpaRepository<OrderHeader, Integer> {

    List<OrderHeader> findAll(Sort sort);
    
}
