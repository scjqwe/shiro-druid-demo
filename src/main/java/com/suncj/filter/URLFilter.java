package com.suncj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * URL过滤类
 * 
 * @author scj
 *
 */
@Order(1)
@WebFilter(filterName = "URLFilter", urlPatterns = "/*")
public class URLFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(URLFilter.class);

	@Override
	public void destroy() {
		log.info("destroy filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("do filter");
		log.info("request addr:{}", request.getRemoteHost());
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		log.info("init filter");
	}

}
