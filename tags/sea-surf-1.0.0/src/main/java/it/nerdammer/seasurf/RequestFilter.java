package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;
import it.nerdammer.seasurf.config.RefererConstraint;
import it.nerdammer.seasurf.config.RefererConstraints;
import it.nerdammer.seasurf.config.RequestOrigin;
import it.nerdammer.seasurf.config.RequestTypeCollection;
import it.nerdammer.seasurf.config.SeaSurfConfig;
import it.nerdammer.seasurf.config.SecurityTokenConstraint;
import it.nerdammer.seasurf.config.SecurityTokenConstraints;

import java.io.IOException;
import java.util.List;

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
		Preferences prefs = config.getPreferences();
		
		// Token security
		SecurityTokenConstraints tokConstraints = config.getSecurityTokenConstraints();
		if(tokConstraints!=null) {
			// Initialize the tokens on storage for subsequent requests
			TokenManager.refreshTokens(httpReq, httpResp, tokConstraints, prefs);
			
			List<SecurityTokenConstraint> constr = tokConstraints.getSecurityTokenConstraint();
			for(SecurityTokenConstraint con : constr) {
				RequestTypeCollection include = con.getInclude();
				RequestTypeCollection exclude = con.getExclude();
				boolean matches = MatchingUtils.resourceMatches(httpReq, include, exclude);
				
				if(matches) {
					boolean tokenMatches = MatchingUtils.tokenMatches(httpReq, con, prefs);
					if(!tokenMatches) {
						boolean present = (TokenManager.getTokenOnRequest(httpReq, con, prefs)!=null);
						if(present) {
							block(httpReq, httpResp, "Seasurf token does not match");
						} else {
							block(httpReq, httpResp, "Seasurf token not found on request parameter");
						}
						return;
					}
				}
			}
		}
		
		
		
		// Referer security
		RefererConstraints refConstraints = config.getRefererConstraints();
		if(refConstraints!=null) {
			List<RefererConstraint> constrs = refConstraints.getRefererConstraint();
			for(RefererConstraint con : constrs) {
				RequestTypeCollection include = con.getInclude();
				RequestTypeCollection exclude = con.getExclude();
				boolean matches = MatchingUtils.resourceMatches(httpReq, include, exclude);
				
				if(matches) {
					RequestOrigin includeOrigin = con.getIncludeOrigin();
					RequestOrigin excludeOrigin = con.getExcludeOrigin();
					boolean blockIfMissing = MatchingUtils.originGetBlockIfMissingProperty(con);
					boolean matchesOrigin = MatchingUtils.originMatches(httpReq, includeOrigin, excludeOrigin, blockIfMissing);
					
					if(!matchesOrigin) {
						block(httpReq, httpResp, "Request origin not allowed for the requested page");
						return;
					}
				}
			}
			
		}
		
		
		chain.doFilter(req, resp);
	}
	
	protected void block(HttpServletRequest req, HttpServletResponse res, String cause) throws IOException {
		res.setStatus(403);
		res.getWriter().println("Blocked by seasurf");
	}
	
	public void destroy() {
	}
	
	public void init(FilterConfig config) throws ServletException {
		ConfigHandler.init(); // initializes the configuration
	}
	
}
