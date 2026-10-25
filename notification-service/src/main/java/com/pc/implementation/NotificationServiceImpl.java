package com.pc.implementation;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pc.payload.request.EmailNotificationRequest;
import com.pc.service.INotificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService{
	
	private final JavaMailSender mailSender;

	@Override
	public void sendEmail(EmailNotificationRequest request) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(request.getTo());
		mail.setSubject(request.getSubject());
		mail.setText(request.getMessage());
		mailSender.send(mail);
	}
	
}
