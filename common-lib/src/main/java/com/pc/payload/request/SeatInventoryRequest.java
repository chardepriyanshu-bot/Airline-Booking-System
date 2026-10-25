
package com.pc.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInventoryRequest {

    @NotNull(message = "Schedule ID is required")
    @Positive(message = "Schedule ID must be positive")
    private Long scheduleId;
}

