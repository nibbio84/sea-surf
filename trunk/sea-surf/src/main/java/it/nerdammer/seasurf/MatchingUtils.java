package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Methods;
import it.nerdammer.seasurf.config.RequestOrigin;
import it.nerdammer.seasurf.config.RequestType;
import it.nerdammer.seasurf.config.RequestTypeCollection;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;

public class MatchingUtils {

	public static Logger logger = Logger.getLogger(MatchingUtils.class);
	
	public static boolean originMatches(HttpServletRequest request, RequestOrigin include, RequestOrigin exclude) {
		return originMatches(request, include) && !originMatches(request, exclude);
	}
	
	public static boolean originMatches(HttpServletRequest request, RequestOrigin origin) {
		List<JAXBElement<String>> pageOrDomains = origin.getPageOrDomain();
		for(JAXBElement<String> pageOrDomain : pageOrDomains) {
			String type = pageOrDomain.getName().getLocalPart();
			String value = pageOrDomain.getValue();
			if("page".equals(type)) {
				if(originMatchesPage(request, value)) {
					return true;
				}
			} else if("domain".equals(type)) {
				if(originMatchesDomain(request, value)) {
					return true;
				}
			} else {
				throw new IllegalStateException("Unknown request origin type: " + type);
			}
		}
		
		return false;
	}
	
	public static boolean originMatchesDomain(HttpServletRequest request, String domain) {
		String referer = request.getHeader("Referer");
		if(referer==null) {
			return false;
		}
		URL url;
		try {
			url = new URL(referer);
		} catch(MalformedURLException e) {
			return false;
		}
		
		String host = url.getHost();
		boolean matches = stringMatches(host, domain);
		return matches;
	}
	
	public static boolean originMatchesPage(HttpServletRequest request, String page) {
		String referer = request.getHeader("Referer");
		if(referer==null) {
			return false;
		}
		boolean matches = stringMatches(referer, page);
		return matches;
	}
	
	public static boolean resourceMatches(HttpServletRequest request, RequestTypeCollection include,  RequestTypeCollection exclude) {
		return resourceMatches(request, include) && !resourceMatches(request, exclude);
	}
	
	public static boolean resourceMatches(HttpServletRequest request, RequestTypeCollection requestTypeCollection) {
		if(requestTypeCollection==null) {
			return false;
		}
		List<RequestType> reqsType = requestTypeCollection.getRequestType();
		for(RequestType reqType : reqsType) {
			if(resourceMatches(request, reqType)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean resourceMatches(HttpServletRequest request, RequestType requestType) {
		Methods methods = requestType.getMethods();
		if(!resourceMatchesMethods(request, methods)) {
			return false;
		}
		
		String pattern = requestType.getPattern();
		if(!resourceMatchesUrlPattern(request, pattern)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean resourceMatchesMethods(HttpServletRequest request, Methods methods) {
		if(methods!=null) {
			List<String> methodsList = methods.getMethod();
			if(!methodsList.contains(request.getMethod().toUpperCase().trim())) {
				return false;
			}
		}
		
		// method matches or methods definition is not present
		
		return true;
	}
	
	public static boolean resourceMatchesUrlPattern(HttpServletRequest request, String pattern) {
		String requestString = request.getRequestURI().substring(request.getContextPath().length());
		
		boolean matches = stringMatches(requestString, pattern);
		return matches;
	}
	
	public static boolean stringMatches(String str, String pattern) {
		StringTokenizer st = new StringTokenizer(pattern, "*", true);
		StringBuilder regexBuilder = new StringBuilder();
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			if(token.equals("*")) {
				regexBuilder.append("(.*)");
			} else {
				regexBuilder.append(Pattern.quote(token));
			}
		}
		
		Pattern pat;
		try {
			pat = Pattern.compile(regexBuilder.toString());
		} catch(PatternSyntaxException e) {
			logger.error("Invalid pattern, this should not happen", e);
			return false;
		}
		
		boolean matches = pat.matcher(str).matches();
		return matches;
	}
	
}
