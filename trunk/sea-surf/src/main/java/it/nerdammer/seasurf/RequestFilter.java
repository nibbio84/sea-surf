package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.SeaSurfConfig;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RequestFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		if(!(req instanceof HttpServletRequest)) {
			chain.doFilter(req, resp);
			return;
		}
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		SeaSurfConfig config = ConfigHandler.getConfig();
		
		// Token security
		
		// Referer security
		
		
		
		chain.doFilter(req, resp);
	}
	
	public void destroy() {
	}
	
	public void init(FilterConfig config) throws ServletException {
		ConfigHandler.init(); // initializes the configuration
	}
	
}
