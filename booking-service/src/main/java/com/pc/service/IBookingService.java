package com.pc.service;

import java.util.List;

import com.pc.payload.request.BookingRequest;
import com.pc.payload.response.BookingResponse;

public interface IBookingService {
	public BookingResponse createBooking(BookingRequest request);

	public BookingResponse getBookingById(Long bookingId);

	public BookingResponse getBookingByReference(String bookingReference);

	public List<BookingResponse> getBookingsByUserId(Long userId);

	public BookingResponse cancelBooking(Long bookingId);
	
	BookingResponse confirmBookingAfterPayment(Long bookingId);
}
