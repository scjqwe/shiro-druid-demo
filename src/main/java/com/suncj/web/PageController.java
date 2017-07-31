package com.suncj.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 页面管理
 */

@Controller
@Api(value = "页面管理", description = "页面管理")
public class PageController {

	@ApiOperation(value = "认证中心首页")
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index() {
		return "index";

	}

}