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
		String paramKey = getRequestKeyName(prefs);
		String token = req.getParameter(paramKey);
		return token;
	}
	
	public static String getStoredToken(HttpServletRequest req, Preferences prefs) {
		if(prefs==null || "SESSION".equals(prefs.getTokenStorage())) {
			String value = getTokenFromSession(req, prefs);
			return value;
		} else if("COOKIE".equals(prefs.getTokenStorage())) {
			String value = getTokenFromCookie(req, prefs);
			return value;
		} else {
			throw new IllegalArgumentException("Unknown token storage: " + prefs.getTokenStorage());
		}
	}
	
	public static String getTokenFromCookie(HttpServletRequest req, Preferences prefs) {
		String nameOnStorage = getStoreKeyName(prefs);
		String value = getTokenFromCookie(req, nameOnStorage);
		return value;
	}
	
	public static String getTokenFromCookie(HttpServletRequest req, String nameOnStorage) {
		Cookie[] cookies = req.getCookies();
		String value = null;
		if(cookies!=null) {
			for(Cookie c : cookies) {
				if(nameOnStorage.equals(c.getName())) {
					value = c.getValue();
					break;
				}
			}
		}
		return value;
	}
	
	public static String getTokenFromSession(HttpServletRequest req, Preferences prefs) {
		String nameOnStorage = getStoreKeyName(prefs);
		String value = getTokenFromSession(req, nameOnStorage);
		return value;
	}
	
	public static String getTokenFromSession(HttpServletRequest req, String nameOnStorage) {
		String value = (String) req.getSession().getAttribute(nameOnStorage);
		return value;
	}
	
	public static void refreshTokens(HttpServletRequest req, HttpServletResponse res, SecurityTokenConstraints constrs, Preferences prefs) {
		if(constrs!=null && constrs.getSecurityTokenConstraint().size()>0) {
			if(prefs==null || "SESSION".equals(prefs.getTokenStorage())) {
				refreshTokenOnSession(req, res, prefs);
			} else if("COOKIE".equals(prefs.getTokenStorage())) {
				refreshTokenOnCookie(req, res, prefs);
			} else {
				throw new IllegalArgumentException("Unknown token storage: " + prefs.getTokenStorage());
			}
		}
	}
	
	public static void refreshTokenOnCookie(HttpServletRequest req, HttpServletResponse res, Preferences prefs) {
		String value = getTokenFromCookie(req, prefs);
		if(value==null) {
			value = newRandomToken(prefs);
			Cookie c = new Cookie(getStoreKeyName(prefs), value);
			c.setPath(req.getContextPath());
			c.setMaxAge(-1);
			res.addCookie(c);
		}
	}
	
	public static void refreshTokenOnSession(HttpServletRequest req, HttpServletResponse res, Preferences prefs) {
		String value = getTokenFromSession(req, prefs);
		if(value==null) {
			value = newRandomToken(prefs);
			req.getSession(true).setAttribute(getStoreKeyName(prefs), value);
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
	
	public static String getStoreKeyName(Preferences prefs) {
		if(prefs==null || prefs.getTokenNameOnStorage()==null) {
			return DEFAULT_STORE_KEY_NAME;
		}
		return prefs.getTokenNameOnStorage();
	}
	
	public static String getRequestKeyName(Preferences prefs) {
		if(prefs==null || prefs.getTokenParameterName()==null) {
			return DEFAULT_REQUEST_KEY_NAME;
		}
		return prefs.getTokenParameterName();
	}
	
	public static int getTokenLength(Preferences prefs) {
		if(prefs==null || prefs.getTokenLength()==null) {
			return DEFAULT_TOKEN_LENGTH;
		}
		return prefs.getTokenLength();
	}
	
	
}
