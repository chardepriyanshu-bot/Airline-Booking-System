package com.pc.model;

import java.time.Instant;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import com.pc.enums.AircraftStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Aircraft_tb")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long aircraftId;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String model;
	
	@Column(nullable = false)
	private String manufacturer;
	
	@Column(nullable = false)
	@Builder.Default
	private Integer seatingCapactiy=0;
	
	@PositiveOrZero(message="Seat must be positive")
	@Builder.Default
	private Integer economySeats=0;
	
	@PositiveOrZero(message="Seat must be positive")
	@Builder.Default
	private Integer premiumEconomySeats=0;
		
	@PositiveOrZero(message="Seat must be positive")
	@Builder.Default
	private Integer businessSeats=0;
	
	@PositiveOrZero(message="Seat must be positive")
	@Builder.Default
	private Integer firstClassSeats=0;
	
	@PositiveOrZero(message="Range must be positive")
	private Integer rangeKm;
	
	@PositiveOrZero(message="Speed must be positive")
	private Integer cruisingSpeedkm;
	
	@PositiveOrZero(message="Altitude must be positive")
	private Double maxAltitudeKm;
	
	@Column(nullable = false)
	private LocalDate yearOfManufacture;
	
	@Enumerated(EnumType.STRING)
	private AircraftStatus aircraftStatus;
	
	@Builder.Default
	private Boolean isAvailable=true;
	
	@ManyToOne
	private Airline airline;
	
	private Long currentAirportId;
	
	@CreatedDate
	@Column(nullable=false)
	private Instant createdAt;
	
	@LastModifiedDate
	@Column(nullable=false)
	private Instant updateAt;
	
	private Integer getTotalSeats() {
		return economySeats+premiumEconomySeats+businessSeats+firstClassSeats;
	}
	
	private Boolean isOperation() {
		return AircraftStatus.ACTIVE.equals(aircraftStatus) && Boolean.TRUE.equals(isAvailable);
	}
	
	private LocalDate lastMaintainanceDate;
}
