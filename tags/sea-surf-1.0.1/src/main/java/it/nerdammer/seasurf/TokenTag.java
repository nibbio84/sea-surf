package it.nerdammer.seasurf;

import it.nerdammer.seasurf.config.Preferences;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TokenTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	
	private String var;
	
	
	@Override
	public int doStartTag() throws JspException {
		
		HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
		
		Preferences prefs = ConfigHandler.getConfig().getPreferences();
		String token = TokenManager.getStoredToken(req, prefs);
		
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
	
}
