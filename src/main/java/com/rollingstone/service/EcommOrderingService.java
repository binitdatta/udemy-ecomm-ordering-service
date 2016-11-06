package com.rollingstone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rollingstone.dao.jpa.EcommOrderRepository;
import com.rollingstone.domain.Order;
import com.rollingstone.domain.Product;
import com.rollingstone.domain.User;
import com.rollingstone.domain.OrderLineItem;

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
    
    @Autowired
   	private UserClient userClient;

       @Autowired
   	private ProductClient productClient;

    public EcommOrderingService() {
    }

   

    @Transactional
    public Order createOrder(Order order) throws Exception {
    	Order orderCreated = null;
    	boolean areOrderedItemsValid = true;
    	log.info("In service create");
    	if (order != null && order.getUser() != null){
    		log.info("In service create"+ order.getUser().getId());
    		if (userClient == null){
        		log.info("In userServiceClient null got user");
    		}
    		else {
    			log.info("In userServiceClient not null got user");
    		}
    		
    		User user = userClient.getUser((new Long(order.getUser().getId())));
    		if (user != null){
        		log.info("In service got user"+user.getId());
				log.info("In Service size of cart :"+order.getOrderItems().size());

        		for (OrderLineItem cartItem : order.getOrderItems()){
    				log.info("Inside cart loop");
        			if (cartItem.getProduct() != null){
        				log.info("In service Product is not null");
        				Product product  = productClient.getProduct(new Long(cartItem.getProduct().getId())); 
        				if (product == null){
        	        		log.info("In service did not get product");
        	        		areOrderedItemsValid = false;
        				}else {
        					log.info("In Service Valid product");
        				}
        			}else {
        				log.info("In service Product is null");
        			}
        		}
    			if (areOrderedItemsValid){
    				orderCreated = orderRepository.save(order);
    			}
    		}else {
    			throw new Exception("Invalid USer");
    		}
    	}
        return orderCreated;
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
