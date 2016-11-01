package com.rollingstone.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rollingstone.domain.Order;



public interface EcommOrderRepository extends PagingAndSortingRepository<Order, Long> {
    Order findUserByRating(int rating);
    Page findAll(Pageable pageable);
}
