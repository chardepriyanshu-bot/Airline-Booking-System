package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pc.payload.response.CityResponse;

@FeignClient(name = "city-service", url = "${city.service.url}")
public interface ICityServiceClient {
	
	@GetMapping("/api/cities/{cityId}")
	public CityResponse getCityById(@PathVariable Long cityId);
}
