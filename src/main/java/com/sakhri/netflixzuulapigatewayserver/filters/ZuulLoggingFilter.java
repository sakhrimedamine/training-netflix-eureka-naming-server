package com.sakhri.netflixzuulapigatewayserver.filters;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class ZuulLoggingFilter extends ZuulFilter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		
		String text;
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		logger.info("{}",request.getRequestURI());
		if ("POST".equalsIgnoreCase(request.getMethod())) 
		{
		   try {
			text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			logger.info("{}",text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
