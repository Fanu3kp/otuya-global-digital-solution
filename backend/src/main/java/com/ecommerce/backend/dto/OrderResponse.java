package com.ecommerce.backend.dto;

import java.time.LocalDateTime;

public class OrderResponse {
	private Long id;
	private String title;
	private String thumbnail;
	private Integer quantity;
	private Double totalPrice;
	private String status;
	private String contactPhone;
	private String contactEmail;
	private String contactAddress;
	private String itemDescription;
	private LocalDateTime createdAt;

	public OrderResponse() {}
	public OrderResponse(Long id, String title, String thumbnail, Integer quantity,
			Double totalPrice, String status, String contactPhone, String contactEmail, String contactAddress, String itemDescription,
			LocalDateTime createdAt) {
		this.id = id;
		this.title = title;
		this.thumbnail = thumbnail;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = status;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.contactAddress = contactAddress;
		this.itemDescription = itemDescription;
		this.createdAt = createdAt;
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getThumbnail() { return thumbnail; }
	public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	public Double getTotalPrice() { return totalPrice; }
	public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public String getContactPhone() { return contactPhone; }
	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
	public String getContactAddress() { return contactAddress; }
	public void setContactAddress(String contactAddress) { this.contactAddress = contactAddress; }
	public String getItemDescription() { return itemDescription; }
	public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
