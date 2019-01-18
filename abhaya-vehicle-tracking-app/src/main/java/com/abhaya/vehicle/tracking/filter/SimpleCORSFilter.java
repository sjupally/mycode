package com.abhaya.vehicle.tracking.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class SimpleCORSFilter implements Filter 
{

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
  {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request = (HttpServletRequest) req;
    
   // response.setHeader(RestTemplateConstants.ALLOW_ORIGIN.getName(), RestTemplateConstants.ALLOW_ORIGIN.getValue());
    response.setHeader(RestTemplateConstants.ALLOW_METHODS.getName(), RestTemplateConstants.ALLOW_METHODS.getValue());
    response.setHeader(RestTemplateConstants.ALLOW_HEADERS.getName(), RestTemplateConstants.ALLOW_HEADERS.getValue());
    response.setHeader(RestTemplateConstants.ALLOW_CREDENTIALS.getName(), RestTemplateConstants.ALLOW_CREDENTIALS.getValue());
    
    
   if(!request.getMethod().equals("OPTIONS")){
    	chain.doFilter(req, res);
    }
  }

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }

}

