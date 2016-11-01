package com.rollingstone.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/*
 * A User POJO serving as an Entity as well as a Data Transfer Object i.e DTO
 */
@Entity
@Table(name = "ecomm_order")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id",referencedColumnName="id")
	User user;

	@Column(nullable = false)
	private double orderTotalValue;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ORDER_DATE", unique = false, nullable = false, length = 10)
	private Date orderDate;

	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
	private Set<OrderLineItem> orderItems =  new HashSet<OrderLineItem>();;


	@Column()
	private int rating;

	public Order() {
	}

	

	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public double getOrderTotalValue() {
		return orderTotalValue;
	}



	public void setOrderTotalValue(double orderTotalValue) {
		this.orderTotalValue = orderTotalValue;
	}



	public Date getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}



	public Set<OrderLineItem> getOrderItems() {
		return orderItems;
	}



	public void setOrderItems(Set<OrderLineItem> orderItems) {
		this.orderItems = orderItems;
	}



	public int getRating() {
		return rating;
	}



	public void setRating(int rating) {
		this.rating = rating;
	}



	public Order(long id, User user, double orderTotalValue, Date orderDate, Set<OrderLineItem> orderItems,
			int rating) {
		super();
		this.id = id;
		this.user = user;
		this.orderTotalValue = orderTotalValue;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", orderTotalValue=" + orderTotalValue + ", orderDate="
				+ orderDate + ", orderItems=" + orderItems + ", rating=" + rating + "]";
	}

	


	

	
}
