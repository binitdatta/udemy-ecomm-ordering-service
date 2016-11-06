package com.rollingstone.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ecomm_order_item")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public
class OrderLineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Product product;

	@Column(nullable = false)
	private int quantity;
	
	@Column(nullable = false)
	private double unitPrice;

	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name = "order_id")
	@JsonBackReference
	Order order;
	
	public OrderLineItem(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public OrderLineItem(Long id, Product product, int quantity, double unitPrice) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "OrderLineItem [id=" + id + ", product=" + product + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + "]";
	}

	

}