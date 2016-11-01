package com.rollingstone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rollingstone.dao.jpa.EcommOrderRepository;
import com.rollingstone.domain.Order;

/*
 * Service class to do CRUD for User and Address through JPS Repository
 */
@Service
public class EcommOrderingService {

    private static final Logger log = LoggerFactory.getLogger(EcommOrderingService.class);

    @Autowired
    private EcommOrderRepository orderRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public EcommOrderingService() {
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrder(long id) {
        return orderRepository.findOne(id);
    }

    public void updateOrder(Order order) {
    	orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
    	orderRepository.delete(id);
    }

    //http://goo.gl/7fxvVf
    public Page<Order> getAllOrders(Integer page, Integer size) {
        Page pageOfOrders = orderRepository.findAll(new PageRequest(page, size));
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("com.rollingstone.getAll.largePayload");
        }
        return pageOfOrders;
    }
}
