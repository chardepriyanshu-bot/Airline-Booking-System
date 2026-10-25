package com.pc.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pc.payload.request.CityRequest;
import com.pc.payload.response.CityResponse;
import com.pc.service.ICityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
@Validated
public class CityController {
	
	private final ICityService cityService;
	
	@PostMapping("/create")
	public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest){
		return ResponseEntity.status(HttpStatus.CREATED).body(cityService.createCity(cityRequest));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CityResponse> getCityById(@PathVariable Long id){
		return ResponseEntity.ok(cityService.getCityById(id));
	}
	
	@GetMapping("/all")	
	public ResponseEntity<Page<CityResponse>> getAllCities(
			@RequestParam(defaultValue = "0") int page,			
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection
			){
		
		Sort sort=Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
		Pageable pageable=PageRequest.of(page, size, sort);
		
		return ResponseEntity.ok(cityService.getAllCities(pageable));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CityResponse> updateCity(@PathVariable Long id, @Valid @RequestBody CityRequest request){
		
		return ResponseEntity.ok(cityService.updateCity(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCity(@PathVariable Long id){
		cityService.deleteCity(id);
		return ResponseEntity.ok("City is successfully deleted.");
	}
	
	@GetMapping("/search")
	public ResponseEntity<Page<CityResponse>> searchCity(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page,			
			@RequestParam(defaultValue = "10") int size
			){
		Pageable pageable=PageRequest.of(page, size);
		return ResponseEntity.ok(cityService.searchCities(keyword, pageable));
	}
	
	@GetMapping("/country")
	public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(
			@RequestParam String countryCode,
			@RequestParam(defaultValue = "0") int page,			
			@RequestParam(defaultValue = "10") int size
			){
		Pageable pageable=PageRequest.of(page, size);
		return ResponseEntity.ok(cityService.getCitiesByCountryCode(countryCode, pageable));
	}
	
	@GetMapping("/exist/{cityCode}")
	public ResponseEntity<Boolean> checkCityExist(@PathVariable String cityCode){
		return ResponseEntity.ok(cityService.cityExists(cityCode.toUpperCase()));
	}
}
