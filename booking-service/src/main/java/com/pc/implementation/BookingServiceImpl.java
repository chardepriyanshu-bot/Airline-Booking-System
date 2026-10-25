package com.pc.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.pc.client.IFlightScheduleClient;
import com.pc.client.INotificationClient;
import com.pc.client.IUserClient;
import com.pc.dto.UserResponse;
import com.pc.enums.BookingStatus;
import com.pc.enums.ScheduleStatus;
import com.pc.exception.BookingNotFoundByReferenceException;
import com.pc.exception.BookingNotFoundException;
import com.pc.mapper.BookingMapper;
import com.pc.model.Booking;
import com.pc.payload.request.BookingRequest;
import com.pc.payload.request.EmailNotificationRequest;
import com.pc.payload.response.BookingResponse;
import com.pc.payload.response.FlightScheduleResponse;
import com.pc.repository.IBookingRepository;
import com.pc.service.IBookingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {
	
	private final IBookingRepository bookingRepository;
	private final IFlightScheduleClient flightScheduleClient;
	private final IUserClient userClient;
	private final INotificationClient notificationClient;
	
	@Override
	@Transactional
	public BookingResponse createBooking(BookingRequest request) {

	    FlightScheduleResponse schedule;

	    try {
	        schedule = flightScheduleClient.getScheduleById(
	                request.getScheduleId());
	    } catch (feign.FeignException.NotFound ex) {
	        throw new ResponseStatusException( HttpStatus.NOT_FOUND,
	                "Flight schedule not found: " + request.getScheduleId());
	    } catch (feign.FeignException ex) {
	        throw new ResponseStatusException( HttpStatus.BAD_GATEWAY,
	                "Unable to validate flight schedule at this time.");
	    }

	    if (schedule == null) {
	        throw new ResponseStatusException(
	                HttpStatus.BAD_GATEWAY,
	                "Flight Schedule Service returned an empty response.");
	    }

	    if (schedule.getScheduleStatus() != ScheduleStatus.SCHEDULED) {
	        throw new ResponseStatusException(
	                HttpStatus.CONFLICT,
	                "Flight schedule is not available for booking.");
	    }

	    if (schedule.getDepartureDateTime() == null
	            || !schedule.getDepartureDateTime().isAfter(LocalDateTime.now())) {
	        throw new ResponseStatusException(
	                HttpStatus.CONFLICT,
	                "Cannot book a flight that has already departed.");
	    }

	    BigDecimal totalAmount = schedule.getBaseFare()
	            .multiply(BigDecimal.valueOf(request.getPassengerCount()));

	    Booking booking = Booking.builder()
	            .bookingReference(generateBookingReference())
	            .userId(request.getUserId())
	            .scheduleId(schedule.getScheduleId())
	            .passengerCount(request.getPassengerCount())
	            .totalAmount(totalAmount)
	            .bookingStatus(BookingStatus.PENDING)
	            .build();

	    Booking savedBooking = bookingRepository.save(booking);

	    return BookingMapper.toResponse(savedBooking);
	}

	@Override
	@Transactional(readOnly = true)
	public BookingResponse getBookingById(Long bookingId) {
		
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
				-> new BookingNotFoundException("Booking does not exist"));
		
		return BookingMapper.toResponse(booking);
	}

	@Override
	@Transactional(readOnly = true)
	public BookingResponse getBookingByReference(String bookingReference) {
		
		Booking booking =bookingRepository.findByBookingReference(bookingReference).orElseThrow(()
				-> new BookingNotFoundByReferenceException("Booking not found with reference "+bookingReference));
		
		return  BookingMapper.toResponse(booking);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BookingResponse> getBookingsByUserId(Long userId) {

	    return bookingRepository.findByUserId(userId)
	            .stream()
	            .map(BookingMapper::toResponse)
	            .toList();
	}

	@Override
	@Transactional
	public BookingResponse cancelBooking(Long bookingId) {
		
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(()
				-> new BookingNotFoundException("Booking does not exist"));
		
		 if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
		        throw new IllegalStateException(
		                "Booking is already cancelled.");
		 }
		 
		 booking.setBookingStatus(BookingStatus.CANCELLED);
		 
		 Booking updatedBooking = bookingRepository.save(booking);
		 
		 sendBookingCancellationEmail(updatedBooking);

		 return BookingMapper.toResponse(updatedBooking);
	}
	
	private static String generateBookingReference() {
        return "AB" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 10)
                .toUpperCase();
    }
	
	@Override
	@Transactional
	public BookingResponse confirmBookingAfterPayment(Long bookingId) {

	    Booking booking = bookingRepository.findById(bookingId)
	            .orElseThrow(() ->
	                    new BookingNotFoundException("Booking does not exist"));

	    if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
	        throw new ResponseStatusException(
	                HttpStatus.CONFLICT,
	                "Cancelled booking cannot be confirmed.");
	    }

	    if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
	        return BookingMapper.toResponse(booking);
	    }

	    booking.setBookingStatus(BookingStatus.CONFIRMED);

	    Booking updatedBooking = bookingRepository.save(booking);
	    
	    sendBookingConfirmationEmail(updatedBooking);

	    return BookingMapper.toResponse(updatedBooking);
	    
	}
	
	private void sendBookingConfirmationEmail(Booking booking) {

	    try {
	        UserResponse user = userClient.getUserById(booking.getUserId());

	        EmailNotificationRequest request =
	                EmailNotificationRequest.builder()
	                        .to(user.getEmail())
	                        .subject("Booking Confirmed - Airline Booking System")
	                        .message(
	                                "Hello " + user.getFullName() + ",\n\n"
	                                + "Your flight booking has been confirmed!\n\n"
	                                + "Booking Reference: "
	                                + booking.getBookingReference() + "\n"
	                                + "Booking ID: " + booking.getUserId() + "\n"
	                                + "Passengers: " + booking.getPassengerCount() + "\n"
	                                + "Total Amount: " + booking.getTotalAmount() + "\n"
	                                + "Status: CONFIRMED\n\n"
	                                + "Thank you for choosing our Airline Booking System."
	                        )
	                        .build();

	        notificationClient.sendEmail(request);

	    } catch (Exception e) {
	        System.err.println(
	                "Booking confirmation email failed: " + e.getMessage()
	        );
	    }
	}
	

	private void sendBookingCancellationEmail(Booking booking) {

    try {
        var user = userClient.getUserById(booking.getUserId());

        EmailNotificationRequest request =
                EmailNotificationRequest.builder()
                        .to(user.getEmail())
                        .subject("Booking Cancelled - Airline Booking System")
                        .message(
                                "Hello " + user.getFullName() + ",\n\n"
                                + "Your flight booking has been cancelled.\n\n"
                                + "Booking Reference: "
                                + booking.getBookingReference() + "\n"
                                + "Booking ID: " + booking.getUserId() + "\n"
                                + "Booking Status: CANCELLED\n"
                                + "Booking Amount: " + booking.getTotalAmount() + "\n\n"
                                + "If you have already paid, please check your "
                                + "refund status with our support team.\n\n"
                                + "Thank you."
                        )
                        .build();

        notificationClient.sendEmail(request);

    } catch (Exception e) {
        System.err.println(
                "Booking cancellation email failed: " + e.getMessage()
        );
    }
}

}
