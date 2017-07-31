package com.suncj.web;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GlobalExceptionHandler implements ErrorController {

	private static final String DEFAULT_ERROR_PATH = "/error";

	private static final String DEFAULT_403_PATH = "/403";

	private static final String DEFAULT_404_PATH = "/404";

	private static final String DEFAULT_500_PATH = "/500";

	@RequestMapping(value = DEFAULT_403_PATH)
	public String _403() {
		return "exception/403";
	}

	@RequestMapping(value = DEFAULT_404_PATH)
	public String _404() {
		return "exception/404";
	}

	@RequestMapping(value = DEFAULT_500_PATH)
	public String _500() {
		return "exception/500";
	}

	@RequestMapping(value = DEFAULT_ERROR_PATH)
	public String error() {
		return "exception/error";
	}

	@Override
	public String getErrorPath() {
		return DEFAULT_ERROR_PATH;
	}

}
