// Design class send the jwt token to the client and proceed the task.

package com.pc.config;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignAuthInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		var attributes = RequestContextHolder.getRequestAttributes();
		if (attributes instanceof ServletRequestAttributes servletAttributes) {
			String authHeader = servletAttributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
			if (authHeader != null) {
				template.header(HttpHeaders.AUTHORIZATION, authHeader);
			}
		}
	}
}