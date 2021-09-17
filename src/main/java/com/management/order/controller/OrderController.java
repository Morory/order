package com.management.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.order.model.Client;
import com.management.order.model.Order;
import com.management.order.model.OrderDetail;
import com.management.order.repository.ClientRepository;
import com.management.order.repository.OrderDetailRepository;
import com.management.order.repository.OrderRepository;
import com.management.order.security.service.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final OrderDetailRepository orderDetailRepository;

    @GetMapping("/number")
    public ResponseEntity<?> getOrderNumber(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Order getOrderNumber called");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(Calendar.getInstance().getTime());
        int cnt = orderRepository.findCountByUserIdAndToday(userDetails.getId(), today);
        String orderNumber = today.replace("-", "") + "-" + String.format("%03d", ++cnt);
        return new ResponseEntity<>(orderNumber, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Order getAllByUser called");
        try {
            List<Order> orders = new ArrayList<>();

            orderRepository.findAllByUserId(userDetails.getId()).forEach(orders::add);

            if(orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> parameters
            , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Order Create called");
        try {
            Optional<Client> client = clientRepository.findById(Long.parseLong(parameters.get("clientId").toString()));

            if(!client.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Order _order = Order.builder()
                    .user(userDetails.getUser())
                    .client(client.get())
                    .orderDate(dateParseOf(parameters.get("orderDate")))
                    .deliveryDate(dateParseOf(parameters.get("deliveryDate")))
                    .orderNumber(parameters.get("orderNumber").toString())
                    .title(parameters.get("title").toString())
                    .completed(Boolean.parseBoolean(parameters.get("completed").toString()))
                    .build();
            List<OrderDetail> list = new ObjectMapper().readValue(
                    parameters.get("list").toString(), new TypeReference<ArrayList<OrderDetail>>() {});
            for(OrderDetail orderDetail : list) {
                orderDetail.setOrder(_order);
            }
            _order.setOrderDetails(new HashSet<>(list));
            orderRepository.save(_order);
            return new ResponseEntity<>(_order, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/status/{id}")
    @Transactional
    public ResponseEntity<Order> updateStatusOfOrder(@PathVariable("id") long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Order updateStatusOfOrder called");
        try {
            Optional<Order> _order = orderRepository.findById(id);

            if(!_order.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Order order = _order.get();
            if(order.getUser().getId() != userDetails.getId() ) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            order.setDeleted(!order.isDeleted());

            return new ResponseEntity<>(orderRepository.save(order), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<HttpStatus> deleteAllByUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("Order deleteAllByUser called");
        try {
            List<Order> orders = orderRepository.findAllByUserIdAndDeletedTrue(userDetails.getId());

            for(Order order : orders) {
                orderDetailRepository.deleteAllByOrderId(order.getId());
                orderRepository.deleteById(order.getId());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Date dateParseOf(Object object) throws ParseException {
        if("".equals(object.toString())) {
            return null;
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").parse(object.toString());
        }
    }
}
