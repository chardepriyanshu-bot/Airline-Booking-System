package com.pc.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSuccessEvent {
	private Long paymentId;
	private String email;
	private String fullName;
	private Long bookingId;
	private BigDecimal amount;
	private String status;
	private LocalDateTime paymentTime;
}
