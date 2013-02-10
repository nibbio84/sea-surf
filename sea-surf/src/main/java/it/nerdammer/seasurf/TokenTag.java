package it.nerdammer.seasurf;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TokenTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	
	private String storage;
	private String nameOnStorage;
	private String var;
	
	
	@Override
	public int doStartTag() throws JspException {
		
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		
		String token;
		if("COOKIE".equalsIgnoreCase(storage)) {
			token = TokenManager.getTokenFromCookie(req, nameOnStorage);
		} else if("SESSION".equalsIgnoreCase(storage)) {
			token = TokenManager.getTokenFromSession(req, nameOnStorage);
		} else {
			throw new IllegalArgumentException("Unknown storage: " + storage + " - Only SESSION or COOKIE can be accepted");
		}
		
		if(token!=null) {
			if(var==null) {
				try {
					pageContext.getOut().println(token);
				} catch(IOException e) {
					throw new JspException(e);
				}
			} else {
				pageContext.setAttribute(var, token);
			}
		}
		
		return SKIP_BODY;
	}
	
	public void setVar(String var) {
		this.var = var;
	}
	
	public String getVar() {
		return var;
	}
	
	public void setNameOnStorage(String nameOnStorage) {
		this.nameOnStorage = nameOnStorage;
	}
	
	public String getNameOnStorage() {
		return nameOnStorage;
	}
	
	public String getStorage() {
		return storage;
	}
	
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
}
