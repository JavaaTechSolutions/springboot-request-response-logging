package com.jts.logging;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

	@GetMapping("/log")
	public String log() {
		return "Hello, World!";
	}
	
	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user) {
		return user;
	}
}
