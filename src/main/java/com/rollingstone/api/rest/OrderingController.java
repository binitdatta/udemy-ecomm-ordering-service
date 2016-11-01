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
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
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
        Order createdCart = this.orderService.createOrder(order);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdCart.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    //@ApiOperation(value = "Get a paginated list of all users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 30)")
    public
    @ResponseBody
    Page<Order> getAllCarts(//@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      //@ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.orderService.getAllOrders(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    //@ApiOperation(value = "Get a single User.", notes = "You have to provide a valid User ID.")
    public
    @ResponseBody
    Order getCart(//@ApiParam(value = "The ID of the User.", required = true)
                             @PathVariable("id") Long id,
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
    //@ApiOperation(value = "Update a User resource.", notes = "You have to provide a valid User ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUser(//@ApiParam(value = "The ID of the existing User resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody Order order,
                                 HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.orderService.getOrder(id));
        if (id != order.getId()) throw new HTTP400Exception("ID doesn't match!");
        this.orderService.updateOrder(order);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@ApiOperation(value = "Delete a User resource.", notes = "You have to provide a valid User ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUser(//@ApiParam(value = "The ID of the existing User resource.", required = true)
                                 @PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.orderService.getOrder(id));
        this.orderService.deleteOrder(id);
    }
}
