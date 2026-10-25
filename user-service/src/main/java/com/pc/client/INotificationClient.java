
package com.pc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pc.payload.request.EmailNotificationRequest;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface INotificationClient {

    @PostMapping("/api/notifications/email")
    public String sendEmail(@RequestBody EmailNotificationRequest request);
}
