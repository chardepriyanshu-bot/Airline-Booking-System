package com.pc.service;

import com.pc.payload.request.EmailNotificationRequest;

public interface INotificationService {
	
	public void sendEmail(EmailNotificationRequest request);
}
