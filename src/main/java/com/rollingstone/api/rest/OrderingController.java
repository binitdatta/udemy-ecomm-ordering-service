package com.rollingstone.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.domain.Order;
import com.rollingstone.exception.HTTP400Exception;
import com.rollingstone.service.EcommOrderingService;

/*
 * This is the OrderingController to be used from Front End through Service Discovery and Zull Proxy
 * 
 */
@RestController
@RequestMapping(value = "/orderingservice/v1/order")
public class OrderingController extends AbstractRestController {

    @Autowired
    private EcommOrderingService orderService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody Order order,
                                 HttpServletRequest request, HttpServletResponse response) {
        Order createdCart;
		try {
			createdCart = this.orderService.createOrder(order);
	        response.setHeader("Location", request.getRequestURL().append("/").append(createdCart.getId()).toString());
		} catch (Exception e) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Page<Order> getAllCarts(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.orderService.getAllOrders(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Order getCart(@PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Order order = this.orderService.getOrder(id);
        checkResourceFound(order);
        return order;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") Long id, @RequestBody Order order,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.orderService.getOrder(id));
        if (id != order.getId()) throw new HTTP400Exception("ID doesn't match!");
        this.orderService.updateOrder(order);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.orderService.getOrder(id));
        this.orderService.deleteOrder(id);
    }
}
