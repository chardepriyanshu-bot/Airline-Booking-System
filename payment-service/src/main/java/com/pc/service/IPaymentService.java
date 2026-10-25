package com.pc.service;

import java.util.List;

import com.pc.payload.request.PaymentRequest;
import com.pc.payload.request.PaymentVerificationRequest;
import com.pc.payload.response.PaymentResponse;
import com.pc.payload.response.RazorpayOrderResponse;


public interface IPaymentService {

    public RazorpayOrderResponse initiatePayment(PaymentRequest request);

    public PaymentResponse getPaymentById(Long paymentId);

    public List<PaymentResponse> getPaymentsByBookingId(Long bookingId);

    public List<PaymentResponse> getPaymentsByUserId(Long userId);
    
    public PaymentResponse verifyPayment(PaymentVerificationRequest request);
}
