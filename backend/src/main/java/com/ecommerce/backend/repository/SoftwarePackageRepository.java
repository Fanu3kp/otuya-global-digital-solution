package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.SoftwarePackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoftwarePackageRepository extends JpaRepository<SoftwarePackage, Long> {

	@Query("SELECT p FROM SoftwarePackage p WHERE (:search IS NULL OR p.title ILIKE %:search%) AND (:category IS NULL OR p.category = :category)")
	Page<SoftwarePackage> findPackages(String search, String category, Pageable pageable);

	List<SoftwarePackage> findByCategory(String category);
	Optional<SoftwarePackage> findBySku(String sku);
	boolean existsBySku(String sku);
}
