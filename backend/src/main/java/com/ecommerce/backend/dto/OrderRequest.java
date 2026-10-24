package com.ecommerce.backend.dto;

public class OrderRequest {
	private Long packageId;
	private Integer quantity;
	private String contactPhone;
	private String contactEmail;
	private String contactAddress;
	private String itemDescription;

	public OrderRequest() {}

	public OrderRequest(Long packageId, Integer quantity, String contactPhone, String contactEmail, String contactAddress, String itemDescription) {
		this.packageId = packageId;
		this.quantity = quantity;
		this.contactPhone = contactPhone;
		this.contactEmail = contactEmail;
		this.contactAddress = contactAddress;
		this.itemDescription = itemDescription;
	}

	public Long getPackageId() { return packageId; }
	public void setPackageId(Long packageId) { this.packageId = packageId; }
	public Integer getQuantity() { return quantity; }
	public void setQuantity(Integer quantity) { this.quantity = quantity; }
	public String getContactPhone() { return contactPhone; }
	public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
	public String getContactEmail() { return contactEmail; }
	public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
	public String getContactAddress() { return contactAddress; }
	public void setContactAddress(String contactAddress) { this.contactAddress = contactAddress; }
	public String getItemDescription() { return itemDescription; }
	public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
}
