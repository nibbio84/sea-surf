package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.RefererConstraints;
import it.nerdammer.seasurf.config.RequestOrigin;
import it.nerdammer.seasurf.config.RequestTypeCollection;
import it.nerdammer.seasurf.config.SeaSurfConfig;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		if(!(req instanceof HttpServletRequest && resp instanceof HttpServletResponse)) {
			chain.doFilter(req, resp);
			return;
		}
		
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		SeaSurfConfig config = ConfigHandler.getConfig();
		
		// Token security
		
		// Referer security
		RefererConstraints refConstraints = config.getRefererConstraints();
		if(refConstraints!=null) {
			RequestTypeCollection include = refConstraints.getInclude();
			RequestTypeCollection exclude = refConstraints.getExclude();
			boolean matches = MatchingUtils.resourceMatches(httpReq, include, exclude);
			
			if(matches) {
				RequestOrigin includeOrigin = refConstraints.getIncludeOrigin();
				RequestOrigin excludeOrigin = refConstraints.getExcludeOrigin();
				boolean matchesOrigin = MatchingUtils.originMatches(httpReq, includeOrigin, excludeOrigin);
				
				if(!matchesOrigin) {
					block(httpReq, httpResp, "Request origin not allowed for the requested page");
					return;
				}
			}
		}
		
		
		chain.doFilter(req, resp);
	}
	
	protected void block(HttpServletRequest req, HttpServletResponse res, String cause) {
		
	}
	
	public void destroy() {
	}
	
	public void init(FilterConfig config) throws ServletException {
		ConfigHandler.init(); // initializes the configuration
	}
	
}
