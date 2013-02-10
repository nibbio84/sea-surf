package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;
import it.nerdammer.seasurf.config.SecurityTokenConstraint;
import it.nerdammer.seasurf.config.SecurityTokenConstraints;

import java.security.SecureRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class TokenManager {
	
	public static final String DEFAULT_STORE_KEY_NAME = "seasurf-token";
	public static final String DEFAULT_REQUEST_KEY_NAME = "seasurf-token";
	public static final int DEFAULT_TOKEN_LENGTH = 30;
	
	public static String getTokenOnRequest(HttpServletRequest req, SecurityTokenConstraint constr, Preferences prefs) {
		String paramKey = getRequestKeyName(constr);
		String token = req.getParameter(paramKey);
		return token;
	}
	
	public static String getStoredToken(HttpServletRequest req, SecurityTokenConstraint constr, Preferences prefs) {
		if("COOKIE".equals(constr.getTokenStorage())) {
			String value = getTokenFromCookie(req, constr, prefs);
			return value;
		} else if("SESSION".equals(constr.getTokenStorage())) {
			String value = getTokenFromSession(req, constr, prefs);
			return value;
		} else {
			throw new IllegalArgumentException("Unknown token storage: " + constr.getTokenStorage());
		}
	}
	
	public static String getTokenFromCookie(HttpServletRequest req, SecurityTokenConstraint constr, Preferences prefs) {
		Cookie[] cookies = req.getCookies();
		String value = null;
		for(Cookie c : cookies) {
			if(getStoreKeyName(constr).equals(c.getName())) {
				value = c.getValue();
				break;
			}
		}
		return value;
	}
	
	public static String getTokenFromSession(HttpServletRequest req, SecurityTokenConstraint constr, Preferences prefs) {
		String value = (String) req.getSession().getAttribute(getStoreKeyName(constr));
		return value;
	}
	
	public static void refreshTokens(HttpServletRequest req, HttpServletResponse res, SecurityTokenConstraints constrs, Preferences prefs) {
		if(constrs!=null) {
			for(SecurityTokenConstraint c : constrs.getSecurityTokenConstraint()) {
				if("COOKIE".equals(c.getTokenStorage())) {
					refreshTokenOnCookie(req, res, c, prefs);
				} else if("SESSION".equals(c.getTokenStorage())) {
					refreshTokenOnSession(req, res, c, prefs);
				} else {
					throw new IllegalArgumentException("Unknown token storage: " + c.getTokenStorage());
				}
			}
		}
	}
	
	public static void refreshTokenOnCookie(HttpServletRequest req, HttpServletResponse res, SecurityTokenConstraint constr, Preferences prefs) {
		String value = getTokenFromCookie(req, constr, prefs);
		if(value==null) {
			value = newRandomToken(prefs);
			Cookie c = new Cookie(getStoreKeyName(constr), value);
			c.setPath(req.getContextPath());
			c.setMaxAge(-1);
			res.addCookie(c);
		}
	}
	
	public static void refreshTokenOnSession(HttpServletRequest req, HttpServletResponse res, SecurityTokenConstraint constr, Preferences prefs) {
		String value = getTokenFromSession(req, constr, prefs);
		if(value==null) {
			value = newRandomToken(prefs);
			req.getSession(true).setAttribute(getStoreKeyName(constr), value);
		}
	}
	
	public static String newRandomToken(Preferences prefs) {
		SecureRandom rnd = new SecureRandom();
		int tokenLength = getTokenLength(prefs);
		byte[] bytes = new byte[tokenLength/2]; // token length in bytes is half the size 
		rnd.nextBytes(bytes);
		String token = toHex(bytes);
		return token;
	}
	
	public static String toHex(byte[] array) {
		StringBuilder bui = new StringBuilder();
		for(int i=0; i<array.length; i++) {
			bui.append(pad(Integer.toHexString(0xFF & array[i]), 2));
		}
		return bui.toString();
	}
	
	public static String pad(String v, int length) {
		while(v.length()<length) {
			v = "0" + v;
		}
		return v;
	}
	
	public static String getStoreKeyName(SecurityTokenConstraint constr) {
		if(constr==null || constr.getTokenNameOnStorage()==null) {
			return DEFAULT_STORE_KEY_NAME;
		}
		return constr.getTokenNameOnStorage();
	}
	
	public static String getRequestKeyName(SecurityTokenConstraint constr) {
		if(constr==null || constr.getTokenParameterName()==null) {
			return DEFAULT_REQUEST_KEY_NAME;
		}
		return constr.getTokenParameterName();
	}
	
	public static int getTokenLength(Preferences prefs) {
		if(prefs==null || prefs.getTokenLength()==null) {
			return DEFAULT_TOKEN_LENGTH;
		}
		return prefs.getTokenLength();
	}
	
	
}
