package com.pc.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

import com.pc.enums.FlightStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="airline_flight_tb")
@Builder
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long flightId;
	
	@Column(nullable = false)
	private String flightName;
	
	@Column(nullable = false)
	private Long airlineId;
	
	@Column(nullable = false)
	private Long aircraftId;
	
	@Column(nullable = false)
	private Long sourceAirportId;
	
	@Column(nullable = false)
	private Long destinationAirportId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FlightStatus flightStatus;
	
	//metadata
	@Version
	private Integer version;
	
	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
