package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "booking-service",
    url = "${booking.service.url}"
)
public interface IBookingServiceClient {

    @PatchMapping("/api/bookings/{bookingId}/confirm-payment")
    void confirmBookingAfterPayment(@PathVariable("bookingId") Long bookingId,
    		@RequestHeader("X-Internal-Token") String internalToken);
}