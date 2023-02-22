package com.corsojava.pizzeria.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
	
	@GetMapping()
	public String handleError(HttpServletRequest request) {
		Object errCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (errCode != null) {
			int code = Integer.parseInt(errCode.toString());
			if (code == HttpStatus.NOT_FOUND.value()) { // 
				return "error-404";
			}
		}
		return "error";
	}
}