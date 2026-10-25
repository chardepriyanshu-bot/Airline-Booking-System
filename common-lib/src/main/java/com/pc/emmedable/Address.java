package com.pc.emmedable;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
	@NotBlank(message="street is required.")
	private String street;
	
	@NotBlank(message="postalCode is required")
	private String postalCode;
}
