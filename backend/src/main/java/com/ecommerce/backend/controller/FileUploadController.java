package com.ecommerce.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class FileUploadController {

	@Value("${file.upload.dir}")
	private String uploadDir;

	@Value("${file.upload.url}")
	private String uploadUrl;

	@PostMapping("/product-image")
	public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Please select a file to upload"));
			}

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path targetLocation = uploadPath.resolve(uniqueFileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			String fileDownloadUri = uploadUrl + uniqueFileName;

			Map<String, String> response = new HashMap<>();
			response.put("fileName", uniqueFileName);
			response.put("fileDownloadUri", fileDownloadUri);
			response.put("size", String.valueOf(file.getSize()));

			return ResponseEntity.ok(response);
		} catch (IOException e) {
			return ResponseEntity.badRequest().body(Map.of("error", "Could not upload file: " + e.getMessage()));
		}
	}

	@GetMapping("/{fileName:.+}")
	public ResponseEntity<Resource> serveFile(@PathVariable String fileName) {
		try {
			Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {
				String contentType = "application/octet-stream";
				try {
					contentType = Files.probeContentType(filePath);
				} catch (IOException e) {
					// ignore
				}
				return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (MalformedURLException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
