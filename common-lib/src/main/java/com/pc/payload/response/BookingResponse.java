package com.pc.payload.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pc.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long bookingId;

    private String bookingReference;

    private Long userId;

    private Long scheduleId;

    private Integer passengerCount;

    private BigDecimal totalAmount;

    private BookingStatus bookingStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}