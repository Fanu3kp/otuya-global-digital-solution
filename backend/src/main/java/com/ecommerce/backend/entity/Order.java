package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id", nullable = false)
	private SoftwarePackage softwarePackage;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private Double totalPrice;

	@Column(nullable = false)
	private String status = "PENDING";

	@Column(nullable = false)
	private String contactPhone;

	@Column(nullable = false)
	private String contactEmail;

	@Column(nullable = false)
	private String contactAddress;

	@Column(length = 1000)
	private String itemDescription;

	private LocalDateTime createdAt = LocalDateTime.now();

	public Order() {}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public User getUser() { return user; }
	public void setUser(User user) { this.user = user; }
	public SoftwarePackage getSoftwarePackage() { return softwarePackage; }
	public void setSoftwarePackage(SoftwarePackage softwarePackage) { this.softwarePackage = softwarePackage; }
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
