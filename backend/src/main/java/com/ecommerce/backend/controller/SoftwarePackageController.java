package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.SoftwarePackageRequest;
import com.ecommerce.backend.dto.SoftwarePackageResponse;
import com.ecommerce.backend.service.SoftwarePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:4200", "http://localhost:8888"})
@RestController
@RequestMapping("/api/packages")
public class SoftwarePackageController {

	@Autowired
	private SoftwarePackageService softwarePackageService;

	@GetMapping
	public ResponseEntity<Page<SoftwarePackageResponse>> getAllPackages(
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String category,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {
		Page<SoftwarePackageResponse> packages = softwarePackageService.getAllPackages(search, category, page, size);
		return ResponseEntity.ok(packages);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SoftwarePackageResponse> getPackageById(@PathVariable Long id) {
		SoftwarePackageResponse pack = softwarePackageService.getPackageById(id);
		return ResponseEntity.ok(pack);
	}

	@PostMapping
	public ResponseEntity<?> createPackage(@RequestBody SoftwarePackageRequest request) {
		try {
			SoftwarePackageResponse pack = softwarePackageService.createPackage(request);
			return ResponseEntity.ok(pack);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PatchMapping("/{id}/thumbnail")
	public ResponseEntity<?> updateThumbnail(@PathVariable Long id, @RequestBody Map<String, String> request) {
		try {
			String thumbnail = request.get("thumbnail");
			SoftwarePackageResponse pack = softwarePackageService.updateThumbnail(id, thumbnail);
			return ResponseEntity.ok(pack);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePackage(@PathVariable Long id, @RequestBody SoftwarePackageRequest request) {
		try {
			SoftwarePackageResponse pack = softwarePackageService.updatePackage(id, request);
			return ResponseEntity.ok(pack);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePackage(@PathVariable Long id) {
		softwarePackageService.deletePackage(id);
		return ResponseEntity.ok(Map.of("message", "Package deleted"));
	}

	@GetMapping("/categories")
	public ResponseEntity<?> getCategories() {
		return ResponseEntity.ok(Map.of("categories", List.of("all", "POS", "Website", "ERP", "Apps")));
	}
}
