package com.jts.logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final OutputStream originalOutputStream;

	public CustomHttpServletResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		this.originalOutputStream = response.getOutputStream();
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return new ServletOutputStream() {
			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setWriteListener(WriteListener writeListener) {
				// Not needed for this example
			}

			@Override
			public void write(int b) throws IOException {
				outputStream.write(b);
			}
		};
	}

	public byte[] getResponseBody() {
		return outputStream.toByteArray();
	}

	public void copyBodyToOriginalOutputStream() throws IOException {
		originalOutputStream.write(outputStream.toByteArray());
	}
}
