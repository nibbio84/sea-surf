package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Methods;
import it.nerdammer.seasurf.config.RequestType;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class MatchingUtils {

	public static Logger logger = Logger.getLogger(MatchingUtils.class);
	
	public static boolean matches(HttpServletRequest request, RequestType requestType) {
		Methods methods = requestType.getMethods();
		if(!matchesMethods(request, methods)) {
			return false;
		}
		
		String pattern = requestType.getPattern();
		if(!matchesUrlPattern(request, pattern)) {
			return false;
		}
		
		return true;
	}
	
	public static boolean matchesMethods(HttpServletRequest request, Methods methods) {
		if(methods!=null) {
			List<String> methodsList = methods.getMethod();
			if(!methodsList.contains(request.getMethod().toUpperCase().trim())) {
				return false;
			}
		}
		
		// method matches or methods definition is not present
		
		return true;
	}
	
	public static boolean matchesUrlPattern(HttpServletRequest request, String pattern) {
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
		
		String requestString = request.getRequestURI().substring(request.getContextPath().length());
		
		boolean matches = pat.matcher(requestString).matches();
		return matches;
	}
	
}
