package com.jts.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class RequestResponseLoggingFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization code if needed
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(httpRequest);

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		CustomHttpServletResponseWrapper responseWrapper = new CustomHttpServletResponseWrapper(httpResponse);

		// Log request body
		logger.info("Request Body: {}", requestWrapper.getBody());
		// Proceed with the filter chain
		chain.doFilter(requestWrapper, responseWrapper);
		// Log response body
		byte[] responseBody = responseWrapper.getResponseBody();
		logger.info("Response Body: {}", new String(responseBody, StandardCharsets.UTF_8));

		// Copy response body to original output stream
		responseWrapper.copyBodyToOriginalOutputStream();
	}

	@Override
	public void destroy() {
		// Cleanup code if needed
	}
}
