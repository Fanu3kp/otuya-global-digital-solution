package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.SoftwarePackage;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.OrderRepository;
import com.ecommerce.backend.repository.SoftwarePackageRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SoftwarePackageRepository softwarePackageRepository;

	@Autowired
	private EmailService emailService;

	@Transactional
	public OrderResponse placeOrder(Long userId, Long packageId, Integer quantity,
			String contactPhone, String contactEmail, String contactAddress, String itemDescription) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		SoftwarePackage pack = softwarePackageRepository.findById(packageId)
			.orElseThrow(() -> new RuntimeException("Package not found"));

		if (pack.getStock() == null || pack.getStock() < quantity) {
			throw new RuntimeException("Insufficient stock");
		}

		Double totalPrice = pack.getPrice() * quantity;

		pack.setStock(pack.getStock() - quantity);
		softwarePackageRepository.save(pack);

		Order order = new Order();
		order.setUser(user);
		order.setSoftwarePackage(pack);
		order.setQuantity(quantity);
		order.setTotalPrice(totalPrice);
		order.setStatus("PENDING");
		order.setContactPhone(contactPhone);
		order.setContactEmail(contactEmail);
		order.setContactAddress(contactAddress);
		order.setItemDescription(itemDescription);
		Order saved = orderRepository.save(order);

		try {
			emailService.sendOrderConfirmation(user.getEmail(), order.getSoftwarePackage().getTitle(), order.getQuantity(), order.getTotalPrice(), saved.getId().toString());
		} catch (Exception e) {
			// Email failure should not block order placement
			System.err.println("Failed to send order confirmation email: " + e.getMessage());
		}

		return toResponse(saved);
	}

	public List<OrderResponse> getOrdersByUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		return orderRepository.findByUser(user).stream()
			.map(this::toResponse)
			.collect(Collectors.toList());
	}

	private OrderResponse toResponse(Order order) {
		return new OrderResponse(
			order.getId(),
			order.getSoftwarePackage().getTitle(),
			order.getSoftwarePackage().getThumbnail(),
			order.getQuantity(),
			order.getTotalPrice(),
			order.getStatus(),
			order.getContactPhone(),
			order.getContactEmail(),
			order.getContactAddress(),
			order.getItemDescription(),
			order.getCreatedAt()
		);
	}
}
