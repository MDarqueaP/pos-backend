package ec.com.edimca.posbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import ec.com.edimca.posbackend.model.OrderHeader;
import ec.com.edimca.posbackend.service.OrderService;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    public ResponseEntity<List<OrderHeader>> getAll() {
        List<OrderHeader> result = this.orderService.getAllOrders();
        return new ResponseEntity<List<OrderHeader>>(result, HttpStatus.OK);
    }

}
