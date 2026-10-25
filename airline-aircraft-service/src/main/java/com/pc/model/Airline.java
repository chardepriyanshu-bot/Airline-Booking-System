package com.pc.model;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import com.pc.emmedable.Support;
import com.pc.enums.AirlineStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Airline_tb")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Airline {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long airlineId;
	
	@Column(unique=true, nullable=false)
	private String iataCode;
	
	@Column(unique=true, nullable=false)
	private String icaoCode;
	
	@Column(nullable = false)
	private Long ownerId;
	
	@Column(nullable=false)
	private String name;
	
	private String alias;
	
	private String loginUrl;
	
	private String website;
	
	@Enumerated(EnumType.STRING)
	private AirlineStatus status=AirlineStatus.ACTIVE;
	
	private String alliance;
	
	private Long headquaterCityId;
	
	@Embedded
	private Support support;
	
	private Long updateById;
	
	@Column(nullable=false, updatable = false)
	@CreatedDate
	private Instant createdAt;
	
	@Column(nullable=false)
	@LastModifiedDate
	private Instant updateAt;
	
}
