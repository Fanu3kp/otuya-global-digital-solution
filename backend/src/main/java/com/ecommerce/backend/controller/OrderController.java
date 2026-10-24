package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.OrderRequest;
import com.ecommerce.backend.dto.OrderResponse;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = {"http://*", "https://*"})
@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			OrderResponse order = orderService.placeOrder(user.getId(), request.getPackageId(), request.getQuantity(),
				request.getContactPhone(), request.getContactEmail(), request.getContactAddress(), request.getItemDescription());
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> getMyOrders() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<OrderResponse> orders = orderService.getOrdersByUser(user.getId());
			return ResponseEntity.ok(orders);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}
}
