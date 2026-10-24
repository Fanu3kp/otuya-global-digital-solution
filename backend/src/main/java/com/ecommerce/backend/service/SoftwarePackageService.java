package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.SoftwarePackageRequest;
import com.ecommerce.backend.dto.SoftwarePackageResponse;
import com.ecommerce.backend.entity.SoftwarePackage;
import com.ecommerce.backend.repository.SoftwarePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoftwarePackageService {

	@Autowired
	private SoftwarePackageRepository softwarePackageRepository;

	public Page<SoftwarePackageResponse> getAllPackages(String search, String category, int page, int size) {
		Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
		Page<SoftwarePackage> packages = softwarePackageRepository.findPackages(search, category, pageable);
		return packages.map(this::toResponse);
	}

	public SoftwarePackageResponse getPackageById(Long id) {
		SoftwarePackage pack = softwarePackageRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Package not found"));
		return toResponse(pack);
	}

	public SoftwarePackageResponse createPackage(SoftwarePackageRequest request) {
		SoftwarePackage pack = new SoftwarePackage();
		pack.setTitle(request.getTitle());
		pack.setDescription(request.getDescription());
		pack.setPrice(request.getPrice());
		pack.setStock(request.getStock());
		pack.setCategory(request.getCategory());
		pack.setBrand(request.getBrand());
		pack.setThumbnail(request.getThumbnail());
		pack.setSku(request.getSku());
		pack.setWeight(request.getWeight());
		pack.setDiscountPercentage(request.getDiscountPercentage());
		pack.setRating(request.getRating());
		pack.setImages(request.getImages());
		SoftwarePackage saved = softwarePackageRepository.save(pack);
		return toResponse(saved);
	}

	public SoftwarePackageResponse updatePackage(Long id, SoftwarePackageRequest request) {
		SoftwarePackage pack = softwarePackageRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Package not found"));
		pack.setTitle(request.getTitle());
		pack.setDescription(request.getDescription());
		pack.setPrice(request.getPrice());
		pack.setStock(request.getStock());
		pack.setCategory(request.getCategory());
		pack.setBrand(request.getBrand());
		pack.setThumbnail(request.getThumbnail());
		pack.setSku(request.getSku());
		pack.setWeight(request.getWeight());
		pack.setDiscountPercentage(request.getDiscountPercentage());
		pack.setRating(request.getRating());
		pack.setImages(request.getImages());
		SoftwarePackage saved = softwarePackageRepository.save(pack);
		return toResponse(saved);
	}

	public SoftwarePackageResponse updateThumbnail(Long id, String thumbnail) {
		SoftwarePackage pack = softwarePackageRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Package not found"));
		pack.setThumbnail(thumbnail);
		pack.setImages(thumbnail != null ? List.of(thumbnail) : null);
		SoftwarePackage saved = softwarePackageRepository.save(pack);
		return toResponse(saved);
	}

	public void deletePackage(Long id) {
		softwarePackageRepository.deleteById(id);
	}

	private SoftwarePackageResponse toResponse(SoftwarePackage pack) {
		return new SoftwarePackageResponse(
			pack.getId(), pack.getTitle(), pack.getDescription(), pack.getPrice(), pack.getStock(),
			pack.getCategory(), pack.getBrand(), pack.getThumbnail(), pack.getSku(), pack.getWeight(),
			pack.getDiscountPercentage(), pack.getRating(), pack.getImages()
		);
	}
}
