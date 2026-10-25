package com.pc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Airline_city_tb")
public class City {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String cityCode;
	
	@Column(nullable = false)
	private String countryName;
	
	@Column(nullable = false)
	private String countryCode;
	
	@Column(nullable = false)
	private String regionCode;
	
	@Column(nullable = false)
	private String timeZoneOfSet;
}
