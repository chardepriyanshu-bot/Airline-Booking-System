package com.pc.model;

import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pc.emmedable.Address;
import com.pc.emmedable.GeoCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Airline_airport_tb")
public class Airport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false, length=3)
	private String iataCode;
	
	@Column(nullable = false)
	private String name;
	
	@Embedded
	private Address address;
	
	@Embedded
	private GeoCode geoCode;
	
	@Column(name="time_zone_id", length=50)
	private ZoneId timeZoneId;
	
	@ManyToOne
	@JsonIgnore
	private City city;
}
