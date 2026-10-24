package com.ecommerce.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	public void sendOrderConfirmation(String toEmail, String orderTitle, Integer quantity, Double totalPrice, String orderId) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("Order Confirmation - Ecommerce App");
		message.setText("Dear Customer,\n\nYour order has been placed successfully.\n\n"
			+ "Order ID: " + orderId + "\n"
			+ "Package: " + orderTitle + "\n"
			+ "Quantity: " + quantity + "\n"
			+ "Total: $" + totalPrice + "\n"
			+ "Status: PENDING\n\n"
			+ "Thank you for shopping with us!");
		mailSender.send(message);
	}
}
