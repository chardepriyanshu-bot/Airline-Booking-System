package com.pc.mapper;

import com.pc.exception.BookingNotFoundException;
import com.pc.model.Booking;
import com.pc.payload.response.BookingResponse;

public class BookingMapper {

    public static BookingResponse toResponse(Booking booking) {
    	
    	if(booking==null) 
    		throw new BookingNotFoundException("Booking does not exist");
    	
        return BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .bookingReference(booking.getBookingReference())
                .userId(booking.getUserId())
                .scheduleId(booking.getScheduleId())
                .passengerCount(booking.getPassengerCount())
                .totalAmount(booking.getTotalAmount())
                .bookingStatus(booking.getBookingStatus())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
   
    
    
 
}