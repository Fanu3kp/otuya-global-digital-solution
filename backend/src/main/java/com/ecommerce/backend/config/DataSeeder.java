package com.ecommerce.backend.config;

import com.ecommerce.backend.entity.Role;
import com.ecommerce.backend.entity.SoftwarePackage;
import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.RoleRepository;
import com.ecommerce.backend.repository.SoftwarePackageRepository;
import com.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SoftwarePackageRepository softwarePackageRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		Role userRole = roleRepository.findByName("USER")
			.orElseGet(() -> roleRepository.save(new Role("USER")));
		Role adminRole = roleRepository.findByName("ADMIN")
			.orElseGet(() -> roleRepository.save(new Role("ADMIN")));

		if (!userRepository.existsByUsername("emilys")) {
			User emilys = new User("emilys", passwordEncoder.encode("emilyspass"), "emilys@example.com");
			emilys.setFirstName("Emily");
			emilys.setLastName("Stark");
			emilys.getRoles().add(userRole);
			userRepository.save(emilys);
		}
		if (!userRepository.existsByUsername("admin")) {
			User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@example.com");
			admin.getRoles().add(adminRole);
			userRepository.save(admin);
		}

		if (softwarePackageRepository.count() == 0) {
			String[] titles = {
				"POS Software Package",
				"Website Development Package",
				"ERP Business Central Package",
				"Mobile Apps Development Package",
				"POS Retail System",
				"E-Commerce Website Package",
				"ERP Cloud Solution",
				"Business Apps Suite"
			};
			String[] descriptions = {
				"Complete Point of Sale system for retail businesses",
				"Professional website design and development",
				"Enterprise Resource Planning with Microsoft Business Central",
				"Custom mobile application development for iOS and Android",
				"Advanced POS system with inventory management",
				"Full e-commerce website with payment integration",
				"Cloud-based ERP solution for business management",
				"Complete business applications suite"
			};
			Double[] prices = {499.99, 1499.99, 2999.99, 1999.99, 599.99, 1799.99, 3499.99, 2499.99};
			Integer[] stocks = {50, 30, 20, 40, 60, 25, 15, 35};
			String[] categories = {"POS", "Website", "ERP", "Apps", "POS", "Website", "ERP", "Apps"};
			String[] brands = {"TechPOS", "WebDev Pro", "Microsoft", "AppDev Studio", "RetailTech", "WebSolutions", "CloudERP", "BizApps"};
			String[] skus = {"POS-001", "WEB-001", "ERP-001", "APP-001", "POS-002", "WEB-002", "ERP-002", "APP-002"};

			for (int i = 0; i < titles.length; i++) {
				SoftwarePackage pack = new SoftwarePackage();
				pack.setTitle(titles[i]);
				pack.setDescription(descriptions[i]);
				pack.setPrice(prices[i]);
				pack.setStock(stocks[i]);
				pack.setCategory(categories[i]);
				pack.setBrand(brands[i]);
				pack.setThumbnail("http://localhost:8080/uploads/packages/package_" + (i + 1) + ".jpg");
				pack.setSku(skus[i]);
				pack.setWeight(1000.0);
				pack.setDiscountPercentage(0.0);
				pack.setRating(4.5);
				pack.setImages(java.util.List.of("http://localhost:8080/uploads/packages/package_" + (i + 1) + ".jpg"));
				softwarePackageRepository.save(pack);
			}
		}
	}
}
