package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "software_packages")
public class SoftwarePackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	private Double price;
	private Integer stock;
	private String category;
	private String brand;
	private String thumbnail;
	private String sku;
	private Double weight;
	private Double discountPercentage;
	private Double rating;

	@ElementCollection
	@Column(name = "image")
	private java.util.List<String> images;

	private LocalDateTime createdAt = LocalDateTime.now();

	public SoftwarePackage() {}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public Double getPrice() { return price; }
	public void setPrice(Double price) { this.price = price; }
	public Integer getStock() { return stock; }
	public void setStock(Integer stock) { this.stock = stock; }
	public String getCategory() { return category; }
	public void setCategory(String category) { this.category = category; }
	public String getBrand() { return brand; }
	public void setBrand(String brand) { this.brand = brand; }
	public String getThumbnail() { return thumbnail; }
	public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
	public String getSku() { return sku; }
	public void setSku(String sku) { this.sku = sku; }
	public Double getWeight() { return weight; }
	public void setWeight(Double weight) { this.weight = weight; }
	public Double getDiscountPercentage() { return discountPercentage; }
	public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }
	public Double getRating() { return rating; }
	public void setRating(Double rating) { this.rating = rating; }
	public java.util.List<String> getImages() { return images; }
	public void setImages(java.util.List<String> images) { this.images = images; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
