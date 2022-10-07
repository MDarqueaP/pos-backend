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

import ec.com.edimca.posbackend.model.OrderHeader;
import ec.com.edimca.posbackend.model.Product;
import ec.com.edimca.posbackend.repository.OrderRepository;
import ec.com.edimca.posbackend.repository.ProductRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderHeader> getAllOrders() {

        Iterable<OrderHeader> orderList = this.orderRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (CollectionUtils.isEmpty((List<OrderHeader>) orderList)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No orders found");
        }

        return (List<OrderHeader>) orderList;
    }

    @Transactional(readOnly = false)
    public Boolean newOrder(OrderHeader order) {

        Date date = new Date();

        order.setCreationDate(new Timestamp(date.getTime()));
        order.getDetail().setOrder(order);
        order.getDetail().setCreationDate(new Timestamp(date.getTime()));
        order.getProducts().forEach(orderProduct -> {
            orderProduct.setOrder(order);
            orderProduct.setCreationDate(new Timestamp(date.getTime()));
            Optional<Product> productFound = this.productRepository.findById(orderProduct.getProduct().getId());
            Short newStock = (short) (productFound.get().getStock() - orderProduct.getQuantity());
            productFound.get().setStock(newStock);
            this.productRepository.save(productFound.get());
        });

        this.orderRepository.save(order);

        return Boolean.TRUE;
    }

}
