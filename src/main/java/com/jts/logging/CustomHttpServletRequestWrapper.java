package com.jts.logging;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private final String body;

	public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(
				new InputStreamReader(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8))));
	}

	@Override
	public ServletInputStream getInputStream() {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// Not needed for this example
			}

			@Override
			public int read() {
				return byteArrayInputStream.read();
			}
		};
	}

	public String getBody() {
		return this.body;
	}
}
