package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;
import it.nerdammer.seasurf.config.SecurityTokenConstraint;

import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class TokenManager {
	
	private static final String DEFAULT_KEY_NAME = "seasurf-token";
	private static final int DEFAULT_TOKEN_LENGTH = 20;
	
	public static String getToken(HttpServletRequest req, HttpServletResponse res, SecurityTokenConstraint constr, Preferences prefs) {
		if("COOKIE".equals(constr.getTokenStorage())) {
			Cookie[] cookies = req.getCookies();
			String value = null;
			for(Cookie c : cookies) {
				if(getKeyName(constr).equals(c.getName())) {
					value = c.getValue();
					break;
				}
			}
			
			if(value==null) {
				value = newRandomToken(prefs);
				Cookie c = new Cookie(getKeyName(constr), value);
				c.setPath(req.getContextPath());
				c.setMaxAge(-1);
				res.addCookie(c);
			}
			
			return value;
		} else if("SESSION".equals(constr.getTokenStorage())) {
			String value = (String) req.getSession().getAttribute(getKeyName(constr));
			if(value==null) {
				value = newRandomToken(prefs);
				req.getSession(true).setAttribute(getKeyName(constr), value);
			}
			return value;
		} else {
			throw new IllegalArgumentException("Unknown token storage: " + constr.getTokenStorage());
		}
	}
	
	
	public static String newRandomToken(Preferences prefs) {
		
		SecureRandom rnd = new SecureRandom();
		int tokenLength = getTokenLength(prefs);
		byte[] bytes = new byte[tokenLength]; 
		rnd.nextBytes(bytes);
		
		StringBuilder bui = new StringBuilder();
		for(int i=0; i<bytes.length; i++) {
			bui.append(Integer.toHexString(0xFF & bytes[i]));
		}
		return bui.toString();
	}
	
	public static String getKeyName(SecurityTokenConstraint constr) {
		if(constr==null || constr.getTokenName()==null) {
			return DEFAULT_KEY_NAME;
		}
		return constr.getTokenName();
	}
	
	public static int getTokenLength(Preferences prefs) {
		if(prefs==null || prefs.getTokenLength()==null) {
			return DEFAULT_TOKEN_LENGTH;
		}
		return prefs.getTokenLength();
	}
	
	
}
